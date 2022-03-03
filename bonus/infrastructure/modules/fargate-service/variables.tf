variable "cluster_name" {}                    # Todo: should be removed in favour of environment_name after mayflower-staging is may-staging everywhere

variable "app_name" {}

variable "component_name" {}

variable "is_prod" { default = false }

variable "port" { default = -1 }
variable "task_cpu" { default = 256 }
variable "task_memory" { default = 512 }
variable "task_desired_count" { default = 1 }
variable "container_definition" {}

variable "with_log_alarms" { default = false }

variable "with_lb" { default = false }
variable "disable_http_alarms" {
  default     = false
  description = "Health checks and status code alarms are on by default if an ALB is attached (with_lb=true)"
}
variable "health_check_path" { default = "" }
variable "task_execution_role_name" { default = "EcsFargateTaskExecution" }
