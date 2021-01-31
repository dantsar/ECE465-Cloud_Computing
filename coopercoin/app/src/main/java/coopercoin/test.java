package coopercoin;

import java.util.Date;

public class test {
    public static void main(String[] args) {
		Block genesisBlock = new Block("Genesis block", "0",  new Date().getTime());
		System.out.println("Hash for block 1 : " + genesisBlock.blockHash);
		
		Block secondBlock = new Block("Second block",genesisBlock.blockHash, new Date().getTime());
		System.out.println("Hash for block 2 : " + secondBlock.blockHash);
		
		Block thirdBlock = new Block("Third block",secondBlock.blockHash,  new Date().getTime());
		System.out.println("Hash for block 3 : " + thirdBlock.blockHash);
		
	}
}
