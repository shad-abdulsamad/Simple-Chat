import java.util.*;
import java.io.*;
import java.net.*;
public class ClientHandler extends Thread {
    Socket client;
    DataInputStream input;
    DataOutputStream output;
    final ArrayList<String> recievedMessages=new ArrayList<>();
    public ClientHandler(Socket clientSocket){
        client=clientSocket;
        try{
            input=new DataInputStream(client.getInputStream());
            output=new DataOutputStream(client.getOutputStream());
        }catch(IOException e){
            e.printStackTrace();
        }
    }
    @Override
    public void run(){
        try{
            String str;
            while(true){
                str=input.readUTF();
                synchronized (recievedMessages){
                    recievedMessages.add(str);
                }
            }
        }catch(IOException e){
            e.printStackTrace();
        }
        try {
            output.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        try {
            input.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        try {
            client.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public void sendMessage(String s) {
        try {
            output.writeUTF(s);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public ArrayList<String> getMessages(){
        return recievedMessages;
    }
}
