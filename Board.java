import java.util.Scanner;

class Board {

    Player[] player;
    Scanner in = new Scanner(System.in);
    int sizeOfPlayer;
    AirPort airPort = new AirPort(31120);
    Chance chance = new Chance();
    Cinema[] cinemas = {new Cinema(4), new Cinema(8), new Cinema(15), new Cinema(22)};
    Ground[] grounds = {new Ground(2), new Ground(7), new Ground(9), new Ground(12), new Ground(14), new Ground(18), new Ground(19), new Ground(23)};

    public Board(int sizeOfPlayer) {
        this.sizeOfPlayer = sizeOfPlayer;
        player = new Player[sizeOfPlayer];
        for (int i = 0; i < sizeOfPlayer; i++) {
            System.out.println("Enter player " + (i + 1) + " name");
            String name = in.next();
            player[i] = new Player(name);//set names
        }
    }

    public String showDetails(Player player) {
        StringBuilder name = new StringBuilder(player.name);
        int size = (15 - name.length()) / 2;
        for (int i = 0; i < size; i++) {
            name = new StringBuilder(" " + name + " ");
        }
        String map = String.format("""
                        %s
                        --------------
                        balance :%d
                        cell: %d
                        --------------
                        """,
                name, player.getBalance(), player.index);
        return map;
    }

}
