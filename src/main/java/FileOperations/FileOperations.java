package FileOperations;

import java.awt.Desktop;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.HashMap;

import Security.CryptoUtils;
import UserAccountOperations.LogIn;
import UserInterface.DialogGUI;
import UserInterface.ProgressBarGUI;
import UserInterface.ShareGUI;
import UserInterface.SharedWithYouDialogGUI;
import UserInterface.SharingProgressGUI;

import com.dropbox.core.DbxException;

public class FileOperations {
	private static final java.io.File TWINCLOUD = new java.io.File(
			System.getProperty("user.home"), "Twin Cloud");

	private static final java.io.File TEMP = new java.io.File(
			System.getProperty("user.home"), ".twincloud/temp");

	DropboxFileOperations dropboxFileOperations;
	GoogledriveFileOperations googledriveFileOperations;
	CryptoUtils cryptoUtils;
	private LogIn login = null;

	public FileOperations(LogIn login) {
		this.login = login;
		try {
			dropboxFileOperations = new DropboxFileOperations(login);
			googledriveFileOperations = new GoogledriveFileOperations(login);
		} catch (IOException | DbxException e) {
			e.printStackTrace();
		}
		cryptoUtils = new CryptoUtils();
		// try {
		// Desktop.getDesktop().open(new File(TWINCLOUD.getAbsolutePath()));
		// } catch (IOException e) {
		// e.printStackTrace();
		// }
	}
	
	public boolean checkSharedAndDownload() {
		String mounted = acceptAllSharedFolders();
		if (!mounted.equals("!!@,")) {
			File localFolder = new File(TWINCLOUD.getAbsolutePath());
			File[] listOfLocalFiles = localFolder.listFiles();
			HashMap<String, Integer> mapOfLocalFiles = new HashMap<String, Integer>();
			for (File currLocalFile : listOfLocalFiles) {
				if (currLocalFile.isFile())
					mapOfLocalFiles.put(currLocalFile.getName(), 1);
				else if (currLocalFile.isDirectory())
					mapOfLocalFiles.put(currLocalFile.getName(), 0);
			}
			ArrayList<String> listOfCloudFiles = getFileNameList();
			
			// Cloud'da olup local klasorde olmayan dosyalari indir.
			for (String currCloudFile : listOfCloudFiles) {
				if (!mapOfLocalFiles.containsKey(currCloudFile)) {
					downloadFile(currCloudFile, TWINCLOUD.getAbsolutePath() + "/"
							+ currCloudFile);
				}
			}
			
			final SharedWithYouDialogGUI dialog = new SharedWithYouDialogGUI(mounted.replace("_TwinCloudFile", ""), "checkSmall.png");
			dialog.btnOk.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					dialog.setVisible(false);
					dialog.dispose();
					
					try {
						Desktop.getDesktop().open(
								new File(TWINCLOUD.getAbsolutePath()));
					} catch (IOException ex) {
						ex.printStackTrace();
					}
				}
			});
			dialog.setVisible(true);
		}
		
		return false;
	}

	public boolean initialFileOperations() {

		File localFolder = new File(TWINCLOUD.getAbsolutePath());
		File[] listOfLocalFiles = localFolder.listFiles();
		HashMap<String, Integer> mapOfLocalFiles = new HashMap<String, Integer>();
		for (File currLocalFile : listOfLocalFiles) {
			if (currLocalFile.isFile())
				mapOfLocalFiles.put(currLocalFile.getName(), 1);
			else if (currLocalFile.isDirectory())
				mapOfLocalFiles.put(currLocalFile.getName(), 0);
		}
		ArrayList<String> listOfCloudFiles = getFileNameList();

		// Cloud'da olup local klasorde olmayan dosyalari indir.
		for (String currCloudFile : listOfCloudFiles) {
			if (!mapOfLocalFiles.containsKey(currCloudFile)) {
				downloadFile(currCloudFile, TWINCLOUD.getAbsolutePath() + "/"
						+ currCloudFile);
			}
			/*
			 * else if (!mapOfLocalFiles.containsKey(currCloudFile) &&
			 * mapOfLocalFiles.get(currCloudFile) == 0) { //TODO Klasor yarat. }
			 */
		}

		// Local'de olup Cloud'da olmayan dosyalari cloud'a yukle.
		for (String currLocalFile : mapOfLocalFiles.keySet()) {
			if (!listOfCloudFiles.contains(currLocalFile)) {
				uploadFile(TWINCLOUD.getAbsolutePath() + "/" + currLocalFile, false);
			}
		}

		return false;
	}

	// key uretecek, key'i dropbox'a aticak, dosya sifreliyecek, dosyayi google
	// drive'a aticak.
	public boolean uploadFile(String filePath, boolean isForced) {
		
		System.out.println("Uploading file: " + filePath + " ...");
		try {
			String fileName = new File(filePath).getName();
			
			if (!isForced)
				if(checkIfFileExist(fileName)) {
					System.out.println("File already exist.");
					return false;
				}

			String encryptionKeyPath = cryptoUtils
					.generate256BitKeyForAES(fileName);
			System.out.println("Encryption key generated.");

			String encryptedPath = cryptoUtils.encrypt(filePath,
					encryptionKeyPath);
			System.out.println("File encrypted.");

			String macKeyPath = cryptoUtils.generateKeyForHMac(fileName);
			System.out.println("MAC key generated.");

			String macPath = cryptoUtils
					.computeHmacSHA256(filePath, macKeyPath);
			System.out.println("MAC calculated.");

			dropboxFileOperations.uploadFile(encryptionKeyPath, fileName
					+ "_TwinCloudFile");
			System.out.println("Encryption key uploaded to Dropbox: "
					+ fileName + ".key");

			googledriveFileOperations.uploadFile(encryptedPath, fileName
					+ "_TwinCloudFile");
			System.out.println("Encrypted file uploaded to Google Drive: "
					+ fileName);

			googledriveFileOperations.uploadFile(macKeyPath, fileName
					+ "_TwinCloudFile");
			System.out.println("MAC key uploaded to Google Drive: " + fileName
					+ ".macKey");

			dropboxFileOperations.uploadFile(macPath, fileName
					+ "_TwinCloudFile");
			System.out.println("MAC uploaded to Dropbox: " + fileName + ".mac");

			deleteDirectory(new File(TEMP.getAbsolutePath()));
			System.out.println("Temporary files deleted.");
			System.out.println("------------------------------------------");

		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	public String downloadFile(String fileName, String savePath) {
		System.out.println("Downloading file: " + fileName + " ...");
		try {
			String keyPath = dropboxFileOperations.downloadFile(fileName
					+ ".key", fileName + "_TwinCloudFile");
			if (keyPath == null)
				return null;
			System.out.println("Key file downloaded from Dropbox: " + fileName
					+ ".key");

			String encryptedPath = googledriveFileOperations.downloadFile(
					fileName, fileName + "_TwinCloudFile");
			if (encryptedPath == null)
				return null;
			System.out.println("Encrypted file downloaded from Google Drive: "
					+ fileName);

			String dencryptedPath = cryptoUtils.decrypt(encryptedPath, keyPath,
					savePath);
			System.out.println("File decrypted and saved.");

			String macKeyPath = googledriveFileOperations.downloadFile(fileName
					+ ".macKey", fileName + "_TwinCloudFile");
			if (macKeyPath == null)
				return null;
			System.out.println("Mac key downloaded from Google Drive: "
					+ fileName + ".macKey");

			String macPath = dropboxFileOperations.downloadFile(fileName
					+ ".mac", fileName + "_TwinCloudFile");
			if (macPath == null)
				return null;
			System.out.println("Mac downloaded from Dropbox: " + fileName
					+ ".mac");

			boolean verify = cryptoUtils.verifyHmacSHA256(dencryptedPath,
					macKeyPath, macPath);
			if (verify) {
				System.out.println("Verify successful :) : " + fileName);
			} else {
				System.out.println("Verify fail :(       : " + fileName);
			}

			deleteDirectory(new File(TEMP.getAbsolutePath()));
			System.out.println("Temporary files deleted.");
			System.out.println("------------------------------------------");

			return dencryptedPath;

		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	public boolean deleteFile(String fileName) {
		System.out.println("Deleting file: " + fileName + " ...");
		googledriveFileOperations.deleteFolder(fileName + "_TwinCloudFile");
		System.out.println("Deleted from Google Drive.");
		dropboxFileOperations.deleteFolder(fileName + "_TwinCloudFile");
		System.out.println("Deleted from Dropbox.");
		System.out.println("Deleted file: " + fileName);
		System.out.println("------------------------------------------");
		return false;
	}
	
	public boolean updateFile(String filePath) {
		
		try {
			String fileName = new File(filePath).getName();
			System.out.println("Updating file: " + fileName + " ...");
			
			String keyPath = dropboxFileOperations.downloadFile(fileName
					+ ".key", fileName + "_TwinCloudFile");
			if (keyPath == null)
				return false;
			System.out.println("Key file downloaded from Dropbox: " + fileName
					+ ".key");

			String macKeyPath = googledriveFileOperations.downloadFile(fileName
					+ ".macKey", fileName + "_TwinCloudFile");
			if (macKeyPath == null)
				return false;
			System.out.println("Mac key downloaded from Google Drive: "
					+ fileName + ".macKey");

			// Delete the old mac file.
			dropboxFileOperations.deleteFolder(fileName + "_TwinCloudFile" + "/" + fileName + ".mac");
			System.out.println("Previous mac file deleted from Dropbox: "
					+ fileName);
			
			String encryptedPath = cryptoUtils.encrypt(filePath,
					keyPath);
			System.out.println("New file encrypted.");
			
			String macPath = cryptoUtils
					.computeHmacSHA256(filePath, macKeyPath);
			System.out.println("New MAC calculated.");
			
			googledriveFileOperations.updateFile(encryptedPath, fileName
					+ "_TwinCloudFile");
			System.out.println("Encrypted file updated in Google Drive: "
					+ fileName);
			
			dropboxFileOperations.uploadFile(macPath, fileName
					+ "_TwinCloudFile");
			System.out.println("New MAC uploaded to Dropbox: " + fileName + ".mac");

			deleteDirectory(new File(TEMP.getAbsolutePath()));
			System.out.println("Temporary files deleted.");
			System.out.println("------------------------------------------");

			return true;

		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	public boolean createFolder(String folerPath) {
		System.out.println("Creating folder: " + folerPath);
		dropboxFileOperations.createFolder(folerPath);
		System.out.println("Folder created in Dropbox.");
		googledriveFileOperations.createFolder(folerPath);
		System.out.println("Folder created in GoogleDrive.");
		System.out.println("------------------------------------------");
		return false;
	}

	public ArrayList<String> getSharedUsers(String fileName) {
		return googledriveFileOperations.getSharedUserList(fileName
				+ "_TwinCloudFile");
	}

	public boolean shareFile(String fileName, String userName, String role) {		
		System.out.println("Sharing file: " + fileName + " ...");
		try {
			dropboxFileOperations.shareFolder(fileName + "_TwinCloudFile",
					userName);
			System.out.println("File shared in Dropbox.");
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return false;
		}
		googledriveFileOperations.shareFile(fileName + "_TwinCloudFile",
				userName, role);
		System.out.println("File shared in Google Drive.");
		System.out.println("------------------------------------------");
		return true;
	}

	public boolean unshareFile(String fileName, String userName) {
		if (googledriveFileOperations.unshareFile(fileName + "_TwinCloudFile", userName)) {
			System.out.println("File unshared on google drive.");
		}
		if (dropboxFileOperations.unshareFolder(fileName + "_TwinCloudFile", userName)) {
			System.out.println("File unshared on dropbox.");
		}
		return false;
	}

	public String acceptAllSharedFolders() {
		HashMap<String, Integer> googleFolderNamesHash = getFolderNamesMap();
		return dropboxFileOperations
				.acceptAllDropboxSharedFolders(googleFolderNamesHash);
	}

	public boolean acceptShare(String fileName) {
		return dropboxFileOperations.acceptShare(fileName + "_TwinCloudFile");
	}

	public String getCapacityAndUsed() {
		return googledriveFileOperations.getCapacityAndUsed();
	}
	
	public String getUserName() {
		return googledriveFileOperations.getUserName();
	}
	
	public String getName() {
		return googledriveFileOperations.getName();
	}

	public ArrayList<String> getFileNameList() {
		try {
			ArrayList<com.google.api.services.drive.model.File> googleFiles = googledriveFileOperations
					.listFiles();
			ArrayList<String> fileNames = new ArrayList<>();
			for (com.google.api.services.drive.model.File file : googleFiles) {
//				if (!file.getTitle().contains(".macKey")
//						&& !file.getTitle().contains("_TwinCloudFile")
//						&& !file.getTitle().contains("Getting started"))
//					fileNames.add(file.getTitle());
				if (file.getTitle().contains("_TwinCloudFile"))
					fileNames.add(file.getTitle().replace("_TwinCloudFile", ""));
			}
			return fileNames;
		} catch (IOException e) {
			e.printStackTrace();
		}

		return null;
	}

	public HashMap<String, Integer> getFolderNamesMap() {
		try {
			ArrayList<com.google.api.services.drive.model.File> googleFiles = googledriveFileOperations
					.listFiles();
			HashMap<String, Integer> mapOfCloudFiles = new HashMap<>();
			for (com.google.api.services.drive.model.File file : googleFiles) {
				if (file.getTitle().contains("_TwinCloudFile"))
					mapOfCloudFiles.put(file.getTitle(), 0);
			}
			return mapOfCloudFiles;
		} catch (IOException e) {
			e.printStackTrace();
		}

		return null;
	}
	
	public boolean checkIfFileExist(String fileName) {
		try {
			ArrayList<com.google.api.services.drive.model.File> googleFiles = googledriveFileOperations
					.listFiles();
			ArrayList<String> fileNames = new ArrayList<>();
			for (com.google.api.services.drive.model.File file : googleFiles) {
				if (!file.getTitle().contains(".macKey")
						&& !file.getTitle().contains("_TwinCloudFile")
						&& !file.getTitle().contains("Getting started"))
					if (file.getTitle().equals(fileName))
						return true;
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

		return false;
	}

	@SuppressWarnings("unused")
	private void copyFile(File from, File to) throws IOException {
		Files.copy(from.toPath(), to.toPath());
	}

	public void deleteDirectory(File directory) {
		File[] files = directory.listFiles();
		if (null != files) {
			for (int i = 0; i < files.length; i++) {
				files[i].delete();
			}
		}

	}

	private void deleteTempFile(String path) {
		File filePath = new File(path);
		if (!filePath.delete())
			System.out.println("Delete unsuccessul: " + path);
	}
}