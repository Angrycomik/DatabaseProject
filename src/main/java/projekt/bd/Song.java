package projekt.bd;

import javafx.scene.image.Image;

public class Song {
    private String songName;
    private String artistName;
    private String albumName;
    private Integer songID;
    private Integer artistID;
    private Integer songartistID;
    private Integer albumID;
    private Integer year;  
    private Image img;
    private Integer rating = null;

    public Song(String songName, String artistName, String albumName, Integer year) {
        this.songName = songName;
        this.artistName = artistName;
        this.albumName = albumName;
        this.year = year;
    }
    public Song(String songName, String artistName, String albumName, Integer year,Integer songartistid) {
        this.songName = songName;
        this.artistName = artistName;
        this.albumName = albumName;
        this.year = year;
        this.songartistID = songartistid;
    }
    public void SetImage(Image image){
        img = image;
    }
    public void setRating(Integer rating){
        this.rating = rating;
    }

    public Image getImage(){
       return img;
    }

    public String getName(){
        return songName;
    }
    public String getArtist(){
        return artistName;
    }
    public String getAlbum(){
        return albumName;
    }
    public Integer getYear(){
        return year;
    }
    public Integer getSongArtistID(){
        return songartistID;
    }
    public Integer getRating(){
        return rating;
    }
}