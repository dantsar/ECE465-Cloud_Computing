package coopercoin;

public class TxIn
{
    public TxOut UTXO;
    public String txOutId;

    public TxIn(String txOutId){
        this.txOutId = txOutId;
    }

}
