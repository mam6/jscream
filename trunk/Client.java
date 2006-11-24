//Client.java
import java.awt.event.*;
import java.awt.*;
import javax.swing.*;
import java .rmi.*;
import java.rmi.server.*;
import java.net.*;

class ClientUI extends JFrame
        {
        JTextArea fieldOutput;
        JTextField fieldInput;
        JButton buttonSend;
        JLabel labelInput, labelOutput;
        String usrName, usrIP;
		JScrollPane myScrollPane;

		hello server;


        ClientUI()
                {
                super("My Messenger");
                
                usrName = JOptionPane.showInputDialog("Enter your great name to begin chatting:");
                
                
                try
                {
                	InetAddress ipAddress = InetAddress.getLocalHost();
                	usrIP = ipAddress .getHostAddress() ;
                	//InetAddress also requires to be in try block
                	server = (hello)Naming .lookup("rmi://192.168.100.1/HelloServer");
                	//if u change ip address dont forget to rmic
                	
                	server.registerUser(usrIP, usrName);
                }
                
                catch (Exception ex)
                {
                    System.out.println(ex);
                }
                
                
                /**********T H R E A D   C A L L   B E G I N S   H E R E  *****/
                		Runnable r = new FtchMsgRunnable();
        				Thread   t = new Thread(r);
        				t.start();
                
        				 
                
                /**************************************************************/
                
                Container c = getContentPane();
                c.setLayout(new FlowLayout());
                c.setBackground(Color.BLACK);

                labelOutput = new JLabel ("Your friends says :");
                labelOutput.setForeground(Color.WHITE);
                
                fieldOutput = new JTextArea(20,30);
                fieldOutput.setForeground(Color.BLUE);
                fieldOutput.setBackground(Color.GRAY);
                fieldOutput .setFont(new Font("SansSerif" , Font.BOLD, 14));
                fieldOutput.setEditable(false);


                labelInput = new JLabel ("Enter text that you want to chat");
                labelInput.setForeground(Color.WHITE);
                
                fieldInput = new JTextField (30);
                fieldInput .setForeground(Color.BLUE);
                fieldInput .setFont(new Font("SansSerif" , Font.PLAIN, 14));

                
                fieldInput.setBackground(Color.LIGHT_GRAY);

                buttonSend = new JButton("Send Message" );


				//Adding scrollpane for the output text area
				JScrollPane myScrollPane = new JScrollPane(fieldOutput );

                c.add(labelOutput  );
                c.add(myScrollPane );
                c.add(labelInput  );
                c.add(fieldInput  );
                c.add(buttonSend  );

                ClientUIHandler  ch = new ClientUIHandler ();
                buttonSend .addActionListener(ch);
                fieldInput.addActionListener(ch);

                }       //end of constructor

        		private class ClientUIHandler  implements ActionListener
                {
                               
                public void actionPerformed(ActionEvent e)
                        {
                        try
                            {                                                        
                            String sInput = fieldInput.getText();
                            fieldInput.setText("");
                            server.DisplayHello(usrIP, sInput);
                            
                            } //end of try block

                        catch (Exception ex)
                            {
                            System.out.println(ex);
                            }
                        }
                } //end of private action listener class
              
              
              
         //Thread class begins here, Here we use timer to constantly 
         //call the fetchmsg function       
         private class FtchMsgRunnable implements Runnable
			{
				
			String fullMsg = "";
			public void run()
				{
				//In order to run timer we need a ActionListenr class
		
				ActionListener actionListener = new ActionListener()
					{
					public void actionPerformed(ActionEvent actionEvent)
						{
						try
                            {                                                        
                                                       
                            fullMsg = server.getMsg();
                            //System.out.println("This is a test string");
                            } //end of try block

                        catch (Exception ex)
                            {
                            System.out.println(ex);
                            }	
							
							
							fieldOutput.setText(fullMsg);
							
						
						}
					};
		
				Timer timer = new Timer(1000,actionListener);
				timer.start();
		
				}  //end of run method
	
	
			}          
                
                
        } // end of outer class


public class Client
        {

        public static void main(String arg[])
                {
                ClientUI  cui = new ClientUI  ();
                cui.setSize(450,575);
                cui.setVisible(true);
                cui.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

                }

        } 