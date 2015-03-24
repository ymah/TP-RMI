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
			SiteItf skeleton = (SiteItf) UnicastRemoteObject.exportObject(new SiteImpl());
			Registry registre= LocateRegistry.createRegistry(10000);
			registre.rebind("Node", skeleton );

		} catch (RemoteException e) {

			System.out.println("erreur server");

		}
	}

}
