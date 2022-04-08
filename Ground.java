class Ground extends Property {

    private int numberOfHouses = 0;
    private boolean isHotel = false;

    public Ground(int index) {
        super("Ground", index);
    }

    public void setOwner(Player player) throws LowBalance {
        if (player.getBalance() < 100){
            throw new LowBalance();
        }else {
        player.addBalance(-100);
        setOwner(player);
        }
    }

    public void build(Player player) throws LowBalance, WrongInput {

        boolean con = true;
        do {
            System.out.println("1- build house 2-change to hotel");
            switch (input.nextInt()) {
                case 1:
                    if (numberOfHouses >= 4) {
                        System.out.println("You can not build it is max");
                        con = false;
                    } else if (player.getBalance() < 150) {
                        throw new LowBalance();
                    } else {
                        player.addBalance(-150);
                        numberOfHouses++;
                        setName("House");
                    }
                    break;

                case 2:
                    if (player.getBalance() < 100) {
                        throw new LowBalance();
                    }
                    if (numberOfHouses == 4) {
                        player.addBalance(-100);
                        isHotel = true;
                    } else {
                        System.out.println("Your number of houses not enough");
                        con = false;
                    }
                    break;
                default:
                    throw new WrongInput();
            }
        } while (!con);
    }
    boolean canBuild(Player currentPlayer){
        for (int i = 0; i < currentPlayer.grounds.size(); i++) {
            if(numberOfHouses > currentPlayer.grounds.get(i).numberOfHouses){
                return false;
            }
        }
        return true;
    }

    public void payToOwner(Player player) throws LowBalance {
        if (!player.equals(getOwner())) {
            if (isHotel) {
                if (player.getBalance() >= 600) {
                    getOwner().addBalance(600);
                    player.addBalance(-600);
                } else
                    throw new LowBalance();
            } else if (numberOfHouses > 0) {
                if (player.getBalance() >= numberOfHouses * 100 + 50) {
                    getOwner().addBalance(numberOfHouses * 100 + 50);
                    player.addBalance(-numberOfHouses * 100 + 50);
                } else
                    throw new LowBalance();
            } else {
                getOwner().addBalance(50);
                player.addBalance(-50);
            }
        }
    }

    public int getNumberOfHouses() {
        return numberOfHouses;
    }

    public boolean isHotel() {
        return isHotel;
    }

    public void doGroundCommands(Ground ground, Player player) {

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
                            if(!canBuild(player)){
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
}
