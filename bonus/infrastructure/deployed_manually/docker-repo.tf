module "bff" {
  source = "../modules/ecr_repository"
  repository_name = "bonus/bff"
  ecr_account = "548449406202"
}
