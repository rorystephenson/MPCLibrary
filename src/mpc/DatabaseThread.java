package mpc;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.ArrayList;

/**
 * Handles interaction with the MPD server database. Used to fetch all song
 * information as well as to instruct the database on the server to update.
 * 
 * @author thelollies
 *
 */

public class DatabaseThread extends Thread{

	private MPC mpc;
	
	private Socket sock;
	private BufferedReader in;
	private PrintWriter out;

	/**
	 * Creates an instance of database thread with the specified settings
	 * 
	 * @param address
	 * @param port
	 * @param activity activity the database is being accessed from
	 */
	public DatabaseThread(MPC mpc){
		this.mpc = mpc;
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

			renewServer();
			renewDatabase(getSongCount());
		} catch(IOException e){
			mpc.connectionFailed("Connection failed, check settings");
		}
		try{
			mpc.getDatabase().endTransaction();
			sock.close();
			if(in != null) in.close();
			if(out != null) out.close();
		} catch(Exception e){}
		mpc.databaseUpdated();
	}

	private int getSongCount() throws IOException {
		out.println("stats");
		String line;
		Integer songCount = null;
		while((line = in.readLine()) != null){
			if(line.equals("OK")){break;}
			if(line.startsWith("songs: "))
				songCount = toInt(line.substring(7));
		}
		if(songCount != null) return songCount;
		throw new IOException("Couldn't determine total song count");
	}

	/**
	 * Instructs the server to update it's database and repeats the instruction
	 * until it is successfully executed.
	 * 
	 * @throws IOException
	 */
	private void renewServer() throws IOException {
		out.println("update");
		in.readLine();in.readLine();
	}

	/**
	 * Requests a list of all songs and their info from the server. Uses this information
	 * to populate the local database.
	 * 
	 * @throws IOException
	 */
	private void renewDatabase(int numSongs) throws IOException {
		int songsProcessed = 0;
		int progressPercent = 0;
		
		// Request all the song data from MPD server
		out.println("listallinfo");

		MusicDatabase musicDatabase = mpc.getDatabase();
		musicDatabase.startTransaction();
		musicDatabase.clear();
		
		String line = in.readLine();
		while(line != null){
			if(line.equals("OK")){break;}
			// Song attributes
			String file = null;
			int time = 0;
			String artist = null;
			String title = null;
			String album = null;
			int track = 0;

			boolean nextSong = false;
			while(line != null){
				String currentLine = line;
				if(line.equals("OK")){break;}
				
				if(currentLine.startsWith("directory: ")){
					line = in.readLine(); continue;} // Skip directory lines
				else if(currentLine.startsWith("file: ")){
					if(nextSong){break;}
					file = currentLine.substring(6);
					nextSong = true;
				}
				else if(currentLine.startsWith("Time: ")){
					time = Integer.parseInt(currentLine.substring(6));}
				else if(currentLine.startsWith("Artist: ")){
					artist = currentLine.substring(8);}
				else if(currentLine.startsWith("Title: ")){
					title = currentLine.substring(7);}
				else if(currentLine.startsWith("Album: ")){
					album = currentLine.substring(7);}
				else if(currentLine.startsWith("Track: ")){
					if(currentLine.contains("/")){ // Handles track numbers with num/total format
						currentLine.substring(7,currentLine.indexOf("/"));
					}
					else{
						track = toInt(currentLine.substring(7));
					}
				}
				line = in.readLine();

			}
			musicDatabase.addSong(new MPCSong(file, time, artist, title, album, track));
			songsProcessed++;
			// Check if we've reached a new level of progress
			int newProg;
			if((newProg = (100 * songsProcessed) / numSongs) > progressPercent){
				progressPercent = newProg;
				mpc.databaseUpdateProgressChanged(newProg);
			}
		}
		musicDatabase.setTransactionSuccessful();
	}
	
	private static int toInt(String str){
		try{
			return Integer.parseInt(str);
		}catch(NumberFormatException e){
			e.printStackTrace();
			return 0;
		}
	}
	
}
