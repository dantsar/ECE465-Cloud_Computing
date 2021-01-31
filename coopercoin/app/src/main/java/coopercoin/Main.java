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
        new HashUtil("SHA-256");

        wA = new Wallet();
        wB = new Wallet();
        Wallet coinbase = new Wallet();

        /* Genesis Transaction */
        float genesisAmount = 150f;
        genesis = new Tx(coinbase.pubKey, wA.pubKey, genesisAmount, null);
        genesis.setHash();
        genesis.digitalSignature = coinbase.signTx(genesis.txHash);
        genesis.txOut.add(0, new Tx().new Output(wA.pubKey, genesisAmount, genesis.txId));
        UTXOPool.put(genesis.txOut.get(0).txId, genesis.txOut.get(0));

        System.out.println("A's Balance: " + wA.getBalance());

        wA.sendAmt(wB.pubKey, 25f);
        System.out.println("A's Balance: " + wA.getBalance());
//        wA.getBalance();


//        genesis.txId
                

//        String hash = HashUtil.SHA256toHex(genesis.txHash);
//        System.out.println(hash);
//        System.out.println(HashUtil.SHA256toHex(genesis.digitalSignature));
//
//        if(genesis.verifySig(genesis.txHash, genesis.digitalSignature, coinbase.pubKey) == true){
//            System.out.println("verified");
//        }else{
//            System.out.println("error, not verified");
//
//        }


//        genesis.signTx(coinbase.privKey);
//        UTXOPool.put(
        //UTXOs.put( ,genesis);

        
        
    }

}
