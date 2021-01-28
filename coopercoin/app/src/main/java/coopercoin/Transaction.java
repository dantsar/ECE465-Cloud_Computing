package coopercoin; 

import java.security.*;
import java.security.Signature;
import org.bouncycastle.*;
import org.bouncycastle.jce.provider.BouncyCastleProvider;


public class Transaction{
    
    public Transaction(){
        System.out.println("pure pain");
        Security.addProvider(new BouncyCastleProvider());
    }

    public byte[] signTrans(String str, PrivateKey privKey){
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

    public boolean verifySig(String data, byte[] sig, PublicKey pubKey){
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
