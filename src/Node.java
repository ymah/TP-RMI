import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;


/**
 * 
 * Main Server, ou Noeud
 * Ce main est a appeler autant de fois que de noeuds sont nécessaire pour l'exemple
 * 
 * main_param : ce main attend un paramètre qui est le numéro du noeud mais également 
 * le port du registre sur la machine.
 *
 */
public class Node {

	public static void main(String[] args) {		
		if (args.length != 1) {
			System.err.println("USAGE : java Node [numero de noeud]");
			return;
		}
		
		String n = args[0];
		int nn = Integer.parseInt(n);
		
		try {
			SiteItf skeleton = (SiteItf) UnicastRemoteObject.exportObject(new SiteImpl(nn), (1098+nn));
			Registry registre= LocateRegistry.createRegistry((1098+nn));
			registre.rebind("Node_"+n, skeleton);
		} catch (RemoteException e) {
			System.out.println("erreur server");
		}
	}

}
