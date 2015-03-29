import java.rmi.Remote;
import java.rmi.RemoteException;

public interface SiteItf extends Remote {

	
	public int getId() throws RemoteException;
	
	/**
	 * get a message from the father node
	 * @throws RemoteException
	 */
	public void getMessage(String m, SiteItf father) throws RemoteException;

	
	/**
	 * send a message to sons nodes
	 * @throws RemoteException
	 */
	public void sendMessage(String m) throws RemoteException;
	

	/**
	 * add a father to current Node
	 * @param pere
	 * @return {@link Boolean}
	 */
	public boolean addPere(SiteItf pere) throws RemoteException;
	/**
	 * add a son to current Node
	 * @param fils
	 * @return {@link Boolean}
	 */
	public boolean addFils(SiteItf fils) throws RemoteException;
	/**
	 * remove a father of current Node
	 * @param pere
	 * @return
	 */
	public boolean rmPere(SiteItf pere) throws RemoteException;
	/**
	 * remove a son of current Node
	 * @param fils
	 * @return
	 */
	public boolean rmFils(SiteItf fils) throws RemoteException;
	
}
