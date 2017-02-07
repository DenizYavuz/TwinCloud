package UserInterface;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JProgressBar;
import javax.swing.SwingWorker;

import FileOperations.FileOperations;
import FolderWatcher.WatchFolderForChanges;
import UserAccountOperations.LogIn;

// Boyle bir worker kulanilma nedeni; login gui'de login olurken progress bas gostermek.
public class LoginGUIWorker {

	private LogInGUI loginGUI;
	
	public void openLoginGUI() {
		EventQueue.invokeLater(new Runnable() {
			@Override
			public void run() {
				loginGUI = new LogInGUI();
				loginGUI.buttonLogin.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						new GuiWorker(loginGUI.textUserName.getText(),
								loginGUI.textPassword.getText()).execute();
						loginGUI.buttonLogin.setEnabled(false);
					}
				});
				loginGUI.setVisible(true);
			}
		});
	}

	class GuiWorker extends SwingWorker<Integer, Integer> {
		
		private ProgressBarGUI loginProgress = new ProgressBarGUI("Logging in to Twin Cloud...");

		String userName;
		String password;
		boolean isSuccessfull;
		LogIn login;

		public GuiWorker(String userName, String password) {
			
			this.userName = userName;
			this.password = password;
			
			// Onde progress bar gosteriliyor.
			loginProgress.setVisible(true);
		}

		@Override
		protected Integer doInBackground() throws Exception {
			// Arkada login islemi yapiliyor.
			login = new LogIn(this.userName, this.password);
			isSuccessfull = login.executeLogin();
			return 0;
		}

		// login islemi bittiginde.
		@Override
		protected void done() {
			loginGUI.buttonLogin.setEnabled(true);
			
			loginProgress.setVisible(false);
			loginProgress.dispose();
			
			// giris basariliysa
			if (isSuccessfull) {
				
				// Giris basarili yazisini yaz
				EventQueue.invokeLater(new Runnable() {
					@Override
					public void run() {
						final DialogGUI dialog = new DialogGUI("Login is successfull !", "checkSmall.png");
						dialog.btnOk.addActionListener(new ActionListener() {
							public void actionPerformed(ActionEvent e) {
								dialog.setVisible(false);
								dialog.dispose();
								loginProgress.setVisible(false);
								loginProgress.dispose();
								loginGUI.setVisible(false);
								loginGUI.dispose();
							}
						});
						dialog.setVisible(true);
					}
				});
				
				// Uygulamayi baslat.
				final FileOperations fileOperations = new FileOperations(login);
				final StartBarMenu startBar = new StartBarMenu(fileOperations, login);
				WatchFolderForChanges folderChanges = new WatchFolderForChanges(fileOperations, startBar);
				folderChanges.start();
				(new Thread() {
					  public void run() {
						  startBar.changeIcon("loading.gif");
						  startBar.changeInfo("loading.gif", "Syncing...");
						  startBar.isInitialisation = true;
						  fileOperations.initialFileOperations();
						  startBar.isInitialisation = false;
						  startBar.changeIcon("twincloud.png");
						  startBar.changeInfo("check.gif", "Up to date.");
					  }
					 }).start();
			}
			else {
				// Login basarisizsa
				final DialogGUI dialog = new DialogGUI("Login is unseccessful !", "cross.png");
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
