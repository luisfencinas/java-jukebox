package model;

public class Song {
	private String title;
	private String artist;
	private String playTime;
	private String mp3;

	public Song(String titleVal, String artistVal, String playTimeVal, String mp3Val) {
		title = titleVal;
		artist = artistVal;
		playTime = playTimeVal;
		mp3 = mp3Val;
	}

	public String getTitle() {
		return title;
	}

	public String getArtist() {
		return artist;
	}

	public String getPlayTime() {
		return playTime;
	}

	public String getMp3() {
		return mp3;
	}

	public String toString() {
		return "Title: " + title + " Artist: " + artist + " Playtime: " + playTime;
	}
}
