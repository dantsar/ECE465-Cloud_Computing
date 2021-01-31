package coopercoin; 

import java.util.Date;
import java.util.ArrayList;
import java.security.*;
import java.security.Signature;

public class Tx
{
    public class Input{
        public Output UTXO;
        public String outputId;

        public Input(String outputId){
            this.outputId = outputId;

        }
    }    

    public class Output{
        public String parentTxId;
        public PublicKey receiver;
        public float value;
        public String txId;

        public Output(PublicKey receiver, float value, String parentTxId){
            this.receiver = receiver;
            this.value = value;
            this.parentTxId = parentTxId;
            this.txId = HashUtil.strToHexHash(HashUtil.strFromKey(receiver) + value + parentTxId);
        }
        
        public boolean isMine(PublicKey pub){
            return receiver==pub;
        }
    }

    public PublicKey sender;
    public PublicKey receiver;
    long timestamp;
    float amtSent;
    byte[] txHash;
    byte[] digitalSignature;

    public ArrayList<Input> txIns;
    public ArrayList<Output> txOut;
    
    
    public Tx(){}

    /* don't forget the in and out */
    public Tx(PublicKey sender, PublicKey receiver, float amount, ArrayList<Input> inputs){
        ArrayList<Input> txIns = new ArrayList<Input>();
        ArrayList<Output> txOut = new ArrayList<Output>();
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
