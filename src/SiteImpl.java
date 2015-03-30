import java.rmi.RemoteException;
import java.util.ArrayList;

public class SiteImpl implements SiteItf {

	private int id;
	private ArrayList<SiteItf> listeFils;
	private ArrayList<SiteItf> listePere;
	private boolean visited;

	/**
	 * 
	 */
	public SiteImpl(int id) {
		this.id = id;
		this.listeFils = new ArrayList<SiteItf>();
		this.listePere = new ArrayList<SiteItf>();
		this.visited = false;
	}

	/*
	 * (non-Javadoc)toString
	 * 
	 * @see SiteItf#getMessage()
	 */
	public void getMessage(String m, SiteItf father) throws RemoteException {

		synchronized (this) {
			if (visited)
				return;
			System.out.println("#########");
			System.out.println("je suis le Node_" + getId());
			System.out.println("Message recu de Node_" + father.getId());
			System.out.println(m);
			this.visited = true;
		}
		this.sendMessage(m);
	}

	/*
	 * (non-Javadoc)
	 * 
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

	/*
	 * (non-Javadoc)
	 * 
	 * @see SiteItf#addPere(SiteItf)
	 */
	public boolean addPere(SiteItf pere) throws RemoteException {
		if (!listePere.contains(pere))
			return listePere.add(pere);
		return false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see SiteItf#addFils(SiteItf)
	 */
	public boolean addFils(SiteItf fils) throws RemoteException {
		if (!listeFils.contains(fils))
			return listeFils.add(fils);
		return false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see SiteItf#rmPere(SiteItf)
	 */
	public boolean rmPere(SiteItf pere) throws RemoteException {
		if (this.listePere.contains(pere))
			return this.listePere.remove(pere);
		return false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see SiteItf#rmFils(SiteItf)
	 */
	public boolean rmFils(SiteItf fils) throws RemoteException {
		if (this.listePere.contains(fils))
			return this.listePere.remove(fils);
		return false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see SiteItf#getId(SiteItf)
	 */
	public int getId() throws RemoteException {
		return this.id;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see SiteItf#getFils(SiteItf)
	 */
	public ArrayList<SiteItf> getFils() throws RemoteException {
		return this.listeFils;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see SiteItf#getPere(SiteItf)
	 */
	public ArrayList<SiteItf> getPere() throws RemoteException {
		return this.listePere;
	}

	@Override
	public void reset() throws RemoteException {
		this.listePere.clear();
		this.listeFils.clear();
	}

	@Override
	public void resetVisited() throws RemoteException {
		this.visited = false;
	}

}
