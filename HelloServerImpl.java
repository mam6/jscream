//HelloServerImpl.java
import javax.swing.*;
import java.rmi.*;
import java.rmi.server.UnicastRemoteObject;
import java.awt.*;
import java.awt.event.*;

class ServerUI extends JFrame
{
	
	JTextArea fieldOutput;
    JTextField fieldInput;
    JButton buttonSend;
    JLabel labelInput, labelOutput;
    JLabel labelTotUsr = new JLabel("Total users connected to server: 0");
    JLabel blankLine = new JLabel(".........................................................................");
    String usrName;
    JScrollPane myScrollPane;
    
    
    ServerUI()
    {
    	
    	super("My Messenger - Server(Admin)");
       
       
                
        usrName = JOptionPane.showInputDialog("Hi, Server Admin, Please enter ur name:");
    	
    	labelTotUsr.setForeground(Color.RED);
    	blankLine.setForeground(Color.DARK_GRAY);
    	
    		
    	Container c = getContentPane();
        c.setLayout(new FlowLayout());
        c.setBackground(Color.DARK_GRAY);

        labelOutput = new JLabel ("Your friends says :");
        labelOutput.setForeground(Color.WHITE);
                
        fieldOutput = new JTextArea(20,30);
        fieldOutput.setForeground(Color.GREEN);
        fieldOutput.setBackground(Color.BLACK);
        fieldOutput .setFont(new Font("SansSerif" , Font.BOLD, 14));
        fieldOutput.setEditable(false);
        
        labelInput = new JLabel ("Enter text that you want to chat");
 		labelInput.setForeground(Color.WHITE);
                
        fieldInput = new JTextField (30);
        fieldInput .setForeground(Color.BLACK);
        fieldInput .setFont(new Font("SansSerif" , Font.PLAIN, 14));

        
        fieldInput.setBackground(Color.LIGHT_GRAY);

        buttonSend = new JButton("Send Message" );


		ServerUIHandler  suiHandler = new ServerUIHandler ();
        buttonSend .addActionListener(suiHandler);
        fieldInput.addActionListener(suiHandler);


		//Adding scrollpane for the output text area
		JScrollPane myScrollPane = new JScrollPane(fieldOutput );	

		c.add(labelTotUsr);
		c.add(blankLine);
        c.add(labelOutput  );
        c.add(myScrollPane );
        c.add(labelInput  );
        c.add(fieldInput  );
        c.add(buttonSend  );              
      	
    		
    } //end of constructor
	
	public void setOutputField(String sFrmClient)
	{
		
		fieldOutput.setText(sFrmClient);
	}
	
	
	public void setlabelTotUsr(int totUsers)
	{
		labelTotUsr.setText("Total users connected to server: " + totUsers);
	}
	
	private class ServerUIHandler  implements ActionListener
    {
    
                               
       public void actionPerformed(ActionEvent e)
       {
            
            HelloServerImpl.setFullMsg(usrName+ ": " + fieldInput.getText() );
            fieldInput.setText("");
            fieldOutput.setText(HelloServerImpl.fullMsg);
            
       } //end of actionPerformed
	
	} //end of private class
	
} //end of outer class




public class HelloServerImpl extends UnicastRemoteObject implements hello
{
	int totUsr,i;
	String usrIP[] = new String[10];
	String usrName[] = new String[10];
	static String fullMsg="";
	ServerUI  sui ;
	
        public HelloServerImpl() throws RemoteException
        {
                super();
                
                sui = new ServerUI();
                sui.setSize(450,575);
                sui.setVisible(true);
                sui.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                
                
                
                
        }
        public void DisplayHello(String currentIP, String sFrmClient) throws RemoteException
        {
        	
        	for(i = 0; i< totUsr; i++)
        	{
        		if(usrIP[i].equals(currentIP))
        		break;
        	}
        		
        		System.out.print(usrName[i]+": ");
                System.out.println( sFrmClient);
                fullMsg = fullMsg + usrName[i] + ": " + sFrmClient+"\n";
				sui.setOutputField(fullMsg);
        }
        
        public static void setFullMsg(String serverInput)
        {
        	fullMsg = fullMsg + serverInput +"\n";
        	
        }
        public String getMsg() throws RemoteException
        	{
        		return fullMsg;
        	}
        
        
        
        public void registerUser(String ipAddress, String name)throws RemoteException
        {
        	usrIP[totUsr] =ipAddress;
        	usrName[totUsr] =name;
        	totUsr++;
        	sui.setlabelTotUsr(totUsr);
        	System.out.println("\nYou are user number : "+totUsr+"\n");
        	fullMsg = fullMsg + name  +  " joined the chat room" +"\n";
        	sui.setOutputField(fullMsg);
        	
        	
        }
        public static void main(String argc[])
        {
                System.setSecurityManager(new RMISecurityManager());
                try
                {
                        HelloServerImpl instance = new HelloServerImpl();
                        Naming.rebind("HelloServer",instance);
                        System.out.println("Server Registered -All the best");
                }
                catch (Exception e)
                {
                        System.err.println(e);  
                }
                
                
                
                
        }


} 