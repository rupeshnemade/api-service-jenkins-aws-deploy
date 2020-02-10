terragrunt = {

  # Configure Terragrunt to automatically store tfstate files in an S3 bucket
  remote_state {
    backend = "s3"

    config {
      encrypt        = true
      bucket         = "hotelservice-us-east-1-tf"
      key            = "${path_relative_to_include()}/terraform.tfstate"
      region         = "us-east-1"
      dynamodb_table = "hotelservice-tfstate"
    }

    s3_bucket_tags = {
      Service = "hotelService"
      Terraform = true
    }

    dynamotable_tags = {
      Service = "hotelService"
      Terraform = true
    }
  }

  # Configure root level variables that all resources can inherit
  terraform {
    extra_arguments "bucket" {
      commands = [
        "${get_terraform_commands_that_need_vars()}",
      ]

      optional_var_files = [
        "${get_tfvars_dir()}/${find_in_parent_folders("account.tfvars", "ignore")}",
      ]
    }
  }
}

