package mpc;

import java.io.Serializable;

/**
 * Holds the required information of an artist and allows comparison
 * between albums.
 * 
 * @author Rory Stephenson
 * 
 */
public class MPCArtist implements MPCMusicMeta, Serializable{

	private static final long serialVersionUID = -2710919412357353967L;
	public final String title;
	
	/**
	 * Holds the required information for artists
	 * @param artist artist name
	 */
	public MPCArtist(String artist){
		this.title = artist == null ? "Unknown Artist" : artist;
	}
	
	@Override
	public String toString(){
		return title;
	}
	
	@Override
	public boolean equals(Object obj) {
		if(!(obj instanceof MPCArtist)) return false;
		MPCArtist other = (MPCArtist)obj;
		return other.title.equals(title);
	}
	
	@Override
	public String getName() {
		return title;
	}
	
	@Override
	public String getDescription() {
		return "Albums";
	}
}
