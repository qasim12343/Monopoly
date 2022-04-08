import java.util.Scanner;

public class Board {

    private static final Board board = new Board();
    Player[] players;
    Scanner in = new Scanner(System.in);
    int sizeOfPlayer;
    Bank bank = Bank.getInstance();
    AirPort airPort = AirPort.getInstance();
    Chance chance = Chance.getInstance();
    Tax tax = Tax.getInstance();
    Prison prison = Prison.getInstance();
    Cinema[] cinemas = {new Cinema(4), new Cinema(8), new Cinema(15), new Cinema(22)};
    Ground[] grounds = {new Ground(2), new Ground(7), new Ground(9), new Ground(12), new Ground(14), new Ground(18), new Ground(19), new Ground(23)};

    private Board() {
    }

    public static Board getInstance(){
        return board;
    }

    public void setBoard(int sizeOfPlayer){
        this.sizeOfPlayer = sizeOfPlayer;
        players = new Player[sizeOfPlayer];
        for (int i = 0; i < sizeOfPlayer; i++) {
            System.out.println("Enter player " + (i + 1) + " name");
            String name = in.next();
            players[i] = new Player(name);//set names
        }
    }

}
