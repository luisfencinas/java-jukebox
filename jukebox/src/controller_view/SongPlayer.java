package controller_view;

import java.io.File;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;

public class SongPlayer extends Application {

	public static void main(String[] args) {
		launch(args);
	}

	private MediaPlayer mediaPlayer;
	public List<String> songPlaylist = new ArrayList<>();
	private ObservableList<String> observablePlaylist = FXCollections.observableArrayList();
	private boolean isPlaying = false;

	@Override
	public void start(Stage arg0) throws Exception {
		// TODO Auto-generated method stub
	}

	public ObservableList<String> getObservablePlaylist() {
		return observablePlaylist;
	}

	public void setObservable() {
		observablePlaylist = FXCollections.observableArrayList(songPlaylist);
	}

	// Method to add a song to the playlist
	public void addSongToPlaylist(String fileName) {
		songPlaylist.add(fileName);
		observablePlaylist.add(fileName);
		if (!isPlaying) {
			playNextSong();
		}
	}

	// Method to play the next song in the playlist
	public void playNextSong() {
		if (!songPlaylist.isEmpty()) {
			String nextSong = songPlaylist.get(0);
			playSong(nextSong);
		} else {
			isPlaying = false;
		}
	}

	public void playSong(String fileName) {
		String path = "jukebox/songfiles/" + fileName + ".mp3";
		System.out.println(path);

		// file into uri object
		File file = new File(path);
		URI uri = file.toURI();
		Media media = new Media(uri.toString());
		mediaPlayer = new MediaPlayer(media);
		mediaPlayer.play();
		mediaPlayer.setOnEndOfMedia(new Waiter());
		isPlaying = true;
	}

	private class Waiter implements Runnable {
		@Override
		public void run() {
			observablePlaylist.remove(0);
			songPlaylist.remove(0);
			try {
				// Wait for 2 seconds
				Thread.sleep(2000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			playNextSong();
		}
	}
}
