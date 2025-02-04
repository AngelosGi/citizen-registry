// terraform/create_instances/main.tf

// Declare that the networking and images values are passed in as variables (see the variables file below).

resource "aws_instance" "db" {
  ami                   = var.images_db_ami_id
  instance_type         = var.instance_type_db
  key_name              = var.key_name
  vpc_security_group_ids = [var.networking_db_sg_id]
  subnet_id             = var.networking_subnets[0]  // Place DB in the first subnet
  tags = {
    Name = "Citizen DB"
  }
}

resource "aws_instance" "app" {
  count                 = 3
  ami                   = var.images_app_ami_id
  instance_type         = var.instance_type_app
  key_name              = var.key_name
  vpc_security_group_ids = [var.networking_app_sg_id]
  subnet_id             = element(var.networking_subnets, count.index % length(var.networking_subnets))
  user_data = <<-EOF
              #!/bin/bash
              export DB_HOST=${aws_instance.db.private_ip}
              export DB_NAME=${var.db_name}
              export DB_USER=${var.db_user}
              export DB_PASSWORD=${var.db_password}
              cd /home/ubuntu/app/service
              nohup java -jar target/*.jar > /var/log/spring-boot-app.log 2>&1 &
              echo "User data script executed at $(date)" >> /var/log/user-data.log
              EOF
  tags = {
    Name = "citizen-app-${count.index}"
  }
}

resource "aws_lb" "app_lb" {
  name                    = "app-load-balancer"
  internal                = false
  load_balancer_type      = "application"
  security_groups         = [var.networking_lb_sg_id]
  subnets                 = var.networking_subnets
}

resource "aws_lb_target_group" "app_tg" {
  name        = "app-target-group"
  port        = 8080
  protocol    = "HTTP"
  vpc_id      = var.networking_vpc_id
  target_type = "instance"

  health_check {
    path                = "/api/citizens"
    interval            = 30
    timeout             = 5
    healthy_threshold   = 5
    unhealthy_threshold = 2
    matcher             = "200"
  }
}

resource "aws_lb_listener" "app_lb_listener" {
  load_balancer_arn = aws_lb.app_lb.arn
  port              = 80
  protocol          = "HTTP"

  default_action {
    type             = "forward"
    target_group_arn = aws_lb_target_group.app_tg.arn
  }
}

resource "aws_lb_target_group_attachment" "app_tg_attachment" {
  count             = 3
  target_group_arn  = aws_lb_target_group.app_tg.arn
  target_id         = aws_instance.app[count.index].id
  port              = 8080
}
