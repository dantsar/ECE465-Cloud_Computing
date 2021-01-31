package coopercoin;

import java.security.PublicKey;

public class TxOut{
    String parentTxId;
    PublicKey receiver;
    public float value;
    public String txId;

    public TxOut(PublicKey receiver, float value, String parentTxId){
        this.receiver = receiver;
        this.value = value;
        this.parentTxId = parentTxId;
        this.txId = HashUtil.strToHexHash(HashUtil.strFromKey(receiver) + value + parentTxId);
    }
    
    public boolean isMine(PublicKey pub){
        return pub==receiver;
    }

}
