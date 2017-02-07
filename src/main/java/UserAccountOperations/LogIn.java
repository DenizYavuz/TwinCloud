package UserAccountOperations;

import java.awt.Color;
import java.awt.Font;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.Locale;
import java.util.Set;

import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.SwingWorker;
import javax.swing.border.EmptyBorder;

import org.mortbay.jetty.security.SSORealm;
import org.openqa.selenium.By;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.Point;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import Security.CryptoUtils;

import com.dropbox.core.DbxAppInfo;
import com.dropbox.core.DbxAuthFinish;
import com.dropbox.core.DbxAuthInfo;
import com.dropbox.core.DbxException;
import com.dropbox.core.DbxRequestConfig;
import com.dropbox.core.DbxWebAuthNoRedirect;
import com.dropbox.core.json.JsonReader.FileLoadException;
import com.dropbox.core.v2.DbxClientV2;
import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp;
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.api.client.googleapis.auth.oauth2.GoogleTokenResponse;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.util.store.FileDataStoreFactory;
import com.google.api.services.drive.Drive;
import com.google.api.services.drive.DriveScopes;
import com.google.api.services.drive.model.About;

public class LogIn {

	/** Directory to store temp files for this application. */
	private static final java.io.File TEMP = new java.io.File(
			System.getProperty("user.home"), ".twincloud");
	
	private static final java.io.File TWINCLOUD = new java.io.File(
			System.getProperty("user.home"), "Twin Cloud");

	private WebDriver driver;
	private boolean isFirefox = true;
	private boolean hideFirefox = false;
	private String userName;
	private String mainPassword;

	private CryptoUtils cryptoUtils;

	private DbxClientV2 dropboxAccount;
	private Drive googleAccount;
	
	// Program start auto login dosyalari varsa bunu cagriyor.
	public LogIn() {
		new File(TEMP.getAbsolutePath()).mkdir();
		new File(TEMP.getAbsolutePath() + "/account").mkdir();
		new File(TEMP.getAbsolutePath() + "/temp").mkdir();
	}

	// Yoksa LoginGUI bunu cairarak login yapiyor.
	public LogIn(String userName, String password) {
		this.userName = userName;
		this.mainPassword = password;

		new File(TEMP.getAbsolutePath()).mkdir();
		new File(TEMP.getAbsolutePath() + "/account").mkdir();
		new File(TEMP.getAbsolutePath() + "/temp").mkdir();

	}

	public boolean executeLogin() {
		cryptoUtils = new CryptoUtils();

		// Tek password'den iki yane derive ediliyor.
		String passwordDropbox = cryptoUtils.derivePassword(mainPassword,
				userName + "@gmail.com", "www.dropbox.com");
		String passwordGoogle = cryptoUtils.derivePassword(mainPassword,
				userName + "@gmail.com", "www.google.com");
		
		System.out.println(passwordDropbox + " " + passwordGoogle);

		try {
			this.dropboxAccount = loginDropbox(passwordDropbox);
			if (this.dropboxAccount == null)
				return false;

			this.googleAccount = loginGoogleDrive(passwordGoogle);
			if (this.googleAccount == null)
				return false;
			
		} catch (IOException | DbxException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	public DbxClientV2 getDropboxAccount() {
		return dropboxAccount;
	}

	public Drive getGoogleAccount() {
		return googleAccount;
	}

	public boolean logOut() {
		String path = TEMP.getAbsolutePath() + "/account/"
				+ "dropboxAccessToken.sc";
		File filePath = new File(path);
		if (!filePath.delete())
			return false;
		path = TEMP.getAbsolutePath() + "/account/" + "googleAccessToken.sc";
		filePath = new File(path);
		if (!filePath.delete())
			return false;
		
		deleteDirectory(new File(TWINCLOUD.getAbsolutePath()));

		System.out.println("Log out for " + userName + " is successful.");
		System.exit(0);
		return true;
	}
	
	public void deleteDirectory(File directory) {
		File[] files = directory.listFiles();
		if (null != files) {
			for (int i = 0; i < files.length; i++) {
				files[i].delete();
			}
		}

	}


	public DbxClientV2 loginDropbox(String password) throws IOException,
			DbxException {
		System.out.println("Logging in to Dropbox...");
		DbxClientV2 client = null;
		String argAuthFileOutput = TEMP.getAbsolutePath() + "/account/"
				+ "dropboxAccessToken.sc";

		DbxRequestConfig config = new DbxRequestConfig("JavaTutorial/1.0",
				Locale.getDefault().toString());

		File f = new File(argAuthFileOutput);
		// Auto login dosyasi varsa Selenium olayina girme.
		if (f.exists() && !f.isDirectory()) {
			try {
				DbxAuthInfo authInfo = DbxAuthInfo.Reader
						.readFromFile(argAuthFileOutput);
				client = new DbxClientV2(config, authInfo.accessToken);
			} catch (FileLoadException ex) {
				ex.printStackTrace();
				System.exit(1);
			}
		} else {
			if (!isFirefox) {

			} else {
				FirefoxProfile firefoxProfile = new FirefoxProfile();
				firefoxProfile.setPreference(
						"browser.private.browsing.autostart", true);
				driver = new FirefoxDriver();
				if (hideFirefox)
					driver.manage().window().setPosition(new Point(-50000, -50000));
			}

			final String APP_KEY = "jhdv8ejc04cjefi";
			final String APP_SECRET = "m9dplknt1b9a6vm";

			try {
				DbxAppInfo appInfo = new DbxAppInfo(APP_KEY, APP_SECRET);

				DbxWebAuthNoRedirect webAuth = new DbxWebAuthNoRedirect(config,
						appInfo);

				// Have the user sign in and authorize your app.
				String authorizeUrl = webAuth.start();

				driver.get(authorizeUrl);
				driver.findElement(By.cssSelector("input[name=login_email]"))
						.sendKeys(this.userName + "@gmail.com");
				driver.findElement(By.cssSelector("input[name=login_password]"))
						.sendKeys(password);

				// Element gorunene kadar bekle.
				WebDriverWait wait = new WebDriverWait(driver, 5);
				wait.until(ExpectedConditions.elementToBeClickable(By
						.cssSelector("button[type=submit]")));
				driver.findElement(By.cssSelector("button[type=submit]"))
						.click();
				
				// Yanlis sifreyi hizli anla.
				
				wait.until(ExpectedConditions.elementToBeClickable(By
						.cssSelector("button[name=allow_access]")));
				driver.findElement(By.cssSelector("button[name=allow_access]"))
						.click();

				// Wait until the element shows up.
				wait.until(ExpectedConditions.elementToBeClickable(By
						.cssSelector("input[class=auth-box]")));
				String token = driver.findElement(
						By.cssSelector("input[class=auth-box]")).getAttribute(
						"data-token");

				String code = token;
				// This will fail if the user enters an invalid authorization
				// code.
				DbxAuthFinish authFinish = webAuth.finish(code);
				String accessToken = authFinish.accessToken;

				client = new DbxClientV2(config, accessToken);

				// client.getAccountInfo().displayName);
				driver.close();

				DbxAuthInfo authInfo = new DbxAuthInfo(authFinish.accessToken,
						appInfo.host);
				try {
					DbxAuthInfo.Writer.writeToFile(authInfo, argAuthFileOutput);
				} catch (IOException ex) {
					ex.printStackTrace();
				}
				System.out.println("Log in to Dropbox completed.");
				return client;
			} catch (Exception e) {
				System.out.println(e.getMessage());
				driver.close();
				return null;
			}

		}
		return client;
	}

	public Drive loginGoogleDrive(String password) throws IOException {
		System.out.println("Logging in to Google Drive...");
		return getDriveService(password);
	}

	/** Application name. */
	private static final String APPLICATION_NAME = "SecureCloud";

	/** Global instance of the JSON factory. */
	private static final JsonFactory JSON_FACTORY = JacksonFactory
			.getDefaultInstance();

	/** Global instance of the HTTP transport. */
	private static HttpTransport HTTP_TRANSPORT;

	/** Global instance of the scopes required by this quickstart. */
	private static final Set<String> SCOPES = DriveScopes.all();

	static {
		try {
			HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
		} catch (Throwable t) {
			t.printStackTrace();
			System.exit(1);
		}
	}

	/**
	 * Creates an authorized Credential object.
	 * 
	 * @return an authorized Credential object.
	 * @throws IOException
	 */
	public Credential authorize(String password) throws IOException {
		// Load client secrets.
		InputStream in = new FileInputStream("client_secret.json");

		GoogleClientSecrets clientSecrets = GoogleClientSecrets.load(
				JSON_FACTORY, new InputStreamReader(in));

		File f = new File(TEMP + "/account/" + "googleAccessToken.sc");
		Credential credential = null;
		if (f.exists() && !f.isDirectory()) {
			// Build flow and trigger user authorization request.
			BufferedReader br = new BufferedReader(new FileReader(TEMP
					+ "/account/" + "googleAccessToken.sc"));
			String line = br.readLine();
			br.close();
			String accessToken = line.split("\\*\\*")[0];
			String refreshToken = line.split("\\*\\*")[1];

			credential = new GoogleCredential.Builder()
					.setJsonFactory(JSON_FACTORY).setTransport(HTTP_TRANSPORT)
					.setClientSecrets(clientSecrets).build();
			credential.setAccessToken(accessToken);
			credential.setRefreshToken(refreshToken);
		} else {
			GoogleAuthorizationCodeFlow flow = new GoogleAuthorizationCodeFlow.Builder(
					HTTP_TRANSPORT, JSON_FACTORY, clientSecrets, SCOPES)
					.setAccessType("offline").setApprovalPrompt("auto").build();
			String url = flow.newAuthorizationUrl()
					.setRedirectUri("http://localhost:51963/Callback").build();

			if (!isFirefox) {
				
			} else {

				try {

					FirefoxProfile firefoxProfile = new FirefoxProfile();
					firefoxProfile.setPreference(
							"browser.private.browsing.autostart", true);
					driver = new FirefoxDriver();
					if (hideFirefox)
						driver.manage().window().setPosition(new Point(-50000, -50000));
					
					driver.get(url);

					WebDriverWait wait = new WebDriverWait(driver, 10);

					try {
						WebDriverWait wait2 = new WebDriverWait(driver, 3);
						wait2.until(ExpectedConditions.elementToBeClickable(By
								.id("next")));
						driver.findElement(By.id("Email")).sendKeys(
								this.userName + "@gmail.com");
						driver.findElement(By.id("next")).click();

						wait2.until(ExpectedConditions.elementToBeClickable(By
								.id("signIn")));
						driver.findElement(By.id("Passwd")).sendKeys(password);
						driver.findElement(By.id("signIn")).click();
					} catch (org.openqa.selenium.TimeoutException e) {
						wait.until(ExpectedConditions.elementToBeClickable(By
								.id("signIn")));
						driver.findElement(By.id("Email")).sendKeys(
								this.userName + "@gmail.com");
						driver.findElement(By.id("Passwd")).sendKeys(password);
						driver.findElement(By.id("signIn")).click();
					}

					wait.until(ExpectedConditions.elementToBeClickable(By
							.id("submit_approve_access")));
					driver.findElement(By.id("submit_approve_access")).click();

					String code = driver.getCurrentUrl().split("code=")[1]; // http://localhost:51963/Callback?code=4/ZLTrLGCshZBCZOhrQU7b5UCZgDO6VH3XKx9c0YNbHgc#
					driver.close();

					GoogleTokenResponse response = flow.newTokenRequest(code)
							.setRedirectUri("http://localhost:51963/Callback")
							.execute();

					credential = new GoogleCredential.Builder()
							.setTransport(HTTP_TRANSPORT)
							.setJsonFactory(JSON_FACTORY)
							.setClientSecrets(clientSecrets).build()
							.setFromTokenResponse(response);

					String accessToken = credential.getAccessToken();
					String refreshToken = credential.getRefreshToken();

					PrintWriter out = new PrintWriter(TEMP.getAbsolutePath()
							+ "/account/googleAccessToken.sc");
					out.print(accessToken + "**" + refreshToken);
					out.close();

				} catch (Exception e) {
					System.out.println(e.getMessage());
					driver.close();
					return null;
				}
			}
		}
		return credential;
	}

	/**
	 * Build and return an authorized Drive client service.
	 * 
	 * @return an authorized Drive client service
	 * @throws IOException
	 */
	public Drive getDriveService(String password) throws IOException {
		Credential credential = authorize(password);
		if (credential == null)
			return null;
		System.out.println("Log in to Google Drive completed.");
		Drive drive = new Drive.Builder(HTTP_TRANSPORT, JSON_FACTORY,
				credential).setApplicationName(APPLICATION_NAME).build();
		return drive;

		// About about = drive.about().get().execute();
		// String email = about.getUser().getEmailAddress();
		// if (email.equals(userName + "@gmail.com")) {
		// return drive;
		// }
		// else {
		// logOut();
		// try {
		// this.dropboxAccount = loginDropbox();
		// } catch (DbxException e) {
		// e.printStackTrace();
		// }
		// credential = authorize();
		// drive = new Drive.Builder(HTTP_TRANSPORT, JSON_FACTORY, credential)
		// .setApplicationName(APPLICATION_NAME).build();
		// return drive;
		// }
	}
}
