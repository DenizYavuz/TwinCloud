package Security;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.*;
import java.util.Arrays;

import javax.crypto.*;
import javax.crypto.spec.*;

public class CryptoUtils {

	// TODO Farkli IV'ler kullan
	static String IV = "AAAAAAAAAAAAAAAA";

	/** Directory to store temp files for this application. */
	private static final java.io.File TEMP = new java.io.File(
			System.getProperty("user.home"), ".twincloud/temp");

	/** Creates a temp folder to store key files and encrypted files. */
	public CryptoUtils() {
		String path = TEMP.getAbsolutePath() + "/";
		File keyFile = new File(path);
		keyFile.mkdirs();
	}

	/**
	 * Generates a random key for AES-256. Save the key into a file named
	 * fileName.key returns the path of key file.
	 */
	public String generate256BitKeyForAES(String fileName) {
		try {
			KeyGenerator keyGen = KeyGenerator.getInstance("AES", "SunJCE");
			keyGen.init(256);
			SecretKey secretKey = keyGen.generateKey();

			String path = TEMP.getAbsolutePath() + "/" + fileName + ".key";
			FileOutputStream outputStream = new FileOutputStream(path);
			outputStream.write(secretKey.getEncoded());
			outputStream.close();

			return path;
		} catch (NoSuchAlgorithmException | NoSuchProviderException
				| IOException e) {
			e.printStackTrace();
		}

		return null;
	}

	// Saves encrypted file and returns fileName. Max filesize is 578,887,168
	// bytes(552 MB)
	/**
	 * 
	 * @param filePath
	 *            Path of the file to be encrypt.
	 * @param keyPath
	 *            Path of the key file.
	 * @return Path of the encrypted file.
	 */
	public String encrypt(String filePath, String keyPath) {
		try {
			// Get key from file.
			File inputFileForKey = new File(keyPath);
			FileInputStream inputStreamForKey = new FileInputStream(
					inputFileForKey);
			byte[] inputBytesForKey = new byte[(int) inputFileForKey.length()];
			inputStreamForKey.read(inputBytesForKey);
			inputStreamForKey.close();
			SecretKey key = new SecretKeySpec(inputBytesForKey, "AES");

			// Set cipher properties.
			Cipher cipher = Cipher
					.getInstance("AES/CBC/PKCS5Padding", "SunJCE");
			cipher.init(Cipher.ENCRYPT_MODE, key,
					new IvParameterSpec(IV.getBytes("UTF-8")));

			//
			File inputFile = new File(filePath);
			FileInputStream inputStream = new FileInputStream(inputFile);
			int length = (int) inputFile.length();
			byte[] inputBytes = new byte[length];
			inputStream.read(inputBytes);
			inputStream.close();

			byte[] outputBytes = cipher.doFinal(inputBytes);

			String path = TEMP.getAbsolutePath() + "/" + inputFile.getName();
			FileOutputStream outputStream = new FileOutputStream(path);
			outputStream.write(outputBytes);
			outputStream.close();
			return path;
		} catch (InvalidKeyException | InvalidAlgorithmParameterException
				| NoSuchAlgorithmException | NoSuchProviderException
				| NoSuchPaddingException | IOException
				| IllegalBlockSizeException | BadPaddingException e) {
			e.printStackTrace();
		}
		return null;
	}

	public String decrypt(String filePath, String keyPath, String savePath) {
		try {
			File inputFileForKey = new File(keyPath);
			FileInputStream inputStreamForKey = new FileInputStream(
					inputFileForKey);
			byte[] inputBytesForKey = new byte[(int) inputFileForKey.length()];
			inputStreamForKey.read(inputBytesForKey);
			inputStreamForKey.close();
			SecretKey key = new SecretKeySpec(inputBytesForKey, "AES");

			Cipher cipher = Cipher
					.getInstance("AES/CBC/PKCS5Padding", "SunJCE");
			cipher.init(Cipher.DECRYPT_MODE, key,
					new IvParameterSpec(IV.getBytes("UTF-8")));

			File inputFile = new File(filePath);
			FileInputStream inputStream = new FileInputStream(inputFile);
			byte[] inputBytes = new byte[(int) inputFile.length()];
			inputStream.read(inputBytes);
			inputStream.close();

			byte[] outputBytes = cipher.doFinal(inputBytes);

			String path = TEMP.getAbsolutePath() + "/" + inputFile.getName();
			path = savePath;
			FileOutputStream outputStream = new FileOutputStream(path);
			outputStream.write(outputBytes);
			outputStream.close();

			return path;
		} catch (InvalidKeyException | InvalidAlgorithmParameterException
				| NoSuchAlgorithmException | NoSuchProviderException
				| NoSuchPaddingException | IOException
				| IllegalBlockSizeException | BadPaddingException e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Generates a random key for HmacSHA256. Save the key into a file named
	 * fileName.mac returns the path of key file.
	 */
	public String generateKeyForHMac(String fileName) {
		try {
			KeyGenerator keyGen = KeyGenerator.getInstance("HmacSHA256",
					"SunJCE");
			SecretKey secretKey = keyGen.generateKey();

			String path = TEMP.getAbsolutePath() + "/" + fileName + ".macKey";
			FileOutputStream outputStream = new FileOutputStream(path);
			outputStream.write(secretKey.getEncoded());
			outputStream.close();

			return path;
		} catch (NoSuchAlgorithmException | NoSuchProviderException
				| IOException e) {
			e.printStackTrace();
		}

		return null;
	}

	public String computeHmacSHA256(String filePath, String keyPath) {
		try {
			// Get key from file.
			File inputFileForKey = new File(keyPath);
			FileInputStream inputStreamForKey = new FileInputStream(
					inputFileForKey);
			byte[] inputBytesForKey = new byte[(int) inputFileForKey.length()];
			inputStreamForKey.read(inputBytesForKey);
			inputStreamForKey.close();
			SecretKey key = new SecretKeySpec(inputBytesForKey, "HmacSHA256");

			// Set Mac properties.
			Mac hmac = Mac.getInstance("HmacSHA256", "SunJCE");
			hmac.init(key);

			// Read file.
			File inputFile = new File(filePath);
			FileInputStream inputStream = new FileInputStream(inputFile);
			int length = (int) inputFile.length();
			byte[] inputBytes = new byte[length];
			inputStream.read(inputBytes);
			inputStream.close();

			// Compute MAC.
			byte[] outputBytes = hmac.doFinal(inputBytes);

			// Save output.
			String path = TEMP.getAbsolutePath() + "/" + inputFile.getName()
					+ ".mac";
			FileOutputStream outputStream = new FileOutputStream(path);
			outputStream.write(outputBytes);
			outputStream.close();
			return path;
		} catch (NoSuchAlgorithmException | NoSuchProviderException
				| IOException | InvalidKeyException e) {
			e.printStackTrace();
		}
		return null;
	}

	public boolean verifyHmacSHA256(String filePath, String keyPath,
			String macPath) {
		try {
			// Get key from file.
			File inputFileForKey = new File(keyPath);
			FileInputStream inputStreamForKey = new FileInputStream(
					inputFileForKey);
			byte[] inputBytesForKey = new byte[(int) inputFileForKey.length()];
			inputStreamForKey.read(inputBytesForKey);
			inputStreamForKey.close();
			SecretKey key = new SecretKeySpec(inputBytesForKey, "HmacSHA256");

			// Set Mac properties.
			Mac hmac = Mac.getInstance("HmacSHA256", "SunJCE");
			hmac.init(key);
			// cipher.init(Cipher.ENCRYPT_MODE, key, new
			// IvParameterSpec(IV.getBytes("UTF-8")));

			// Read file.
			File inputFile = new File(filePath);
			FileInputStream inputStream = new FileInputStream(inputFile);
			int length = (int) inputFile.length();
			byte[] inputBytes = new byte[length];
			inputStream.read(inputBytes);
			inputStream.close();

			// Compute MAC.
			byte[] outputBytes = hmac.doFinal(inputBytes);

			// Read MAC file.
			File macFile = new File(macPath);
			FileInputStream macStream = new FileInputStream(macFile);
			int macLength = (int) macFile.length();
			byte[] macBytes = new byte[macLength];
			macStream.read(macBytes);
			macStream.close();

			// outputBytes =? macBytes
			return Arrays.equals(outputBytes, macBytes);

		} catch (NoSuchAlgorithmException | NoSuchProviderException
				| IOException | InvalidKeyException e) {
			e.printStackTrace();
			return false;
		}
	}

	public String derivePassword(String password, String userName,
			String providerName) {
		String derivedPassword = "";

		String text = password + userName + providerName;
		try {
			MessageDigest digest = MessageDigest.getInstance("SHA-256");
			byte[] hash = digest.digest(text
					.getBytes(StandardCharsets.UTF_8));

			StringBuffer hashString = new StringBuffer();
			for (int i = 0; i < hash.length; i++) {
				int asciiNumber = (hash[i] + 128) % 65 + 39;
				//System.out.println(asciiNumber);
				if ((asciiNumber >= 48 && asciiNumber <= 57)
						|| (asciiNumber >= 65 && asciiNumber <= 90)
						|| asciiNumber == 95 || (asciiNumber >= 97 && asciiNumber <= 122)) {
					char hex = (char) asciiNumber;
					hashString.append(hex);
				}
			}

			derivedPassword = hashString.toString();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		
		if (derivedPassword.length() >= 16)
			return derivedPassword.substring(0, 16);
		else
			return derivedPassword.substring(0, derivedPassword.length());
	}
	
	public String digestFile(String path) {
		
		FileInputStream fis = null;
		String md5 = "";
		try {
			fis = new FileInputStream(new File(path));
			md5 = org.apache.commons.codec.digest.DigestUtils.md5Hex(fis);
			fis.close();
		} catch (IOException e2) {
			
		}
		return md5;
	}
	
	public String bytesToHex(byte[] bytes) {
		char[] hexArray = "0123456789ABCDEF".toCharArray();
	    char[] hexChars = new char[bytes.length * 2];
	    for ( int j = 0; j < bytes.length; j++ ) {
	        int v = bytes[j] & 0xFF;
	        hexChars[j * 2] = hexArray[v >>> 4];
	        hexChars[j * 2 + 1] = hexArray[v & 0x0F];
	    }
	    return new String(hexChars);
	}

	public String sha256(String base) {
		try {
			MessageDigest digest = MessageDigest.getInstance("SHA-256");
			byte[] hash = digest.digest(base.getBytes("UTF-8"));
			StringBuffer hexString = new StringBuffer();

			for (int i = 0; i < hash.length; i++) {
				String hex = Integer.toHexString(0xff & hash[i]);
				if (hex.length() == 1)
					hexString.append('0');
				hexString.append(hex);
			}

			return hexString.toString();
		} catch (Exception ex) {
			throw new RuntimeException(ex);
		}
	}
}