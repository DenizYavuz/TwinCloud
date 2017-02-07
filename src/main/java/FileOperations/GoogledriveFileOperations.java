package FileOperations;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.math.MathContext;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import UserAccountOperations.LogIn;

import com.dropbox.core.DbxException;
import com.google.api.client.http.FileContent;
import com.google.api.client.http.GenericUrl;
import com.google.api.client.http.HttpResponse;
import com.google.api.services.drive.Drive;
import com.google.api.services.drive.Drive.Children;
import com.google.api.services.drive.Drive.Files;
import com.google.api.services.drive.model.About;
import com.google.api.services.drive.model.Channel;
import com.google.api.services.drive.model.ChildList;
import com.google.api.services.drive.model.ChildReference;
import com.google.api.services.drive.model.File;
import com.google.api.services.drive.model.FileList;
import com.google.api.services.drive.model.ParentReference;
import com.google.api.services.drive.model.Permission;
import com.google.api.services.drive.model.PermissionList;

public class GoogledriveFileOperations {

	/** Directory to store temp files for this application. */
	private static final java.io.File TEMP = new java.io.File(
			System.getProperty("user.home"), ".twincloud/temp");

	static Drive service;

	public GoogledriveFileOperations(LogIn login) {
		service = login.getGoogleAccount();
	}

	/**
	 * 
	 * @param filePath
	 * @param folderName
	 * @return
	 */
	public boolean uploadFile(String filePath, String folderName) {
		java.io.File fileToUpload = new java.io.File(filePath);

		try {
			String folderId = getFileId(folderName);
			// If folder does not exist, create folder.
			if (folderId == null) {
				File folderBody = new File();
				folderBody.setTitle(folderName);
				folderBody.setMimeType("application/vnd.google-apps.folder");
				File folder = service.files().insert(folderBody).execute();
				folderId = folder.getId();
				// Folder created.
			}

			// File's metadata.
			File fileBody = new File();
			fileBody.setTitle(fileToUpload.getName());
			fileBody.setDescription("description");
			fileBody.setMimeType("application/octet-stream");

			// Set the parent folder.
			fileBody.setParents(Arrays.asList(new ParentReference()
					.setId(folderId)));

			// File's content.
			FileContent mediaContent = new FileContent(
					"application/octet-stream", fileToUpload);
			File file = service.files().insert(fileBody, mediaContent)
					.execute();

		} catch (IOException e) {
			System.out.println("An error occured: " + e);
			return false;
		}
		return true;
	}

	public String downloadFile(String fileName, String folderName) {
		java.io.File file = new java.io.File(TEMP.getPath() + "/" + fileName);
		try {
			OutputStream outputStream = new FileOutputStream(file);

			ChildList result = service.children().list(getFileId(folderName))
					.execute();

			String fileIdInFolder = "";
			for (ChildReference child : result.getItems()) {
				// TODO cok kotu bi cozum.
				String title = service.files().get(child.getId()).execute()
						.getTitle();
				if (title.equals(fileName))
					fileIdInFolder = child.getId();
			}

			service.files().get(fileIdInFolder)
					.executeMediaAndDownloadTo(outputStream);
			return file.getPath();
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

		return null;
	}
	
	public boolean updateFile(String filePath, String folderName) {
		
		java.io.File fileToUpload = new java.io.File(filePath);
		String fileName = fileToUpload.getName();
		try {
			String fileId = getFileId(fileName);
			File file = service.files().get(fileId).execute();
			file.setTitle(fileName);
			file.setDescription("description");
			file.setMimeType("application/octet-stream");
			  
			// File's new content.
			java.io.File fileContent = new java.io.File(filePath);
			FileContent mediaContent = new FileContent("application/octet-stream", fileContent);
			
			// Send the request to the API.
			File updatedFile = service.files().update(fileId, file, mediaContent).execute();

		} catch (IOException e) {
			System.out.println("An error occured: " + e);
			return false;
		}
		return true;
	}

	public boolean deleteFolder(String folderName) {
		System.out.println(folderName);
		String fileId = getFileId(folderName);
		System.out.println(fileId);
		try {
			File file = service.files().get(fileId).execute();
			System.out.println(file.getShared());

			if (file.getUserPermission().getRole().equals("owner"))
				service.files().delete(fileId).execute();
			else {
				service.files().trash(fileId).execute();
			}

		} catch (IOException e) {
			System.out.println("An error occurred: " + e);
		}
		return false;
	}

	public boolean createFolder(String path) {
		File body = new File();
		body.setTitle(path);
		body.setMimeType("application/vnd.google-apps.folder");
		try {
			service.files().insert(body).execute();
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	public ArrayList<String> getSharedUserList(String folderName) {

		String fileId = getFileId(folderName);
		ArrayList<String> userNames = new ArrayList<String>();
		try {
			List<Permission> permissions = service.permissions().list(fileId)
					.execute().getItems();
			for (Permission perm : permissions) {
				userNames.add(perm.getEmailAddress().replace("@gmail.com", "")
						+ ":" + perm.getName() + ":" + perm.getRole());
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return userNames;
	}

	public boolean shareFile(String folderName, String userName, String role) {
		String fileId = getFileId(folderName);
		if (fileId == null) {
			System.out.println("File not found!");
		}
		Permission permission = insertPermission(service, fileId, userName
				+ "@gmail.com", "user", role);
		if (permission == null)
			return false;
		else
			return true;
	}

	public boolean unshareFile(String folderName, String userName) {
		String fileId = getFileId(folderName);
		PermissionList permissions = null;
		try {
			permissions = service.permissions().list(fileId).execute();
		} catch (IOException e) {
			e.printStackTrace();
		}
		if (permissions == null)
			return false;

		try {
			List<Permission> permissionList = permissions.getItems();
			for (Permission permission : permissionList) {
				if (permission.getEmailAddress()
						.equals(userName + "@gmail.com")) {
					service.permissions().delete(fileId, permission.getId())
							.execute();
					return true;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}

		return false;
	}

	public ArrayList<File> listFiles() throws IOException {
		ArrayList<File> result = new ArrayList<File>();
		Files.List request = service.files().list();

		do {
			FileList files = request.execute();

			result.addAll(files.getItems());
			request.setPageToken(files.getNextPageToken());
		} while (request.getPageToken() != null
				&& request.getPageToken().length() > 0);

		return result;
	}

	// TODO Find more efficient way.
	private static String getFileId2(String fileName) {
		try {
			ArrayList<File> result = new ArrayList<File>();
			Files.List request = service.files().list();

			do {
				FileList files = request.execute();

				result.addAll(files.getItems());
				request.setPageToken(files.getNextPageToken());
			} while (request.getPageToken() != null
					&& request.getPageToken().length() > 0);

			for (File file : result) {
				if (file.getTitle().equals(fileName)) {
					return file.getId();
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

		return null;
	}

	public static String getFileId(String fileName) {
		try {
			ArrayList<File> result = new ArrayList<File>();
			Files.List request = service.files().list();
			request.set("q", "title=\'" + fileName + "\'");
			do {
				FileList files = request.execute();

				result.addAll(files.getItems());
				request.setPageToken(files.getNextPageToken());
			} while (request.getPageToken() != null
					&& request.getPageToken().length() > 0);

			try {
				return result.get(0).getId();
			}
			catch(IndexOutOfBoundsException e) {
				System.out.println(e.getMessage());
			}
			//if (result.size() == 1) {
				//return result.get(0).getId();
//			} else
//				return null;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	public String getCapacityAndUsed() {
		About about = null;
		try {
			about = service.about().get().execute();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		double d = about.getQuotaBytesUsed() / 1000000.0;
		BigDecimal bd = new BigDecimal(d);
		bd = bd.round(new MathContext(3));
		double rounded = bd.doubleValue();
		return Math.round((about.getQuotaBytesTotal() / 1000000000.0)) + ":" + rounded;
	}
	
	public String getUserName() {
		About about = null;
		try {
			about = service.about().get().execute();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return about.getUser().getEmailAddress().replace("@gmail.com", "");
	}
	
	public String getName() {
		About about = null;
		try {
			about = service.about().get().execute();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return about.getUser().getDisplayName();
	}

	/**
	 * Insert a new permission.
	 *
	 * @param service
	 *            Drive API service instance.
	 * @param fileId
	 *            ID of the file to insert permission for.
	 * @param value
	 *            User or group e-mail address, domain name or {@code null}
	 *            "default" type.
	 * @param type
	 *            The value "user", "group", "domain" or "default".
	 * @param role
	 *            The value "owner", "writer" or "reader".
	 * @return The inserted permission if successful, {@code null} otherwise.
	 */
	private Permission insertPermission(Drive service, String fileId,
			String value, String type, String role) {
		Permission newPermission = new Permission();

		newPermission.setValue(value);
		newPermission.setType(type);
		newPermission.setRole(role);
		try {
			return service.permissions().insert(fileId, newPermission)
					.execute();
		} catch (IOException e) {
			System.out.println("An error occurred: " + e);
		}
		return null;
	}
}
