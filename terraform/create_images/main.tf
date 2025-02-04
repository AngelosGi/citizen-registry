// terraform/create_images/main.tf

resource "aws_instance" "db" {
  ami                   = "ami-07eef52105e8a2059"
  instance_type         = var.instance_type_db
  key_name              = var.key_name
  vpc_security_group_ids = [var.db_sg_id]
  subnet_id             = var.subnet_id
  tags = {
    Name = "postgres-db"
  }

  user_data = <<-EOF
              #!/bin/bash
              sudo apt-get update
              sudo apt-get install -y postgresql postgresql-contrib
              sudo systemctl start postgresql
              # create non-root user and db
              sudo -u postgres psql -c "CREATE USER ${var.db_user} WITH PASSWORD '${var.db_password}';"
              sudo -u postgres createdb -O ${var.db_user} ${var.db_name}
              touch /tmp/user_data_complete
              EOF
}

resource "null_resource" "wait_for_db_instance" {
  depends_on = [aws_instance.db]

  provisioner "remote-exec" {
    inline = [
      "while [ ! -f /tmp/user_data_complete ]; do sleep 10; done",
      "echo 'User-data script completed'"
    ]
    connection {
      type        = "ssh"
      user        = "ubuntu"
      private_key = file(var.private_key_path)
      host        = aws_instance.db.public_ip
    }
  }
}

resource "aws_ami_from_instance" "db_ami" {
  name               = "db-ami"
  source_instance_id = aws_instance.db.id

  tags = {
    Name = "citizen-db-ami"
  }

  depends_on = [null_resource.wait_for_db_instance]
}

resource "aws_instance" "app" {
  ami           = "ami-07eef52105e8a2059" // Ubuntu Server 24.04 LTS AMI
  instance_type = var.instance_type_app
  key_name      = var.key_name
  vpc_security_group_ids = [var.app_sg_id]
  subnet_id             = var.subnet_id
  tags = {
    Name = "spring-boot-setup"
  }

  user_data = <<-EOF
              #!/bin/bash
              sudo apt-get update -y
              sudo apt-get install -y openjdk-21-jdk git maven
              git clone ${var.spring_boot_app_git_repo} /home/ubuntu/app
              cd /home/ubuntu/app
              mvn clean package
              touch /tmp/user_data_complete
              EOF
}

resource "null_resource" "wait_for_app_instance" {
  depends_on = [aws_instance.app]

  provisioner "remote-exec" {
    inline = [
      "while [ ! -f /tmp/user_data_complete ]; do sleep 10; done",
      "echo 'User-data script completed'"
    ]
    connection {
      type        = "ssh"
      user        = "ubuntu"
      private_key = file(var.private_key_path)
      host        = aws_instance.app.public_ip
    }
  }
}

resource "aws_ami_from_instance" "app_ami" {
  name               = "app-ami"
  source_instance_id = aws_instance.app.id

  tags = {
    Name = "citizen-app-ami"
  }

  depends_on = [null_resource.wait_for_app_instance]
}
