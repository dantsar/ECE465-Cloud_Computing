package coopercoin;

import org.bouncycastle.jce.provider.BouncyCastleProvider;
import java.security.Security;
import java.util.HashMap;

public class Main{

    /* global hashmap that the wallets access to get their balance and confirm payments */
    public static HashMap<String,Tx.Output> UTXOPool = new HashMap<String,Tx.Output>();
//  public static Block blockChain = new Block();

    public static int difficulty = 3;
    public static Wallet wA;
    public static Wallet wB;
    public static Tx genesis;
    public static float minTx = 0.01f;

    public static void main(String [] args){
        Security.addProvider(new BouncyCastleProvider());

        wA = new Wallet();
        wB = new Wallet();
        Wallet coinbase = new Wallet();

        /* Genesis Transaction */
        genesis = new Tx(coinbase.pubKey, wA.pubKey, 100f, null);
        coinbase.signTx(genesis.txHash);


//        genesis.signTx(coinbase.privKey);
//        UTXOPool.put(
        //UTXOs.put( ,genesis);

        System.out.println("A's Balance: " + wA.getBalance());
        
        
    }

}
