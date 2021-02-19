package coopercoin;


import java.util.Date;

public class BlockHeader
{
    public String blockHash;
    public String prevHash;
    public String rootHash;
    public int nonce;

    private long date; 


    BlockHeader(String prevHash){
        this.prevHash = prevHash;
    }

    BlockHeader(BlockHeader copy){
        this.prevHash = copy.prevHash;
        this.rootHash = copy.rootHash;
        this.date = copy.date;
    }

    public void setRootHash(String rootHash){
        this.rootHash = rootHash;
        this.date = new Date().getTime();
    }

    public String getRootHash(){
        if(rootHash == null){
            return null;
        }
        return rootHash;
    }

    /* returns the hash with the provided nonce */
    public String testHash(int nonce){
        String preImage = prevHash + Long.toString(date) + rootHash + Integer.toString(nonce);
        return HashUtil.strToHexHash(preImage);
    }

    /* called when appropriate nonce was found */
    public void setBlockHash(int nonce, String blockHash){
        this.nonce = nonce;
        this.blockHash = blockHash;
    }

    public String getBlockHash(){
        return this.blockHash;
    }

}