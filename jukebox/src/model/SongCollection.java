package model;

import java.util.ArrayList;

public class SongCollection {
	private ArrayList<Song> songGroup;

	public SongCollection() {
		songGroup = new ArrayList<>();
	}

	public void add_Song(Song newSong) {
		songGroup.add(newSong);
	}
}
