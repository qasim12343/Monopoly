import java.util.Scanner;

public class Property {
    String name;
    int index;
    
    Property(String name, int index){
        this.index = index;
        this.name = name;
    }
    Scanner input = new Scanner(System.in);
}

class LowBalance extends Exception{
    public LowBalance() {
        super("You do not have enough money");
    }
}
class WrongInput extends Exception{
    public WrongInput() {
        super("Wrong input! try again");
    }
}

class AirPort extends Property {
    
    AirPort( int index) {
        super("Airport", index);
    }

    public void buyTicket(Player player) throws LowBalance, WrongInput {
        if(player.getBalance() < 50)
            throw new LowBalance();
        player.addBalance(-50);
        System.out.println("Choose location to travel");
        if(player.getIndex() == 3) System.out.println("11 or 20");
        else if(player.getIndex() == 11) System.out.println("3 or 20");
        else if(player.getIndex() == 20) System.out.println("3 or 11");

        int inp = input.nextInt();

        switch (inp) {
            case 3 -> player.setIndex(3);
            case 11 -> player.setIndex(11);
            case 20 -> player.setIndex(20);
            default -> throw new WrongInput();
        }
    }
}

class Chance  {
    
    public void getMoney(Player player){
        System.out.println(" Your chance card is 200$");
        player.addBalance(200);
    }
    public void goToPrison(Player player){
        System.out.println("Your chance card is prison ");
        player.setIndex(13);
    }
    public void payToBank(Player player){
        System.out.println("Your chance card is 10% tax");
        player.addBalance(-player.getBalance()*10/100);
    }
    public void goThreeCellsAhead(Player player){
        System.out.println("Your chance card is three cell go ahead");
        player.setIndex(player.getIndex()+3);
    }
    public void chanceToReleasePrison(Player player){
        System.out.println("Your chance card is a chanceToReleaseCard");
        player.setChanceToRelease(player.getChanceToRelease() + 1);
    }
    public void TaxCard(Player player){
        System.out.println("Your chance card a taxCard");
        player.setTaxCard(player.getTaxCard()+1);
    }
    public void pay10$ToPlayers(Player[] players, int size, Player currentPlayer){
        System.out.println("Your chance card is 10$ to every player");
        for (int i = 0; i < size; i++) {
            players[i].addBalance(10);
            currentPlayer.addBalance(-10);
        }
    }
}

class Ground extends Property {
    Player owner = new Player("Bank");
    int numberOfHouses = 0;
    boolean isHotel = false;

    Ground( int index) {
        super("Ground", index);
    }
    public void setName(String name){
        super.name = name;
    }

    public void setOwner(Player player) throws LowBalance {
        if(player.getBalance() < 100)
            throw new LowBalance();
        player.addBalance(-100);
        owner = player;
    }
    public void build(Player player) throws LowBalance, WrongInput {

        boolean con = true;
        do{
            System.out.println("1- build house 2-change to hotel");
            switch (input.nextInt()){
                case 1:
                    if(numberOfHouses >= 4){
                    System.out.println("You can not build it is max");
                    con = false;
                    }
                    else if(player.getBalance() < 150){
                        throw new LowBalance();
                    }
                    else{
                        player.addBalance(-150);
                        numberOfHouses++;
                        setName("House");
                    }
                    break;
                    
                case 2:
                    if(player.getBalance() < 100){
                        throw new LowBalance();
                    }
                    if(numberOfHouses == 4){
                        player.addBalance(-100);
                        isHotel = true;
                    }else{
                        System.out.println("Your number of houses not enough");
                        con = false;
                    }
                    break;
                default: throw new WrongInput();
            }
        }while (!con);
    }
    public void payToOwner(Player player) throws LowBalance {
        if(!player.equals(owner)){
            if(isHotel){
                if(player.getBalance() >= 600){
                    owner.addBalance(600);
                    player.addBalance(-600);
                }else
                    throw new LowBalance();
            }
            else if(numberOfHouses > 0){
                if(player.getBalance() >= numberOfHouses*100+50){
                    owner.addBalance(numberOfHouses*100+50);
                    player.addBalance(-numberOfHouses*100+50);
                }else
                    throw new LowBalance();
            }
            else {
                owner.addBalance(50);
                player.addBalance(-50);
            }
        }
    }
}

class Cinema extends Property {
    
    Player owner = new Player("Bank");
    Cinema( int index) {
        super("Cinema", index);
    }

    public void payToOwner(Player player) throws LowBalance {
        if(!player.equals(owner)){
            if(owner.cinemas.size() == 1 || owner.cinemas.size() == 2){
                if(player.getBalance() >= owner.cinemas.size()*25){
                    owner.addBalance(owner.cinemas.size()*25);
                    player.addBalance(-25*owner.cinemas.size());
                }else
                    throw new LowBalance();
            }
            else if(owner.cinemas.size() >= 3){
                if(player.getBalance() >= 100){
                    owner.addBalance(100);
                    player.addBalance(-100);
                }else
                    throw new LowBalance();
            }
        }
    }
    public void setOwner(Player player) throws LowBalance {
        if(player.getBalance() < 200)
            throw new LowBalance();
        player.addBalance(-200);
        owner = player;
    }
}

class Bank {
    void deposit(Player player) throws LowBalance {
        if(player.getBalance() <= 1)
            throw new LowBalance();
        else{
            player.addBalance(-player.getBalance()/2);
            player.setDepositCard(player.getDepositCard()+1);
        }
    }
    void useDepositCard(Player player) {
        if(player.getDepositCard() == 1){
            player.addBalance(player.getDepositRemain()*2);
            System.out.println("You earn "+player.getDepositRemain() * 2 + " from last deposit");
            player.setDepositCard(0);
            player.setDepositRemain(0);
        }
    }
}
