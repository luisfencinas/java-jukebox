package model;

import java.io.Serializable;
import java.time.LocalDate;

// This class name is just a suggestion. 
// The account will need to use the type LocalDate
public class JukeboxAccount implements Serializable {

	private static final long serialVersionUID = 1L;

	private LocalDate lastRefresh;

	private String name;
	private String password;

	private int songsAvailable;

	public JukeboxAccount(String userName, String userPassword) {
		lastRefresh = LocalDate.now();
		name = userName;
		password = userPassword;
		songsAvailable = 3;
	}

	public String getName() {
		return name;
	}

	public boolean attemptLogin(String userName, String userPassword) {
		if (name.equals(userName) && password.equals(userPassword)) {
			return true;
		}
		return false;
	}

	public boolean attemptSong() {
		attemptRefresh();
		if (songsAvailable > 0) {
			songsAvailable--;
			return true;
		} else {
			return false;
		}
	}

	public boolean canPlaySong() {
		return !(songsAvailable == 0);
	}

	public void attemptRefresh() {
		if (LocalDate.now().isAfter(lastRefresh)) {
			songsAvailable = 3;
			lastRefresh = LocalDate.now();
		}
	}

	public void pretendItsTomorrow() {
		lastRefresh = lastRefresh.minusDays(1);
	}

	public int getSongsSelected() {
		return 3 - songsAvailable;
	}

	public String getTimeLeft() {
		String val = "";
		val += lastRefresh;
		return val;
	}

}
