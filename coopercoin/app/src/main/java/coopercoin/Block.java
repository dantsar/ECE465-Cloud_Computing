package coopercoin;

public class Block
{
    // public byte[] prevHashUtil; 
    // public byte[] rootHashUtil;
    // public byte[] blockHashUtil;

    public String blockHash;
    public String prevHash;
    public long date; 
    public String data;
    private int nonce; 

    public Block(String data, String prevHash, long date){
        this.prevHash = prevHash;
//        this.blockHash = getHashUtil();
        this.data = data;
        this.date = date;
        this.nonce = 0;
    }

//    public String getHashUtil(){
//        String hash = HashUtil.SHA256toHex(prevHash + data + date + nonce);
//        return hash;
//    }

    // public String mineBlock(int prefix) {
    //     String prefixString = new String(new char[prefix]).replace('\0', '0');
    //     while (!hash.substring(0, prefix).equals(prefixString)) {
    //         nonce++;
    //         hash = calculateBlockHash();
    //     }
    //     return hash;
    // }

    
}
