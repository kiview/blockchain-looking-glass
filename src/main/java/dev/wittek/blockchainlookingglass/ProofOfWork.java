package dev.wittek.blockchainlookingglass;

import org.apache.commons.codec.digest.DigestUtils;

public class ProofOfWork {

    static final String difficulty = "0000";


    public static void main(String[] args) {
        var block = new Block("foo",
                "0000e35ce8a9b0244178bf28e4966c2ce1b8385723a96a6b838858cdd6ca0a1e");
        block.mine();

        System.out.println(block);

        var secondBlock = new Block("bar", block.calculateHash());
        secondBlock.mine();
        System.out.println(secondBlock);
    }


    public static class Block {

        private final String data;
        private final String previousHash;
        private long nonce = 0;

        public Block(String data, String previousHash) {
            this.data = data;
            this.previousHash = previousHash;
        }

        void mine() {
            var start = System.currentTimeMillis();
            while (!calculateHash().startsWith(difficulty)) {
                nonce++;
            }
            var duration = System.currentTimeMillis() - start;
            System.out.println("Mining took " + duration + "ms" );
        }

        String calculateHash() {
            return DigestUtils.sha256Hex(previousHash + data + nonce);
        }

        @Override
        public String toString() {
            return "Block{" +
                    "data='" + data + '\'' +
                    ", previousHash='" + previousHash + '\'' +
                    ", nonce=" + nonce +
                    ", hash=" + calculateHash() +
                    '}';
        }
    }

}
