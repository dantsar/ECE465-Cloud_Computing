package coopercoin; 

import java.util.Date;
import java.util.ArrayList;
import java.security.PublicKey;
import java.security.PrivateKey;
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
            this.txId = HashUtil.strToHexHash(HashUtil.hexFromKey(receiver) + value + parentTxId);
        }
        
        public boolean isMine(PublicKey pub){
            return receiver==pub;
        }
    }

    public PublicKey sender;
    public PublicKey receiver;
    long timestamp;
    float amtSent;
    String txId;

    byte[] txHash;
    byte[] digitalSignature;
    public ArrayList<Input> txIns = new ArrayList<Input>();
    public ArrayList<Output> txOut = new ArrayList<Output>();
    
    public static int txSequence = 0;
    
    public Tx(){}

    /* don't forget the in and out */
    public Tx(PublicKey sender, PublicKey receiver, float amount, ArrayList<Input> inputs){
        this.sender = sender;
        this.receiver = receiver;
        this.amtSent = amount;
        this.timestamp = new Date().getTime();
        this.txIns = inputs;
        setHash();
    }

    public boolean verifySignature(){
        try{
            Signature sign = Signature.getInstance("ECDSA", "BC");
            sign.initVerify(sender);
            sign.update(txHash);
            return sign.verify(digitalSignature);
        }catch(Exception e){
            System.out.println("Signature Failed");
            System.out.println(e);
        }
        return false;
    }

    /* gets called by Block when it's processing the transaction */
    public boolean processTx(){
        if(verifySignature() == false){
            System.out.println("transaction signature verification failed");
            return false;
        }

        for(Input i : txIns){
            i.UTXO = Main.UTXOPool.get(i.outputId); 
        }

        float inputAmt = 0;
        for(Input i : txIns) {
            if(i.UTXO == null) continue; 
            inputAmt += i.UTXO.value;
        }

        if(inputAmt < amtSent){
            System.out.println("transaction input too small");
            return false;
        }
        float leftOverChange = inputAmt-amtSent;
        txId = HashUtil.SHA256toHex(txHash);
        txOut.add(new Output(this.receiver, amtSent, txId));
        if(leftOverChange != 0f){
            txOut.add(new Output(this.sender,leftOverChange,txId));
        }

       for(Output i : txOut) {
            Main.UTXOPool.put(i.txId, i);
        }

        for(Input i : txIns){
            if(i.UTXO==null) continue;
            Main.UTXOPool.remove(i.UTXO.txId);
        }

        return true;
    }

    public byte[] setHash(){
        txHash = HashUtil.strToSHA256(HashUtil.hexFromKey(sender)+
                                      HashUtil.hexFromKey(receiver)+
                                      Float.toString(amtSent)+
                                      Long.toString(timestamp)+
                                      txSequence); // timestamp might not be necessary */
        return txHash;
    }

    public void signTx(PrivateKey privKey){
        try{
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
//    public boolean verifySig(byte[] data, byte[] sig, PublicKey pubKey){
//        try{
//            Signature sign = Signature.getInstance("ECDSA", "BC");
//            sign.initVerify(pubKey);
//            sign.update(data);
//            return sign.verify(sig);
//        }catch(Exception e){
//            System.out.println("Signature Failed");
//            System.out.println(e);
//        }
//        return false;
//    }
}
