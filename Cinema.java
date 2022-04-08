public class Cinema extends Property {
    
    Cinema(int index) {
        super("Cinema", index);
    }

    public void payToOwner(Player player) throws LowBalance {
        if (!player.equals(getOwner())) {
            if (getOwner().cinemas.size() == 1 || getOwner().cinemas.size() == 2) {
                if (player.getBalance() >= getOwner().cinemas.size() * 25) {
                    getOwner().addBalance(getOwner().cinemas.size() * 25);
                    player.addBalance(-25 * getOwner().cinemas.size());
                } else
                    throw new LowBalance();
            } else if (getOwner().cinemas.size() >= 3) {
                if (player.getBalance() >= 100) {
                    getOwner().addBalance(100);
                    player.addBalance(-100);
                } else
                    throw new LowBalance();
            }
        }
    }
    @Override
    public void setOwner(Player player) throws LowBalance {
        if (player.getBalance() < 200)
            throw new LowBalance();
        player.addBalance(-200);
        setOwner(player) ;
    }

    public void doCinemaCommands(Cinema cinema, Player player) {
        System.out.println(cinema.getOwner().getName() + "'s " + cinema.getName());
        do {
            Continue = true;
            try {
                if (cinema.getOwner().getName().equals("Banker")) {
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
}
