package coopercoin;

import java.util.ArrayList;

public class Block
{

    public BlockHeader header;
    public String blockHash = null;
    public String prevHash;
//    public String rootHash; //hash of the the transaction(s)
    private long date; 
    public ArrayList<Tx> txMade;
    public int nonce; 
    public final int txLimit = 4;
    public int blocksize;

    public Block(Tx txMade, String prevHash){
        this.header = new BlockHeader(prevHash);


        this.prevHash = prevHash;
        this.date = System.currentTimeMillis();
        //this.blockHash = getHash();
        this.txMade.add(txMade);
        this.blocksize = 1;
//        setBlockTxHash();
    }

    // TO DO: implement merkle tree for more transactions */
//    // public void setBlockTxHash(){
//        this.rootHash = HashUtil.strToHexHash(HashUtil.hexFromKey(txMade.sender)+
//                                              HashUtil.hexFromKey(txMade.receiver)+
//                                              Float.toString(txMade.amtSent)+
//                                              txMade.txId);
//    // }

    /* for now, only works if the txLimit is a power of two :^) */
    public void setRootHash(){
        ArrayList<String> hexHashes = HashUtil.hashOfTxList(txMade);
        while(hexHashes.size() > 1){
            int len = hexHashes.size();
            System.err.println("Size: " + len);
            for(int i = 0; i < len/2; i++)
            {
                String left = hexHashes.remove(i);
                String right = hexHashes.remove(i);

                String hash = HashUtil.strToHexHash(left+right);
                hexHashes.add(i, hash);
            }
        }
        header.setRootHash(hexHashes.get(0));
    }

    public boolean addTx(Tx tx){
        if(blocksize == txLimit){
            setRootHash();
            return false;
        }
        blocksize++;
        txMade.add(tx);
        return true;
    }

    // public String getHash(){
    //     String preImage = prevHash + Long.toString(date) + rootHash + Integer.toString(nonce);
    //     try{
    //         return HashUtil.strToHexHash(preImage);
    //     }catch(Exception err) {
    //         System.err.println("error getting hash");
    //         System.err.println(preImage);
	// 		throw new RuntimeException(err);
    //     }
    // }

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
