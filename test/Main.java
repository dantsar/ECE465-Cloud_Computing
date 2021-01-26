import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import java.security.spec.EllipticCurve;


public class Main{

    /* From https://www.baeldung.com/sha-256-hashing-java */
    private static String bytesToHex(byte[] hash) {
        StringBuilder hexString = new StringBuilder(2 * hash.length);
        for (int i = 0; i < hash.length; i++) {
            String hex = Integer.toHexString(0xff & hash[i]);
            if(hex.length() == 1) {
                hexString.append('0');
            }
            hexString.append(hex);
        }
        return hexString.toString();
    }

    int x = 5;
    public static void main(String [] args)
    {
//        Main tes = new Main();
//        System.out.println("THE NUMBER IS: " + tes.x);
//        tes.x = 100;
//        System.out.println("THE NUMBER IS: " + tes.x);

//        OtherClass other = new OtherClass();
//
//        other.sayHello();
//        String myName = "Daniel Tsarev";
//        other.greetPerson(myName);


    /* playing around with SHA256 */
//    String testStr = "Hello there, my name is Daniel Tsarev!";
//
//    MessageDigest md = null;
//
//    try{
//        md = MessageDigest.getInstance("SHA-256");
//    }catch(NoSuchAlgorithmException e){
//        /* Log error later */
//        System.out.println("No such hash algorithm");
//    }
//    byte[] enc_msg = md.digest(testStr.getBytes());
//    
//    String hash = bytesToHex(enc_msg);
//    System.out.println("String: " + testStr);
//    System.out.println("Hashed string: " + hash);
    /* playing around with Elliptic Curve */
    
    System.out.println("hello there");
    }

 
}
