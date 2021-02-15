package coopercoin;

public class Block
{
    public String blockHash = null;
    public String prevHash;
    public String rootHash; //hash of the the transaction(s)
    private long date; 
    public Tx txMade;
    public int nonce; 

    public Block(Tx txMade, String prevHash){
        this.prevHash = prevHash;
        this.date = System.currentTimeMillis();
        this.blockHash = getHash();
        this.txMade = txMade;
        setBlockTxHash();
    }

    // TO DO: implement merkle tree for more transactions */
    public void setBlockTxHash(){
        this.rootHash = HashUtil.strToHexHash(HashUtil.hexFromKey(txMade.sender)+
                                              HashUtil.hexFromKey(txMade.receiver)+
                                              Float.toString(txMade.amtSent)+
                                              txMade.txId);
    }

    public String getHash(){
        String preImage = prevHash + Long.toString(date) + rootHash + Integer.toString(nonce);
//        System.err.println(preImage);
        try{
            return HashUtil.strToHexHash(preImage);
        }catch(Exception err) {
            System.err.println("error getting hash");
            System.err.println(preImage);
//			throw new RuntimeException(err);
        }
        return null;
    }

    synchronized public void setBlockHash(String hash){
        if(blockHash == null){
            blockHash = hash;
        }
    }

//    public void mineBlock(int prefix) {
//        String prefixString = new String(new char[prefix]).replace('\0', '0');
//        while (!blockHash.substring(0, prefix).equals(prefixString)) {
//            nonce++;
//            blockHash = getHash();
//        }
//    }
}
