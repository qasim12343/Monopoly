import java.util.Random;
import java.util.Scanner;

public class Play {
    static boolean Continue = true;
    public static Board b1;
    public static Scanner input = new Scanner(System.in);

    public static void main(String[] args) throws Exception {
        String command = input.next();
        if (!command.equals("create_game"))
            System.out.println("no game created");
        else
            create_game();

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
                            player.cinemas.add(cinema);
                            break;
                        case 2:
                            break;
                        default: throw new WrongInput();
                    }
                }else
                    cinema.payToOwner(player);
            } catch (WrongInput e1) {
                cont1 = false;
            } catch (LowBalance l) {
                System.out.println(l.getMessage());
                player.sellProperty();                                          // sell 
                cont1 = false;
            }
        } while (!cont1);
    }

    public static void doGroundCommands(Ground ground, Player player) {

        if (!ground.owner.name.equals("Bank") && !ground.owner.equals(player)) {
            try {
                ground.payToOwner(player);
            } catch (LowBalance e) {
                player.sellProperty();
            }
        }

        else{
            boolean cont1 = true;
            do {
                try {
                    System.out.println(ground.name + "'s " + ground.owner.name);
                    System.out.println("1-buy  2-build  3-go ahead ");
                    switch (input.nextInt()) {
                        case 1:
                            if (!ground.owner.name.equals("Bank")) {
                                System.out.println("you can not buy");
                                cont1 = false;
                            } else {
                                ground.setOwner(player);
                                player.grounds.add(ground);
                                break;
                            }
                            break;
                        case 2:
                            if (player.equals(ground.owner)) {
                                ground.build(player);
                            } else {
                                System.out.println("You can not build");
                                cont1 = false;
                            }
                            break;
                        case 3:
                            break;
                        default:
                            throw new WrongInput();
                    }
                } catch (WrongInput e1) {
                    cont1 = false;
                } catch (LowBalance l) {
                    System.out.println(l.getMessage());
                    player.sellProperty();
                    cont1 = false;
                }
            } while (!cont1);
        }
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
            if(currentPlayer.index == 13){
                if (dice == 1)
                    currentPlayer.index += dice;
            }else {
                currentPlayer.index += dice;
                if(dice == 6){
                    System.out.println("Enter dice number");
                    dice = input.nextInt();
                    while (dice > 6 || dice < 1) {
                        System.out.println("try again");
                        dice = input.nextInt();
                    }if(dice == 6)
                        currentPlayer.index = 13;
                    else
                        currentPlayer.index += dice;
                }
            }

            if (currentPlayer.index > 24) {
                countRound++;
                currentPlayer.index = 1;
            }

            switch (currentPlayer.index) {

            //Parking
                case 1:
                    break;

            //Airport
                case 3:
                case 11:
                case 20:
                    boolean cont = true;
                    do{
                        System.out.println("You are in Airport");
                        System.out.println("1-buy a ticket to travel   2-go on");
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
                    currentPlayer.addBalance(-100);
                    break;
            //Award
                case 6:
                    System.out.println("Achieve 200 from award");
                    currentPlayer.addBalance(200);
                    break;
            //Grounds
                case 2:
                    doGroundCommands(b1.grounds[0], currentPlayer);break;
                case 7:
                    doGroundCommands(b1.grounds[1], currentPlayer);break;
                case 9:
                    doGroundCommands(b1.grounds[2], currentPlayer);break;
                case 12:
                    doGroundCommands(b1.grounds[3], currentPlayer);break;
                case 14:doGroundCommands(b1.grounds[4], currentPlayer);break;
                case 18:doGroundCommands(b1.grounds[5], currentPlayer);break;
                case 19:doGroundCommands(b1.grounds[6], currentPlayer);break;
                case 23:doGroundCommands(b1.grounds[7], currentPlayer);break;
                    
            //Bank
                case 21:
                    break;
            // Tax
                case 17:
                    if(currentPlayer.getBalance()*10/100 > currentPlayer.getBalance()){
                        System.out.println("1-sell property  2-use taxCard");
                        boolean con = true;
                        do{
                            switch (input.nextInt()){
                                case 1 : currentPlayer.sellProperty();break;
                                case 2 : if(currentPlayer.TaxCard >= 1){
                                    currentPlayer.TaxCard--;
                                    }else System.out.println("You do not have taxCard");break;
                                default: con = false;
                            }
                        }while (!con);
                    }
                    else {
                        System.out.println("Cost " + currentPlayer.getBalance() * 10 / 100 + " for the tax");
                        currentPlayer.addBalance(-currentPlayer.getBalance() * 10 / 100);
                    }break;
            //Prison
                case 13:
                    boolean cont2 = true;
                    System.out.println("You are in prison");
                    do{
                        System.out.println("1-release by chanceCard\n2-stay  3-pay 50 $ to release");
                        switch (input.nextInt()) {
                            case 1:
                                if(currentPlayer.chanceToRelease >=1){
                                    currentPlayer.chanceToRelease--;
                                    currentPlayer.index++;break;
                                } else{
                                    System.out.println("You do not have enough card!");
                                    cont2 = false;
                                }break;

                            case 2:
                                if (currentPlayer.getBalance() < 10){
                                    System.out.println("You do not have enough money to pay");
                                    System.out.println("1- Use another option\n2-sell property");    ///sell
                                    switch (input.nextInt()){
                                        case 1:cont2 = false;break;
                                        case 2:break;
                                    }
                                }
                                else
                                    currentPlayer.addBalance(-10);
                                break;
                            case 3:
                                if (currentPlayer.getBalance() < 50){
                                    System.out.println("You do not have enough money to pay");
                                    System.out.println("1- Use another option\n2-sell property");    ///sell
                                    switch (input.nextInt()){
                                        case 1:cont2 = false;break;
                                        case 2:break;
                                    }
                                }else{
                                    currentPlayer.addBalance(-50);
                                    currentPlayer.index++;
                                }
                                break;
                        }
                    }while (!cont2);
                    break;
            // Chance
                case 24:
                    System.out.println("Chance cell");
                    switch (new Random().nextInt(6)){
                        case 0 : b1.chance.getMoney(currentPlayer);break;
                        case 1 : b1.chance.goToPrison(currentPlayer);break;
                        case 2 : b1.chance.payToBank(currentPlayer);break;
                        case 3 : b1.chance.goThreeCellsAhead(currentPlayer);break;
                        case 4 : b1.chance.chanceToReleasePrison(currentPlayer);break;
                        case 5 : b1.chance.TaxCard(currentPlayer);break;
                        case 6 : b1.chance.pay10$ToPlayers(b1.player, b1.sizeOfPlayer, currentPlayer);break;

                    }
                    break;
            }
            for (int i = 0; i < b1.sizeOfPlayer; i++) {
                System.out.print(b1.showDetails(b1.player[i])+"|");
            }
            turn++;
            if (turn >= b1.sizeOfPlayer)
                turn = 0;
        } while (winner());

    }

    public static boolean winner() {

        for (int i = 0; i < b1.sizeOfPlayer; i++) {
            if (b1.player[i].getBalance() <= 10) {
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