package coopercoin;

public class Main{

    public static void main(String [] args){
        String name = "Daniel Tsarev";
        Hash h = new Hash("SHA-256");
//        System.out.println(name);
//        System.out.println(Hash.strToHexHash(name));


//        byte [] hash = h.strToHash(name);
//        System.out.println(h.bytesToHex(hash));
//
//        System.out.println(h.strToHexHash(name));
//        Block b = new Block("My name jeff");

        Wallet w = new Wallet();
        String sign = w.signString(name);
        w.signString("FUUUUUUUUCK JAVA");
    }

}
