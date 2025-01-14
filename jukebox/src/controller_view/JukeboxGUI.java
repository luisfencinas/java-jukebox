package controller_view;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * This app should contain all elements by the end of Sprint 2
 * The starter code only shows the minimum 
 */

import javafx.application.Application;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import model.JukeboxAccountCollection;
import model.Song;

public class JukeboxGUI extends Application {

	public static void main(String[] args) {
		launch(args);
	}

	LoginPane loginPane;
	BorderPane everything;
	GridPane songMenu;

//	private HBox hbox;
//	private Button songButton1, songButton2;

	private Label songListLabel, songQueueLabel;
	private TableView<Song> table;
	private ObservableList<Song> defaultSongs;
	private ListView queueList;
	private ObservableList<String> songList;
	private Button play;
	private SongPlayer ipod;
	private JukeboxAccountCollection accounts;
	private List<String> songPlaylist = new ArrayList<>();

	@Override
	public void start(Stage primaryStage) throws Exception {
		ipod = new SongPlayer();
		play = new Button("Play");
		LayoutGUI();
		primaryStage.setOnCloseRequest((event) -> {
			// Handle the closing alert
			Alert alert = new Alert(AlertType.CONFIRMATION);
			alert.setHeaderText("Click cancel to not save");
			alert.setContentText("Click OK to save to a .ser file");
			Optional<ButtonType> result = alert.showAndWait();
			accounts.logout();
			if (result.get() == ButtonType.OK) {
				write();
			}
			// Save the ArrayList if requested

			Platform.exit();
			System.exit(0);
		});
		Scene scene = new Scene(everything, 700, 500);
		primaryStage.setScene(scene);
		primaryStage.show();
	}

	private void LayoutGUI() {
		getSaved();
		loginPane = new LoginPane(accounts);
		everything = new BorderPane();
		songMenu = new GridPane();
		setButtonActions();
		table = new TableView<Song>();
		songList = ipod.getObservablePlaylist();
		queueList = new ListView(songList);

		TableColumn<Song, String> title = new TableColumn<>("Title");
		title.setPrefWidth(150);
		TableColumn<Song, String> artist = new TableColumn<>("Artist");
		artist.setPrefWidth(100);
		TableColumn<Song, String> playTime = new TableColumn<>("Playtime");
		playTime.setPrefWidth(70);

		title.setCellValueFactory(new PropertyValueFactory<Song, String>("title"));
		artist.setCellValueFactory(new PropertyValueFactory<Song, String>("artist"));
		playTime.setCellValueFactory(new PropertyValueFactory<Song, String>("playTime"));

		setSongList();
		table.setItems(defaultSongs);
		table.getColumns().addAll(title, artist, playTime);

		songListLabel = new Label("Song List");
		songQueueLabel = new Label("Song Queue");

		songMenu.add(songListLabel, 0, 0);
		songMenu.add(songQueueLabel, 2, 0);
		songMenu.add(table, 0, 2);
		songMenu.add(queueList, 2, 2);
		songMenu.add(play, 1, 2);
		everything.setCenter(songMenu);
		everything.setBottom(loginPane);
	}

	void setButtonActions() {
		play.setOnAction((event) -> {
			if (loginPane.getLoggedIn()) {
				Song songPlay = table.getSelectionModel().getSelectedItem();
				if (songPlay != null) {
					if (loginPane.getCurrAccount().attemptSong()) {
						ipod.addSongToPlaylist(songPlay.getMp3());
						loginPane.updateStatus();
					}
				}
			}
		});
	}

	private void setSongList() {
		defaultSongs = FXCollections.observableArrayList();
		Song newSong1 = new Song("Caught a Pokémon!", "Game Freak", "0:05", "Capture");
		defaultSongs.add(newSong1);
		Song newSong2 = new Song("LopingSting", "Kevon Macleod", "0:04", "LopingSting");
		defaultSongs.add(newSong2);
		Song newSong3 = new Song("Danse Macabre-Violin Hook", "Kevin Macleod", "0:34", "DanseMacabreViolinHook");
		defaultSongs.add(newSong3);
		Song newSong4 = new Song("Determined Tumbao 20", "Freeplay Music", "0:20", "DeterminedTumbao");
		defaultSongs.add(newSong4);
		Song newSong5 = new Song("Longing In Their Hearts", "Bonnie Raitt", "4:48", "LongingInTheirHearts");
		defaultSongs.add(newSong5);
		Song newSong6 = new Song("Swing Cheese 15", "FreePlay Music", "0:15", "SwingCheese");
		defaultSongs.add(newSong6);
		Song newSong7 = new Song("The Curtain Rises", "Kevin Macleod", "0:28", "TheCurtainRises");
		defaultSongs.add(newSong7);
		Song newSong8 = new Song("Untameable Fire", "Unknown", "4:41", "UntameableFire");
		defaultSongs.add(newSong8);	
	}

	private void write() {
		String fileName = "objects.ser";
		try {
			FileOutputStream bytesToDisk = new FileOutputStream(fileName);
			ObjectOutputStream outFile = new ObjectOutputStream(bytesToDisk);
			outFile.writeObject(accounts);
			outFile.close();
		} catch (IOException ioe) {
			System.out.println("Writing objects failed");
		}
		fileName = "queue.ser";
		try {
			FileOutputStream bytesToDisk = new FileOutputStream(fileName);
			ObjectOutputStream outFile = new ObjectOutputStream(bytesToDisk);
			outFile.writeObject(ipod.songPlaylist);
			outFile.close();
		} catch (IOException ioe) {
			System.out.println("Writing objects failed");
		}
	}

	private void getSaved() {
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setHeaderText("Start up Option");
		alert.setContentText("Read saved data? Press \"OK\" if yes");
		Optional<ButtonType> result = alert.showAndWait();
		if (result.get() == ButtonType.OK) {
			try {
				FileInputStream rawBytes = new FileInputStream("objects.ser");
				ObjectInputStream inFile = new ObjectInputStream(rawBytes);
				JukeboxAccountCollection serializedCollection = (JukeboxAccountCollection) inFile.readObject();
				inFile.close();
				accounts = serializedCollection;

				rawBytes = new FileInputStream("queue.ser");
				inFile = new ObjectInputStream(rawBytes);
				List<String> serializedQueue = (List<String>) inFile.readObject();
				inFile.close();
				ipod.songPlaylist = serializedQueue;
				ipod.setObservable();
				ipod.playNextSong();
			} catch (IOException | ClassNotFoundException e) {
				e.printStackTrace();
				System.out.println("Error reading from serialized data file: " + e.getMessage());
			}

		} else {
			accounts = new JukeboxAccountCollection();
		}
	}

}