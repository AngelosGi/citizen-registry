#terraform/variables.tf

variable "region" {
  description = "The AWS region to deploy to"
  type        = string
  default     = "eu-central-1"
}

variable "db_password" {
  description = "Password for the PostgreSQL database"
  type        = string
  sensitive   = true
}

