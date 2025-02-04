#terraform/main.tf

module "networking" {
  source = "./networking"
  region = var.region
}

module "create_images" {
  source      = "./create_images"
  region      = var.region
  db_password = var.db_password

  // Pass networking outputs required by create_images:
  db_sg_id  = module.networking.db_sg_id
  app_sg_id   = module.networking.app_sg_id
  // Use one of the public subnets (e.g., the first one)
  subnet_id = module.networking.subnets[0]

  depends_on = [module.networking]  // Ensure networking is created first
}

module "create_instances" {
  source      = "./create_instances"
  region      = var.region
  db_password = var.db_password
  vpc_id      = module.networking.vpc_id

  // Pass networking outputs required by create_instances:
  networking_db_sg_id = module.networking.db_sg_id
  networking_app_sg_id = module.networking.app_sg_id
  networking_lb_sg_id  = module.networking.lb_sg_id
  networking_subnets   = module.networking.subnets
  networking_vpc_id    = module.networking.vpc_id

  // Pass AMI outputs from create_images:
  images_db_ami_id  = module.create_images.db_ami_id
  images_app_ami_id = module.create_images.app_ami_id

  depends_on = [module.create_images]  // Ensure images are created before instances
}
