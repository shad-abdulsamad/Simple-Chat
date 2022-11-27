import java.util.Scanner;
public class Main {
    public static void main(String[] args) throws InterruptedException {
        Scanner input=new Scanner(System.in);
        System.out.println("Type 'Client' to start the second client");
        if(input.next().equals("Client")) {
            new Client();
        }
    }
}