public class UncountableAsset {
    int balance;
    public UncountableAsset(int balance){
        this.balance = balance;
    }
}


class BankPossessions extends UncountableAsset{
    public BankPossessions(int balance) {
        super(balance);
    }

    public void airPort(){

    }
    public void tax(){

    }
    public void chance(){

    }
    public void award(){

    }
    public void road(){

    }
    public void prison(){}

}
class PlayerPossessions extends UncountableAsset{
    public PlayerPossessions(int balance) {
        super(balance);
    }
    public void grounds(){}
    public void cinema(){}

}
