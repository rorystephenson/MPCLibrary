package mpc;

/**
 * Holds a query in a compact form which can be passed to the database
 * to fetch songs or compared with other queries rather than comparing
 * a list of results of the query to see if the lists are the same.
 * 
 * This class implmenets parcelable so that it can be passed in intents
 * between activites.
 * 
 * @author thelollies
 */

public class MPCQuery{

	private int type = 0;
	private String artist = "";
	private String album = "";
	private int buttonId = 0;
	
	// Note:
	// Queries with return type MPCSong <= 3
	// Queries with return type MPCAlbum <= 5
	// Query with return type String = 6
	public final static int ALL_SONGS = 1;
	public final static int SONGS_BY_ALBUM_ARTIST = 2;
	public final static int SONGS_BY_ARTIST = 3;
	public final static int ALL_ALBUMS = 4;
	public final static int ALBUMS_BY_ARTIST = 5;
	public final static int ALL_ARTISTS = 6;
	
	/**
	 * Constructor used when the query does not require artist or album 
	 * information.
	 * @param type MPCQuery constant indicating type of query
	 */
	public MPCQuery(int type){
		this.type = type;
	}
	
	/**
	 * Constructor used when the query does not require album 
	 * information.
	 * @param type MPCQuery constant indicating type of query
	 */
	public MPCQuery(int type, String artist){
		this.type = type;
		this.artist = artist;
	}
	
	/**
	 * Constructor used when the query requires artist and album 
	 * information. Clicked button is the button pertaining to the
	 * category this query was made from. Required by the return to
	 * playing feature.
	 *  
	 * @param type MPCQuery constant indicating type of query
	 */
	public MPCQuery(int type, String artist, String album){
		this.type = type;
		this.artist = artist;
		this.album = album;
	}
	
	@Override
	public boolean equals(Object o){
		if(o instanceof MPCQuery){
			MPCQuery q = (MPCQuery) o;
			return (type == q.getType() && 
					artist.equals(q.getArtist()) 
					&& album.equals(q.getAlbum())
					&& buttonId == q.getButtonId());
		}
		return false;
	}
	
	/**
	 * @return MPCQuery constant indicating the type of query
	 */
	public int getType(){
		return type;
	}
	
	/**
	 * @return the query's artist
	 */
	public String getArtist(){
		return artist;
	}
	
	/**
	 * @return the query's album
	 */
	public String getAlbum(){
		return album;
	}
	
	/**
	 * Sets the id of the button pertaining to the category this query was
	 * made from
	 */
	
	public void setButton(int buttonId){
		this.buttonId = buttonId;
	}
	
	/**
	 * @return the id of the button pertaining to the category from which this query was made
	 */
	public int getButtonId() {
		return buttonId;
	}

}
