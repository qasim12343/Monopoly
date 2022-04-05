import java.util.ArrayList;
import java.util.Scanner;

public class Player {
    private int balance;
    int depositCard = 0;
    int depositRemain = 0;
    int chanceToRelease = 0;
    int index = 1;
    ArrayList<Cinema> cinemas = new ArrayList<>();
    ArrayList<Ground> grounds = new ArrayList<>();
    String name;

    public Player(String name) {
        this.name = name;
        balance = 1500;
    }

    public int getBalance() {
        return balance;
    }

    public void addBalance(int balance) {
        this.balance += balance;
    }

    Cinema findCinema (ArrayList<Cinema> properties, int index){
        for (int i = 0; i < properties.size(); i++) {
            if(properties.get(i).index == index)
                return properties.get(i);
        }
        return null;
    }
    Ground findGround(ArrayList<Ground> properties, int index){
        for (int i = 0; i < properties.size(); i++) {
            if(properties.get(i).index == index)
                return properties.get(i);
        }
        return null;
    }
    public void sellProperty(){
        System.out.println("which property do you want to sell");
        System.out.println("1- cinema  2-Ground|House|hotel  ");
        boolean con = true;
        do{
            switch (new Scanner(System.in).nextInt()){
                case 1:
                    if(cinemas.size() == 0){
                    System.out.println("You are not able to sell");
                    con = false;break;
                    }else{
                        for (int i = 0; i < cinemas.size(); i++) {
                            System.out.println(i+"-cinema"+cinemas.get(i).index);
                        }
                        boolean con1 = true;
                        do{
                            int in = new Scanner(System.in).nextInt();
                            switch (in){
                                case 4:
                                case 8:
                                case 15:
                                case 22:try {
                                    Cinema temp = findCinema(cinemas, in);
                                    if(temp != null){
                                        temp.setOwner(new Player("Bank"));
                                        addBalance(100);break;
                                    }else
                                        con1 = false;break;
                                } catch (LowBalance e) {
                                    e.printStackTrace();
                                }break;
                                default: con1 = false;break;
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
                            System.out.println(i+"-Ground "+grounds.get(i).index);
                        }
                        boolean con1 = true;
                        do{
                            int in = new Scanner(System.in).nextInt();
                            switch (in){
                                case 2:
                                case 7:
                                case 9:
                                case 12:
                                case 14:
                                case 18:
                                case 19:
                                case 23:
                                    try {
                                    Ground temp = findGround(grounds, in);
                                    if(temp != null){
                                        temp.setOwner(new Player("Bank"));
                                        if(temp.isHotel)
                                            addBalance(400);
                                        else
                                            addBalance(((temp.numberOfHouses*150)+100)/2);
                                        break;
                                    }else
                                        con1 = false;break;
                                } catch (LowBalance e) {
                                    e.printStackTrace();
                                }break;
                                default: con1 = false;break;
                            }

                        }while (!con1);
                    }
                    break;
            }
        }while (!con);
    }
    public int index(){
        return index;
    }
}