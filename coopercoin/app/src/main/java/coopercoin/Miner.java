package coopercoin;

import java.util.concurrent.atomic.AtomicBoolean;


public class Miner extends Thread
{
    public static int difficulty;
    public static int nonce;
    private final int nonceRange = 10000;
    public static BlockHeader globalHeader;
    public BlockHeader internalHeader;

    /* flags for Miners */
    public static AtomicBoolean blockHashFoundFLAG = new AtomicBoolean(false);
    public static AtomicBoolean waitingFLAG = new AtomicBoolean(false); /* set if waiting for new block */

    public Miner(int startingDifficulty){
        this.difficulty = startingDifficulty;
        waitingFLAG.set(true);
    }

    public void run(){
        while(true){
            try{

                while(waitingFLAG.get() == true) Thread.yield();
                this.internalHeader = new BlockHeader(globalHeader);

                /* Mine the block */
                while(mineBlock() == false);
                waitingFLAG.set(true);

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
        System.out.println("Found: " + internalHeader.blockHash);
        globalHeader = internalHeader;
        this.nonce = 0;
    }

    synchronized private int getNonce(){
        nonce += nonceRange;
        return nonce;
    }

    synchronized public static void setBlock(BlockHeader newHeader){
        globalHeader = newHeader;
        waitingFLAG.set(false);
        blockHashFoundFLAG.set(false);
    }

    public boolean mineBlock(){
        // internalHeader = new BlockHeader(globalHeader);
        int internalNonce = getNonce();
        String blockHash = null;
        String prefixString = new String(new char[difficulty]).replace('\0', '0');
        for(int i = internalNonce; i < internalNonce+nonceRange; i++)
        {
            if(blockHashFoundFLAG.get()){ /* return because hash was already found */
                return true;
            }
            
            blockHash = internalHeader.testHash(i);

            if(blockHash.substring(0,difficulty).equals(prefixString)){
                internalHeader.blockHash = blockHash;
                blockHashFound();
                return true;
            }

        }
        return false;
    }

    public static BlockHeader getMinedHeader(){
        return globalHeader;
    }

    /* test and wait */
    public static void TAW(){
        try{
            while(Miner.blockHashFoundFLAG.get() == false) Thread.sleep(100); /* waits until hash is found */
        }catch(Exception e){
            System.out.println("problem");
        }
    }
    

}
