
public class NodeGenerator {

	public static void main(String[] args) {
		if (args.length != 1) {
			System.err.println("USAGE : java Server [nombre de noeuds]");
			return;
		}
		int n = Integer.parseInt(args[0]);

		for(int i = 1; i <= n; i++){
			String[] argsN = {Integer.toString(i)};
			System.out.println("Building Node"+i+"....");
			Node.main(argsN);
			System.out.println("Success!");
		}
	}

}
