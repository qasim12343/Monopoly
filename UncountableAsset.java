import java.util.Scanner;

public class UncountableAsset {
    Scanner input = new Scanner(System.in);
    int balance;
    Player player;
    public UncountableAsset(int balance, Player player) {
        this.balance = balance;
        this.player = player;
    }
    public UncountableAsset(){}
}


class AirPort extends UncountableAsset {
    public void buyTicket(){
        balance -= 50;
        System.out.println("Choose location to travel");
        if(player.numberOfCell == 3) System.out.println("11 or 20");
        else if(player.numberOfCell == 11) System.out.println("3 or 20");
        else if(player.numberOfCell == 20) System.out.println("3 or 11");

        int inp = input.nextInt();

        switch (inp) {
            case 3 -> player.numberOfCell = 3;
            case 11 -> player.numberOfCell = 11;
            case 20 -> player.numberOfCell = 20;
            default -> throw new ArithmeticException("wrong input");
        }
    }
    public void goOn(){
        player.numberOfCell++;
    }
}

class Tax extends UncountableAsset {

}

class Chance extends UncountableAsset {

}

class Award extends UncountableAsset {

}

class Road extends UncountableAsset {

}

class Prison extends UncountableAsset {

}

class Ground extends UncountableAsset {

}



