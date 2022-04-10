public class Cinema extends Property {
    
    public Cinema(int index) {
        super("Cinema", index);
    }

    public void payToOwner(Player player) throws LowBalance {
        if (!player.equals(getOwner())) {
            if(getOwner().cinemas.size() == 0){
                if (player.getBalance() >=  25) {
                    getOwner().addBalance( 25);
                    player.addBalance(-25 );
                } else
                    throw new LowBalance(player);
            }
            if (getOwner().cinemas.size() >= 3) {
                if (player.getBalance() >= 100) {
                    getOwner().addBalance(100);
                    player.addBalance(-100);
                } else
                    throw new LowBalance(player);
            } else {
                if (player.getBalance() >= getOwner().cinemas.size() * 25) {
                    getOwner().addBalance(getOwner().cinemas.size() * 25);
                    player.addBalance(-25 * getOwner().cinemas.size());
                } else
                    throw new LowBalance(player);
            }
        }
    }
   @Override
    public void setOwner(Player player) throws LowBalance {
        if (player.getBalance() < 200)
            throw new LowBalance(player);
        player.addBalance(-200);
        super.setOwner(player);
    }
}
