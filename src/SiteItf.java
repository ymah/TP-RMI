import java.rmi.Remote;
import java.rmi.RemoteException;


public interface SiteItf extends Remote {


	public void getMessage() throws RemoteException;

	
	public void sendMessage() throws RemoteException;
	
	
	
}
