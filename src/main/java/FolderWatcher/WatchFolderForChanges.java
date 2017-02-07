package FolderWatcher;

import static java.nio.file.StandardWatchEventKinds.*;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardWatchEventKinds;
import java.nio.file.WatchEvent;
import java.nio.file.WatchKey;
import java.nio.file.WatchService;

import Security.CryptoUtils;
import UserInterface.StartBarMenu;
import FileOperations.FileOperations;

public class WatchFolderForChanges extends Thread{
	
	private static final java.io.File TWINCLOUD = new java.io.File(
			System.getProperty("user.home"), "Twin Cloud");
	
	Path dir = Paths.get(TWINCLOUD.getAbsolutePath());
	
	private FileOperations fileOperation = null;
	private StartBarMenu startBarMenu = null;
	private CryptoUtils cryptoutils = null;
	
	private String prevFile = "";
	
	public void run() {
		watch();
		System.out.println("Runned");
	}
	
	public WatchFolderForChanges(FileOperations fileOperation, StartBarMenu startBarMenu) {
		this.fileOperation = fileOperation;
		this.startBarMenu = startBarMenu;
		cryptoutils = new CryptoUtils();
		
		String[] capacity = fileOperation.getCapacityAndUsed().split(":");
		startBarMenu.changeUsedSpace(capacity[0], capacity[1]);
		startBarMenu.changeInfo("check.png", "Up to date.");
	}
	
	private void watch2(FileOperations fileOperation) {
		Path dir = Paths.get(TWINCLOUD.getAbsolutePath());
        try {
			new WatchDir(dir, fileOperation).processEvents();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private void watch() {
		try {
			Path path = Paths.get(TWINCLOUD.getAbsolutePath());
			System.out.println(path);
			WatchService watchService = FileSystems.getDefault()
					.newWatchService();
			
			@SuppressWarnings("unused")
			WatchKey watchKey = path.register(watchService,
					StandardWatchEventKinds.ENTRY_CREATE,
					StandardWatchEventKinds.ENTRY_DELETE,
					StandardWatchEventKinds.ENTRY_MODIFY);
			while (true) {
				WatchKey wk = watchService.take();
				for (WatchEvent<?> event : wk.pollEvents()) {
					
					// get event type
					WatchEvent.Kind<?> kind = event.kind();

					// get file name
					@SuppressWarnings("unchecked")
					WatchEvent<Path> ev = (WatchEvent<Path>) event;
					Path fileName = ev.context();
					String absolutePath = TWINCLOUD.getAbsolutePath() + "\\" + fileName;
					
					if (!startBarMenu.isAutoSyncEnabled())
						continue;
					
					if (kind == OVERFLOW) {
						continue;
					} else if (kind == ENTRY_CREATE) {
						create(fileName, absolutePath);
					} else if (kind == ENTRY_DELETE) {
						delete(fileName, absolutePath);
					} else if (kind == ENTRY_MODIFY) {
						modify(fileName, absolutePath);
					}
				}
				// reset the key
				boolean valid = wk.reset();
				if (!valid) {
					System.out.println("Key has been unregisterede");
				}
				
				String[] capacity = fileOperation.getCapacityAndUsed().split(":");
				startBarMenu.changeUsedSpace(capacity[0], capacity[1]);
			}
		} catch (IOException | InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	public void create(Path fileName, String absolutePath) {
		if (fileName.getFileName().toString().startsWith("~"))
			return;
		
		System.out.println("WatchEvent Create:" + fileName);
		startBarMenu.changeIcon("loading.gif");
		startBarMenu.changeInfo("loading.gif", "Uploading...");
		
		File f = new File(absolutePath);
		if (f.isDirectory()) {
			System.out.println("Folder: " + absolutePath);
			//fileOperation.createFolder(fileName.toString());
		}
		else if (f.isFile()){
			fileOperation.uploadFile(absolutePath, false);
		}
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		startBarMenu.changeIcon("twincloud.png");
		startBarMenu.changeInfo("check.gif", "Up to date.");
		
		prevFile = cryptoutils.digestFile(absolutePath);
	}
	
	public void delete(Path fileName, String absolutePath) {
		startBarMenu.changeIcon("loading.gif");
		startBarMenu.changeInfo("loading.gif", "Deleting...");
		
		if (fileName.getFileName().toString().startsWith("~"))
			return;
		
		System.out.println("WatchEvent Delete:" + fileName);
		fileOperation.deleteFile(fileName.toString());
		startBarMenu.changeIcon("twincloud.png");
		startBarMenu.changeInfo("check.gif", "Up to date.");
	}
	
	public void modify(Path fileName, String absolutePath) {
		
		System.out.println("hash: " + prevFile);
		if (cryptoutils.digestFile(absolutePath).equals(prevFile)) {
			System.out.println("Same with prev file");
			return;
		}
		
		startBarMenu.changeIcon("loading.gif");
		startBarMenu.changeInfo("loading.gif", "Modifying...");
		
		if (fileName.getFileName().toString().startsWith("~"))
			return;
		
		System.out.println("WatchEvent Modify:" + fileName);
		fileOperation.updateFile(absolutePath);
		
//		fileOperation.deleteFile(fileName.toString());
//		fileOperation.uploadFile(absolutePath, true);
		
		prevFile = cryptoutils.digestFile(absolutePath);
		startBarMenu.changeIcon("twincloud.png");
		startBarMenu.changeInfo("check.gif", "Up to date.");
	}
}
