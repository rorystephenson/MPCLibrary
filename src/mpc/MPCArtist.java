package mpc;

import java.io.Serializable;

public class MPCArtist implements MPCMusicMeta, Serializable{

	private static final long serialVersionUID = -2710919412357353967L;
	public final String title;
	
	/**
	 * Holds the required information for artists
	 * @param file uri of the music file
	 * @param time length of the song
	 * @param artist artist name
	 * @param title song name
	 * @param album album name
	 * @param track track number on the CD
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
