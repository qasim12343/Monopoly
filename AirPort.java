public class AirPort extends Property {

    public AirPort() {
        super("Airport", 3);
    }

    public void buyTicket(Player player) throws LowBalance, WrongInput {
        if (player.getBalance() < 50)
            throw new LowBalance(player);
        player.addBalance(-50);
        System.out.println("Choose location to travel");
        if (player.getIndex() == 3) System.out.println("11 or 20");
        else if (player.getIndex() == 11) System.out.println("3 or 20");
        else if (player.getIndex() == 20) System.out.println("3 or 11");

        int inp = input.nextInt();

        switch (inp) {
            case 3 : player.setIndex(3);break;
            case 11 : player.setIndex(11);break;
            case 20 : player.setIndex(20);break;
            default : throw new WrongInput();
        }
    }
    public void airport(Player currentPlayer) {
        setIndex(currentPlayer.getIndex());
        System.out.println("You are in Airport");
        do {
            Continue = true;
            System.out.println("1-buy a ticket to travel   2-go on");
            switch (input.nextInt()) {
                case 1:
                    try {
                        buyTicket(currentPlayer);
                    } catch (LowBalance e) {
                        System.out.println(e.getMessage());
                        currentPlayer.sellProperty();
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
    }
}
