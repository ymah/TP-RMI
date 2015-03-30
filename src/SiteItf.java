import java.io.Serializable;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;

public interface SiteItf extends Remote, Serializable {

	/**
	 * get Id of node
	 * 
	 * @return
	 * @throws RemoteException
	 */
	public int getId() throws RemoteException;

	/**
	 * get a message from the father node
	 * 
	 * @throws RemoteException
	 */
	public void getMessage(String m, SiteItf father) throws RemoteException;

	/**
	 * send a message to sons nodes
	 * 
	 * @throws RemoteException
	 */
	public void sendMessage(String m) throws RemoteException;

	/**
	 * add a father to current Node
	 * 
	 * @param pere
	 * @return {@link Boolean}
	 */
	public boolean addPere(SiteItf pere) throws RemoteException;

	/**
	 * add a son to current Node
	 * 
	 * @param fils
	 * @return {@link Boolean}
	 */
	public boolean addFils(SiteItf fils) throws RemoteException;

	/**
	 * remove a father of current Node
	 * 
	 * @param pere
	 * @return
	 */
	public boolean rmPere(SiteItf pere) throws RemoteException;

	/**
	 * remove a son of current Node
	 * 
	 * @param fils
	 * @return
	 */
	public boolean rmFils(SiteItf fils) throws RemoteException;

	/**
	 * return an arrayList containing the sons of the current Node
	 * 
	 * @return
	 * @throws RemoteException
	 */
	public ArrayList<SiteItf> getFils() throws RemoteException;

	/**
	 * return an arrayList containing the fathers of the current Node
	 * 
	 * @return
	 * @throws RemoteException
	 */
	public ArrayList<SiteItf> getPere() throws RemoteException;

	/**
	 * reset the lists of fathers and sons
	 * 
	 * @throws RemoteException
	 */
	public void reset() throws RemoteException;

	/**
	 * reset the visited value for another send threw the tree
	 * 
	 * @throws RemoteException
	 */
	public void resetVisited() throws RemoteException;
}
