package dev.wittek.blockchainlookingglass;

import org.web3j.crypto.Credentials;
import org.web3j.model.Storage;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.http.HttpService;
import org.web3j.tx.gas.StaticGasProvider;

import java.math.BigInteger;

public class Web3jSmartContractExample {

    public static void main(String[] args) throws Exception {

        var web3j = Web3j.build(new HttpService("http://127.0.0.1:8545"));
        var creds = Credentials.create("628ae44e1e088dd4379ebb54d6621f47a028cd3b95888e992e27fb5046403a9f");

        var gasPrice = BigInteger.valueOf(100000);
        var gasLimit = BigInteger.valueOf(100000);
        Storage storageSC = Storage.deploy(web3j, creds, new StaticGasProvider(gasPrice, gasLimit)).send();

        BigInteger valueFromLedger = storageSC.retrieve().send();
        System.out.println(valueFromLedger);

        storageSC.store(BigInteger.valueOf(4711)).send();
        System.out.println(storageSC.retrieve().send());

        storageSC.store(BigInteger.valueOf(42)).send();
        System.out.println(storageSC.retrieve().send());


    }

}
