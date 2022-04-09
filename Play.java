import java.util.Scanner;

public class Play {
    public static Board b1 = Board.getInstance();
    public static Scanner input = new Scanner(System.in);

    public static boolean Continue = true;

    public static void main(String[] args) {
        String command = input.next();
        if (!command.equals("create_game"))
            System.out.println("no game created");
        else
            create_game();

    }
    public static void doGroundCommands(Ground ground, Player player) {
        if (!ground.getOwner().getName().equals("Bank") && !ground.getOwner().equals(player)) {
            try {
                ground.payToOwner(player);
            } catch (LowBalance e) {
                player.sellProperty();
            }
        } else {
            System.out.println(ground.getOwner().getName() + "'s " +ground.getName() );
            do {
                System.out.println("1-buy  2-build  3- Continue ");
                Continue = true;
                try {
                    switch (input.nextInt()) {
                        case 1:
                            if (!ground.getOwner().getName().equals("Bank")) {
                                System.out.println("you can not buy");
                                Continue = false;
                                break;
                            } else {
                                ground.setOwner(player);
                                player.grounds.add(ground);
                            }
                            break;
                        case 2:
                            if(!ground.canBuild(player)){
                                System.out.println("You can not build because equality");
                                break;
                            }
                            else if (player.equals(ground.getOwner())) {
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
    public static void doCinemaCommands(Cinema cinema, Player player) {
        System.out.println(cinema.getOwner().getName() + "'s " + cinema.getName());
        do {
            Continue = true;
            try {
                if (cinema.getOwner().getName().equals("Bank")) {
                    System.out.println("1-buy  2-Continue");
                    switch (input.nextInt()) {
                        case 1:
                            cinema.setOwner(player);
                            player.cinemas.add(cinema);
                            break;
                        case 2:
                            cinema.payToOwner(player);
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
            Player currentPlayer = b1.players[turn];
            System.out.println();
            System.out.print(b1.players[turn]);
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
                    b1.airPort.airport(currentPlayer);
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
                    b1.bank.bank(currentPlayer);
                    break;
                // Tax
                case 17:
                    b1.tax.tax(currentPlayer);
                    break;
                //Prison
                case 13:
                    b1.prison.prison(currentPlayer);
                    break;
                // Chance
                case 24:
                    b1.chance.chanceCell(currentPlayer, b1.players, b1.sizeOfPlayer);
                    break;
            }
            turn++;

            if (turn >= b1.sizeOfPlayer)
                turn = 0;
        } while (winner());

    }

    public static boolean winner() {

        for (int i = 0; i < b1.sizeOfPlayer; i++) {
            if (b1.players[i].lowBalance && !b1.players[i].sellProperty()) {
                b1.sizeOfPlayer--;
                System.out.println("Player " + (i + 1) + " lost\n");
                if (b1.sizeOfPlayer - i >= 0) System.arraycopy(b1.players, i + 1, b1.players, i, b1.sizeOfPlayer - i);
            }
        }
        if (b1.sizeOfPlayer == 1) {
            System.out.println(b1.players[0].getName() + " is winner");
            return false;
        }
        return true;
    }
}