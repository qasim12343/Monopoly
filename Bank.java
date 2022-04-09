public class Bank extends Property{
    Player banker = new Player("Bank");
    public Bank() {
        super("Bank", 21);
        banker.addBalance(10000000);
    }

    void deposit(Player player) throws LowBalance {
        if (player.getBalance() <= 1)
            throw new LowBalance(player);
        else {
            player.addBalance(-player.getBalance() / 2);
            player.setDepositRemain(player.getBalance() / 2);
            player.setDepositCard(player.getDepositCard() + 1);
        }
    }

    void useDepositCard(Player player) {
        if (player.getDepositCard() == 1) {
            player.addBalance(player.getDepositRemain() * 2);
            System.out.println("You earn " + player.getDepositRemain() * 2 + " from last deposit");
            player.setDepositCard(0);
            player.setDepositRemain(0);
        }
    }
    public void bank(Player currentPlayer) {
        System.out.println("Your in Bank");
        useDepositCard(currentPlayer);
        do {
            System.out.println("1- deposit  2-continue");
            Continue = true;
            switch (input.next()) {
                case "1":
                    try {
                        deposit(currentPlayer);
                        break;
                    } catch (LowBalance e) {
                        currentPlayer.lowBalance = true;
                        currentPlayer.sellProperty();
                        Continue = false;
                    }
                    break;
                case "2":
                    break;
                default:
                    Continue = false;
            }
        } while (!Continue);
    }

}
