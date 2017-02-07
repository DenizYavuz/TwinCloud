package UserInterface;

import java.awt.Desktop;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JProgressBar;
import javax.swing.SwingWorker;

import FileOperations.FileOperations;
import FolderWatcher.WatchFolderForChanges;
import UserAccountOperations.LogIn;
import UserInterface.LoginGUIWorker.GuiWorker;

public class ShareDialogGUIWorker {
	
	private static final java.io.File TWINCLOUD = new java.io.File(
			System.getProperty("user.home"), "Twin Cloud");

	private ShareDialogGUI shareDialog;
	FileOperations fileOperations;
	
	public void openShareGUI(final String fileName, final String fileOwner, FileOperations fileoperations) {
		fileOperations = fileoperations;
		EventQueue.invokeLater(new Runnable() {
			@Override
			public void run() {
				shareDialog = new ShareDialogGUI();
				shareDialog.setVisible(true);
				shareDialog.buttonShare.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						new GuiWorker(shareDialog.textUserName.getText(), fileName, fileOwner, fileOperations).execute();
					}
				});
			}
		});
	}

	class GuiWorker extends SwingWorker<Integer, Integer> {
		
		private SharingProgressGUI shareProgress = new SharingProgressGUI();

		String userName;
		String fileName;
		String fileOwner;
		int result = -1; //0:success, 1:not owner error, 2:check username error
		
		public GuiWorker(String userName, String fileName, String fileOwner, FileOperations fileOperations) {
			this.userName = userName;
			this.fileName = fileName;
			this.fileOwner = fileOwner;
			
			// Onde progress bar gosteriliyor.
			shareProgress.setVisible(true);
		}

		@Override
		protected Integer doInBackground() throws Exception {
			// TODO if (shareDialog.checkReadWrite.isSelected()) buna gore writer reader yap.			
			if (fileOperations.getUserName().equals(fileOwner)) {
				if (fileOperations.shareFile(fileName, userName, "writer"))
					result = 0;
					//shareGUI.listSharedUserOfFile(fileName);
				else {
					result = 2;
				}
			}
			else {
				result = 1;
			}
			return 0;
		}

		// login islemi bittiginde.
		@Override
		protected void done() {
			shareProgress.setVisible(false);
			if (result == 0) {
				shareDialog.setVisible(false);
				shareDialog.dispose();
				
				final DialogGUI dialog = new DialogGUI("Share is successfull !", "checkSmall.png");
				dialog.btnOk.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						dialog.setVisible(false);
						dialog.dispose();
					}
				});
				dialog.setVisible(true);
			}
			else if (result == 1) {
				final DialogGUI dialog = new DialogGUI("You are not owner of the file !", "cross.png");
				dialog.setVisible(true);
				dialog.btnOk.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						dialog.setVisible(false);
						dialog.dispose();
					}
				});
			}
			else if (result == 2) {
				final DialogGUI dialog = new DialogGUI("Please check the username !", "cross.png");
				dialog.setVisible(true);
				dialog.btnOk.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						dialog.setVisible(false);
						dialog.dispose();
					}
				});
			}
		}

	}

}
