class Ground extends Property {

    private int numberOfHouses = 0;
    private boolean isHotel = false;

    public Ground(int index) {
        super("Ground", index);
    }

    @Override
    public void setOwner(Player player) throws LowBalance {
        if (player.getBalance() < 100){
            throw new LowBalance(player);
        }else {
        player.addBalance(-100);
        super.setOwner(player);
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
                        throw new LowBalance(player);
                    } else {
                        player.addBalance(-150);
                        numberOfHouses++;
                        setName("House");
                    }
                    break;

                case 2:
                    if (player.getBalance() < 100) {
                        throw new LowBalance(player);
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
                    throw new LowBalance(player);
            } else if (numberOfHouses > 0) {
                if (player.getBalance() >= numberOfHouses * 100 + 50) {
                    getOwner().addBalance(numberOfHouses * 100 + 50);
                    player.addBalance(-numberOfHouses * 100 + 50);
                } else
                    throw new LowBalance(player);
            } else {
                if(player.getBalance() >= 50){
                getOwner().addBalance(50);
                player.addBalance(-50);
                }else throw new LowBalance(player);
            }
        }
    }

    public int getNumberOfHouses() {
        return numberOfHouses;
    }

    public boolean isHotel() {
        return isHotel;
    }


}
