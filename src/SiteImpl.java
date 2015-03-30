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
		this.listeFils = new ArrayList<SiteItf>();
		this.listePere = new ArrayList<SiteItf>();
	}

	/*
	 * (non-Javadoc)toString
	 * 
	 * @see SiteItf#getMessage()
	 */
	public void getMessage(String m, SiteItf father) throws RemoteException {
		synchronized (this) {
			System.out.println("Message recu de Node_"+father.getId());
			System.out.println(m);
		}
		this.sendMessage(m);
	}

	/* (non-Javadoc)
	 * @see SiteItf#sendMessage()
	 */
	public void sendMessage(final String m) throws RemoteException {
		for (final SiteItf fils : listeFils) {
			new Thread() {
				public void run() {
					try {
						fils.getMessage(m, SiteImpl.this);
					} catch (RemoteException e) {
						e.printStackTrace();
					}
				}
			}.start();
		}
	}


	/* (non-Javadoc)
	 * @see SiteItf#addPere(SiteItf)
	 */
	public boolean addPere(SiteItf pere) throws RemoteException{
		if (!listePere.contains(pere))
			return listePere.add(pere);
		return false;
	}

	/* (non-Javadoc)
	 * @see SiteItf#addFils(SiteItf)
	 */
	public boolean addFils(SiteItf fils) throws RemoteException{
		if (!listeFils.contains(fils))
			return listeFils.add(fils);
		return false;
	}

	/* (non-Javadoc)
	 * @see SiteItf#rmPere(SiteItf)
	 */
	public boolean rmPere(SiteItf pere) throws RemoteException{
		if(this.listePere.contains(pere))
			return this.listePere.remove(pere);
		return false;
	}

	/* (non-Javadoc)
	 * @see SiteItf#rmFils(SiteItf)
	 */
	public boolean rmFils(SiteItf fils) throws RemoteException{
		if(this.listePere.contains(fils))
			return this.listePere.remove(fils);
		return false;
	}

	@Override
	public int getId() throws RemoteException{
		return this.id;
	}

	@Override
	public ArrayList<SiteItf> getFils() throws RemoteException {
		return this.listeFils;
	}

	@Override
	public ArrayList<SiteItf> getPere() throws RemoteException {
		return this.listePere;
	}

}
