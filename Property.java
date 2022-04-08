import java.util.Scanner;

abstract public class Property {
    private String name;
    private int index;
    boolean Continue = true;
    private final Player getOwner = Bank.getInstance().banker;

    public Player getOwner() {
        return getOwner;
    }
    abstract void setOwner(Player Owner) throws LowBalance;

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

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

