import java.util.ArrayList;
import java.util.Scanner;

public class Player {

    private int balance;
    private final String name;
    private int depositCard = 0;
    private int depositRemain = 0;
    private int chanceToRelease = 0;
    private int TaxCard = 0;
    private int getIndex = 1;
    ArrayList<Cinema> cinemas = new ArrayList<>();
    ArrayList<Ground> grounds = new ArrayList<>();

    public Player(String name) {
        this.name = name;
        balance = 300;
    }

    public int getDepositCard() {
        return depositCard;
    }

    public void setDepositCard(int depositCard) {
        this.depositCard = depositCard;
    }

    public int getDepositRemain() {
        return depositRemain;
    }

    public void setDepositRemain(int depositRemain) {
        this.depositRemain = depositRemain;
    }

    public int getChanceToRelease() {
        return chanceToRelease;
    }

    public void setChanceToRelease(int chanceToRelease) {
        this.chanceToRelease = chanceToRelease;
    }

    public int getTaxCard() {
        return TaxCard;
    }

    public void setTaxCard(int taxCard) {
        TaxCard = taxCard;
    }

    public int getIndex() {
        return getIndex;
    }

    public void setIndex(int getIndex) {
        this.getIndex = getIndex;
    }

    public String getName() {
        return name;
    }

    public int getBalance() {
        return balance;
    }

    public void addBalance(int balance) {
        this.balance += balance;
    }

    int findCinema (ArrayList<Cinema> properties, int index){
        for (int i = 0; i < properties.size(); i++) {
            if(properties.get(i).index == index)
                return i;
        }
        return -1;
    }
    int findGround(ArrayList<Ground> properties, int index){
        for (int i = 0; i < properties.size(); i++) {
            if(properties.get(i).index == index)
                return i;
        }
        return -1;
    }

    public void sellProperty()  {
        System.out.println("which property do you want to sell");
        boolean con = true;
        do{
            System.out.println("1- cinema  2-Ground|House|hotel  3-return");
            switch (new Scanner(System.in).nextInt()){
                case 1:
                    if(cinemas.size() == 0){
                    System.out.println("You are not able to sell");
                    con = false;break;
                    }else{
                        for (int i = 0; i < cinemas.size(); i++) {
                            System.out.println(cinemas.get(i).index+"- cinema"+cinemas.get(i).index);
                        }
                        boolean con1 = true;
                        do{
                            int input = new Scanner(System.in).nextInt();
                            switch (input){
                                case 4:
                                case 8:
                                case 15:
                                case 22:try {
                                    int indexOfCinema = findCinema(cinemas, input);
                                    if(indexOfCinema != -1){
                                        cinemas.get(indexOfCinema).setOwner(new Player("Bank"));
                                        cinemas.remove(indexOfCinema);
                                        addBalance(100);break;
                                    }else
                                        con1 = false;break;
                                } catch (LowBalance e) {
                                    e.printStackTrace();
                                }break;
                                default: con1 = false;
                                break;
                                }

                        }while (!con1);
                    }
                    break;
                    
                case 2:
                    if(grounds.size() == 0){
                        System.out.println("You are not able to sell");
                        con = false;break;
                    }else{
                        for (int i = 0; i < grounds.size(); i++) {
                            System.out.println(grounds.get(i).index+"- Ground "+grounds.get(i).index);
                        }
                        boolean con1 = true;
                        do{
                            int input = new Scanner(System.in).nextInt();
                            switch (input){
                                case 2:
                                case 7:
                                case 9:
                                case 12:
                                case 14:
                                case 18:
                                case 19:
                                case 23:
                                    try {
                                    int indexOfGround = findGround(grounds, input);
                                    if(indexOfGround != -1){
                                        grounds.get(indexOfGround).setOwner(new Player("Bank"));
                                        if(grounds.get(indexOfGround).isHotel)
                                            addBalance(400);
                                        else
                                            addBalance(((grounds.get(indexOfGround).numberOfHouses*150)+100)/2);
                                        grounds.remove(indexOfGround);
                                    }else
                                        con1 = false;
                                } catch (LowBalance e) {
                                    e.printStackTrace();
                                }
                                    break;
                                default: con1 = false;
                                    break;
                            }
                        }while (!con1);
                    }
                    break;
                case 3:break;
                default:
                    con = false;
                    break;
            }
        }while (!con);
    }
    boolean canBuild(Ground currentGround){
        for (int i = 0; i < grounds.size(); i++) {
            if(currentGround.numberOfHouses > grounds.get(i).numberOfHouses){
                return false;
            }
        }
        return true;
    }

    @Override
    public String toString(){
        StringBuilder name = new StringBuilder(getName());
        int size = (15 - name.length()) / 2;
        for (int i = 0; i < size; i++) {
            name.append(" ");
        }
        StringBuilder cinemaString = new StringBuilder("");
        StringBuilder groundString = new StringBuilder("");
        if(cinemas.size() > 0){
            for (int i = 0; i < cinemas.size(); i++) {
                cinemaString.append(cinemas.get(i).name).append(" ").append(cinemas.get(i).index).append(" - ");
            }
        }
        if(grounds.size() > 0){
            for (int i = 0; i < grounds.size(); i++) {
                groundString.append(grounds.get(i).name).append(" ").append(grounds.get(i).index).append(" - ");
            }
        }
        String map = String.format("""
                        %s           
                        ------------------------
                        balance :%d    %s    
                        cell: %d       %s
                        ------------------------
                        """,
                name, getBalance(),cinemaString, getIndex, groundString);
        return map;
    }
}