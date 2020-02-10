
terraform-plan:
	terragrunt plan-all  --terragrunt-source-update --terragrunt-non-interactive --terragrunt-working-dir us_east_1/

terraform-apply:
	terragrunt apply-all --terragrunt-source-update --terragrunt-non-interactive --terragrunt-working-dir us_east_1/
