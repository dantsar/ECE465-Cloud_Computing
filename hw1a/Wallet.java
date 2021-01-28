package badcoin;

import java.security.*;
import java.security.Signature;
import java.security.spec.ECGenParameterSpec;
import java.util.ArrayList;


public class Wallet{

    /* public and private key pairs */
    public PublicKey pubKey;
    private PrivateKey privKey;

    /* Array list of UTXOs (unspent transactions) */
    ArrayList<Transaction> UTXOs = new ArrayList<Transaction>();

    public Wallet(){
        genKeyPair();
    }

    public void genKeyPair(){
        try{
            KeyPairGenerator keyPair = KeyPairGenerator.getInstance("EC");
//            ECGenParameterSpec ecSpec = new ECGenParameterSpec("secp256k1");
            keyPair.initialize(256);
            KeyPair keys = keyPair.genKeyPair();

            pubKey = keys.getPublic();
            privKey = keys.getPrivate();
        }catch(Exception e){
            /* acutal error reporting later :^) */
            System.out.println("Key Generation Failed");
            System.out.println(e);
        }
//        try{
//            KeyPairGenerator keyPair = KeyPairGenerator.getInstance("ECDSA", "BC");
//            ECGenParameterSpec ecSpec = new ECGenParameterSpec("secp256k1");
//            keyPair.initialize(ecSpec);
//            KeyPair keys = keyPair.genKeyPair();
//
//            pubKey = keys.getPublic();
//            privKey = keys.getPrivate();
//        }catch(Exception e){
//            /* acutal error reporting later :^) */
//            System.out.println("Key Generation Failed");
//            System.out.println(e);
//        }

    }


    public String signString(String str){
        byte[] bytes = str.getBytes();
        byte[] sigBytes = null;
        String retStr = null;
        try{
            Signature sign = Signature.getInstance("SHA256withECDSA");
            sign.initSign(privKey);
            sign.update(bytes);
            sigBytes = sign.sign();
        }catch(Exception e){
            System.out.println("Signature Failed");
            System.out.println(e);
        }
        System.out.println("SIGNED: " + Hash.bytesToHex(sigBytes));
        return retStr;
        
    }

}
