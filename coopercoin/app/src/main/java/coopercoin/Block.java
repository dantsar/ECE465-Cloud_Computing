package coopercoin;

import java.util.Date;

/**
 *
 *
 *
 */


public class Block
{
    public byte[] prevHashUtil; 
    public byte[] rootHashUtil;
    public byte[] blockHashUtil;
    public long date; 
    public String data;
    private int nonce; 
    


    public Block(String data){
        this.data = data;
//        this.prevHashUtil = prevHashUtil;
        this.date = new Date().getTime();
        this.nonce = 0;
        getHashUtil();
    }

    public void getHashUtil(){
        String stuff = data + date + nonce;
        System.out.println(HashUtil.strToHexHash(stuff));
    }



//    public byte[] getRootHashUtil(ArrayList<Tx> ){
//
//        
//    }


//    public byte[] mineBlock(int diff){
//        byte[] stuff;
//    }

    
}
