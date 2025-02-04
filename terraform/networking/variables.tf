#terraform/networking/variables.tf

variable "vpc_cidr_block" {
  description = "The CIDR block for the VPC"
  type        = string
  default     = "10.0.0.0/16"
}

variable "public_subnet_a_cidr_block" {
  description = "The CIDR block for the first public subnet"
  type        = string
  default     = "10.0.1.0/24"
}

variable "public_subnet_b_cidr_block" {
  description = "The CIDR block for the second public subnet"
  type        = string
  default     = "10.0.2.0/24"
}

variable "availability_zone_a" {
  description = "The availability zone for the first public subnet"
  type        = string
  default     = "eu-central-1a"
}

variable "availability_zone_b" {
  description = "The availability zone for the second public subnet"
  type        = string
  default     = "eu-central-1b"
}
variable "region" {
  type        = string
  description = "The AWS region for the create_instances module"
}