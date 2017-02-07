import java.io.File;
import java.util.Timer;
import java.util.TimerTask;

import FileOperations.FileOperations;
import FolderWatcher.WatchFolderForChanges;
import UserAccountOperations.LogIn;
import UserInterface.LogInGUI;
import UserInterface.LoginGUIWorker;
import UserInterface.StartBarMenu;


public class ProgramStart {
	
	/** Directory to store temp files for this application. */
	private static final java.io.File TEMP = new java.io.File(
			System.getProperty("user.home"), ".twincloud");
	
	private static final java.io.File TWINCLOUD = new java.io.File(
			System.getProperty("user.home"), "Twin Cloud");
	
	LogInGUI loginGUI;
	
	FileOperations fileOperations;
	public ProgramStart() {
		
		// Yoksa yeni Twin Cloud fodler'i olusturuyor bilgisyar'da User icine. Or: C:\Users\Deniz\Twin Cloud
		new File(TWINCLOUD.getAbsolutePath()).mkdir();
		
		String dropboxLogin = TEMP.getAbsolutePath() + "/account/" + "dropboxAccessToken.sc";
		String googleLogin = TEMP.getAbsolutePath() + "/account/" + "googleAccessToken.sc";
		File dropboxFile = new File(dropboxLogin);
		File googleFile = new File(googleLogin);
		
		// Auto Login icin gerekli dropboxFile ve googleFile var mi diye bakiyor.
		if (dropboxFile.exists() && googleFile.exists()) {
			LogIn login = new LogIn();
			login.executeLogin();
			fileOperations = new FileOperations(login);
			StartBarMenu startBar = new StartBarMenu(fileOperations, login);
			WatchFolderForChanges folderChanges = new WatchFolderForChanges(fileOperations, startBar);
			folderChanges.start();
			
			startBar.changeIcon("loading.gif");
			startBar.changeInfo("loading.gif", "Syncing...");
			
			// Yukardaki change icon ve change info sysncing boyunca sabit tutuluyor. Bu olmazsa ikonlar sacmaliyor.
			startBar.isInitialisation = true;
			fileOperations.initialFileOperations();
			startBar.isInitialisation = false;
			startBar.changeIcon("twincloud.png");
			startBar.changeInfo("check.gif", "Up to date.");
		}
		else {
			LoginGUIWorker loginWorker = new LoginGUIWorker();
			loginWorker.openLoginGUI();
		}
		
		Timer timer = new Timer();
		timer.scheduleAtFixedRate(new TimerTask() {
			  @Override
			  public void run() {
				  System.out.println("girdi...");
				  if (fileOperations != null)
					fileOperations.checkSharedAndDownload();
			  }
			}, 10*1000, 10*1000);
	}
	
}
