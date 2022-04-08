import java.util.Random;

public class Chance extends Property{
    private Chance() {
        super("Chance", 24);
    }
    private static final Chance chance = new Chance();
    public static Chance getInstance(){
        return chance;
    }

    public void chanceCell(Player currentPlayer, Player[] players, int sizeOfPlayer) {
        System.out.println("Chance cell");
        switch (new Random().nextInt(6)) {
            case 0:
                getMoney(currentPlayer);
                break;
            case 1:
                goToPrison(currentPlayer);
                break;
            case 2:
                payToBank(currentPlayer);
                break;
            case 3:
                goThreeCellsAhead(currentPlayer);
                break;
            case 4:
                chanceToReleasePrison(currentPlayer);
                break;
            case 5:
                TaxCard(currentPlayer);
                break;
            case 6:
                pay10$ToPlayers(players, sizeOfPlayer, currentPlayer);
                break;

        }
    }

    public void getMoney(Player player) {
        System.out.println(" Your chance card is 200$");
        player.addBalance(200);
    }

    public void goToPrison(Player player) {
        System.out.println("Your chance card is prison ");
        player.setIndex(13);
    }

    public void payToBank(Player player) {
        System.out.println("Your chance card is 10% tax");
        player.addBalance(-player.getBalance() * 10 / 100);
    }

    public void goThreeCellsAhead(Player player) {
        System.out.println("Your chance card is three cell go ahead");
        player.setIndex(player.getIndex() + 3);
    }

    public void chanceToReleasePrison(Player player) {
        System.out.println("Your chance card is a chanceToReleaseCard");
        player.setChanceToRelease(player.getChanceToRelease() + 1);
    }

    public void TaxCard(Player player) {
        System.out.println("Your chance card a taxCard");
        player.setTaxCard(player.getTaxCard() + 1);
    }

    public void pay10$ToPlayers(Player[] players, int size, Player currentPlayer) {
        System.out.println("Your chance card is 10$ to every player");
        for (int i = 0; i < size; i++) {
            players[i].addBalance(10);
            currentPlayer.addBalance(-10);
        }
    }

    @Override
    void setOwner(Player getOwner) {
        setOwner(Bank.getInstance().banker);
    }
}
