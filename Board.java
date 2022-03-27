import java.util.Scanner;

public class Board {
    int[] cells = new int[24];
    Player[] player;
    Scanner in = new Scanner(System.in);
    int sizeOfPlayer;

    public Board(int sizeOfPlayer) {
        this.sizeOfPlayer = sizeOfPlayer;
        player = new Player[sizeOfPlayer];
        for (int i = 0; i < sizeOfPlayer; i++) {
            System.out.println("Enter player " + (i + 1) + " name");
            String name = in.next();
            player[i] = new Player(name);//set names
        }
    }

}

class test {
    public static Board b1;
    public static Scanner input = new Scanner(System.in);

    public static void main(String[] args) {
        String command = input.next();
        if (!command.equals("create_game"))
            System.out.println("no game created");
        else
            create_game();

    }

    public static void create_game() {
        System.out.println("Enter number of players");
        int size = input.nextInt();
        while (size < 2 || size > 4) {
            System.out.println("wrong size");
            size = input.nextInt();
        }
        b1 = new Board(size);

        if (input.next().equals("start_game")){
            int countRound = 1;
            System.out.println("round "+countRound);

            int turn = 0;
            do {
                System.out.println(b1.player[turn].name + "'s turn");
                System.out.println("Enter dice number");
                int dice = input.nextInt();
                while (dice > 6 || dice < 1) {
                    System.out.println("try again");
                    dice = input.nextInt();
                }
                b1.player[turn].numberOfCell += dice;

                if (b1.player[turn].numberOfCell > 24){
                    countRound++;
                    b1.player[turn].numberOfCell = 1;
                }

                switch (b1.player[turn].numberOfCell){
                    case 1:
                        System.out.println("Enter your command: 1-build, 2-buy, 3-sell, 4-fly\n" +
                                "5-free, 6-invest, 7-index, 8-property, 9-time, 10-rank");
                        switch (input.nextInt()){
                            case 1: break;
                            case 2: break;
                            case 3: break;
                            case 4: break;
                            case 5: break;
                            case 6: break;
                            case 7: break;
                            case 8: break;
                            case 9: break;
                            case 10: break;

                            default:
                                System.out.println("wrong command");
                                break;
                        }
                        break;
                    case 2: b1.player[turn].balance -= 100; break;
                    case 3: b1.player[turn].balance -= 300; break;
                    case 4: b1.player[turn].balance -= 400; break;
                    case 5: b1.player[turn].balance -= 500; break;
                    case 6: b1.player[turn].balance -= 110; break;
                    case 7: b1.player[turn].balance -= 120; break;
                    case 8: b1.player[turn].balance -= 140; break;
                    case 9: b1.player[turn].balance -= 10; break;
                    case 10: b1.player[turn].balance -= 1; break;
                    case 11: b1.player[turn].balance -= 103; break;
                    case 12: b1.player[turn].balance -= 105; break;
                    case 13: b1.player[turn].balance -= 101; break;
                    case 14: b1.player[turn].balance -= 301; break;
                    case 15: b1.player[turn].balance -= 40; break;
                    case 16: b1.player[turn].balance -= 50; break;
                    case 17: b1.player[turn].balance -= 60; break;
                    case 18: b1.player[turn].balance -= 70; break;
                    case 19: b1.player[turn].balance -= 80; break;
                    case 20: b1.player[turn].balance -= 90; break;
                    case 21: break;
                    case 22: b1.player[turn].balance -= 100; break;
                    case 23: b1.player[turn].balance -= 100; break;
                    case 24: b1.player[turn].balance -= 100; break;
                }
                System.out.println(b1.player[turn].name+"'s balance: "+b1.player[turn].balance);
                System.out.println(b1.player[turn].name+"'s cell number: "+b1.player[turn].numberOfCell+"\n");
                turn++;
                if (turn >= b1.sizeOfPlayer)
                    turn = 0;
            }while (winner());
        }
    }
    public static boolean winner(){

        for (int i = 0; i < b1.sizeOfPlayer; i++) {
            if(b1.player[i].balance <= 10){
                b1.sizeOfPlayer--;
                System.out.println("Player "+(i+1)+" lost\n");
                for (int j = i; j < b1.sizeOfPlayer; j++) {
                    b1.player[j] = b1.player[j+1];
                }
            }
        }
        if(b1.sizeOfPlayer == 1){
            System.out.println(b1.player[0].name+" is winner");
            return false;
        }
        return true;
    }
}