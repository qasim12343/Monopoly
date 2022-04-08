import java.util.Scanner;

public class Play {
    public static Board b1 = Board.getInstance();
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
        b1.setBoard(size);

        int countRound = 1;
        System.out.println("round " + countRound);

        int turn = 0;
        do {
            Player currentPlayer = b1.players[turn];
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
                    b1.airPort.airport(currentPlayer);
                    break;
                //Cinema
                case 4:
                    b1.cinemas[0].doCinemaCommands(b1.cinemas[0], currentPlayer);
                    break;
                case 8:
                    b1.cinemas[1].doCinemaCommands(b1.cinemas[1], currentPlayer);
                    break;
                case 15:
                    b1.cinemas[2].doCinemaCommands(b1.cinemas[2], currentPlayer);
                    break;
                case 22:
                    b1.cinemas[3].doCinemaCommands(b1.cinemas[3], currentPlayer);
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
                    b1.grounds[0].doGroundCommands(b1.grounds[0], currentPlayer);
                    break;
                case 7:
                    b1.grounds[1].doGroundCommands(b1.grounds[1], currentPlayer);
                    break;
                case 9:
                    b1.grounds[2].doGroundCommands(b1.grounds[2], currentPlayer);
                    break;
                case 12:
                    b1.grounds[3].doGroundCommands(b1.grounds[3], currentPlayer);
                    break;
                case 14:
                    b1.grounds[4].doGroundCommands(b1.grounds[4], currentPlayer);
                    break;
                case 18:
                    b1.grounds[5].doGroundCommands(b1.grounds[5], currentPlayer);
                    break;
                case 19:
                    b1.grounds[6].doGroundCommands(b1.grounds[6], currentPlayer);
                    break;
                case 23:
                    b1.grounds[7].doGroundCommands(b1.grounds[7], currentPlayer);
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
            for (int i = 0; i < b1.sizeOfPlayer; i++) {
                System.out.print((b1.players[i]));
            }
            System.out.println();
            turn++;
            if (turn >= b1.sizeOfPlayer)
                turn = 0;
        } while (winner());

    }

    public static boolean winner() {

        for (int i = 0; i < b1.sizeOfPlayer; i++) {
            if (b1.players[i].getBalance() <= 10 && b1.players[i].cinemas.size() == 0 && b1.players[i].grounds.size() == 0) {
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