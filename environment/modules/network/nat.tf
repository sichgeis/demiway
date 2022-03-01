resource "aws_eip" "elastic_ips" {
  count = length(var.zones)
  vpc = true
}

resource "aws_nat_gateway" "nat" {
  count         = length(var.zones)
  allocation_id = aws_eip.elastic_ips.*.id[count.index]
  subnet_id     = aws_subnet.public.*.id[count.index]
}

output "nat_gateway_ids" {
  value = aws_nat_gateway.nat.*.id
}

output "nat_gateway_ips" {
  value = aws_nat_gateway.nat.*.public_ip
}
