package dev.wittek.blockchainlookingglass.groovy

import org.web3j.crypto.Credentials
import org.web3j.model.Storage
import org.web3j.protocol.Web3j
import org.web3j.protocol.http.HttpService

def ethereumUrl = "http://127.0.0.1:7545"
def httpService = new HttpService(ethereumUrl)
def web3j = Web3j.build(httpService)

def cred = Credentials.create("94ab635e7631cb59ff0f057785a3b7f212c9d33e872b8e41c92ce162fdf73c71")

def contract = Storage.deploy(web3j, cred, 100000, 100000).send()

def valueFromLedger = contract.retrieve().send()
println "$valueFromLedger is now on the ledger"

println "Calling SC"
contract.store(4711).send()
valueFromLedger = contract.retrieve().send()
println "$valueFromLedger is now on the ledger"

println "Calling SC"
contract.store(42).send()
valueFromLedger = contract.retrieve().send()
println "$valueFromLedger is now on the ledger"