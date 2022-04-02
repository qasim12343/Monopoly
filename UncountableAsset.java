import java.util.Scanner;

public class UncountableAsset {
    Scanner input = new Scanner(System.in);
    Player player;
    public UncountableAsset(Player player) {
        this.player = player;
    }
    public UncountableAsset(){}
}


class AirPort extends UncountableAsset {
    public void buyTicket(){
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
            default -> throw new ArithmeticException("wrong input");  /// must be defined an exception
        }
    }
    public void goOn(){
        player.numberOfCell++;
    }
}

class Tax extends UncountableAsset {
    public void payTax(){
        player.balance -= player.balance*10/100;
    }

}

class Chance extends UncountableAsset {
    public void getMoney(){
        player.balance+=200;
    }
    public void goToPrison(){
        player.numberOfCell = 13;
    }
    public void payToBank(){
        player.balance -= player.balance*10/100;
    }
    public void goThreeCellsAhead(){
        player.numberOfCell+=3;
    }
    public void chanceToReleasePrison(){ ///must be completed

    }


}

class Award extends UncountableAsset {
    public void addBalance(){
        player.balance +=200;
    }

}

class Road extends UncountableAsset {
    public void pay(){
        player.balance -= 100;
    }

}

class Prison extends UncountableAsset {

}

class Ground extends UncountableAsset {
    Player Owner;

    public void setOwner() {
        player.balance -= 100;
        Owner = player;
    }
}
class cinema extends UncountableAsset {
    int number;
    Player owner;
    public void payToOwner(){

    }
    public void setOwner() {
        player.balance -= 100;
        owner = player;
    }
}



