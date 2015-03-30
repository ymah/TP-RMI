import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.ArrayList;

/**
 * 
 * Main cÃ´tÃ© client interactif
 *
 */
public class Client {

	// Contiendra la liste des commandes utilisable
	private static String[] cmd;
	// Contiendra le usage de chaque commande
	private static String[] cmdOpt;

	// Addresse du registry
	private static String registry; 
		
	// ensemble des noeuds récupérés grâce au lookup
	private static SiteItf[] nodes;

	/**
	 * 
	 * @param args
	 * 
	 * Un argument attendu = nombre de noeuds à lookup.
	 * 
	 */
	public static void main(String[] args) {
		if (args.length != 2) {
			System.err
					.println("USAGE : java Client [nombre de noeud] [adresse du registre]\nie. java Client 6 rmi://localhost:1099");
			return;
		}

		// on récupère l'argument censé être le nombre de noeud à lookup
		String n = args[0];
		int nn = Integer.parseInt(n);

		registry = args[1];

		if (nn < 1) {
			System.err
					.println("USAGE : java Client [nombre de noeud] [adresse du registre]\nie. java Client 6 rmi://localhost:1099\nnombre de noeud > 1");
		}

		// construction des tableaux de commande
		// correspond à un "constructeur"
		buildcmd();
		
		// initialisation du tableau d'interfaces
		nodes = new SiteItf[nn];
				
		// lookup des différents noeuds
		try {
			for(int i = 1; i <= nn; i++){
				nodes[i-1] = (SiteItf) Naming.lookup(registry+"/Node_"+i);
			}					
		} catch (RemoteException e) {
			e.printStackTrace();
		} catch (NotBoundException e) {
			e.printStackTrace();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}

		/*
		 * Initialisation terminée, affichage de la partie interactive
		 */
		System.out.println("Build Complete\n########\nNodes array filled.\n");
		System.out.println("Here you can build the tree / graph :");
		System.out.println("Type help to see the commands you can use.");

		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		String cmd;
		while (true) {
			try {
				cmd = br.readLine();
				String[] cmds = cmd.split(" ");
				switch(cmds[0]){
					case "help":
						help();
						break;
					case "list":
						list();
						break;
					case "addFils":
						if(cmds.length < 3) help(2);
						else{
							int pere = Integer.parseInt(cmds[1]); 
							int fils = Integer.parseInt(cmds[2]);
							if(fils <= nodes.length && pere <= nodes.length
									&& fils > 0 && pere > 0){
								addFils(nodes[pere-1], nodes[fils-1]);
							}else help(2);
						}
						break;
					case "addPere":
						if(cmds.length < 3) help(3);
						else{
							int fils = Integer.parseInt(cmds[1]); 
							int pere = Integer.parseInt(cmds[2]);
							if(fils <= nodes.length && pere <= nodes.length
									&& fils > 0 && pere > 0){
								addPere(nodes[fils-1], nodes[pere-1]);
							}else help(3);
						}
						break;
					case "send":
						if(cmds.length < 3) help(4);
						else{
							int node = Integer.parseInt(cmds[2]);
							if(node <= nodes.length)
								send(cmds[1],nodes[node-1]);
							else help(4);
						}
						break;
					case "reset":
						resetNodes();
						break;
					case "buildCourseTree":
						buildCourseTree();
						break;
					case "exit":
						System.out.println("Bye.");
						return;
					default:
						System.out.println("Unknown command, type help to list them.");
						break;
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

	}

	/**
	 * Remplissage des tableaux cmd et cmdOpt cmd : liste des commandes
	 * utilisables cmdOpt : USAGE de chaque commande
	 */
	private static void buildcmd() {
		cmd = new String[8];
		cmdOpt = new String[8];

		cmd[0] = "help";
		cmdOpt[0] = "";
		cmd[1] = "list";
		cmdOpt[1] = "List the nodes, their fathers and their sons.";
		cmd[2] = "addFils";
		cmdOpt[2] = "USAGE : addFils 1 2\n\tAdd Node2 as son of Node1.";
		cmd[3] = "addPere";
		cmdOpt[3] = "USAGE : addPere 1 2\n\tAdd Node2 as father of Node1.";
		cmd[4] = "send";
		cmdOpt[4] = "USAGE : send message 1\n\tSend the message \"message\" from Node1.";
		cmd[5] = "reset";
		cmdOpt[5] = "Reset the nodes sons and fathers to null.";
		cmd[6] = "buildCourseTree";
		cmdOpt[6] = "Build a tree exemple (seen on CAR courses).";
		cmd[cmd.length - 1] = "exit";
		cmdOpt[cmd.length - 1] = "Exit the client.";
	}

	/**
	 * Sysout des commandes et de leur USAGE
	 */
	private static void help() {
		System.out.println("##############");
		System.out.println("#####HELP#####");
		System.out.println("##############");

		for (int i = 1; i < cmd.length; i++) {
			System.out.println(cmd[i] + " : ");
			System.out.println("\t" + cmdOpt[i]);
		}
	}

	/**
	 * @param i
	 *            Sysout USAGE de la commande
	 */
	private static void help(int i) {
		if (cmdOpt.length > i)
			System.out.println(cmdOpt[i]);
		else
			System.out.println("command not registred.");
	}

	/**
	 * @throws RemoteException
	 *             Crï¿½er l'arbre vu en TP pour les tests Rq : il doit y avoir 6
	 *             noeuds ou plus.
	 */
	private static void buildCourseTree() throws RemoteException {
		if (nodes.length >= 6) {
			resetNodes();
			addFils(nodes[0], nodes[1]);
			addFils(nodes[0], nodes[4]);
			addFils(nodes[1], nodes[2]);
			addFils(nodes[1], nodes[3]);
			addFils(nodes[4], nodes[5]);
		} else
			System.out.println("to build this tree, we need at least 6 nodes.");
	}

	/**
	 * @throws RemoteException
	 *             Reset les listes de peres/fils de chaque noeud.
	 */
	private static void resetNodes() throws RemoteException {
		for (SiteItf n : nodes)
			n.reset();
	}

	/**
	 * @param m
	 * @param node
	 * @throws RemoteException
	 *             Envoi du message m au noeud node
	 */
	private static void send(String m, SiteItf node) throws RemoteException {
		for (SiteItf n : nodes)
			n.resetVisited();
		node.getMessage(m, node);
	}

	// private static void display() {
	//
	// }

	/**
	 * @param fils
	 * @param pere
	 * @throws RemoteException
	 *             Ajout de "pere" en tant que pere du noeud "fils" + de "fils"
	 *             en tant que fils du noeud "pere"
	 */
	private static void addPere(SiteItf fils, SiteItf pere)
			throws RemoteException {
		fils.addPere(pere);
		pere.addFils(fils);
	}

	/**
	 * @param pere
	 * @param fils
	 * @throws RemoteException
	 *             Ajout de "fils" en tant que fils du noeud "pere" + de "pere"
	 *             en tant que pere du noeud "fils"
	 */
	private static void addFils(SiteItf pere, SiteItf fils)
			throws RemoteException {
		pere.addFils(fils);
		fils.addPere(pere);
	}

	/**
	 * @throws RemoteException
	 *             Listing des noeuds disponnibles, de leur pï¿½re et de leur fils
	 */
	private static void list() throws RemoteException {
		System.out.println("##############");
		System.out.println("#####LIST#####");
		System.out.println("##############");

		ArrayList<SiteItf> fils;
		ArrayList<SiteItf> peres;
		String dispF, dispP;

		for (int i = 0; i < nodes.length; i++) {
			dispF = dispP = "";
			System.out.println("Node_" + (i + 1) + " :");
			fils = nodes[i].getFils();
			peres = nodes[i].getPere();
			for (SiteItf s : fils) {
				dispF += "Node_" + s.getId();
			}
			for (SiteItf s : peres) {
				dispP += "Node_" + s.getId();
			}
			if (dispP.equals(""))
				System.out.println("\tNo fathers.");
			else
				System.out.println("\tfathers : " + dispP);
			if (dispF.equals(""))
				System.out.println("\tNo sons.");
			else
				System.out.println("\tsons : " + dispF);
		}
	}

}
