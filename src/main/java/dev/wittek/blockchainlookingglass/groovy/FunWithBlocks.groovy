package dev.wittek.blockchainlookingglass.groovy


def chain = new GroovyChain("foo", "000")
println(chain)

chain << "bar"
println(chain)

chain << "baz"

println(chain)