import java.rmi.RemoteException;
import java.util.ArrayList;


public class SiteImpl implements SiteItf {

	private ArrayList<SiteItf> listeFils;
	private ArrayList<SiteItf> listePere;
	
	/**
	 * 
	 */
	public SiteImpl(){
	}

	
	
	/* (non-Javadoc)
	 * @see SiteItf#getMessage()
	 */
	public void getMessage() throws RemoteException{
		System.out.println("message Reçu");
		this.sendMessage();
	}

	
	public void sendMessage() throws RemoteException{
		for (SiteItf siteItf : listeFils) {
			siteItf.getMessage();
		}
	}
	
	public void init(){
		new Thread(){
			public void run(){
				
			}
		}.start();
	}




	public boolean addPere(SiteItf pere) {

		if(!listePere.contains(pere))
			return listePere.add(pere);
		return false;
	}



	@Override
	public boolean addFils(SiteItf fils) {
		if(!listeFils.contains(fils))
			return listeFils.add(fils);
		return false;
	}

}
