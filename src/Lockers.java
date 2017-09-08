//August Wagner
//5/10/12
//Locker Program   
import java.util.Scanner;
public class Lockers{
    public static void main(String [] args){
        System.out.println("Locker Program.");
        Scanner sc = new Scanner(System.in);
        while(true){
            System.out.print("\nHow many lockers would you like? ");
            int n = sc.nextInt();
            while(n <= 0){
                System.out.print("\nSorry, that's an invalid amount of lockers. Enter a new number: ");
                n = sc.nextInt();
            }
            boolean[] locks = new boolean[n];
            System.out.println();
            run(locks);
            System.out.print("\nWould you like to enter more data? y/n: ");
            if(sc.next().equals("n"))
                break;
        }
        System.out.println("\nProgrammed by August Wagner");
    }

    public static void run(boolean [] b){
        for(int i = 0; i < b.length; i++){//fill array with true values (closed lockers)
            b[i] = true;
        }
        for(int i = 1; i <= b.length; i++){//assign values to each locker for every student who goes through
            for(int j = i; j <= b.length; j++){
                if(j % i == 0){
                    if(b[j-1])
                        b[j-1] = false;
                    else
                        b[j-1] = true;
                }
            }
        }
        String output = "";
        int rowCntr = 0, colCntr = 0;
        for(int i = 0; i < b.length; i++){//display info
            colCntr++;
            if(!b[i])
                output = "[ ]";
            else
                output = "|*|";
            System.out.print(output);
            if(colCntr == 5)
                System.out.print("     ");
            if(colCntr == 10){
                colCntr = 0;
                System.out.println();
                rowCntr++;
            }
            if(rowCntr == 5){
                System.out.println();
                rowCntr = 0;
            }
        }
    }
}