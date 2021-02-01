package coopercoin;

import java.security.KeyPairGenerator;
import java.security.PublicKey;
import java.security.PrivateKey;
import java.security.KeyPair;
import java.security.Signature;
import java.security.spec.ECGenParameterSpec;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

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
        if(getBalance() < amount){
            System.out.println("Not enough funds, Transaction discraded");
            return null;
        }
    
        ArrayList<Tx.Input> input = new ArrayList<Tx.Input>();

        float total = 0f;
        for(Map.Entry<String,Tx.Output> i: UTXOs.entrySet()){
            Tx.Output UTXO = i.getValue();
            total += UTXO.value;
            input.add(new Tx().new Input(UTXO.txId));
            if(total >= amount) break;
        }
//        for(String i : UTXOs.keySet()){
//            System.out.println(i);
//            value += UTXOs.get(i).value;
//            input.add(new Tx().new Input(UTXOs.get(i).txId)); /* this is super ugly. Trust me, I know :^)*/
//            if(value >= amount) break;
//        }

        Tx tx = new Tx(pubKey, receiver, amount, input);
        tx.signTx(privKey);

        /* clear input UTXOs */
        for(Tx.Input i : input){
            UTXOs.remove(i.outputId);
        }

        return tx;
    }
    
    /* debugging */
    public void seeWallet(){
        for(String i : UTXOs.keySet()){
            System.out.println(i + " " + UTXOs.get(i).value);

        }

    }

    

    /* needed for genesis transaction, without making the private key public :^) */
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
            Tx.Output tx = Main.UTXOPool.get(i);
            if(tx.isMine(pubKey)){
                balance += tx.value;
                UTXOs.put(tx.txId, tx);
            }
        }   
        return balance;
    }

}
