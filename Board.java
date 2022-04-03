import java.util.Scanner;

public class Board {

    Player[] player;
    Scanner in = new Scanner(System.in);
    int sizeOfPlayer;
    AirPort airPort = new AirPort();
    Cinema[] cinemas = new Cinema[4];
    Ground[] grounds = new Ground[8];

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

    public static void main(String[] args) throws Exception {
        String command = input.next();
        if (!command.equals("create_game"))
            System.out.println("no game created");
        else
            create_game();

    }
    public static void showCommands(){
        System.out.println("Enter your command: 1-build, 2-buy, 3-sell, 4-fly\n" +
                "5-free, 6-invest, 7-index, 8-property, 9-time, 10-rank");
    }
    public static void doCinemaCommands(Cinema cinema,Player player){
        boolean cont1 = true;
        do {
            try {
                if (cinema.owner == null) {
                    System.out.println("1-buy  2-go ahead");
                    switch (input.nextInt()) {
                        case 1:
                            cinema.setOwner(player);
                            break;
                        case 2:
                            player.numberOfCell++;
                            break;
                        default: throw new WrongInput();
                    }
                }else
                    cinema.payToOwner(player);
            } catch (WrongInput e1) {
                cont1 = false;
            } catch (LowBalance l) {
                System.out.println(l.getMessage());
                System.out.println("Choose the property that you want to sell");
            }
        } while (!cont1);
    }

    public static void create_game() throws Exception {
        System.out.println("Enter number of players");
        int size = input.nextInt();
        while (size < 2 || size > 4) {
            System.out.println("wrong size");
            size = input.nextInt();
        }
        b1 = new Board(size);
        
        int countRound = 1;
        System.out.println("round " + countRound);

        int turn = 0;
        do {
            Player currentPlayer = b1.player[turn];
            System.out.println(currentPlayer.name + "'s turn");
            System.out.println("Enter dice number");
            int dice = input.nextInt();
            while (dice > 6 || dice < 1) {
                System.out.println("try again");
                dice = input.nextInt();
            }
            currentPlayer.numberOfCell += dice;

            if (currentPlayer.numberOfCell > 24) {
                countRound++;
                currentPlayer.numberOfCell = 1;
            }

            switch (currentPlayer.numberOfCell) {

            //Parking
                case 1:
                    break;

            //Airport
                case 3:
                case 11:
                case 20:
                    boolean cont = true;
                    do{
                        System.out.println("1-buy a ticket to travel\n2-go on");
                        switch (input.nextInt()) {
                            case 1: b1.airPort.buyTicket(currentPlayer);break;
                            case 2: break;
                            default:
                                cont = false;
                        }
                    }while (!cont);

            //Cinema
                case 4:
                    doCinemaCommands(b1.cinemas[0], currentPlayer );break;
                case 8:
                    doCinemaCommands(b1.cinemas[1], currentPlayer );break;
                case 15:
                    doCinemaCommands(b1.cinemas[2], currentPlayer );break;
                case 22:
                    doCinemaCommands(b1.cinemas[3], currentPlayer );break;
            //Road
                case 5:
                case 10:
                case 16:
                    currentPlayer.balance -= 100;
                    break;
            //Award
                case 6:
                    currentPlayer.balance += 200;
                    break;
            //Grounds
                case 2:

                    break;
                case 7:
                case 9:
                case 12:
                case 14:
                case 18:
                case 19:
                    currentPlayer.balance -= 80;
                    break;
                case 23:
                    currentPlayer.balance -= 100;
                    break;
            //Bank
                case 21:
                    break;
            // Tax
                case 17:
                    currentPlayer.balance -= currentPlayer.balance*10/100;
                    break;
            //Prison
                case 13:
                    currentPlayer.balance -= 101;
                    break;
            // Chance
                case 24:
                    currentPlayer.balance -= 100;
                    break;
            }
            System.out.println(currentPlayer.name + "'s balance: " + currentPlayer.balance);
            System.out.println(currentPlayer.name + "'s cell number: " + currentPlayer.numberOfCell + "\n");
            turn++;
            if (turn >= b1.sizeOfPlayer)
                turn = 0;
        } while (winner());

    }

    public static boolean winner() {

        for (int i = 0; i < b1.sizeOfPlayer; i++) {
            if (b1.player[i].balance <= 10) {
                b1.sizeOfPlayer--;
                System.out.println("Player " + (i + 1) + " lost\n");
                for (int j = i; j < b1.sizeOfPlayer; j++) {
                    b1.player[j] = b1.player[j + 1];
                }
            }
        }
        if (b1.sizeOfPlayer == 1) {
            System.out.println(b1.player[0].name + " is winner");
            return false;
        }
        return true;
    }
}