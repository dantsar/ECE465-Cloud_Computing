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
        Security.addProvider(new BouncyCastleProvider());
        genKeyPair();
    }

    public void genKeyPair(){
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

    public PublicKey getPubKey(){
        return pubKey;
    }

    /* LMAOOO WHO CARE'S ABOUT SECURITY */
    /* will fix this later? */
    public PrivateKey getPrivKey(){
        return privKey;
    }

}
