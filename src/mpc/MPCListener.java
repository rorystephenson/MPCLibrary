package mpc;

/**
 * Interface for listening to MPClient updates.
 * @author Rory Stephenson
 *
 */
public interface MPCListener {
	
	/**
	 * Failed connection
	 * @param message
	 */
	public void connectionFailed(final String message);
	
	/**
	 * A database update has been succesfully completed
	 */
	public void databaseUpdated();
	
	/**
	 * There has been a change to the MPD server status
	 * @param newStatus
	 */
	public void statusUpdate(MPCStatus newStatus);
}
