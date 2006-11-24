//hello.java
import java.rmi.*;
public interface hello extends Remote
{
        public void DisplayHello(String usrIP, String s)throws RemoteException;
		public void registerUser(String ipAddress, String name) throws RemoteException;
		public String getMsg() throws RemoteException;
}
