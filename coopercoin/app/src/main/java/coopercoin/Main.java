package coopercoin;

public class Main{

    public static void main(String [] args){
        String name = "Daniel Tsarev";
        Hash h = new Hash("SHA-256");
//        Security.addProvider(new BouncyCastleProvider());
//        System.out.println(name);
//        System.out.println(Hash.strToHexHash(name));


//        byte [] hash = h.strToHash(name);
//        System.out.println(h.bytesToHex(hash));
//
//        System.out.println(h.strToHexHash(name));
//        Block b = new Block("My name jeff");

        Wallet w = new Wallet();
        Transaction t = new Transaction();
        
        byte[] sign = t.signTrans(name, w.getPrivKey());
        System.out.println(Hash.bytesToHex(sign));

        if(t.verifySig(name, sign, w.getPubKey())){
            System.out.println("verified");
        }else{
            System.out.println("not verified");
        }

    }

}
