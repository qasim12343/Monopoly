import java.util.Scanner;

public class Board {

    private static Board board = null;
    Player[] players;
    Scanner scanner = new Scanner(System.in);
    int sizeOfPlayer;
    Bank bank = new Bank();
    AirPort airPort = new AirPort();
    Chance chance = new Chance();
    Tax tax = new Tax();
    Prison prison = new Prison();
    Cinema[] cinemas = {new Cinema(4), new Cinema(8), new Cinema(15), new Cinema(22)};
    Ground[] grounds = {new Ground(2), new Ground(7), new Ground(9), new Ground(12), new Ground(14), new Ground(18), new Ground(19), new Ground(23)};

    private Board() {
    }

    public static Board getInstance(){
        if(board == null){
            board = new Board();
        }
        return board;
    }

    public void setBoard(int sizeOfPlayer){
        this.sizeOfPlayer = sizeOfPlayer;
        players = new Player[sizeOfPlayer];
        for (int i = 0; i < sizeOfPlayer; i++) {
            System.out.println("Enter player " + (i + 1) + " name");
            String name = scanner.next();
            players[i] = new Player(name);//set names
        }
    }

}
