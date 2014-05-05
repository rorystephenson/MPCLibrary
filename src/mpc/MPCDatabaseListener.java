package mpc;

public interface MPCDatabaseListener {
	public void databaseUpdated();
	public void databaseUpdateProgressChanged(int progress);
	public void connectionFailed(final String message);
}
