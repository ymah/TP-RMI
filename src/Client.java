import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.ArrayList;


public class Client {
	
	private static String[] cmd;
	private static String[] cmdOpt;
	
	private static int port = 1098;
	private static Registry[] registries;
	private static SiteItf[] nodes;
	
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
		
		buildcmd();
		
		nodes = new SiteItf[nn];
		registries = new Registry[nn];
		
		
		try {
			for(int i = 1; i <= nn; i++){
				registries[i-1] = LocateRegistry.getRegistry(port+i);
				nodes[i-1] = (SiteItf) registries[i-1].lookup("Node_"+i);
			}					
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NotBoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		System.out.println("Here you can build the tree / graph :");
		System.out.println("Type help to see the commands you can use.");
		
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		String cmd;
		while(true){
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
							addPere(nodes[1], nodes[0]);
						}
						break;
					case "display":
						display();
						break;
					case "send":
						if(cmds.length < 3) help(5);
						else{
							send("message",nodes[0]);
						}
						break;
					default:
						System.out.println("Unknown command, type help to list the ones you can use.");
						break;
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
	}

	private static void buildcmd() {
		cmd = new String[7];
		cmdOpt = new String[7];
		
		cmd[0] = "help";
		cmdOpt[0] = "";
		cmd[1] = "list";
		cmdOpt[1] = "List the nodes, their fathers and their sons.";
		cmd[2] = "addFils";
		cmdOpt[2] = "USAGE : addFils 1 2\n\tAdd Node2 as son of Node1.";
		cmd[3] = "addPere";
		cmdOpt[3] = "USAGE : addPere 1 2\n\tAdd Node2 as father of Node1.";
		cmd[4] = "display";
		cmdOpt[4] = "Display the tree/graph.";
		cmd[5] = "send";
		cmdOpt[5] = "USAGE : send message 1\n\tSend the message \"message\" from Node1.";
		cmd[6] = "exit";
		cmdOpt[6] = "Exit the client.";
	
	}

	private static void help(){
		System.out.println("##############");
		System.out.println("#####HELP#####");
		System.out.println("##############");
		
		for(int i = 1; i < cmd.length; i++){
			System.out.println(cmd[i]+" : ");
			System.out.println("\t"+cmdOpt[i]);
		}
	}
	
	private static void help(int i) {
		if(cmdOpt.length < i)
			System.out.println(cmdOpt[i]);
		else System.out.println("command not registred.");
	}
	
	private static void send(String string, SiteItf siteItf) {
		// TODO Auto-generated method stub
		
	}

	private static void display() {
		// TODO Auto-generated method stub
		
	}

	private static void addPere(SiteItf fils, SiteItf pere) throws RemoteException{
		fils.addPere(pere);		
	}

	private static void addFils(SiteItf pere, SiteItf fils) throws RemoteException {
		pere.addFils(fils);
	}
	
	private static void list() throws RemoteException{
		System.out.println("##############");
		System.out.println("#####LIST#####");
		System.out.println("##############");
		
		ArrayList<SiteItf> fils;
		ArrayList<SiteItf> peres;
		String dispF, dispP;
		
		for(int i = 0; i < nodes.length; i++){
			dispF = dispP = "";
			System.out.println("Node_"+(i+1)+" :");
			fils  = nodes[i].getFils();
			peres = nodes[i].getPere();
			for(SiteItf s: fils){
				dispF += "Node_"+s.getId();
			}
			for(SiteItf s: peres){
				dispP += "Node_"+s.getId();
			}
			if(dispP.equals("")) System.out.println("\tNo fathers.");
			else System.out.println("\tfathers : "+dispP);
			if(dispF.equals("")) System.out.println("\tNo sons.");
			else System.out.println("\tsons : "+dispF);
		}
	}
	
}
