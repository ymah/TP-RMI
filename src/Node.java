
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
	
	public static void main(String[] args) {		
		if (args.length != 2) {
			System.err.println("USAGE : java Node [numero de noeud] [registry address]\nie. java Node 1 rmi://localhost:1099");
			return;
		}
		
		String n = args[0];
		int nn = Integer.parseInt(n);
		
		try {
			SiteItf skeleton = new SiteImpl(nn);
			Naming.rebind(args[1]+"/Node_"+nn, (SiteItf) skeleton);
		} catch (RemoteException e) {
			e.printStackTrace();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
	}

}
