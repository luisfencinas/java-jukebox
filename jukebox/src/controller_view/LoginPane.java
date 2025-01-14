package controller_view;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import model.JukeboxAccount;
import model.JukeboxAccountCollection;

public class LoginPane extends GridPane {

	private Label accNameLabel, accPasswordLabel, statusLabel;
	private TextField userNameField;
	private PasswordField passwordField;
	private Button loginButton, logoutButton, createAccountButton;

	public JukeboxAccountCollection allAccounts;

	private JukeboxAccount currAccount;
	private boolean loggedIn;

	public LoginPane(JukeboxAccountCollection all) {
		this.allAccounts = all;
		layoutGUI();
		setActions();
	}

	private void layoutGUI() {
		accNameLabel = new Label("Account Name");
		accPasswordLabel = new Label("Password");
		statusLabel = new Label("Login to play songs");

		userNameField = new TextField();
		passwordField = new PasswordField();

		loginButton = new Button("Login");
		logoutButton = new Button("Logout");
		createAccountButton = new Button("Create new account");
		logoutButton.setDisable(true);

		this.setHgap(10);
		this.setVgap(10);

		this.add(statusLabel, 2, 0);

		this.add(accNameLabel, 1, 1);
		this.add(userNameField, 2, 1);
		this.add(loginButton, 3, 1);
		this.add(accPasswordLabel, 1, 2);
		this.add(passwordField, 2, 2);
		this.add(logoutButton, 3, 2);

		this.add(createAccountButton, 2, 3);
	}

	private void setActions() {
		loginButton.setOnAction((event) -> {
			String usernameAttempt = userNameField.getText();
			String passwordAttempt = passwordField.getText();
			if (allAccounts.attemptLogin(usernameAttempt, passwordAttempt)) {
				currAccount = allAccounts.getCurrentAccount();
				this.updateStatus();
				loggedIn = true;
				loginButton.setDisable(true);
				logoutButton.setDisable(false);
			} else {
				statusLabel.setText("Invalid credentials");
			}
		});
		logoutButton.setOnAction((event) -> {
			statusLabel.setText("Login to play songs");
			allAccounts.logout();
			currAccount = null;
			loggedIn = false;
			loginButton.setDisable(false);
			logoutButton.setDisable(true);
		});
		createAccountButton.setOnAction((event) -> {
			String createUsername = userNameField.getText();
			String createPassword = passwordField.getText();
			if (allAccounts.isAlreadyUser(createUsername)) {
				statusLabel.setText("Account name already taken");
			} else {
				allAccounts.add(new JukeboxAccount(createUsername, createPassword));
				allAccounts.attemptLogin(createUsername, createPassword);
				currAccount = allAccounts.getCurrentAccount();
				this.updateStatus();
				loggedIn = true;
				loginButton.setDisable(true);
				logoutButton.setDisable(false);
			}
		});

	}

	public void updateStatus() {
		if (currAccount.canPlaySong()) {
			statusLabel.setText(currAccount.getSongsSelected() + " songs selected today.");
		} else {
			statusLabel.setText("You have reached your limit for songs played today.");
		}
	}

	public JukeboxAccount getCurrAccount() {
		return currAccount;
	}

	public boolean getLoggedIn() {
		return loggedIn;
	}
}