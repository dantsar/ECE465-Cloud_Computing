package coopercoin;

import java.util.ArrayList;

public class test {
	public static ArrayList<Block> blockchain = new ArrayList<Block>();
	public static int difficulty = 4;

	public static void main(String[] args) {
		for(int n = 0; n < 4; n++){
			String nString = n + "";
			if(n == 0){
				blockchain.add(new Block("Genesis block", "0"));
			}
			else{
				blockchain.add(new Block("Block " + nString, blockchain.get(n-1).blockHash));	
			}
			blockchain.get(n).mineBlock(difficulty);
			System.out.println("Block "+n+" mined!\t"+blockchain.get(n).blockHash);
		}
	}
}
