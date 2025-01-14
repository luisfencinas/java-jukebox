package model;

import java.io.Serializable;
import java.util.ArrayList;

public class JukeboxAccountCollection implements Serializable {

	private static final long serialVersionUID = 1L;

	private ArrayList<JukeboxAccount> accountArray;
	private JukeboxAccount signedIntoAccount;
	private boolean loggedIn;
	private int n;

	public JukeboxAccountCollection() {
		accountArray = new ArrayList<JukeboxAccount>(16);
		loggedIn = false;
		n = 0;

		this.setTestAccounts();
	}

	public void add(JukeboxAccount newAccount) {
		accountArray.add(newAccount);
		n++;
	}

	public void setTestAccounts() {
		this.add(new JukeboxAccount("Chris", "1"));
		this.add(new JukeboxAccount("Devon", "22"));
		this.add(new JukeboxAccount("River", "333"));
		this.add(new JukeboxAccount("Ryan", "4444"));
	}

	public boolean attemptLogin(String username, String password) {
		if (!loggedIn) {
			for (int i = 0; i < n; i++) {
				JukeboxAccount currAccount = accountArray.get(i);
				if (currAccount.attemptLogin(username, password)) {
					signedIntoAccount = currAccount;
					loggedIn = true;
					return true;
				}
			}
		}
		return false;
	}

	public boolean isAlreadyUser(String usernameAttempt) {
		for (int i = 0; i < n; i++) {
			JukeboxAccount currAccount = accountArray.get(i);
			if (currAccount.getName().equals(usernameAttempt)) {
				return true;
			}
		}
		return false;
	}

	public void logout() {
		signedIntoAccount = null;
		loggedIn = false;
	}

	public JukeboxAccount getCurrentAccount() {
		return signedIntoAccount;
	}

	public boolean checkLogged() {
		return loggedIn;
	}
}
