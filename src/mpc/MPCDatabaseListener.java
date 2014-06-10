package mpc;

/**
 * Interface for listening to database updates.
 * @author Rory Stephenson
 *
 */
public interface MPCDatabaseListener {
	/**
	 * Database update complete
	 */
	public void databaseUpdated();
	
	/**
	 * Database update progress has changed to the specified amount
	 * @param progress
	 */
	public void databaseUpdateProgressChanged(int progress);
	
	/**
	 * Connection failed whilst updating the database
	 * @param message
	 */
	public void connectionFailed(final String message);
}
