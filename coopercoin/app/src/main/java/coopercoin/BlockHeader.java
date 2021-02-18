package coopercoin;


import java.util.Date;

public class BlockHeader
{
//    public String blockhash;
    public String prevHash;
    public String rootHash;
    public int nonce;
    private long date; 


    BlockHeader(String prevHash){
        this.prevHash = prevHash;
    }

    public void setRootHash(String rootHash){
        this.rootHash = rootHash;
        this.date = new Date().getTime();

    }

}