resource "aws_internet_gateway" "public" {
  vpc_id   = aws_vpc.vpc.id

  tags = {
    Name = var.name
  }
}

resource "aws_subnet" "public" {
  count             = length(var.public_subnets)
  vpc_id            = aws_vpc.vpc.id
  cidr_block        = var.public_subnets[count.index]
  availability_zone = var.zones[count.index]
  map_public_ip_on_launch = true

  tags = {
    Name = "${var.name}-public.${element(var.zones, count.index)}"
    Tier = "Public"
  }
}

resource "aws_route_table" "public" {
  vpc_id   = aws_vpc.vpc.id

  route {
      cidr_block = "0.0.0.0/0"
      gateway_id = aws_internet_gateway.public.id
  }

  tags = {
    Name = "${var.name}-public"
  }
}

resource "aws_route_table_association" "public" {
  count          = length(var.public_subnets)
  subnet_id      = aws_subnet.public.*.id[count.index]
  route_table_id = aws_route_table.public.id
}

output "public_subnet_ids" {
  value = aws_subnet.public.*.id
}
