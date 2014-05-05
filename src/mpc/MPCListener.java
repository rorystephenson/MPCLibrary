package mpc;

public interface MPCListener {
	public void connectionFailed(final String message);
	public void databaseUpdated();
	public void statusUpdate(MPCStatus newStatus);
}
