package coopercoin;

import java.security.MessageDigest;

public class Block
{
    public String blockHash;
    public String prevHash;
    private long date; 
    public String data;
    private int nonce; 

    public Block(String data, String prevHash){
        this.prevHash = prevHash;
        this.data = data;
        this.date = System.currentTimeMillis();
        this.blockHash = getHash();
    }

   public String getHash(){
        String preImage = prevHash + data + date + Integer.toString(nonce);
        try{
            MessageDigest digest = MessageDigest.getInstance("SHA-256");  
            byte[] hash = digest.digest(preImage.getBytes());
            String hexHash = HashUtil.SHA256toHex(hash);
            return hexHash;
        }
        catch(Exception err) {
			throw new RuntimeException(err);
		}
    }

    public void mineBlock(int prefix) {
        String prefixString = new String(new char[prefix]).replace('\0', '0');
        while (!blockHash.substring(0, prefix).equals(prefixString)) {
            nonce++;
            blockHash = getHash();
        }
    }
}
