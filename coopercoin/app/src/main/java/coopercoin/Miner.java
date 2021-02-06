package coopercoin;

import java.util.concurrent.atomic.AtomicBoolean;
public class Miner extends Thread
{
    public static int difficulty;
    public static Block globalBlock;
    public Block internalBlock;
    public static int nonce;
    public static int nonceRange;

    /* flags for Miners */
    public static AtomicBoolean blockHashFoundFLAG = new AtomicBoolean(false);
    public static AtomicBoolean waitingFLAG = new AtomicBoolean(false); /* set if waiting for new block */

    public Miner(Block startingBlock, int startingDifficulty){
        this.globalBlock = startingBlock;
        this.internalBlock = new Block(startingBlock.txMade, startingBlock.prevHash);
        this.difficulty = startingDifficulty;
        this.nonce = 0;
        this.nonceRange = 10000;
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
            try{
                while(mineBlock() == false) Thread.yield();
                waitingFLAG.set(true);
                //return;
                while(waitingFLAG.get() == true) Thread.yield();
            }catch(Exception e){
                System.err.println(e);
            }
        }
    }
    
//    synchronized public 

    /* set blockMinedFLAG is the appropriate hash was found */
    synchronized public void blockHashFound(){
        blockHashFoundFLAG.set(true);
        globalBlock = internalBlock;
        this.nonce = 0;
        System.err.println("hash found");
    }

    synchronized public int getNonce(){
        nonce += 100;
        return nonce;
    }

    synchronized public void setBlock(Block newBlock){
        this.globalBlock = newBlock;
        this.internalBlock = new Block(newBlock.txMade, newBlock.prevHash);
        this.waitingFLAG.set(false);
        this.blockHashFoundFLAG.set(false);
    }

    public boolean mineBlock(){
       if(blockHashFoundFLAG.get()){
            return true;
        }

        System.err.println("Mining Block");        

        int internalNonce = getNonce();
        String blockHash = "bbbbbb";
        String prefixString = new String(new char[difficulty]).replace('\0', '0');
        for(int i = internalNonce; i < internalNonce+nonceRange; i++)
        {
            internalBlock.nonce = i;
            blockHash = internalBlock.getHash();

            if(blockHash.substring(0,difficulty).equals(prefixString)){
                System.out.println(blockHash);
                blockHashFound();
                return true;
            }
        }

        return false;
    }

}
