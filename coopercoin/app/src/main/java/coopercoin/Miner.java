package coopercoin;

public class Miner extends Thread
{
    public static int difficulty;
    public static Block globalBlock;
    public Block internalBlock;
    public static int nonce;
    public static int nonceRange = 100;
    public static boolean blockHashFoundFLAG;
    public static boolean waitingFLAG; /* set if waiting for new block */

    public Miner(Block startingBlock, int startingDifficulty){
        this.globalBlock = startingBlock;
        this.internalBlock = startingBlock;
        this.difficulty = startingDifficulty;
    }

    public void run(){
        /* forever running */
//        try{
//            Thread.sleep(1500);
//        }catch(Exception e){
//            System.out.println("error sleeping");
//        }
        System.out.println("miner running");
        while(true){
            while(mineBlock() == false);
            waitingFLAG = true;
            while(waitingFLAG == true);
        }
    }

    /* set blockMinedFLAG is the appropriate hash was found */
    synchronized public void blockHashFound(){
        blockHashFoundFLAG = true;
        globalBlock = internalBlock;
        this.nonce = 0;
        System.out.println("hash found");
    }

    synchronized public int getNonce(){
        nonce += 100;
        return nonce;
    }

    synchronized public void setBlock(Block newBlock){
        this.globalBlock = newBlock;
        this.internalBlock = newBlock;
        this.waitingFLAG = false;
    }

    public boolean mineBlock(){
        if(blockHashFoundFLAG){
            return true;
        }
        
        int internalNonce = getNonce();
        String blockHash = "haven't found it yet :'( ";
        String prefixString = new String(new char[difficulty]).replace('\0', '0');
        for(int i = internalNonce; i < internalNonce+100; i++)
        {
            if(blockHash.substring(0,difficulty).equals(prefixString)){
                System.out.println(blockHash);
                blockHashFound();
                return true;
            }
            internalBlock.nonce = i;
            blockHash = internalBlock.getHash();
        }

        return false;
    }

}
