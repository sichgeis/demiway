module "bff" {
  source = "./modules/fargate-service"

  cluster_name = var.cluster_name

  app_name = var.app_name
  component_name = "bff"

  task_desired_count = "1"

  port = "5002"
  container_definition = data.template_file.container_definition_bff.rendered

  with_log_alarms = true

  disable_http_alarms = true
  health_check_path = "/bff/health"
}

data "template_file" "container_definition_bff" {
  template = file("bff-task-definition.json")

  vars = {
    name = "${var.app_name}-bff"
    image = "${var.ecr_endpoint}/bonus/bff:latest"
    log_group_region = var.region
    log_group_name = "${var.app_name}/bff"
    region = var.region
    allow_localhost_origin = true
  }
}

output "task_def_bff_arn" {
  value = module.bff.task_def_arn
}

