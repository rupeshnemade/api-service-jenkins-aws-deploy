region = "us-east-1"

remote_state_bucket   = "network-tf-state-us-east-1"

# ECS Variable

apiService_ECR_URL    = "960885402552.dkr.ecr.us-east-1.amazonaws.com/hotel/apiervice"

workerService_ECR_URL = "960885402552.dkr.ecr.us-east-1.amazonaws.com/hotel/workerservice"

image_tag             = "latest"

instance_type         = "t2.large"