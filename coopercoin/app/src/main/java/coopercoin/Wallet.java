package coopercoin;

import java.security.*;
import java.security.Signature;
import java.security.spec.ECGenParameterSpec;
import java.util.ArrayList;
import org.bouncycastle.*;
import org.bouncycastle.jce.provider.BouncyCastleProvider;


public class Wallet{

    /* public and private key pairs */
    public PublicKey pubKey;
    public PrivateKey privKey;

    /* Array list of UTXOs (unspent transactions) */
    ArrayList<Transaction> UTXOs = new ArrayList<Transaction>();

    public Wallet(){
        genKeyPair();
    }

    public void genKeyPair(){
//        try{
//            KeyPairGenerator keyPair = KeyPairGenerator.getInstance("EC");
//            ECGenParameterSpec ecSpec = new ECGenParameterSpec("secp256k1");
//            keyPair.initialize(256);
//            KeyPair keys = keyPair.genKeyPair();
//
//            pubKey = keys.getPublic();
//            privKey = keys.getPrivate();
//        }catch(Exception e){
//            /* acutal error reporting later :^) */
//            System.out.println("Key Generation Failed");
//            System.out.println(e);
//        }
        Security.addProvider(new BouncyCastleProvider());
        try{
            KeyPairGenerator keyPair = KeyPairGenerator.getInstance("ECDSA", "BC");
            ECGenParameterSpec ecSpec = new ECGenParameterSpec("secp256k1");
            keyPair.initialize(ecSpec);
            KeyPair keys = keyPair.genKeyPair();

            pubKey = keys.getPublic();
            privKey = keys.getPrivate();
        }catch(Exception e){
            /* acutal error reporting later :^) */
            System.out.println("Key Generation Failed");
            System.out.println(e);
        }

    }


    public byte[] signTrans(String str){
        byte[] bytes = str.getBytes();
        byte[] sigBytes = null;
        try{
//            Signature sign = Signature.getInstance("SHA256withECDSA");
            Signature sign = Signature.getInstance("ECDSA", "BC");
            sign.initSign(privKey);
            sign.update(bytes);
            sigBytes = sign.sign();
        }catch(Exception e){
            System.out.println("Signature Failed");
            System.out.println(e);
        }
        return sigBytes;
    }

    public boolean verifySig(String data, byte[] sig){
        boolean v = false;
        try{
            Signature sign = Signature.getInstance("ECDSA", "BC");
            sign.initVerify(pubKey);
            sign.update(data.getBytes());
            v = sign.verify(sig);
        }catch(Exception e){
            System.out.println("Signature Failed");
            System.out.println(e);
        }
        return v;
    }

}
