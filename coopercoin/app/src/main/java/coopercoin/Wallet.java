package coopercoin;

import java.security.KeyPairGenerator;
import java.security.PublicKey;
import java.security.PrivateKey;
import java.security.KeyPair;
import java.security.Signature;
import java.security.spec.ECGenParameterSpec;
import java.util.ArrayList;
import java.util.HashMap;

public class Wallet{

    public PublicKey pubKey;
    private PrivateKey privKey;
    HashMap<String,Tx.Output> UTXOs = new HashMap<String,Tx.Output>();

    public Wallet(){
        genKeyPair();
    }

    public void genKeyPair(){
        try{
            KeyPairGenerator keyPair = KeyPairGenerator.getInstance("ECDSA", "BC");
            ECGenParameterSpec ecSpec = new ECGenParameterSpec("secp256k1");
            keyPair.initialize(ecSpec);
            KeyPair keys = keyPair.genKeyPair();

            pubKey = keys.getPublic();
            privKey = keys.getPrivate();
        }catch(Exception e){
            /* acutal error reporting and loggin later :^) */
            System.out.println("Key Generation Failed");
            System.out.println(e);
        }
    }

    public Tx sendAmt(PublicKey receiver, float amount){
        ArrayList<Tx.Input> input = new ArrayList<Tx.Input>();

        float value = 0f;
        for(String i : UTXOs.keySet()){
            value += UTXOs.get(i).value;
            input.add(new Tx().new Input(UTXOs.get(i).txId)); /* this is super ugly. Trust me, I know :^)*/
            if(value >= amount) break;
        }

        if(value < amount){
            System.out.println("Wallet: " + HashUtil.hexFromKey(pubKey) + " is broke and can't send " + amount);
            return null;
        }

        Tx tx = new Tx(pubKey, receiver, amount, input);
        tx.signTx(privKey);

        /* clear input UTXOs */
        for(Tx.Input i : input){
            UTXOs.remove(i.outputId);
        }

        return tx;
    }

    /* needed for genesis transaction, without making the private key public */
    public byte[] signTx(byte[] txHash){
        byte[] signBytes = null;
        try{
            Signature sign = Signature.getInstance("ECDSA", "BC");
            sign.initSign(privKey);                               
            sign.update(txHash);                                  
            signBytes = sign.sign();                  
        }catch(Exception e){                                      
            System.out.println("Signature Failed");               
            System.out.println(e);                                
        }
        return signBytes;
    }

    /* access the global Main.UTXOPool and confirms the transaction */
    /* TO DO: rewrite to use UTXOPool class... maybe?*/
    public float getBalance(){
        float balance = 0;
        for(String i : Main.UTXOPool.keySet()){
            Tx.Output txOut = UTXOs.get(i);
            if(txOut.isMine(pubKey)){
                balance += UTXOs.get(i).value;
                UTXOs.put(txOut.txId, txOut);
            }
        }   
        return balance;
    }

}
