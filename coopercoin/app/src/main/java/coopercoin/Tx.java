package coopercoin; 

import java.util.Date;
import java.util.ArrayList;
import java.security.*;
import java.security.Signature;

public class Tx
{
    


    public PublicKey sender;
    public PublicKey receiver;
    long timestamp;
    float amtSent;
    byte[] txHash;
    byte[] digitalSignature;

    public ArrayList<TxIn> txIns = new ArrayList<TxIn>();
    public ArrayList<TxOut> txOut = new ArrayList<TxOut>();
    
    
    /* don't forget the in and out */
    public Tx(PublicKey sender, PublicKey receiver, float amount, ArrayList<TxIn> inputs){
        this.sender = sender;
        this.receiver = receiver;
        this.amtSent = amount;
        this.timestamp = new Date().getTime();
        this.txIns = inputs;
    }

    /* gets called by Block when it's processing the transaction */
    public boolean processTx(){
        /* check is input's are able to satisfy the amtSent */
        /* gonna worry about it later */
        return false;
    }

//    public setHash(){
//        this.txHash = HashUtil.strToHash(HashUtil.strFromKey(sender)+
//                                         HashUtil.strFromKey(receiver)+
//                                         this.amtSent+
//                                         this.timestamp);
//    }
    

    public void signTx(PrivateKey privKey){
//        byte[] bytes = str.getBytes();
//        byte[] sigBytes = null;
        try{
//            Signature sign = Signature.getInstance("SHA256withECDSA");
            Signature sign = Signature.getInstance("ECDSA", "BC");
            sign.initSign(privKey);
            sign.update(txHash);
            this.digitalSignature = sign.sign();
        }catch(Exception e){
            System.out.println("Signature Failed");
            System.out.println(e);
        }
    }


    /* Will probably be in the block,
       but here for now!! */
    public boolean verifySig(String data, byte[] sig, PublicKey pubKey){
        try{
            Signature sign = Signature.getInstance("ECDSA", "BC");
            sign.initVerify(pubKey);
            sign.update(data.getBytes());
            return sign.verify(sig);
        }catch(Exception e){
            System.out.println("Signature Failed");
            System.out.println(e);
        }
        return false;
    }


}
