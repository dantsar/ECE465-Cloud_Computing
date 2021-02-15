package coopercoin;

import java.util.Base64;
import java.security.Key;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Helper/Utility class that provides the functions needed for hashing
 * The class only needs to be initialized once with the algorithm 
 * that is provided once 
 */

public class HashUtil
{

    /* From https://www.baeldung.com/sha-256-hashing-java */
    public static String SHA256toHex(byte[] hash){
        try{
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            StringBuilder hexString = new StringBuilder(2 * hash.length);
            for (int i = 0; i < hash.length; i++) {
                String hex = Integer.toHexString(0xff & hash[i]);
                if(hex.length() == 1) {
                    hexString.append('0');
                }
                hexString.append(hex);
            }
            return hexString.toString();

        } catch(Exception err) {
			throw new RuntimeException(err);
		}
    }

    public static byte[] strToSHA256(String str){
        try{
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            md.update(str.getBytes());
            return md.digest(); 
        }catch(Exception e){
            throw new RuntimeException(e);
        }
    }

    public static String strToHexHash(String str){
        try{
            return SHA256toHex(strToSHA256(str));
        }catch(Exception e){
            throw new RuntimeException(e);
        }
    }

    /* https://stackoverflow.com/questions/52384809/public-key-to-string-and-then-back-to-public-key-java */
    public static String hexFromKey(Key key){
        if(key == null) return "0";
        return Base64.getEncoder().encodeToString(key.getEncoded());
    }

}
