import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;


/**
 * @author mahieddine
 *
 */
public class Server {

	public static void main(String[] args) {
		
		try {

			SiteItf skeleton = (SiteItf) UnicastRemoteObject.exportObject(new SiteImpl(), 10000);
			Registry registry = LocateRegistry.createRegistry(10000);
			registry.rebind("noeud1", skeleton);

		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
