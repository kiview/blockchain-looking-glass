package dev.wittek.blockchainlookingglass;

import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.DefaultBlockParameter;
import org.web3j.protocol.websocket.WebSocketService;

import java.io.IOException;
import java.math.BigInteger;
import java.util.stream.IntStream;

public class Web3jExample {

    public static void main(String[] args) throws IOException {

        var url = "wss://websockets.bloxberg.org";
        var webSocketService = new WebSocketService(url, false);
        webSocketService.connect();

        var web3j = Web3j.build(webSocketService);

        var transactionCount = web3j.ethGetBlockTransactionCountByNumber(DefaultBlockParameter.valueOf(BigInteger.valueOf(5000000)))
                .send().getTransactionCount();
        System.out.println(transactionCount);

        var summary = IntStream.range(5000000, 5000100)
                .peek(System.out::println)
                .mapToObj(BigInteger::valueOf)
                .map(DefaultBlockParameter::valueOf)
                .mapToLong(i -> {
                    try {
                        var response = web3j.ethGetBlockTransactionCountByNumber(i).send();
                        return response.getTransactionCount().longValue();
                    } catch (IOException e) {
                        e.printStackTrace();
                        return 0;
                    }
                }).summaryStatistics();

        System.out.println(summary);

        web3j.shutdown();
        webSocketService.close();
    }

}
