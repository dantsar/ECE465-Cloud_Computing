package coopercoin;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;


/**
 * Helper/Utility class that provides the functions needed for hashing
 * The class only needs to be initialized once with the algorithm 
 * that is provided once 
 */

public class Hash
{
    /* basically a global class*/
    private static MessageDigest md = null;

    public Hash(String algo){
        try{
            md = MessageDigest.getInstance(algo);
        }catch(NoSuchAlgorithmException e){
            /* actual logging will be done later :^) */
            System.out.println("Algorithm Not Found!!!");
        }
    }

    /* From https://www.baeldung.com/sha-256-hashing-java */
    public static String bytesToHex(byte[] hash){
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

    public static byte[] strToHash(String str){
        byte[] hash = md.digest(str.getBytes()); 
        return hash;
    }

    /* quality of life */
    public static String strToHexHash(String str){
        String hash = bytesToHex(strToHash(str));
        return hash;
    }

}
