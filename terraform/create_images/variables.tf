#terraform/create_images/variables.tf

variable "region" {
  type        = string
  description = "AWS region for the create_images module"
}

variable "instance_type_app" {
  description = "EC2 instance type for the Spring Boot application"
  type        = string
  default     = "t2.micro"
}

variable "instance_type_db" {
  description = "EC2 instance type for the PostgreSQL database"
  type        = string
  default     = "t3.medium"
}

variable "db_user" {
  description = "Username for the PostgreSQL database"
  type        = string
  default     = "appuser"
}

variable "db_password" {
  description = "Password for the PostgreSQL database"
  type        = string
  sensitive   = true
}

variable "spring_boot_app_git_repo" {
  description = "Git repository URL of the Spring Boot application"
  type        = string
  default     = "https://github.com/AngelosGi/citizen-registry.git"
}

variable "db_name" {
  description = "Name of the database"
  type        = string
  default     = "citizens"
}

variable "key_name" {
  description = "The name of the SSH key pair"
  type        = string
  default     = "cloud_test"
}

variable "private_key_path" {
  description = "Path to the private key file for SSH access"
  type        = string
  default     = "~/.ssh/cloud_test.pem"
}

variable app_sg_id {
  description = "The ID of the application security group"
  type = string
}
variable "db_sg_id" {
  description = "Security group ID for the DB instance (provided by networking module)"
  type        = string
}

variable "subnet_id" {
  description = "Subnet ID in which to launch the instance (provided by networking module)"
  type        = string
}
