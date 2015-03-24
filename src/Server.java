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
			SiteItf skeleteon = (SiteItf) UnicastRemoteObject.exportObject(new SiteImpl());
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
