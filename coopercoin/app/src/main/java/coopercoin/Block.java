package coopercoin;

import java.util.ArrayList;

public class Block
{
    public BlockHeader header;
    public ArrayList<Tx> txMade;
    public final int txLimit = 4;
    public int blocksize = 0;

    /* only really used for the genesis tx */
    public Block(Tx txMade, String prevHash){
        this.header = new BlockHeader(prevHash);
        this.blocksize = 0;
    }

    public Block(Tx txMade, BlockHeader prevHeader){
        this.header = new BlockHeader(prevHeader.getBlockHash());
        this.blocksize = 0;
    }

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

    public void setHeader(BlockHeader header){
        this.header = header;
    }

    public BlockHeader getHeader(){
        return this.header;
    }

    public String getHash(){
        return header.getBlockHash();
    }
    // synchronized public void setBlockHash(String hash){
    //     if(blockHash == null){
    //         blockHash = hash;
    //     }
    // }
}
