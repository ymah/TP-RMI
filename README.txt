Réalistion d'une passerelle client/serveur RMI
Dylan Forest - Mahieddine Yaker
30/03/15

Ce TP contient un total de 3 executables permettant la mise en place d'une serveur d'objets et d'un client interagissant avec ces objets.

Comme définit dans le cours nous avons implémentés une interface et une implémentation de l'objet RMI, ici il s'agit de SiteItf et SiteImpl.
Ces classes désignent un noeud de l'arbre définit en TP, chacun d'entre eux contient une liste de père et une liste de fils, et est en mesure
d'envoyer un message à l'ensemble de ses fils et ce via des Threads.

Etant une passerelle RMI, chaque noeud est en fait un serveur à part entière relié à un même rmiregistry.
Pour émuler cela, le main présent dans Node.java prenant deux paramètre, un numéro de noeud ainsi que l'adresse du registre rmi, va binder une
nouvelle instance de la classe SiteImpl vers le registre lancé à l'adresse passée en second paramètre.

  ...
  SiteItf skeleton = new SiteImpl(nn);
  Naming.rebind(args[1]+"/Node_"+nn, (SiteItf) skeleton);
  ...
  
Chaque noeud / serveur aura donc un nom de la forme "Node_x" ou x est le numéro du noeud.

Pour nous éviter de lancer plusieurs fois le main de Node pour créer les noeuds nécessaire côté serveur, nous avons mis à disposition (pour les test)
le main NodeGenerator qui prendra deux paramètres, le premier étant le nombre de noeud à créer et le second l'adresse du registre rmi.

  ...
  for(int i = 1; i <= n; i++){
      argsN[0] = Integer.toString(i);
	  argsN[1] = args[1];
	  System.out.println("Building Node"+i+"....");
	  Node.main(argsN);
	  System.out.println("Success!");
  }
  ...
  
Ce main va simplement appeler x fois le main de Node sur la même adresse de registre, et ainsi créer x noeud (contrainte : sur la même machine..)

Une fois générer, ces noeuds pourront être utilisé via la passerelle rmi par le troisième et dernier main de notre implémentation : Client.java

Cette dernière classe prend les mêmes paramètre que NodeGenerator, cependant, plutôt que de créer les noeuds elle va les rechercher via l'adresse
du registre du second paramètre grâce à la méthode suivante :

  for(int i = 1; i <= nn; i++){
	  nodes[i-1] = (SiteItf) Naming.lookup(registry+"/Node_"+i);
  }
  
Nous avons décidés de rendre cette partie interactive, c'est à dire qu'une fois le tableau de noeuds rempli avec les noeud présents côté serveur, 
il nous est possible de les parcourir, de créer des arbres / graphs, de les lister, et d'envoyer des messages à un noeud pour en voir la propagation.

Une méthode permettra de générer l'arbre du tp (à condition d'avoir lancé et lookup 6 noeuds au minimum) :

  private static void buildCourseTree() throws RemoteException {
	  if(nodes.length >= 6){
		  resetNodes();
		  addFils(nodes[0], nodes[1]);
		  addFils(nodes[0], nodes[4]);
	      addFils(nodes[1], nodes[2]);
	      addFils(nodes[1], nodes[3]);
	      addFils(nodes[4], nodes[5]);
	  }else System.out.println("to build this tree, we need at least 6 nodes.");
  }

  Rq : addFils(x,y) ajoutera, et y comme fils de x et x comme père de y.
  
et une autre pour réinitialiser les noeuds (pas de pères ni de fils pour chacun) :

  private static void resetNodes() throws RemoteException {
	  for(SiteItf n: nodes)
	      n.reset();
  }
  
##################################
voir USAGE.txt Pour le test du TP.
##################################