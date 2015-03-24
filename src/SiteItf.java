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
	public void getMessage(Message m) throws RemoteException;

	
	/**
	 * send a message to sons nodes
	 * @throws RemoteException
	 */
	public void sendMessage(Message m) throws RemoteException;
	

	/**
	 * add a father to current Node
	 * @param pere
	 * @return {@link Boolean}
	 */
	public boolean addPere(SiteItf pere);
	/**
	 * add a son to current Node
	 * @param fils
	 * @return {@link Boolean}
	 */
	public boolean addFils(SiteItf fils);
	/**
	 * remove a father of current Node
	 * @param pere
	 * @return
	 */
	public boolean rmPere(SiteItf pere);
	/**
	 * remove a son of current Node
	 * @param fils
	 * @return
	 */
	public boolean rmFils(SiteItf fils);
	
}
