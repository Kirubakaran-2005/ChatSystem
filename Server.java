import java.net.ServerSocket;
import java.net.Socket;
import java.util.*;
import java.io.*;

public class Server {
    
    static ArrayList<Socket> list = new ArrayList<>();
    public static void main(String[] args) throws Exception
    {
        ServerSocket sersoc=null;
        try{
        sersoc = new ServerSocket(4444);
        while(true)
        {
            System.out.println("Waiting for clients to join");
            Socket socket = sersoc.accept();
            System.out.println("new Client joined");
            Thread thread = new Thread(){
                public void run()
                {
                    try{
                    list.add(socket);
                    clienthandler(socket); }
                    catch(Exception e){ e.printStackTrace(); }
                }
            };
            thread.start();
        } }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        finally
        {
           try{ 
            System.out.println("Server Closed Unexpectedly!!");
            sersoc.close(); }
           catch(Exception e) { e.printStackTrace(); }
        }

    }

    //Broadcasting message to all clients
    static void clienthandler(Socket socket) throws Exception
    {
        BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        PrintWriter pr;
        String message;
        try{
        while(!socket.isClosed())
        {
            message=br.readLine();
            for(Socket eachsoc:list)
            {
                if(message!=null){
                pr = new PrintWriter(eachsoc.getOutputStream(),true);
                if(eachsoc!=socket) pr.println(message);}
            }
        }
        }
        catch(Exception e)
        {
           e.printStackTrace();
        }
        finally{
            //removing particular client from the server list
            list.remove(socket);
            System.out.println("Client Disconnected!");
            if(!socket.isClosed()) socket.close();
            br.close();
        }
    }
}
