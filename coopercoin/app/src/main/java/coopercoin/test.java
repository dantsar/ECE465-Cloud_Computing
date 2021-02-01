package coopercoin;

import java.util.ArrayList;

public class test {
	public static ArrayList<Block> blockchain = new ArrayList<Block>();
	public static int difficulty = 4;
	public static Boolean isBlockchainValid(){
		boolean flag = true;
		String hashTarget = new String(new char[difficulty]).replace('\0', '0');
		for (int i = 0; i < blockchain.size(); i++) {
			String prevHash = i==0 ? "0" : blockchain.get(i-1).blockHash;
			flag = blockchain.get(i).blockHash.equals(blockchain.get(i).getHash())
				&& prevHash.equals(blockchain.get(i).prevHash)
			  	&& blockchain.get(i).getHash().substring(0, difficulty).equals(hashTarget);
				if (!flag) break;
		}
		return flag;
	}
	public static void main(String[] args) {
		for(int n = 0; n < 4; n++){
			String nString = n + "";
			if(n == 0){
//				blockchain.add(new Block("Genesis block", "0"));
			}
			else{
//				blockchain.add(new Block("Block " + nString, blockchain.get(n-1).blockHash));	
			}
//			blockchain.get(n).mineBlock(difficulty);
			System.out.println("Block "+n+" mined!\t"+blockchain.get(n).blockHash);
			System.out.println("Blockchain is Valid: " + isBlockchainValid() + "\n");
		}
	}
}
