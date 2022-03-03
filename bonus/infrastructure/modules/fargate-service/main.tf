resource "aws_alb_target_group" "this" {
  count = var.with_lb ? 1 : 0

  vpc_id               = data.aws_vpc.vpc.id
  target_type          = "ip"
  name                 = "${var.app_name}-${var.component_name}"
  port                 = 80
  protocol             = "HTTP"
  deregistration_delay = 10 # seconds

  health_check {
    path                = var.health_check_path
    interval            = 30 # seconds
    healthy_threshold   = 2
    unhealthy_threshold = 4
    timeout             = 5
    matcher             = "200"
  }
}

resource "aws_ecs_task_definition" "this" {
  family                   = "${var.app_name}-${var.component_name}"
  requires_compatibilities = ["FARGATE"]
  network_mode             = "awsvpc"
  cpu                      = var.task_cpu
  memory                   = var.task_memory

  execution_role_arn = data.aws_iam_role.ecs_task_execution.arn
  task_role_arn      = data.aws_iam_role.task.arn

  container_definitions = var.container_definition
}

resource "aws_ecs_service" "without_lb" {
  cluster     = data.aws_ecs_cluster.ecs_cluster.id
  launch_type = "FARGATE"

  name            = "${var.app_name}-${var.component_name}"
  task_definition = aws_ecs_task_definition.this.arn
  desired_count   = var.task_desired_count

  network_configuration {
    security_groups = [aws_security_group.allow_lb_on_task.id]
    subnets         = data.aws_subnet_ids.private.ids
  }
}

resource "aws_security_group" "allow_lb_on_task" {
  vpc_id      = data.aws_vpc.vpc.id
  name        = "allow-lb-on-${aws_ecs_task_definition.this.family}"
  description = "Allow the ALB to access the ECS task ${aws_ecs_task_definition.this.family}"
}

resource "aws_security_group_rule" "outbound" {
  security_group_id = aws_security_group.allow_lb_on_task.id
  type              = "egress"
  from_port         = "0"
  to_port           = "0"
  protocol          = "-1"
  cidr_blocks       = ["0.0.0.0/0"]
}

resource "aws_cloudwatch_log_group" "this" {
  name              = "${var.app_name}/${var.component_name}"
  retention_in_days = 3
}

output "target_group_arn" {
  value = concat(aws_alb_target_group.this.*.arn, tolist([""]))[0]
}

output "task_def_arn" {
  value = aws_ecs_task_definition.this.arn
}

output "log_group_name" {
  value = aws_cloudwatch_log_group.this.name
}
