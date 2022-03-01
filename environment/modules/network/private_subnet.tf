resource "aws_subnet" "private" {
  count             = length(var.private_subnets)
  vpc_id            = aws_vpc.vpc.id
  cidr_block        = element(var.private_subnets, count.index)
  availability_zone = element(var.zones, count.index)

  tags = {
    Name = "${var.name}-private.${var.zones[count.index]}"
    Tier = "Private"
  }
}

resource "aws_route_table" "private" {
  count    = length(var.private_subnets)
  vpc_id   = aws_vpc.vpc.id
  route {
    cidr_block     = "0.0.0.0/0"
    nat_gateway_id = aws_nat_gateway.nat.*.id[count.index]
  }

  tags = {
    Name = "${var.name}-private.${element(var.zones, count.index)}"
  }
}

resource "aws_route_table_association" "private" {
  count          = length(var.private_subnets)
  subnet_id      = aws_subnet.private.*.id[count.index]
  route_table_id = aws_route_table.private.*.id[count.index]
}

output "private_subnet_ids" {
  value = aws_subnet.private.*.id
}

