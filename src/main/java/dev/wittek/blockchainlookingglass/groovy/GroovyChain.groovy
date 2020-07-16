package dev.wittek.blockchainlookingglass.groovy

import groovy.transform.ToString
import org.apache.commons.codec.digest.DigestUtils

@ToString
class GroovyChain {

    final String difficulty;

    List<Block> blocks = []

    GroovyChain(String genesisData, String difficulty) {
        this.difficulty = difficulty
        def genesisBlock = new Block(data: genesisData, previousHash: "")
        genesisBlock.mine(difficulty)
        blocks << genesisBlock
    }

    void leftShift(String data) {

        def newBlock = new Block(
                data: data,
                previousHash: blocks.head().previousHash
        )
        newBlock.mine(difficulty)
        blocks << newBlock

    }

    @ToString
    static class Block {
        String data
        String previousHash
        long nonce

        void mine(String difficulty) {
            while (!calculateHash().startsWith(difficulty)) {
                nonce++
            }
        }

        String calculateHash() {
            return DigestUtils.sha256Hex(previousHash + data + nonce);
        }
    }

}
