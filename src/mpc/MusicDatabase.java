package mpc;

/**
 * Interface for the music database.
 */
public interface MusicDatabase {
	
	public void clear();
	public void addSong(MPCSong song);
	
	/**
	 * Starts a transaction.
	 * This method is not required but if implemented (in conjunction with the other
	 * two transactional methods) it will perform database updates transactionally.
	 */
	public void startTransaction();
	
	/**
	 * Marks a transaction as successful.
	 * This method is not required but if implemented (in conjunction with the other
	 * two transactional methods) it will perform database updates transactionally.
	 */
	public void setTransactionSuccessful();
	
	/**
	 * Ends a transaction.
	 * This method is not required but if implemented (in conjunction with the other
	 * two transactional methods) it will perform database updates transactionally.
	 */
	public void endTransaction();
	
}
