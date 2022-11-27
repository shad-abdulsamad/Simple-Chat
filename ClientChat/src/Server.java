import java.io.*;
import java.net.*;
import java.util.*;
public class Server {
    public static void main(String[] args) throws InterruptedException {
        System.out.println("===SERVER STARTED===");
        Server s=new Server();
    }
    public Server() throws InterruptedException {
        try{
            ServerSocket serverSocket=new ServerSocket(9999);
            Socket clientSocket=serverSocket.accept();
            ClientHandler clientHandle1=new ClientHandler(clientSocket);
            clientHandle1.start();

            clientSocket=serverSocket.accept();
            ClientHandler clientHandle2=new ClientHandler(clientSocket);
            clientHandle2.start();
            //listen
            ArrayList<String> msgs;
            while(true){
                msgs=clientHandle1.getMessages();
                if(!msgs.isEmpty()){
                    synchronized (msgs){
                        for(int i=0;i< msgs.size();i++){
                            clientHandle2.sendMessage(msgs.get(i));
                        }
                        msgs.clear();
                    }
                }
                //clienthandle2
                msgs=clientHandle2.getMessages();
                if(!msgs.isEmpty()){
                    synchronized (msgs){
                        for(int i=0;i< msgs.size();i++){
                            clientHandle1.sendMessage(msgs.get(i));
                        }
                        msgs.clear();
                    }
                }
                serverSocket.close();
            }
        } catch (IOException e) {
            System.out.println(e);
        }
    }
}