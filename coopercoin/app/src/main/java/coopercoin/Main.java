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
//    public static float minTx = 0.01f; // let's not worry about this for now...

    public static int difficulty = 4;
    public static ArrayList<Block> blockchain = new ArrayList<Block>();
    

    public static void main(String [] args){
        Security.addProvider(new BouncyCastleProvider());
        new HashUtil("SHA-256");

        wA = new Wallet();
        wB = new Wallet();
        Wallet coinbase = new Wallet();

        /* Genesis Transaction */
        float genesisAmount = 100f;
        genesisTx = new Tx(coinbase.pubKey, wA.pubKey, genesisAmount, null);
        genesisTx.setHash();
        genesisTx.digitalSignature = coinbase.signTx(genesisTx.txHash);
        genesisTx.txOut.add(0, new Tx().new Output(wA.pubKey, genesisAmount, genesisTx.txId));
        UTXOPool.put(genesisTx.txOut.get(0).txId, genesisTx.txOut.get(0));

        Block genesisBlock = new Block(genesisTx, "0");
        
        Miner miner1 = new Miner(genesisBlock, difficulty);
        Miner miner2 = new Miner(genesisBlock, difficulty);
        Miner miner3 = new Miner(genesisBlock, difficulty);

        miner1.start();
        miner2.start();
        miner3.start();
        
        while(Miner.blockHashFoundFLAG == false);
        blockchain.add(genesisBlock);

        System.out.println("A's Balance: " + wA.getBalance());
        System.out.println("B's Balance: " + wB.getBalance());
        Tx sentToB = wA.sendAmt(wB.pubKey, 25f);
        sentToB.processTx();
        System.out.println("sent coins");
        System.out.println("A's Balance: " + wA.getBalance());
        System.out.println("B's Balance: " + wB.getBalance());
        
        Block newBlock = new Block(sentToB, sentToB.txId);
        miner1.setBlock(newBlock); 
        while(miner1.blockHashFoundFLAG == false);
        blockchain.add(newBlock);
        

//        boolean genesis = true;
//        boolean sendOrder = true;
//        while(true){
//            while(Miner.blockHashFoundFLAG == false);
//            /* add genesis block to blockchain */
//            if(genesis == true){
//                genesis = false;
//                blockchain.add(genesisBlock);
//            }
//            
//            
//            System.out.println("A's Balance: " + wA.getBalance());
//            System.out.println("B's Balance: " + wB.getBalance());
//            Tx sentToB = wA.sendAmt(wB.pubKey, 25f);
//            sentToB.processTx();
//            System.out.println("sent coins");
//            System.out.println("A's Balance: " + wA.getBalance());
//            System.out.println("B's Balance: " + wB.getBalance());
//            
//        }

        

        /* Testing out transaction BECAUSE THEY WORK!!! */
//        System.out.println("A's Balance: " + wA.getBalance());
//        System.out.println("B's Balance: " + wB.getBalance());
////        wA.seeWallet();
//        Tx sentToB = wA.sendAmt(wB.pubKey, 25f);
//        sentToB.processTx();
//        System.out.println("sent coins");
//        System.out.println("A's Balance: " + wA.getBalance());
//        System.out.println("B's Balance: " + wB.getBalance());

    }

}
