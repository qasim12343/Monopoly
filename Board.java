import java.util.Scanner;

public class Board {

    Player[] player;
    Scanner in = new Scanner(System.in);
    int sizeOfPlayer;
    AirPort airPort = new AirPort();
    Cinema[] cinemas = {new Cinema(), new Cinema(), new Cinema(), new Cinema()};
    Ground[] grounds = {new Ground(),new Ground(),new Ground(),new Ground(),new Ground(),new Ground(),new Ground(),new Ground()};

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
                if (cinema.owner.name.equals("Bank")) {
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

    public static void create_game() {
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
            if(currentPlayer.numberOfCell == 13){
                if (dice == 1)
                    currentPlayer.numberOfCell += dice;
            }else {
                currentPlayer.numberOfCell += dice;
                if(dice == 6){
                    System.out.println("Enter dice number");
                    dice = input.nextInt();
                    while (dice > 6 || dice < 1) {
                        System.out.println("try again");
                        dice = input.nextInt();
                    }if(dice == 6)
                        currentPlayer.numberOfCell = 13;
                    else
                        currentPlayer.numberOfCell += dice;
                }
            }

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
                            case 1:
                                try {
                                    b1.airPort.buyTicket(currentPlayer);
                                } catch (LowBalance e) {
                                    System.out.println(e.getMessage());
                                } catch (WrongInput e) {
                                    cont = false;
                                }
                            case 2: break;
                            default:
                                cont = false;
                                break;
                        }
                    }while (!cont);
                    break;
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
                    System.out.println("Cost 100 $ for road");
                    currentPlayer.balance -= 100;
                    break;
            //Award
                case 6:
                    System.out.println("Achieve 200 from award");
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
                    if(currentPlayer.balance*10/100 > currentPlayer.balance)
                        System.out.println("1-sell property");
                    System.out.println("Cost "+currentPlayer.balance*10/100+ " for the tax");
                    currentPlayer.balance -= currentPlayer.balance*10/100;
                    break;
            //Prison
                case 13:
                    boolean cont2 = true;
                    do{
                        System.out.println("You are in prison");
                        System.out.println("1-release by chanceCard\n2-stay  3-pay 50 $ to release");
                        switch (input.nextInt()) {
                            case 1:
                                if(currentPlayer.chanceToRelease >=1){
                                    currentPlayer.numberOfCell++;break;
                                } else{
                                    System.out.println("You do not have enough card!");
                                    cont2 = false;
                                }break;

                            case 2:
                                if (currentPlayer.balance < 10){
                                    System.out.println("You do not have enough money to pay");
                                    System.out.println("1- Use another option\n2-sell property");    ///sell
                                    switch (input.nextInt()){
                                        case 1:cont2 = false;break;
                                        case 2:break;
                                    }
                                }
                                else
                                    currentPlayer.balance -= 10;
                                break;
                            case 3:
                                if (currentPlayer.balance < 50){
                                    System.out.println("You do not have enough money to pay");
                                    System.out.println("1- Use another option\n2-sell property");    ///sell
                                    switch (input.nextInt()){
                                        case 1:cont2 = false;break;
                                        case 2:break;
                                    }
                                }else{
                                    currentPlayer.balance-=50;
                                    currentPlayer.numberOfCell++;
                                }
                                break;
                        }
                    }while (!cont2);
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