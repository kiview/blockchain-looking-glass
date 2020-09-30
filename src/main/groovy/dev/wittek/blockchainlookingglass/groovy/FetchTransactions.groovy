package dev.wittek.blockchainlookingglass.groovy

import org.hipparchus.stat.descriptive.DescriptiveStatistics
import org.web3j.protocol.Web3j
import org.web3j.protocol.core.DefaultBlockParameter
import org.web3j.protocol.websocket.WebSocketService

def ethereumUrl = "wss://websockets.bloxberg.org"
def webSocketService = new WebSocketService(ethereumUrl, false)
webSocketService.connect()

def web3j = Web3j.build(webSocketService)

def stats = new DescriptiveStatistics()

web3j.with {

    (5000000..5000100).each {
        def transactionCount = ethGetBlockTransactionCountByNumber(DefaultBlockParameter.valueOf(it))
                .send().getTransactionCount()
        stats.addValue(transactionCount)
    }

}

println stats



webSocketService.close()