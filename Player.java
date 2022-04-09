import java.util.ArrayList;
import java.util.Scanner;

public class Player {

    private int balance;
    private final String name;
    private int depositCard = 0;
    private int depositRemain = 0;
    private int chanceToRelease = 0;
    private int TaxCard = 0;
    private int Index = 1;
    boolean lowBalance = false;
    ArrayList<Cinema> cinemas = new ArrayList<>();
    ArrayList<Ground> grounds = new ArrayList<>();

    public Player(String name) {
        this.name = name;
        balance = 200;
    }

    int findCinema (ArrayList<Cinema> properties, int index){
        for (int i = 0; i < properties.size(); i++) {
            if(properties.get(i).getIndex() == index)
                return i;
        }
        return -1;
    }
    int findGround(ArrayList<Ground> properties, int index){
        for (int i = 0; i < properties.size(); i++) {
            if(properties.get(i).getIndex() == index)
                return i;
        }
        return -1;
    }

    public void sellProperty()  {

        System.out.println("which property do you want to sell");
        lowBalance = true;
        boolean con = true;
        do{
            System.out.println("1- cinema  2-Ground|House|hotel  3-return");
            switch (new Scanner(System.in).nextInt()){
                case 1:
                    if(cinemas.size() == 0){
                    System.out.println("You are not able to sell");
                    con = false;break;
                    }else{
                        for (Cinema cinema : cinemas) {
                            System.out.println(cinema.getIndex() + "- cinema" + cinema.getIndex());
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
                                        cinemas.get(indexOfCinema).setOwner(Board.getInstance().bank.banker);
                                        cinemas.remove(indexOfCinema);
                                        addBalance(100);
                                        lowBalance = false;
                                        System.out.println("You sold your cinema!");break;
                                    }else
                                        con1 = false;break;
                                } catch (LowBalance e) {
                                    System.out.println(e.getMessage());
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
                            System.out.println(grounds.get(i).getIndex()+"- Ground "+grounds.get(i).getIndex());
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
                                        grounds.get(indexOfGround).setOwner(Board.getInstance().bank.banker);
                                        if(grounds.get(indexOfGround).isHotel()){
                                            addBalance(400);
                                            lowBalance = false;
                                        } else
                                            addBalance(((grounds.get(indexOfGround).getNumberOfHouses()*150)+100)/2);
                                        grounds.remove(indexOfGround);
                                        System.out.println("You sold your ground!");
                                        break;
                                    }else
                                        con1 = false;
                                } catch (LowBalance e) {
                                    e.getMessage();
                                }break;
                                default: con1 = false;
                                    break;
                            }
                        }while (!con1);
                    }
                    break;
                case 3:con = true;
                    break;
                default:
                    con = false;
                    break;
            }
        }while (!con);

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
        return Index;
    }

    public void setIndex(int Index) {
        this.Index = Index;
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

    @Override
    public String toString(){
        StringBuilder cinemaString = new StringBuilder();
        StringBuilder groundString = new StringBuilder();
        if(cinemas.size() > 0){
            for (int i = 0; i < cinemas.size(); i++) {
                cinemaString.append(cinemas.get(i).getName()).append(" ").append(cinemas.get(i).getIndex()).append(" - ");
            }
        }
        if(grounds.size() > 0){
            for (int i = 0; i < grounds.size(); i++) {
                groundString.append(grounds.get(i).getName()).append(" ").append(grounds.get(i).getIndex()).append(" - ");
            }
        }
        return String.format("""
                        %s's turn
                        ------------------------
                        balance :%d    properties: %s   %s
                        depositRemain: %d        TaxCard: %d     releaseCard: %d
                        ------------------------
                        """,
                getName(), getBalance(),cinemaString, groundString,
                getDepositRemain(), getTaxCard(),getChanceToRelease());
    }
}