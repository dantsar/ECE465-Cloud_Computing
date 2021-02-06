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
    
    /* set blockMinedFLAG if the appropriate hash was found */
    synchronized public void blockHashFound(){
        if(blockHashFoundFLAG.get()){ /* hash was already found */
            return;
        }
        blockHashFoundFLAG.set(true);
        globalBlock = internalBlock;
        this.nonce = 0;
    }

    synchronized public int getNonce(){
        nonce += 100;
        return nonce;
    }

    synchronized public void setBlock(Block newBlock){
        globalBlock = newBlock;
        internalBlock = new Block(newBlock.txMade, newBlock.prevHash);
        waitingFLAG.set(false);
        blockHashFoundFLAG.set(false);
    }

    public static Block getMinedBlock(){
        return globalBlock;
    }

    public boolean mineBlock(){
        int internalNonce = getNonce();
        String blockHash = null;
        String prefixString = new String(new char[difficulty]).replace('\0', '0');
        for(int i = internalNonce; i < internalNonce+nonceRange; i++)
        {
           if(blockHashFoundFLAG.get()){ /* return because hash was already found */
                return true;
            }

            internalBlock.nonce = i;
            blockHash = internalBlock.getHash();

            if(blockHash.substring(0,difficulty).equals(prefixString)){
                internalBlock.blockHash = blockHash;
                blockHashFound();
                return true;
            }
        }

        return false;
    }

}
