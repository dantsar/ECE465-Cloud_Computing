


public class Main{

    public static void main(String [] args){
        String name = "Daniel Tsarev";
        Hash h = new Hash("SHA-256");

        byte [] hash = h.strToHash(name);
        System.out.println(h.bytesToHex(hash));

        System.out.println(h.strToHexHash(name));

    }

}
