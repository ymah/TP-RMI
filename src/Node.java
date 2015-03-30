
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;


/**
 * 
 * Main Server, ou Noeud
 * Ce main est a appeler autant de fois que de noeuds sont nécessaire pour l'exemple
 * 
 * main_param : ce main attend un paramètre qui est le numéro du noeud
 * + un second paramètre qui est l'adresse du registre ex: rmi://localhost:1099
 *
 */
public class Node {

//	private static int port = 1099;
	
	public static void main(String[] args) {		
		if (args.length != 2) {
			System.err.println("USAGE : java Node [numero de noeud] [registry address]\nie. java Node 1 rmi://localhost:1099");
			return;
		}
		
		String n = args[0];
		int nn = Integer.parseInt(n);
		
		try {
//			SiteItf skeleton = (SiteItf) UnicastRemoteObject.exportObject(new SiteImpl(nn), (port+nn));
//			Registry registre= LocateRegistry.createRegistry((port+nn));
//			SiteItf skeleton = new SiteImpl(nn);
//			registre.rebind("Node_"+n, skeleton);
//			Naming.rebind("rmi://localhost:1099/"+"Node_"+nn, skeleton);
			
//			Registry registre;
//			SiteItf skeleton = (SiteItf) UnicastRemoteObject.exportObject(new SiteImpl(nn), (port+1));
//			registre = LocateRegistry.getRegistry((port+1));
//			registre.rebind("Node_"+n, skeleton);
			
			SiteItf skeleton = new SiteImpl(nn);
			Naming.rebind(args[1]+"/Node_"+nn, (SiteItf) skeleton);
		} catch (RemoteException e) {
			e.printStackTrace();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
	}

}
