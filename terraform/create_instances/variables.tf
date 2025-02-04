// terraform/create_instances/variables.tf

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

variable "db_name" {
  description = "Name of the PostgreSQL database"
  type        = string
  default     = "citizens"
}

variable "key_name" {
  description = "The name of the SSH key pair"
  type        = string
  default     = "cloud_test"
}

// New variables to receive outputs from the networking module:
variable "networking_db_sg_id" {
  description = "Security group ID for the database, from the networking module"
  type        = string
}

variable "networking_app_sg_id" {
  description = "Security group ID for the application instances, from the networking module"
  type        = string
}

variable "networking_lb_sg_id" {
  description = "Security group ID for the load balancer, from the networking module"
  type        = string
}

variable "networking_subnets" {
  description = "List of public subnet IDs from the networking module"
  type        = list(string)
}

variable "networking_vpc_id" {
  description = "The VPC ID from the networking module"
  type        = string
}

// Variables to receive AMI IDs from the create_images module:
variable "images_db_ami_id" {
  description = "AMI ID for the DB instance created in the create_images module"
  type        = string
}

variable "images_app_ami_id" {
  description = "AMI ID for the app instance created in the create_images module"
  type        = string
}

variable "db_password" {
  description = "Password for the PostgreSQL database"
  type        = string
  sensitive   = true
}
variable "region" {
  type        = string
  description = "The AWS region for the create_instances module"
}

variable "vpc_id" {
  type        = string
  description = "The VPC ID used within create_instances"
}
