import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;


public class Server {

	public static void main(String[] args) {
		
		SiteImpl skeleteon = (SiteImpl) UnicastRemoteObject.exportObject(new SiteImpl());
	}

}
