import java.rmi.RemoteException;
import java.util.ArrayList;

public class SiteImpl implements SiteItf {

	private int id;
	private ArrayList<SiteItf> listeFils;
	private ArrayList<SiteItf> listePere;

	/**
	 * 
	 */
	public SiteImpl(int id) {
		this.id = id;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see SiteItf#getMessage()
	 */
	public void getMessage(Message m) throws RemoteException {
		synchronized (this) {
			System.out.println(m.toString());
		}
		this.sendMessage(m);
	}

	/* (non-Javadoc)
	 * @see SiteItf#sendMessage()
	 */
	public void sendMessage(Message m) throws RemoteException {
		for (SiteItf siteItf : listeFils) {
			siteItf.getMessage(m);
		}
	}


	/* (non-Javadoc)
	 * @see SiteItf#addPere(SiteItf)
	 */
	public boolean addPere(SiteItf pere) {

		if (!listePere.contains(pere))
			return listePere.add(pere);
		return false;
	}

	/* (non-Javadoc)
	 * @see SiteItf#addFils(SiteItf)
	 */
	public boolean addFils(SiteItf fils) {
		if (!listeFils.contains(fils))
			return listeFils.add(fils);
		return false;
	}

	/* (non-Javadoc)
	 * @see SiteItf#rmPere(SiteItf)
	 */
	public boolean rmPere(SiteItf pere) {
		if(this.listePere.contains(pere))
			return this.listePere.remove(pere);
		return false;
	}

	/* (non-Javadoc)
	 * @see SiteItf#rmFils(SiteItf)
	 */
	public boolean rmFils(SiteItf fils) {
		if(this.listePere.contains(fils))
		return false;
		return this.listePere.remove(fils);
	}

}
