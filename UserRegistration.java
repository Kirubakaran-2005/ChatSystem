import javax.swing.*;
import java.awt.event.*;
import java.sql.*;
import java.awt.*;

class Registration implements ActionListener
{
    //button components
    JButton registerbutton;
    JButton loginbutton;
    JButton signupbutton;
    JButton infobutton;

    //login components
    JTextField userfield;
    JPasswordField passwordfield;
    JLabel userlabel;
    JLabel passwordlabel;
    JFrame loginframe;
    
    //registration components
    JTextField reg_userfield;
    JPasswordField reg_passwordfield;
    JLabel reg_userlabel;
    JLabel reg_passwordlabel;
    JFrame registerframe;

    Connection con;
    PreparedStatement pst;

    Registration() throws Exception
    {
        //database connection
        String url="jdbc:mysql://localhost:3306/chat_app";
        String user="root";
        String pass="Kiruba$DB$4";

        String query="insert into users (username,password) values(?,?)";
        con = DriverManager.getConnection(url,user,pass);
        pst=con.prepareStatement(query);

        //Title
        JLabel label = new JLabel();
        label.setText("ChatHut");
        label.setFont(new Font("MV Boli",Font.BOLD,25));
        label.setForeground(Color.WHITE);
        label.setBounds(110,35,160,70);

        //login frame components
        userlabel = new JLabel();
        userlabel.setText("Username");
        userlabel.setBounds(30,120,85,25);

        userfield = new JTextField();
        userfield.setBounds(110,120,140,25);

        passwordlabel = new JLabel();
        passwordlabel.setText("Password");
        passwordlabel.setBounds(30,150,75,25);

        passwordfield = new JPasswordField();
        passwordfield.setBounds(110,150,140,25);

        loginbutton = new JButton();
        loginbutton.setText("Log In");
        loginbutton.setBounds(120,180,100,25);
        loginbutton.setFocusable(false);
        loginbutton.addActionListener(this);

        JLabel registertext = new JLabel();
        registertext.setText("Don't have an account?");
        registertext.setBounds(30,350,150,25);

        registerbutton = new JButton();
        registerbutton.setText("Register");
        registerbutton.setBounds(190,350,100,25);
        registerbutton.setFocusable(false);
        registerbutton.addActionListener(this);

        infobutton = new JButton();
        infobutton.setText("<-Developer Info->");
        infobutton.setBounds(100,400,145,25);
        infobutton.setFocusable(false);
        infobutton.addActionListener(this);
        
        loginframe = new JFrame();
        loginframe.setTitle("ChatHut - UserLogin");
        loginframe.setSize(350,530);
        loginframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        loginframe.setResizable(false);
        loginframe.setVisible(true);
        loginframe.getContentPane().setBackground(new Color(25,164,226));
        loginframe.setLayout(null);
        loginframe.add(userlabel);
        loginframe.add(userfield);
        loginframe.add(passwordlabel);
        loginframe.add(passwordfield);
        loginframe.add(registerbutton);
        loginframe.add(registertext);
        loginframe.add(loginbutton);
        loginframe.add(infobutton);
        loginframe.add(label);
    }

    public void actionPerformed(ActionEvent e)
    {
        if(e.getSource()==registerbutton)
        {
            reg_userlabel = new JLabel();
            reg_userlabel.setText("Username");
            reg_userlabel.setBounds(30,120,85,25);

            reg_userfield = new JTextField();
            reg_userfield.setBounds(110,120,140,25);

            reg_passwordlabel = new JLabel();
            reg_passwordlabel.setText("Password");
            reg_passwordlabel.setBounds(30,150,75,25);

            reg_passwordfield = new JPasswordField();
            reg_passwordfield.setBounds(110,150,140,25);

            signupbutton= new JButton();
            signupbutton.setText("SignUp");
            signupbutton.setBounds(120,180,100,25);
            signupbutton.setFocusable(false);
            signupbutton.addActionListener(this);

            registerframe = new JFrame();
            registerframe.setTitle("ChatHut - UserRegistration");
            registerframe.setSize(350,530);
            registerframe.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
            registerframe.setResizable(false);
            registerframe.setVisible(true);
            registerframe.setLayout(null);
            registerframe.add(reg_userlabel);
            registerframe.add(reg_userfield);
            registerframe.add(reg_passwordlabel);
            registerframe.getContentPane().setBackground(new Color(25,164,226));
            registerframe.add(reg_passwordfield);
            registerframe.add(signupbutton);
        }
        if(e.getSource()==signupbutton)
        {
            try{
                String username = reg_userfield.getText();
                String password = new String(reg_passwordfield.getPassword());
    
                if(username.equals("") || password.equals("")) 
                {
                    JOptionPane.showMessageDialog(null,"Invalid Username or Password!!","Message",JOptionPane.ERROR_MESSAGE);
                    reg_userfield.setText("");
                    reg_passwordfield.setText("");
                }
                else {pst.setString(1,username);
                pst.setString(2,password);
                pst.executeUpdate();
                
                JOptionPane.showMessageDialog(null,"Registration Successfull","Message",JOptionPane.INFORMATION_MESSAGE);
                registerframe.dispose(); }
                }
                catch(Exception excep)
                {
                    JOptionPane.showMessageDialog(null,"Username already exists!","Message",JOptionPane.ERROR_MESSAGE);
                    excep.printStackTrace();
                }
        }
        if(e.getSource()==loginbutton)
        {
            String username = userfield.getText();
            String password = new String(passwordfield.getPassword());
            try{
            String query = "select * from users where binary username=? and binary password=?";
            PreparedStatement pstlog = con.prepareStatement(query);
            pstlog.setString(1,username);
            pstlog.setString(2,password);
            ResultSet rs=pstlog.executeQuery();
            if (rs.next()) {
                JOptionPane.showMessageDialog(null, "Login successful!");
                new UserWindow(username);
                loginframe.dispose();
            } else {
                JOptionPane.showMessageDialog(null, "Invalid username or password.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
        catch(Exception exception)
        {
            exception.printStackTrace();
        }
        }
        if(e.getSource()==infobutton)
        {
            JOptionPane.showMessageDialog(null,"Developer: Kirubakaran V\nVersion: 1.0",
            "Developer Info",JOptionPane.INFORMATION_MESSAGE);
        }
    }
}

public class UserRegistration
{
       public static void main(String[] args) throws Exception
    {
        new Registration();
    }
}