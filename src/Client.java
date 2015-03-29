import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;


public class Client {
	
	private static int port = 1098;
	
	/**
	 * 
	 * @param args
	 * 
	 * Un argument attendu = nombre de noeuds à lookup.
	 * 
	 */
	public static void main(String[] args) {
		if (args.length != 1) {
			System.err.println("USAGE : java Client [numero de noeud]");
			return;
		}
		
		String n = args[0];
		int nn = Integer.parseInt(n);
		
		if(nn < 1){
			System.err.println("USAGE : java Client [numero de noeud]\nNombre de noeud >= 1");
		}
		
		SiteItf[] nodes = new SiteItf[nn];
		Registry[] registries = new Registry[nn];
		
		try {
			for(int i = 1; i <= nn; i++){
				registries[i-1] = LocateRegistry.getRegistry(port+i);
				nodes[i-1] = (SiteItf) registries[i-1].lookup("Node_"+i);
			}
			
			if(nodes[0].addFils(nodes[1])) System.out.println("ouaisouaisouais");
			if(nodes[1].addFils(nodes[0])) System.out.println("ouaisouaisouais");
			
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NotBoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
