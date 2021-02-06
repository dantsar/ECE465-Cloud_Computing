package coopercoin;

import org.bouncycastle.jce.provider.BouncyCastleProvider;
import java.security.Security;
import java.util.HashMap;
import java.util.ArrayList;

public class Main{

    /* global hashmap that the wallets access to get their balance and confirm payments */
    public static HashMap<String,Tx.Output> UTXOPool = new HashMap<String,Tx.Output>();

    public static Wallet wA;
    public static Wallet wB;
    public static Tx genesisTx;

    public static int difficulty = 4;
    public static ArrayList<Block> blockchain = new ArrayList<Block>();
    

    public static void main(String [] args){
        Security.addProvider(new BouncyCastleProvider());

        wA = new Wallet();
        wB = new Wallet();
        Wallet coinbase = new Wallet();

        System.err.println("Wallets created");

        /* Genesis Transaction */
        float genesisAmount = 100f;
        genesisTx = new Tx(coinbase.pubKey, wA.pubKey, genesisAmount, null);
        genesisTx.setHash();
        genesisTx.digitalSignature = coinbase.signTx(genesisTx.txHash);
        genesisTx.txOut.add(0, new Tx().new Output(wA.pubKey, genesisAmount, genesisTx.txId));
        UTXOPool.put(genesisTx.txOut.get(0).txId, genesisTx.txOut.get(0));

        Block genesisBlock = new Block(genesisTx, "0");
        System.err.println("Genesis block made");       


        Miner miner1 = new Miner(genesisBlock, difficulty);
//        Miner miner2 = new Miner(genesisBlock, difficulty);
//        Miner miner3 = new Miner(genesisBlock, difficulty);

        miner1.start();
//        miner2.start();
//        miner3.start();
        
        try{
            while(Miner.blockHashFoundFLAG.get() == false) Thread.sleep(100); /* waits until hash is found */
        }catch(Exception e){
            System.out.println("problem");
        }

        blockchain.add(genesisBlock);
        
        float amtSending;
        Block newBlock = null;
        Tx sentTx = null;
        int itterations = 4;
        for(int i = 0; i < itterations; i++) /* replace with for(;;) for infinite transactions */
        {
            try{
                amtSending = wA.getBalance()/2;
                System.out.println("A's Balance: " + wA.getBalance());
                System.out.println("B's Balance: " + wB.getBalance());
                sentTx = wA.sendAmt(wB.pubKey, amtSending);
                sentTx.processTx();
                System.out.println("A sent B " + amtSending + " coins");
                System.out.println("A's Balance: " + wA.getBalance());
                System.out.println("B's Balance: " + wB.getBalance());
                
                newBlock = new Block(sentTx, blockchain.get(blockchain.size()-1).blockHash);
                miner1.setBlock(newBlock); 

                while(Miner.blockHashFoundFLAG.get() == false) Thread.sleep(100); /* waits until hash is found */
                blockchain.add(Miner.getMinedBlock());

//                amtSending = wB.getBalance()/2;
//                System.out.println("B's Balance: " + wB.getBalance());
//                System.out.println("A's Balance: " + wA.getBalance());
//                sentTx = wB.sendAmt(wA.pubKey, amtSending);
//                sentTx.processTx();
//                System.out.println("B sent A " + amtSending + " coins");
//                System.out.println("B's Balance: " + wB.getBalance());
//                System.out.println("A's Balance: " + wA.getBalance());
//                
//                newBlock = new Block(sentTx, blockchain.get(blockchain.size()-1).blockHash);
//                miner1.setBlock(newBlock); 
//
//                while(Miner.blockHashFoundFLAG.get() == false) Thread.sleep(100); 
//                blockchain.add(Miner.getMinedBlock());

            }catch(Exception e){
                System.err.println("houston we have a problem");
            }
        }

        System.out.println("\nhashes of all the blocks: ");
        for(Block i : blockchain){
            System.out.println(i.blockHash);
        }    
    

    }
}
