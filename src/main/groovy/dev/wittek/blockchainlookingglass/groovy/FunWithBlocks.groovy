package dev.wittek.blockchainlookingglass.groovy

// Create a chain and add some blocks
def chain = new GroovyChain("foo", "000")
println(chain)

// The joys of Groovy operator overload \o/
chain << "bar"
println chain

chain << "baz"
println chain
