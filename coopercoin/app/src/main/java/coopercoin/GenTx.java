package coopercoin;


public class GenTx implements Runnable{
    Wallet wallet;
    Wallet otherWallet;

    public GenTx(Wallet wallet, Wallet otherWallet){
        this.wallet = wallet;
        this.otherWallet = otherWallet;
    }

    public void run(){
        System.out.println("haha look at me Im a running thread");
    }

}
