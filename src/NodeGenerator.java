
import java.rmi.RMISecurityManager;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;


/**
 * Generateur de noeuds
 * Ce main prend en paramètre le nombre de noeud à générer et l'adresse du rmiregistry ex: rmi://localhost:1099
 * 
 * Appel x fois le main de Node avec un numéro de noeud différent.
 * Voir Node.java pour plus de précision.
 *
 */
public class NodeGenerator {

	public static void main(String[] args) {
		if (args.length != 2) {
			System.err.println("USAGE : java Server [nombre de noeuds] [registry address]\nie. java NodeGenerator 6 rmi://localhost:1099");
			return;
		}
		int n = Integer.parseInt(args[0]);

		String[] argsN = new String[2];
				
		for(int i = 1; i <= n; i++){
			argsN[0] = Integer.toString(i);
			argsN[1] = args[1];
			System.out.println("Building Node"+i+"....");
			Node.main(argsN);
			System.out.println("Success!");
		}
	}

}
