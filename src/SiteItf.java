import java.rmi.Remote;
import java.rmi.RemoteException;


/**
 * @author mahieddine
 *
 */
public interface SiteItf extends Remote {


	/**
	 * get a message from the father node
	 * @throws RemoteException
	 */
	public void getMessage() throws RemoteException;

	
	/**
	 * send a message to sons nodes
	 * @throws RemoteException
	 */
	public void sendMessage() throws RemoteException;
	
	
	public void init();
	
	public boolean addPere(SiteItf pere);
	public boolean addFils(SiteItf fils);
	
	
}
