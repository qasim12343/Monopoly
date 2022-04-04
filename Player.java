import java.util.ArrayList;
import java.util.Scanner;

public class Player {
    int balance;
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
    public void sellProperty(){
        System.out.println("which property do you want to sell");
        System.out.println("1- cinema  2-Ground|House|hotel  ");
        boolean con = true;
        do{
            switch (new Scanner(System.in).nextInt()){
                case 1:
                    if(cinemas.size() == 0){
                    System.out.println("You are not able to sell");
                    con = false;
                    }else{
                        for (int i = 0; i < cinemas.size(); i++) {
                            System.out.println("cinema"+cinemas.get(i).index);
                        }
                        boolean con1 = true;
                        do{
                            switch (new Scanner(System.in).nextInt()){
                                case 4:
                                case 8:
                                case 15:
                                case 22:break;
                                }

                        }while (!con);
                    }
                case 2:
            }
        }while (!con);
    }
    public int index(){
        return index;
    }
   
}