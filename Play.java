import java.util.Random;
import java.util.Scanner;

public class Play {

    static boolean Continue = true;
    public static Board b1 = Board.getInstance();
    public static Scanner input = new Scanner(System.in);

    public static void main(String[] args) throws Exception {

//        String command = input.next();
//        if (!command.equals("create_game"))
//            System.out.println("no game created");
//        else
            create_game();

    }

    public static void doCinemaCommands(Cinema cinema, Player player) {
        System.out.println(cinema.owner.getName() + "'s " + cinema.name);
        do {
            Continue = true;
            try {
                if (cinema.owner.getName().equals("Bank")) {
                    System.out.println("1-buy  2-Continue");
                    switch (input.nextInt()) {
                        case 1:
                            cinema.setOwner(player);
                            player.cinemas.add(cinema);
                            break;
                        case 2:cinema.payToOwner(player);
                            break;
                        default:
                            throw new WrongInput();
                    }
                } else
                    cinema.payToOwner(player);
            } catch (WrongInput e1) {
                Continue = false;
            } catch (LowBalance l) {
                System.out.println(l.getMessage());
                player.sellProperty();
                Continue = false;
            }
        } while (!Continue);
    }

    public static void doGroundCommands(Ground ground, Player player) {

        if (!ground.owner.getName().equals("Bank") && !ground.owner.equals(player)) {
            try {
                ground.payToOwner(player);
            } catch (LowBalance e) {
                player.sellProperty();
            }
        } else {
            System.out.println(ground.owner.getName() + "'s " +ground.name );
            do {
                System.out.println("1-buy  2-build  3- Continue ");
                Continue = true;
                try {
                    switch (input.nextInt()) {
                        case 1:
                            if (!ground.owner.getName().equals("Bank")) {
                                System.out.println("you can not buy");
                                Continue = false;
                                break;
                            } else {
                                ground.setOwner(player);
                                player.grounds.add(ground);
                            }
                            break;
                        case 2:
                            if(!player.canBuild(ground)){
                                System.out.println("You can not build because equality");
                                break;
                            }
                            else if (player.equals(ground.owner)) {
                                ground.build(player);
                                break;
                            } else {
                                System.out.println("You can not build");
                                Continue = false;
                            }
                            break;
                        case 3:ground.payToOwner(player);
                            break;
                        default:
                            throw new WrongInput();
                    }
                } catch (WrongInput e1) {
                    Continue = false;
                } catch (LowBalance l) {
                    System.out.println(l.getMessage());
                    player.sellProperty();
                    Continue = false;
                }
            } while (!Continue);
        }
    }

    public static void create_game() {
        System.out.println("Enter number of players");
        int size = input.nextInt();
        while (size < 2 || size > 4) {
            System.out.println("wrong size");
            size = input.nextInt();
        }
        b1.setBoard(size);

        int countRound = 1;
        System.out.println("round " + countRound);

        int turn = 0;
        do {
            Player currentPlayer = b1.player[turn];
            System.out.println(currentPlayer.getName() + "'s turn");
            System.out.println("Enter dice number");
            int dice = input.nextInt();
            while (dice > 6 || dice < 1) {
                System.out.println("try again");
                dice = input.nextInt();
            }
            if (currentPlayer.getIndex() == 13) {
                if (dice == 1)
                   currentPlayer.setIndex(currentPlayer.getIndex() + dice);
            } else {
                currentPlayer.setIndex(currentPlayer.getIndex() + dice);
                if (dice == 6) {
                    System.out.println("Enter dice number");
                    dice = input.nextInt();
                    while (dice > 6 || dice < 1) {
                        System.out.println("try again");
                        dice = input.nextInt();
                    }
                    if (dice == 6)
                        currentPlayer.setIndex(13) ;
                    else
                        currentPlayer.setIndex(currentPlayer.getIndex() + dice);
                }
            }

            if (currentPlayer.getIndex() > 24) {
                countRound++;
                currentPlayer.setIndex(currentPlayer.getIndex() - 24) ;
            }

            switch (currentPlayer.getIndex()) {

                //Parking
                case 1:
                    break;

                //Airport
                case 3:
                case 11:
                case 20:
                    System.out.println("You are in Airport");
                    do {
                        Continue = true;
                        System.out.println("1-buy a ticket to travel   2-go on");
                        switch (input.nextInt()) {
                            case 1:
                                try {
                                    b1.airPort.buyTicket(currentPlayer);
                                } catch (LowBalance e) {
                                    System.out.println(e.getMessage());
                                } catch (WrongInput e) {
                                    Continue = false;
                                }
                            case 2:
                                break;
                            default:
                                Continue = false;
                                break;
                        }
                    } while (!Continue);
                    break;
                //Cinema
                case 4:
                    doCinemaCommands(b1.cinemas[0], currentPlayer);
                    break;
                case 8:
                    doCinemaCommands(b1.cinemas[1], currentPlayer);
                    break;
                case 15:
                    doCinemaCommands(b1.cinemas[2], currentPlayer);
                    break;
                case 22:
                    doCinemaCommands(b1.cinemas[3], currentPlayer);
                    break;

                //Road
                case 5:
                case 10:
                case 16:
                    if(currentPlayer.getBalance() >= 100){
                        System.out.println("Cost 100 $ for road");
                        currentPlayer.addBalance(-100);
                    }else
                        currentPlayer.sellProperty();
                    break;
                //Award
                case 6:
                    System.out.println("Achieve 200 from award");
                    currentPlayer.addBalance(200);
                    break;
                //Grounds
                case 2:
                    doGroundCommands(b1.grounds[0], currentPlayer);
                    break;
                case 7:
                    doGroundCommands(b1.grounds[1], currentPlayer);
                    break;
                case 9:
                    doGroundCommands(b1.grounds[2], currentPlayer);
                    break;
                case 12:
                    doGroundCommands(b1.grounds[3], currentPlayer);
                    break;
                case 14:
                    doGroundCommands(b1.grounds[4], currentPlayer);
                    break;
                case 18:
                    doGroundCommands(b1.grounds[5], currentPlayer);
                    break;
                case 19:
                    doGroundCommands(b1.grounds[6], currentPlayer);
                    break;
                case 23:
                    doGroundCommands(b1.grounds[7], currentPlayer);
                    break;

                //Bank
                case 21:
                    System.out.println("Your in Bank");
                    b1.bank.useDepositCard(currentPlayer);
                    System.out.println("1- deposit  2-continue");
                    do {
                        Continue = true;
                        String s = input.next();
                        if (s.equals("1")) {
                            try {
                                b1.bank.deposit(currentPlayer);
                                break;
                            } catch (LowBalance e) {
                                e.getMessage();
                                currentPlayer.sellProperty();
                                Continue = false;
                            }

                        } else if (s.equals("2")) {
                        } else {
                            Continue = false;
                        }
                    } while (!Continue);
                    break;
                // Tax
                case 17:
                    if (currentPlayer.getBalance() * 10 / 100 > currentPlayer.getBalance()) {
                        System.out.println("1-sell property  2-use taxCard");
                        do {
                            Continue = true;
                            switch (input.nextInt()) {
                                case 1:
                                    currentPlayer.sellProperty();
                                    break;
                                case 2:
                                    if (currentPlayer.getTaxCard() >= 1) {
                                        currentPlayer.setTaxCard(currentPlayer.getTaxCard()-1);
                                    } else
                                        System.out.println("You do not have taxCard");
                                        Continue = false;
                                        break;
                                default:
                                    Continue = false;
                            }
                        } while (!Continue);
                    } else {
                        System.out.println("Cost " + currentPlayer.getBalance() * 10 / 100 + " for the tax");
                        currentPlayer.addBalance(-currentPlayer.getBalance() * 10 / 100);
                    }
                    break;
                //Prison
                case 13:
                    System.out.println("You are in prison");
                    do {
                        Continue = true;
                        System.out.println("1-release by chanceCard\n2-stay  3-pay 50 $ to release");
                        switch (input.nextInt()) {
                            case 1:
                                if (currentPlayer.getChanceToRelease() >= 1) {
                                    currentPlayer.setChanceToRelease(currentPlayer.getChanceToRelease() - 1);
                                    currentPlayer.setIndex(currentPlayer.getIndex()+1);
                                    break;
                                } else {
                                    System.out.println("You do not have enough card!");
                                    Continue = false;
                                }
                                break;

                            case 2:
                                if (currentPlayer.getBalance() < 10) {
                                    System.out.println("You do not have enough money to pay");
                                    System.out.println("1- sell property  2- return");
                                    switch (input.nextInt()) {
                                        case 1:
                                            currentPlayer.sellProperty();
                                            break;
                                        default:
                                            Continue = false;
                                            break;
                                    }
                                    break;
                                } else
                                    currentPlayer.addBalance(-10);
                                break;
                            case 3:
                                if (currentPlayer.getBalance() < 50) {
                                    System.out.println("You do not have enough money to pay");
                                    do {
                                        System.out.println("1- Use another option\n2-sell property");
                                        switch (input.nextInt()) {
                                            case 1:
                                                Continue = false;
                                                break;
                                            case 2:
                                                currentPlayer.sellProperty();
                                                break;
                                        }
                                    } while (!Continue);
                                } else {
                                    currentPlayer.addBalance(-50);
                                    currentPlayer.setIndex(currentPlayer.getIndex()+1);
                                }
                                break;
                        }
                    } while (!Continue);
                    break;
                // Chance
                case 24:
                    System.out.println("Chance cell");
                    switch (new Random().nextInt(6)) {
                        case 0:
                            b1.chance.getMoney(currentPlayer);
                            break;
                        case 1:
                            b1.chance.goToPrison(currentPlayer);
                            break;
                        case 2:
                            b1.chance.payToBank(currentPlayer);
                            break;
                        case 3:
                            b1.chance.goThreeCellsAhead(currentPlayer);
                            break;
                        case 4:
                            b1.chance.chanceToReleasePrison(currentPlayer);
                            break;
                        case 5:
                            b1.chance.TaxCard(currentPlayer);
                            break;
                        case 6:
                            b1.chance.pay10$ToPlayers(b1.player, b1.sizeOfPlayer, currentPlayer);
                            break;

                    }
                    break;
            }

               showChart();

            System.out.println();
            turn++;
            if (turn >= b1.sizeOfPlayer)
                turn = 0;
        } while (winner());

    }

    public static boolean winner() {

        for (int i = 0; i < b1.sizeOfPlayer; i++) {
            if (b1.player[i].getBalance() <= 10 && b1.player[i].cinemas.size() == 0 && b1.player[i].grounds.size() == 0) {
                b1.sizeOfPlayer--;
                System.out.println("Player " + (i + 1) + " lost\n");
                for (int j = i; j < b1.sizeOfPlayer; j++) {
                    b1.player[j] = b1.player[j + 1];
                }
            }
        }
        if (b1.sizeOfPlayer == 1) {
            System.out.println(b1.player[0].getName() + " is winner");
            return false;
        }
        return true;
    }

    public static String  changeName(int round ,int index ) {
        if (round < b1.sizeOfPlayer) {
            Player p = b1.player[round];
            String [] firstName ={"P1","P2","P3","P4"};

            for (int i = 0; i < 24; i++) {
                if (p.getIndex()==index) {
                    return firstName[round];
                }else return "  ";
            }
            return "  ";
        }
        else return "  ";
    }


    public static void showChart(){

        System.out.println(BLUE_BOLD+"==============================================================="+
                "======================================");
        System.out.println(BLUE_BOLD+"             | "+
                GREEN_BOLD+changeName(0,8) +"  "+changeName(1,8)+"   "+  BLUE_BOLD+"   |  "+
                GREEN_BOLD+changeName(0,9) +"  "+changeName(1,9)+"  "+ BLUE_BOLD+"    |  "+
                GREEN_BOLD+changeName(0,10) +"  "+changeName(1,10)+"  "+ BLUE_BOLD+"    |  "+
                GREEN_BOLD+changeName(0,11) +"  "+changeName(1,11)+"   "+ BLUE_BOLD+"     | "+
                GREEN_BOLD+changeName(0,12) +"  "+changeName(1,12)+"  "+ BLUE_BOLD+"    | ");
        System.out.println(BLUE_BOLD+"             | "+
                GREEN_BOLD+changeName(2,8) +"  "+changeName(3,8)+"   "+  BLUE_BOLD+"   |  "+
                GREEN_BOLD+changeName(2,9) +"  "+changeName(3,9)+"  "+ BLUE_BOLD+"    |  "+
                GREEN_BOLD+changeName(2,10) +"  "+changeName(3,10)+"  "+ BLUE_BOLD+"    |  "+
                GREEN_BOLD+changeName(2,11) +"  "+changeName(3,11)+"    "+ BLUE_BOLD+"    | "+
                GREEN_BOLD+changeName(2,12) +"  "+changeName(3,12)+"  "+ BLUE_BOLD+"    | ");
        System.out.println(BLUE_BOLD+"---------------------------------------------------------------"+
                "--------------------------------------");
        System.out.println("             |"+YELLOW_BOLD+"   8:Cinema       9:Ground        10:Root "+
                "       11:AirPlane     12:Ground  "+BLUE_BOLD+"|");

        System.out.println( GREEN_BOLD+changeName(0,7) +" "+changeName(1,7)+" "+changeName(2,7) +" "+changeName(3,7)+
                "  "+BLUE_BOLD +"|"+RED_BOLD+ "7:" +YELLOW_BOLD + "Ground " +" "+"                  "+
                "                                      "+YELLOW_BOLD+"Prison :"+RED_BOLD+"13"+BLUE_BOLD+"|"
        +GREEN_BOLD+changeName(0,13) +" "+changeName(1,13)+" "+changeName(2,13) +" "+changeName(3,13));
        System.out.println(BLUE_BOLD+"_____________|                                    " +
                "                                        |____________");
        System.out.println( GREEN_BOLD+changeName(0,6) +" "+changeName(1,6)+" "+changeName(2,6) +" "+changeName(3,6)+
                "  "+BLUE_BOLD +"|"+RED_BOLD+ "6:" +YELLOW_BOLD + "Award  " +" "+"                  "+
                "                                      "+YELLOW_BOLD+"Ground :"+RED_BOLD+"15"+BLUE_BOLD+"|"+
                GREEN_BOLD+changeName(0,14) +" "+changeName(1,14)+" "+changeName(2,14) +" "+changeName(3,14));

        System.out.println(BLUE_BOLD+"_____________|                                    " +
                "                                        |____________");
        System.out.println( GREEN_BOLD+changeName(0,5) +" "+changeName(1,5)+" "+changeName(2,5) +" "+changeName(3,5)
                +"  "+BLUE_BOLD +"|"+RED_BOLD+ "5:" +YELLOW_BOLD + "Root   "+" "+"                  "+
                "                                      "+YELLOW_BOLD+"Cinema :"+RED_BOLD+"14"+BLUE_BOLD+"|"+
                GREEN_BOLD+changeName(0,15) +" "+changeName(1,15)+" "+changeName(2,15) +" "+changeName(3,15));

        System.out.println(BLUE_BOLD+"_____________|                                    " +
                "                                        |____________");
        System.out.println( GREEN_BOLD+changeName(0,4) +" "+changeName(1,4)+" "+changeName(2,4) +" "+changeName(3,4)
                +"  "+BLUE_BOLD +"|"+RED_BOLD+ "4:" +YELLOW_BOLD + "Cinema " +" "+"                  "+
                "                                      "+YELLOW_BOLD+"Root   :"+RED_BOLD+"15"+BLUE_BOLD+"|"+
                GREEN_BOLD+changeName(0,16) +" "+changeName(1,16)+" "+changeName(2,16) +" "+changeName(3,16));

        System.out.println(BLUE_BOLD+"_____________|                                    " +
                "                                        |____________");
        System.out.println( GREEN_BOLD+changeName(0,3) +" "+changeName(1,3)+" "+changeName(2,3) +" "+changeName(3,3)
                +"  "+GREEN_BOLD +"|"+RED_BOLD+ "3:" +YELLOW_BOLD + "AirPlane" +" "+"                  "+
                "                                    "+YELLOW_BOLD+"Taxation:"+RED_BOLD+"17"+BLUE_BOLD+"|" +
                RED_BOLD+changeName(0,17) +" "+changeName(1,17)+" "+changeName(2,17) +" "+changeName(3,17));

        System.out.println(BLUE_BOLD+"_____________|                                    " +
                "                                        |____________");
        System.out.println( GREEN_BOLD+changeName(0,2) +" "+changeName(1,2)+" "+changeName(2,2) +" "+changeName(3,2)+
                "  "+BLUE_BOLD +"|"+RED_BOLD+ "2:" +YELLOW_BOLD + "Ground  " +" "+"                  "+
                "                                     "+YELLOW_BOLD+"Ground :"+RED_BOLD+"18"+BLUE_BOLD+"|"+
                GREEN_BOLD+changeName(0,18) +" "+changeName(1,18)+" "+changeName(2,18) +" "+changeName(3,18));
        System.out.println(BLUE_BOLD+"_____________|                                    " +
                "                                        |____________");

        System.out.println( GREEN_BOLD+changeName(0,1) +" "+changeName(1,1)+" "+changeName(2,1) +" "+changeName(3,1)
                +"  "+BLUE_BOLD +"|"+RED_BOLD+ "1:" +YELLOW_BOLD + "Park   " +" "+"                  "+
                "                                      "+YELLOW_BOLD+"Ground :"+RED_BOLD+"19"+BLUE_BOLD+"|"+
                GREEN_BOLD+changeName(0,19) +" "+changeName(1,19)+" "+changeName(2,19) +" "+changeName(3,19));

        System.out.println("             |"+YELLOW_BOLD+"   24:Chance     23:Ground        22:Cinema "+
                "        21:Bank    20:AirPlane  "+BLUE_BOLD+"|");
        System.out.println(BLUE_BOLD+"-------------------------------------------------------------"+
                "------------------------------------------");
        System.out.println(BLUE_BOLD+"             |   "+
                GREEN_BOLD+changeName(0,24) +"  "+changeName(1,24)+"   "+  BLUE_BOLD+"   |  "+
                GREEN_BOLD+changeName(0,23) +"  "+changeName(1,23)+"  "+ BLUE_BOLD+"    |  "+
                GREEN_BOLD+changeName(0,22) +"  "+changeName(1,22)+"  "+ BLUE_BOLD+"    |  "+
                GREEN_BOLD+changeName(0,21) +"  "+changeName(1,21)+"   "+ BLUE_BOLD+"     | "+
                GREEN_BOLD+changeName(0,20) +"  "+changeName(1,20)+"  "+ BLUE_BOLD+"    | ");
        System.out.println(BLUE_BOLD+"             |   "+
                GREEN_BOLD+changeName(2,24) +"  "+changeName(3,24)+"   "+  BLUE_BOLD+"   |  "+
                GREEN_BOLD+changeName(2,23) +"  "+changeName(3,23)+"  "+ BLUE_BOLD+"    |  "+
                GREEN_BOLD+changeName(2,22) +"  "+changeName(3,22)+"  "+ BLUE_BOLD+"    |  "+
                GREEN_BOLD+changeName(2,21) +"  "+changeName(3,21)+"    "+ BLUE_BOLD+"    | "+
                GREEN_BOLD+changeName(2,20) +"  "+changeName(3,20)+"  "+ BLUE_BOLD+"    | ");

        System.out.println(BLUE_BOLD+"==================================================================="+
                "====================================");

    }

    public static final String RED_BOLD = "\033[1;31m";    // RED
    public static final String GREEN_BOLD = "\033[1;32m";  // GREEN
    public static final String YELLOW_BOLD = "\033[1;33m"; // YELLOW
    public static final String BLUE_BOLD = "\033[1;34m";   // BLUE





}