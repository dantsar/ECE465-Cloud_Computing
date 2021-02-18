package coopercoin;

import java.util.ArrayList;

public class Block
{

    public BlockHeader header;
    public String blockHash = null;
    public String prevHash;
    public String rootHash; //hash of the the transaction(s)
    private long date; 
    public ArrayList<Tx> txMade;
    public int nonce; 
    public final int txLimit = 8;
    public int blocksize;

    public Block(Tx txMade, String prevHash){
        this.header = new BlockHeader(prevHash);


        this.prevHash = prevHash;
        this.date = System.currentTimeMillis();
        this.blockHash = getHash();
        this.txMade.add(txMade);
        this.blocksize = 1;
//        setBlockTxHash();
    }

    // TO DO: implement merkle tree for more transactions */
    public void setBlockTxHash(){
        // this.rootHash = HashUtil.strToHexHash(HashUtil.hexFromKey(txMade.sender)+
        //                                       HashUtil.hexFromKey(txMade.receiver)+
        //                                       Float.toString(txMade.amtSent)+
        //                                       txMade.txId);
    }

    public void setRootHash(){
        
        while(txMade.size() != 1){
            int len = txMade.size();
            for(int i = 0; i < len; i++)
            {
                
                /* apply a crappy merkle tree algorithm */

            }
        }


    }

    public boolean addTx(Tx tx){
        if(txLimit == 10){
            return false;
        }
        blocksize++;
        txMade.add(tx);
        return true;
    }

    public String getHash(){
        String preImage = prevHash + Long.toString(date) + rootHash + Integer.toString(nonce);
        try{
            return HashUtil.strToHexHash(preImage);
        }catch(Exception err) {
            System.err.println("error getting hash");
            System.err.println(preImage);
			throw new RuntimeException(err);
        }
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
