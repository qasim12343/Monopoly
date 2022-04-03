import java.util.Scanner;

public class UncountableAsset {
    Scanner input = new Scanner(System.in);

}
class LowBalance extends Exception{
    public LowBalance() {
        super("You do not have enough money");
    }
}
class WrongInput extends Exception{
    public WrongInput() {
        super("Wrong input! try again");
    }
}
class AirPort extends UncountableAsset {


    public void buyTicket(Player player) throws LowBalance, WrongInput {
        if(player.balance < 50)
            throw new LowBalance();
        player.balance -= 50;
        System.out.println("Choose location to travel");
        if(player.numberOfCell == 3) System.out.println("11 or 20");
        else if(player.numberOfCell == 11) System.out.println("3 or 20");
        else if(player.numberOfCell == 20) System.out.println("3 or 11");

        int inp = input.nextInt();

        switch (inp) {
            case 3 -> player.numberOfCell = 3;
            case 11 -> player.numberOfCell = 11;
            case 20 -> player.numberOfCell = 20;
            default -> throw new WrongInput();  /// must be defined an exception
        }
    }
}

class Chance extends UncountableAsset {

    public void getMoney(Player player){
        player.balance+=200;
    }
    public void goToPrison(Player player){
        player.numberOfCell = 13;
    }
    public void payToBank(Player player){
        player.balance -= player.balance*10/100;
    }
    public void goThreeCellsAhead(Player player){
        player.numberOfCell+=3;
    }
    public void chanceToReleasePrison(){ ///must be complete

    }
}

class Prison extends UncountableAsset {


}

class Ground extends UncountableAsset {
    Player Owner;
    public void setOwner(Player player) {
        player.balance -= 100;
        Owner = player;
    }
}

class Cinema extends UncountableAsset {
    int number;
    Player owner;


    public void payToOwner(Player player) throws LowBalance {
        if(!player.equals(owner)){
            if(owner.numberOfCinemas == 1){
                if(player.balance >= 25){
                    owner.balance += 25;
                    player.balance -= 25;
                }else
                    throw new LowBalance();
            }
            else if(owner.numberOfCinemas == 2){
                if(player.balance >= 50){
                    owner.balance += 50;
                    player.balance -= 50;
                }else
                    throw new LowBalance();
            }
            else if(owner.numberOfCinemas >= 3){
                if(player.balance >= 100){
                    owner.balance += 100;
                    player.balance -= 100;
                }else
                    throw new LowBalance();
            }
        }
    }
    public void setOwner(Player player) throws LowBalance {
        if(player.balance < 200)
            throw new LowBalance();
        player.balance -= 200;
        player.numberOfCinemas++;
        owner = player;
    }
}
class Bank {
    void deposit(Player player){
        if(player.balance <= 1)
            System.out.println("Your balance is low");
        else{
            player.balance -= player.balance/2;
            player.depositCard++;
        }
    }
    void useDepositCard(Player player){
        if(player.depositCard>=1)
            player.balance += player.depositRemain*2;
        else
            System.out.println("You don't have enough card");
    }
}



