package FileOperations;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import org.apache.commons.io.FilenameUtils;

import com.dropbox.core.DbxException;
import com.dropbox.core.v2.DbxClientV2;
import com.dropbox.core.v2.DbxFiles.CreateFolderException;
import com.dropbox.core.v2.DbxFiles.DeleteException;
import com.dropbox.core.v2.DbxFiles.FolderMetadata;
import com.dropbox.core.v2.DbxFiles.ListFolderResult;
import com.dropbox.core.v2.DbxFiles.Metadata;
import com.dropbox.core.v2.DbxSharing;
import com.dropbox.core.v2.DbxSharing.AddMember;
import com.dropbox.core.v2.DbxSharing.ShareFolderException;
import com.dropbox.core.v2.DbxSharing.ShareFolderLaunch;
import com.dropbox.core.v2.DbxSharing.SharedFolderMetadata;
import com.google.api.services.drive.Drive;

import UserAccountOperations.LogIn;

public class DropboxFileOperations {
	/** Directory to store temp files for this application. */
	private static final java.io.File TEMP = new java.io.File(
			System.getProperty("user.home"), ".twincloud/temp");

	DbxClientV2 account;

	public DropboxFileOperations(LogIn login) throws IOException, DbxException {
		account = login.getDropboxAccount();

		String path = TEMP.getAbsolutePath() + "/";
		File keyFile = new File(path);
		keyFile.mkdirs();
	}

	/**
	 * 
	 * @param filePath Yuklenecek dosyanin local bilgisayardaki yeri.
	 * @param folderName Yuklenecek dosyanin Dropbox'ta hangi klasor altina yuklenecegi: "/folder1"
	 * @return
	 */
	public boolean uploadFile(String filePath, String folderName) {
		boolean isUploaded = false;
		File inputFile = new File(filePath);

		try {
			FileInputStream inputStream = new FileInputStream(inputFile);
			try {
				account.files.createFolder("/" + folderName);
			}
			catch (CreateFolderException e) {
				// Eger klasor zaten varsa yaratma.
			}
			account.files.uploadBuilder("/" + folderName + "/" + inputFile.getName())
					.run(inputStream);
			isUploaded = true;
			inputStream.close();
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return isUploaded;
	}

	/**
	 * 
	 * @param fileName Indirilecek dosyanin ismi.
	 * @param folderName Dosyanin Dropbox'ta nerde saklandigi.
	 * @return
	 */
	public String downloadFile(String fileName, String folderName) {
		File outputFile = new File(TEMP.getAbsolutePath() + "/" + fileName);

		try {
			FileOutputStream outputStream = new FileOutputStream(outputFile);
			account.files.downloadBuilder("/" + folderName + "/" + fileName).run(outputStream);
			outputStream.close();

			return outputFile.getPath();
		} catch (Exception e) {
			System.out.println(e.getMessage());
			//downloadFile(fileName, folderName);
		}
		return null;
	}

	/**
	 * 
	 * @param folderName Silinecek dosya ismi.
	 * @return
	 */
	public boolean deleteFolder(String folderName) {
		try {
			account.files.delete("/" + folderName);
		} catch (DbxException e) {
			System.out.println(e.getMessage());
			return false;
		}
		return true;
	}

	public boolean createFolder(String folderPath) {
		try {
			account.files.createFolder("/" + folderPath);
		} catch (DbxException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	/**
	 * 
	 * @param folderName Paylasilcak klasorun adi.
	 * @param userName
	 * @return
	 * @throws DbxException
	 */
	public boolean shareFolder(String folderName, String userName)
			throws DbxException {
		ArrayList<DbxSharing.AddMember> list = new ArrayList<DbxSharing.AddMember>();
		DbxSharing.AddMember a1 = new DbxSharing.AddMember(
				DbxSharing.MemberSelector.email(userName + "@gmail.com"),
				DbxSharing.AccessLevel.editor);

		list.add(a1);
		SharedFolderMetadata md2 = null;
		try {
			ShareFolderLaunch l = account.sharing.shareFolder("/" + folderName);
			md2 = l.getComplete();
		} catch (ShareFolderException e) {
			System.out.println(folderName);
			md2 = account.sharing.getFolderMetadata(getDropboxSharedFolderId(folderName));
		}

		account.sharing.addFolderMember(md2.sharedFolderId, list);

		return true;
	}
	
	public boolean unshareFolder(String folderName, String userName) {
		try {
			DbxSharing.MemberSelector.email(userName + "@gmail.com");
			SharedFolderMetadata md = account.sharing.getFolderMetadata(getDropboxSharedFolderId(folderName));
			account.sharing.removeFolderMember(md.sharedFolderId, DbxSharing.MemberSelector.email(userName + "@gmail.com"), false);
		}
		catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
	// TODO Mount ederken dosya ismi degisebiliyor. Onu kontrol et.
	public String acceptAllDropboxSharedFolders(HashMap<String, Integer> googleFolderNamesHash) {
		
		String mountedFile = "!!@,";
		
		ArrayList<SharedFolderMetadata> sharedFolderList = null;
		try {
			sharedFolderList = account.sharing.listFolders().entries;
		} catch (DbxException e1) {
			System.out.println(e1.getMessage());
		}
		for (SharedFolderMetadata currFolder : sharedFolderList) {
			try {
				if (googleFolderNamesHash.containsKey(currFolder.name)) {
					System.out.println("Mounting: " + currFolder.name + " " + currFolder.accessType + " " + currFolder.isTeamFolder);
					SharedFolderMetadata mountedFolder = account.sharing.mountFolder(getDropboxSharedFolderId(currFolder.name));
					System.out.println("mountedFolderName: " + mountedFolder.name);
					mountedFile = mountedFolder.name;
				}
				else {
					//deleteFolder(currFolder.name);
					//System.out.println("Folder deleted: " + currFolder);
				}
			} catch (DbxException e) {
				System.out.println(e.getMessage());
				//mountedFile = "!!@,";
			}
		}
		
		// Mount ederken dosya ismi degisme sorununu cozmek icin.
		// twintestfile10 (3).txt_TwinCloudFile -> twintestfile10.txt_TwinCloudFile
			ListFolderResult sharedFolderList1 = null;
			try {
				sharedFolderList1 = account.files.listFolder("");
			} catch (DbxException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			for (Metadata currFolder : sharedFolderList1.entries) {
				try {
					//System.out.println(currFolder.name + " -> " + currFolder.name.replaceAll(" \\(.*\\)\\.", "."));
					account.files.move("/" + currFolder.name, "/" + currFolder.name.replaceAll(" \\(.*\\)\\.", "."));
				} catch (DbxException e) {
					//System.out.println(e.getMessage());
				}
			}

		return mountedFile;
	}

	// TODO Mount ederken dosya ismi degisebiliyor. Onu kontrol et.
	public boolean acceptShare(String folderName) {
		try {
			account.sharing.mountFolder(getDropboxSharedFolderId(folderName));
		} catch (DbxException e) {
			e.printStackTrace();
		}
		return true;
	}

	private String getDropboxSharedFolderId(String folderName) {
		try {
			ArrayList<SharedFolderMetadata> sharedFolderList = account.sharing
					.listFolders().entries;
			for (SharedFolderMetadata currFolder : sharedFolderList) {
				if (currFolder.name.equals(folderName)) {
					return currFolder.sharedFolderId;
				}
			}
		} catch (DbxException e) {
			e.printStackTrace();
		}
		return null;
	}
}