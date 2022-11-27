import java.util.*;
import java.io.*;
import java.net.*;
public class Client{
    public static void main(String[] args) {
        Client client=new Client();
    }
    boolean isOn;
    public Client(){
        try{
            InetAddress ip=InetAddress.getLocalHost();
            System.out.println(ip);
            Socket socket=new Socket(ip,9999);
            DataInputStream dataInputStream=new DataInputStream(socket.getInputStream());
            DataOutputStream dataOutputStream=new DataOutputStream(socket.getOutputStream());
            isOn=true;
            Thread thread=new Thread(){
                @Override
                public void run(){
                    String str="";
                    try{
                        while(isOn){
                            str=dataInputStream.readUTF();
                            System.out.println("other client said: "+str);
                        }
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
            };
            thread.start();
            Scanner input=new Scanner(System.in);
            String str="";
            while(true){
                str=input.nextLine();
                dataOutputStream.writeUTF(str);
                if(str.equalsIgnoreCase("Exit")){
                    break;
                }
            }
            isOn=false;
            dataInputStream.close();
            dataOutputStream.close();
            socket.close();
        } catch (UnknownHostException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}