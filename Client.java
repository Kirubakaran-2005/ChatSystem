import java.net.Socket;
import java.util.*;
import javax.swing.*;
import java.io.*;

public class Client {

    Socket socket;
    BufferedReader br;
    PrintWriter pw=null;
    Scanner scan=null;
    JTextArea messagearea;
    String username=null;

    Client(JTextArea messagearea,String username) throws IOException
    {
        this.username=username;
        this.messagearea=messagearea;
        socket = new Socket("localhost",4444);
        BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        try{
        scan = new Scanner(System.in);
        pw = new PrintWriter(socket.getOutputStream(),true);
        pw.println(username+" joined the chat");

        //listening for messages by a new thread 
        Thread thread = new Thread()
        {
            public void run()
            {
                String receivedmessage=null;
                try{
                while((receivedmessage=br.readLine())!=null)
                {
                    String finalmessage=receivedmessage;
                    SwingUtilities.invokeLater(() -> {
                    messagearea.append(finalmessage + "\n");
                    });
                }}
                catch(Exception e){ }
                finally{ }
            }
        };
        thread.start();
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        
        
    }
    void sendMessage(String message,String sendername) throws Exception
    {
        //sending messages
        try{
            if(message!=null)
            {
                pw.println(sendername+": "+message);
                messagearea.append("You: "+message+"\n");
            }
            }
            catch(Exception e)
            {
                e.printStackTrace();
            }
    }
    //Closing message to server
    void exitMessage() throws IOException
    {
        pw.println(username+" left the chat!");
        socket.close();
    }
}
