package mpc;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.net.Socket;

/**
 * Thread used to query the server's status (used to see if music is playing
 * and if the playlist is set to shuffle).
 * @author thelollies
 *
 */

public class StatusThread extends Thread{

	private Socket sock;
	private BufferedReader in;
	private PrintWriter out;

	private MPC mpc;
	protected MPCStatus status = null;
	private boolean fireListener;

	public StatusThread(MPC mpc, boolean fireListener){
		this.mpc = mpc;
		this.fireListener = fireListener;
	}

	@Override
	public void run(){

		// Establish socket connection
		try{
			sock = new Socket();
			sock.connect(new InetSocketAddress(mpc.getAddress(), mpc.getPort()), mpc.timeout);

			in = new BufferedReader(new InputStreamReader(sock.getInputStream()));
			out = new PrintWriter(sock.getOutputStream(), true);

			// Clear version number from buffer
			in.readLine();

			checkPlayingStatus();

		} catch(IOException e){
			mpc.connectionFailed("Connection failed, check settings");
		}
		try{
			sock.close();
			if(in != null) in.close();
			if(out != null) out.close();
		} catch(Exception e){e.printStackTrace();}
	}

	/**
	 * Determines whether a song is playing and whether the MPD server
	 * is set to shuffle. Also checks volume.
	 * @throws IOException
	 */
	private void checkPlayingStatus() throws IOException {
		out.println("status");
		
		boolean playing = false;
		boolean shuffling = false;
		Integer currentVol = null;

		String response;
		while((response = in.readLine()) != null){
			if(response.equals("OK")){break;}
			if(response.startsWith("state: ")){
				String state = response.substring(7);
				playing = state.equals("play") ? true : false;
			}
			if(response.startsWith("random: ")){
				int shuffleValue = Integer.parseInt(response.substring(8));
				shuffling = shuffleValue == 1 ? true : false;
			}
			if(response.startsWith("volume: ")){
				currentVol = Integer.parseInt(response.substring(8));
			}
		}
		this.status = new MPCStatus(playing, shuffling, currentVol);
		if(fireListener) mpc.statusUpdate(this.status);
	}

}