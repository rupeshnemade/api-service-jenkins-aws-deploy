We are using Jenkins server hosted on AWS to deploy the infrastructure on AWS.
This Jenkins slave has all IAM permissions to create resources in AWS.

Terragrunt is a thin wrapper for Terraform that provides extra tools for keeping your Terraform configurations DRY, working with multiple Terraform modules, and managing remote state.

Terragrunt will use terraform code located at "https://github.com/rupeshnemade/api-service-aws-terraform.git".
All the dynamic input variables are present in "us_east_1_dev\account.tfvars' folder.

Terragrunt will maintain a terraform state file in S3 and state lock in DynamoDB for best practices. This information is given in "us_east_1_dev\terraform.tfvars" file.

Jenkins will execute below terragrung command to deploy teraform modules on AWS:-

  - terraform-plan:
	  terragrunt plan-all  --terragrunt-source-update --terragrunt-non-interactive --terragrunt-working-dir us_east_1/

  - terraform-apply:
	  terragrunt apply-all --terragrunt-source-update --terragrunt-non-interactive --terragrunt-working-dir us_east_1/
