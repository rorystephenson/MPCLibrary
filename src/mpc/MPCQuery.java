package mpc;

import java.io.Serializable;

/**
 * Holds a query in a compact form which can be passed to the database
 * to fetch songs or compared with other queries rather than comparing
 * a list of results of the query to see if the lists are the same.
 *  
 * @author Rory Stephenson
 */

public class MPCQuery implements Serializable{

	private static final long serialVersionUID = 1L;
	private int type = 0;
	private MPCArtist artist = null;
	private MPCAlbum album = null;
	
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
	private MPCQuery(int type){
		this.type = type;
	}
	
	public static MPCQuery allSongsQuery(){
		return new MPCQuery(ALL_SONGS);
	}
	
	public static MPCQuery allArtistsQuery(){
		return new MPCQuery(ALL_ARTISTS);
	}
	
	public static MPCQuery allAlbumsQuery(){
		return new MPCQuery(ALL_ALBUMS);
	}
	
	public static MPCQuery songsByArtist(MPCAlbum album){
		return new MPCQuery(SONGS_BY_ARTIST, album);
	}
	
	public static MPCQuery songsByAlbumArtist(MPCAlbum album){
		return new MPCQuery(SONGS_BY_ALBUM_ARTIST, album);
	}
	
	public static MPCQuery albumsByArtist(MPCArtist artist){
		return new MPCQuery(ALBUMS_BY_ARTIST, artist);
	}
	
	/**
	 * Constructor used when the query does not require album 
	 * information.
	 * @param type MPCQuery constant indicating type of query
	 */
	private MPCQuery(int type, MPCArtist artist){
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
	private MPCQuery(int type, MPCAlbum album){
		this.type = type;
		this.album = album;
	}
	
	@Override
	public boolean equals(Object o){
		if(o instanceof MPCQuery){
			MPCQuery q = (MPCQuery) o;
			return (type == q.getType() && 
					artist.equals(q.getArtist()) 
					&& album.equals(q.getAlbum()));
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
	public MPCArtist getArtist(){
		return artist;
	}
	
	/**
	 * @return the query's album
	 */
	public MPCAlbum getAlbum(){
		return album;
	}
	
}
