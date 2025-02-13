#terraform/networking/outputs.tf

output "vpc_id" {
  description = "The ID of the created VPC"
  value       = aws_vpc.main.id
}

output "subnets" {
  description = "The IDs of the created public subnets"
  value       = [aws_subnet.public_a.id, aws_subnet.public_b.id]
}

output "internet_gateway_id" {
  description = "The ID of the created internet gateway"
  value       = aws_internet_gateway.gw.id
}

output "db_sg_id" {
  description = "The ID of the database security group"
  value       = aws_security_group.db_sg.id
}

output "app_sg_id" {
  description = "The ID of the application security group"
  value       = aws_security_group.app_sg.id
}

output "lb_sg_id" {
  description = "The ID of the load balancer security group"
  value       = aws_security_group.lb_sg.id
}
