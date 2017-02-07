package UserInterface;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.SwingWorker;

import FileOperations.FileOperations;
import FolderWatcher.WatchFolderForChanges;
import Security.CryptoUtils;
import UserAccountOperations.LogIn;
import UserAccountOperations.SignUp;
import UserInterface.LoginGUIWorker.GuiWorker;

public class SingUpGUIWorker {

	private SignUpGUIBasic signupGUIBasic;

	public void openSingUpGUI() {
		EventQueue.invokeLater(new Runnable() {
			@Override
			public void run() {
				signupGUIBasic = new SignUpGUIBasic();
				signupGUIBasic.buttonSignUp
						.addActionListener(new ActionListener() {
							public void actionPerformed(ActionEvent e) {

								new GuiWorker(signupGUIBasic.textPassword
										.getText(), signupGUIBasic.textUserName
										.getText()).execute();
							}
						});
				signupGUIBasic.setVisible(true);
			}
		});
	}

	class GuiWorker extends SwingWorker<Integer, Integer> {

		private ProgressBarGUI loginProgress = new ProgressBarGUI(
				"Verifying e-mail...");

		String userName;
		String password;
		boolean isUserNameSuccessfull = false;
		boolean isPasswordSuccessfull = false;
		SignUp signUp;

		public GuiWorker(String password, String userName) {

			this.userName = userName;
			this.password = password;

			loginProgress.setVisible(true);
		}

		@Override
		protected Integer doInBackground() throws Exception {
			String userPassword = signupGUIBasic.textPassword.getText();
			String n = ".*[0-9].*";
		    String a = ".*[A-Z].*";
			if (userPassword.length() >= 8 && userPassword.matches(n) && userPassword.toUpperCase().matches(a)) {
				signUp = new SignUp();
				isUserNameSuccessfull = signUp.checkEmail(userName);
				isPasswordSuccessfull = true;
				return 0;
			}
			else {
				isPasswordSuccessfull = false;
			}
			
			return 0;
		}

		// login islemi bittiginde.
		@Override
		protected void done() {
			loginProgress.setVisible(false);
			loginProgress.dispose();

			// TODO Username'i kontrol et Google'in sitesinden.
			if (isUserNameSuccessfull && isPasswordSuccessfull) {
				CryptoUtils cryptoUtils = new CryptoUtils();
				String googlePassword = cryptoUtils.derivePassword(
						signupGUIBasic.textPassword.getText(),
						signupGUIBasic.textUserName.getText() + "@gmail.com",
						"www.google.com");
				String dropboxPassword = cryptoUtils.derivePassword(
						signupGUIBasic.textPassword.getText(),
						signupGUIBasic.textUserName.getText() + "@gmail.com",
						"www.dropbox.com");

				final SignUpResultGUI resultGUI = new SignUpResultGUI(
						signupGUIBasic.textUserName.getText() + "@gmail.com",
						googlePassword, signupGUIBasic.textUserName.getText()
								+ "@gmail.com", dropboxPassword);
				resultGUI.setVisible(true);

				resultGUI.buttonOk.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						resultGUI.setVisible(false);
						resultGUI.dispose();
						signupGUIBasic.setVisible(false);
						signupGUIBasic.dispose();
					}
				});
			}
			else if (!isPasswordSuccessfull) {
				final DialogGUI dialog = new DialogGUI("Your password must suit the rules !", "cross.png");
				dialog.btnOk.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						dialog.setVisible(false);
						dialog.dispose();
						loginProgress.setVisible(false);
						loginProgress.dispose();
					}
				});
				dialog.setVisible(true);
			}
			else if (!isUserNameSuccessfull) {
				// Verify olmazsa
				final DialogGUI dialog = new DialogGUI("Please enter a diffrent username !", "cross.png");
				dialog.btnOk.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						dialog.setVisible(false);
						dialog.dispose();
						loginProgress.setVisible(false);
						loginProgress.dispose();
					}
				});
				dialog.setVisible(true);
			}
		}

	}

}
