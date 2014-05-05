package mpc;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.List;

/**
 * Handles playlist manipulation on the MPD server. Allows songs to be enqued on
 * the MPD server's playlist.
 * 
 * @author thelollies
 */

public class EnqueThread extends Thread {

	private Socket sock;
	private BufferedReader in;
	private PrintWriter out;

	private List<MPCSong> songs;
	private MPC mpc;

	/**
	 * Creates an instance of EnqueThread with the specified settings
	 * 
	 * @param address
	 * @param port
	 * @param songs list of songs to enque in the order they will be enqued
	 */
	public EnqueThread(MPC mpc, List<MPCSong> songs){
		this.mpc = mpc;
		this.songs = songs;
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

			enqueSongs();
		} catch(IOException e){
			mpc.connectionFailed("Connection failed, check settings");
		}
		
		try{
			sock.close();
			in.close();
			out.close();
		} catch(Exception e){}
	}

	/**
	 * 	Clears the MPD playlist before enqueing the list of songs in
	 *  the order they are passed.
	 * 
	 * @throws IOException
	 */
	
	private void enqueSongs() throws IOException{

		// Clear the playlist then request all song locations
		out.println("clear");
		in.readLine(); // clear the "OK" response for clearing

		out.println("command_list_begin"); // indicate to server to wait for multiple entries

		for(MPCSong song : songs){
			out.println("add \"" + song.file + "\"");
		}
		out.println("command_list_end"); // indicate to server that it can process the entries
		in.readLine();

	}
	
}
