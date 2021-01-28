package coopercoin;

import java.util.Date;

/**
 *
 *
 *
 */


public class Block
{
    public byte[] prevHash; 
    public byte[] rootHash;
    public byte[] blockHash;
    public long date; 
    public String data;
    private int nonce; 
    


    public Block(String data){
        this.data = data;
//        this.prevHash = prevHash;
        this.date = new Date().getTime();
        this.nonce = 0;
        getHash();
    }

    public void getHash(){
        String stuff = data + date + nonce;
        System.out.println(Hash.strToHexHash(stuff));
    }



//    public byte[] getRootHash(ArrayList<Transaction> ){
//
//        
//    }


//    public byte[] mineBlock(int diff){
//        byte[] stuff;
//    }

    
}
