package coopercoin;

public class Block
{
    public String blockHash;
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
        String preImage = prevHash + date + rootHash + Integer.toString(nonce);
        try{
            return HashUtil.strToHexHash(preImage);
        }catch(Exception err) {
            System.out.println("error getting hash");
			throw new RuntimeException(err);
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
