public class Prison extends Property{

    public Prison() {
        super("Prison", 13);
    }
    public void prison(Player currentPlayer) {
        System.out.println("You are in prison");
        do {
            Continue = true;
            System.out.println("1-release by chanceCard   2-stay  3-pay 50 $ to release");
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
                        if (input.nextInt() == 1) {
                            currentPlayer.sellProperty();
                            currentPlayer.lowBalance = true;
                        } else {
                            Continue = false;
                        }
                        break;
                    } else
                        currentPlayer.addBalance(-10);
                    break;
                case 3:
                    if (currentPlayer.getBalance() < 50) {
                        System.out.println("You do not have enough money to pay");
                        System.out.println("1- Use another option  2-sell property");
                        switch (input.nextInt()) {
                            case 1:
                                Continue = false;
                                break;
                            case 2:
                                currentPlayer.lowBalance = true;
                                currentPlayer.sellProperty();
                                break;
                        }
                    } else {
                        currentPlayer.addBalance(-50);
                        currentPlayer.setIndex(currentPlayer.getIndex()+1);
                    }
                    break;
            }
        } while (!Continue);
    }

}
