import javax.swing.*;
import java.awt.event.*;
import java.awt.*;


class UserWindow implements ActionListener {
    
    JButton sendbutton;
    Client client;
    JTextField messagefield;
    String username;
    UserWindow(String username)
    {
        this.username=username;
        //TOP STATUS COMPONENTS
        //Title
        JLabel label = new JLabel();
        label.setText("ChatHut");
        label.setForeground(Color.WHITE);
        label.setFont(new Font("MV Boli",Font.BOLD, 20));
        label.setVerticalAlignment(JLabel.CENTER);
        label.setHorizontalAlignment(JLabel.CENTER);

        JPanel statuspanel = new JPanel();
        statuspanel.setPreferredSize(new Dimension(250,40));
        statuspanel.setBackground(new Color(31,39,42));
        statuspanel.setLayout(new BorderLayout());
        statuspanel.add(label);

        //MESSAGE AREA
        JTextArea messagearea = new JTextArea();
        messagearea.setEditable(false);
        JScrollPane scrollpane = new JScrollPane(messagearea,JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);

        //BOTTOM MESSAGE COMPONENTS
        sendbutton = new JButton();
        sendbutton.setText("Send");
        sendbutton.setBounds(225,10,70,30);
        sendbutton.setFocusable(false);
        sendbutton.addActionListener(this);

        messagefield = new JTextField();
        messagefield.setBounds(15,10,200,30);

        JPanel messagepanel = new JPanel();
        messagepanel.setPreferredSize(new Dimension(250,50));
        messagepanel.setBackground(new Color(31,39,42));
        messagepanel.setLayout(null);
        messagepanel.add(messagefield);
        messagepanel.add(sendbutton);
        
        //CHAT FRAME
        JFrame chatframe = new JFrame();
        chatframe.setTitle("Welcome "+username);
        chatframe.setSize(350,530);
        chatframe.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        chatframe.setResizable(false);
        chatframe.setVisible(true);
        chatframe.setLayout(new BorderLayout());
        chatframe.add(messagepanel,BorderLayout.SOUTH);
        chatframe.add(statuspanel,BorderLayout.NORTH);
        chatframe.add(scrollpane,BorderLayout.CENTER);
        //closing chatframe
        chatframe.addWindowListener(new WindowAdapter() 
        {
            public void windowClosing(WindowEvent e)
            {
                 try {client.exitMessage();
                chatframe.dispose(); }
                 catch(Exception exception) { }
            }
        }
        );

        try { client = new Client(messagearea,username); }
        catch(Exception e){ }
    }

    public void actionPerformed(ActionEvent e)
    {
        if(e.getSource()==sendbutton)
        {
           try{ 
            client.sendMessage(messagefield.getText(),username);
            messagefield.setText(""); }
           catch(Exception excep) { }
        }
    }
}