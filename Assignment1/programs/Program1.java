import java.io.*;
import java.time.LocalTime;
import java.util.*;

public class Program1{
    public static void main(String[] args) throws IOException {

        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter a positive number: ");
        String input = scanner.next();
        while (!input.equals("q")){
            if(isdigit(input)){
                int x = Integer.parseInt(input);
                if(x >0){
                    doSomething(x);
                }else{
                    System.out.println("Error: must be positive");
                }
            }
            System.out.print("Enter a positive number: ");
            input = scanner.next();
        }
        System.out.println("quit");
    }
    public static void doSomething(int x) throws IOException {
        int sum = 0;
        for(int i = 1; i <= x; i++){
            sum += i;
        }
        System.out.println(sum);

        System.out.println(LocalTime.now());

        File f = new File(x + ".txt");
        if(!f.exists()){
            f.createNewFile();
            System.out.println(f.getAbsolutePath());
        }
    }
    public static boolean isdigit(String s){
        try{
            Integer.parseInt(s);
            return true;
        }catch (NumberFormatException e){
            return false;
        }

    }

}
