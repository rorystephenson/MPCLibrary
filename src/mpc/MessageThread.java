package mpc;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.net.Socket;

public class MessageThread extends Thread{

	private MPC mpc;
	
	private Socket sock;
	private PrintWriter out;

	private String message;

	public MessageThread(MPC mpc, String message){
		this.mpc = mpc;
		this.message = message;
	}

	@Override
	public void run(){

		try{
			sock = new Socket();
			sock.connect(new InetSocketAddress(mpc.getAddress(), mpc.getPort()), mpc.timeout);

			out = new PrintWriter(sock.getOutputStream(), true);

			// Send the message
			out.println(message);

		} catch(IOException e){
			mpc.connectionFailed("Connection failed, check settings");
		} 

		try{
			out.close();
			sock.close();
		} catch(Exception e){}

	}
}