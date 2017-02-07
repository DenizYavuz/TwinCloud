package UserAccountOperations;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.IOException;
import java.util.Scanner;

import org.apache.commons.io.FileUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.openqa.selenium.By;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.Point;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import UserInterface.PopupGUI;
import Exceptions.GoogleSignUpException;

public class SignUp {

	private WebDriver driver;
	private boolean isFirefox = true;
	private JavascriptExecutor js;
	private String firstNamePrev = "";
	private String lastNamePrev = "";
	private String userNamePrev = "";
	private String passwordPrev = "";
	private String phonePrev = "";
	private String captchaPrev = "";
	private String phoneVerificationCodePrev = "";
	
	public SignUp() {
		if (!isFirefox) {
		} else {
			driver = new FirefoxDriver();
			// driver.manage().window().setSize(new Dimension(1920,1080));
			 driver.manage().window().setPosition(new Point(-5000, -5000));
		}

		driver.get("https://accounts.google.com/SignUp");
	}

	public void enterFirstName(String firstName) {
		if (firstName.length() > firstNamePrev.length()) {
			String toSend = firstName.replace(firstNamePrev, "");
			driver.findElement(By.id("FirstName")).sendKeys(toSend);
		} else {
			int deleteCount = firstNamePrev.length() - firstName.length();
			for (int i = 0; i < deleteCount; i++)
				driver.findElement(By.id("FirstName")).sendKeys("\b");
		}
		firstNamePrev = firstName;
	}

	public void enterLastName(String lastName) {
		if (lastName.length() > lastNamePrev.length()) {
			String toSend = lastName.replace(lastNamePrev, "");
			driver.findElement(By.id("LastName")).sendKeys(toSend);
		} else {
			int deleteCount = lastNamePrev.length() - lastName.length();
			for (int i = 0; i < deleteCount; i++)
				driver.findElement(By.id("LastName")).sendKeys("\b");
		}
		lastNamePrev = lastName;
	}
	
	public boolean checkEmail(String userName) {
		driver.findElement(By.id("GmailAddress")).sendKeys(userName);
		driver.findElement(By.id("LastName")).sendKeys("a");
		
		try {Thread.sleep(1000);} catch (InterruptedException e) {}
		
		String mailAdressError = driver
				.findElement(By.id("errormsg_0_GmailAddress")).getText()
				.trim();
		System.out.println("error: " + mailAdressError);
		if (mailAdressError.equals("")) {
			driver.close();
			return true;
		}
		driver.close();
		return false;
	}

	public void enterUserName(String userName) {
		if (userName.length() > userNamePrev.length()) {
			String toSend = userName.replace(userNamePrev, "");
			driver.findElement(By.id("GmailAddress")).sendKeys(toSend);
		} else {
			int deleteCount = userNamePrev.length() - userName.length();
			for (int i = 0; i < deleteCount; i++)
				driver.findElement(By.id("GmailAddress")).sendKeys("\b");
		}
		userNamePrev = userName;
	}

	public void enterPassword(String password) {
		if (password.length() > passwordPrev.length()) {
			String toSend = password.replace(passwordPrev, "");
			driver.findElement(By.id("Passwd")).sendKeys(toSend);
			driver.findElement(By.id("PasswdAgain")).sendKeys(password);
		} else {
			int deleteCount = passwordPrev.length() - password.length();
			for (int i = 0; i < deleteCount; i++) {
				driver.findElement(By.id("Passwd")).sendKeys("\b");
			}
			driver.findElement(By.id("PasswdAgain")).sendKeys(password);
		}
		passwordPrev = password;
	}

	public void enterPhone(String phone) {
		if (phone.length() > phonePrev.length()) {
			String toSend = phone.replace(phonePrev, "");
			driver.findElement(By.id("RecoveryPhoneNumber")).sendKeys(toSend);
		} else {
			int deleteCount = phonePrev.length() - phone.length();
			for (int i = 0; i < deleteCount; i++)
				driver.findElement(By.id("RecoveryPhoneNumber")).sendKeys("\b");
		}
		phonePrev = phone;
	}

	public void enterCaptcha(String captcha) {
		if (captcha.length() > captchaPrev.length()) {
			String toSend = captcha.replace(captchaPrev, "");
			driver.findElement(By.id("recaptcha_response_field")).sendKeys(
					toSend);
		} else {
			int deleteCount = captchaPrev.length() - captcha.length();
			for (int i = 0; i < deleteCount; i++)
				driver.findElement(By.id("recaptcha_response_field")).sendKeys(
						"\b");
		}
		captchaPrev = captcha;
	}
	
	public void enterPhoneVerificationCode(String phoneVerificationCode) {
		if (phoneVerificationCode.length() > phoneVerificationCodePrev.length()) {
			String toSend = phoneVerificationCode.replace(phoneVerificationCodePrev, "");
			driver.findElement(By.id("verify-phone-input")).sendKeys(
					toSend);
		} else {
			int deleteCount = phoneVerificationCodePrev.length() - phoneVerificationCode.length();
			for (int i = 0; i < deleteCount; i++)
				driver.findElement(By.id("verify-phone-input")).sendKeys(
						"\b");
		}
		phoneVerificationCodePrev = phoneVerificationCode;
	}

	public boolean signUp(String name, String surname, String username,
			String password, String phone) {
		return dropboxSignUp(name, surname, username + "@gmail.com", password);
	}
	
	public String googleSignUpSimple() {
		
		return null;
	}

	// Since other fields like name, surname, username, password, phone are
	// filled in enter methods, this method fills the rest and click sign up
	// button.
	public String googleSignUp() {

		/*
		 * new WebDriverWait(driver,
		 * TimeSpan.FromSeconds(timeOut)).Until(ExpectedConditions
		 * .ElementExists((By.Id(login))));
		 */

		js = (JavascriptExecutor) driver;
		WebDriverWait wait = new WebDriverWait(driver, 10);

		driver.findElement(By.id("BirthDay")).sendKeys("01");
		js.executeScript("document.getElementById('HiddenBirthMonth').setAttribute('value', '01')");
		driver.findElement(By.id("BirthYear")).sendKeys("1990");
		js.executeScript("document.getElementById('HiddenGender').setAttribute('value', 'MALE')");

		WebElement tos = driver.findElement(By.id("TermsOfService"));
		if (tos.getAttribute("class").contains("form-error"))
			js.executeScript("arguments[0].click();", tos);

		// Kayit ol butonuna tikla.
		WebElement submitButton = driver.findElement(By.id("submitbutton"));
		js.executeScript("arguments[0].click();", submitButton);

		// If there is an error find it.
		String toReturn = "";
		if (driver.getCurrentUrl().equals("https://accounts.google.com/SignUp")) {
			String nameError = driver
					.findElement(By.id("errormsg_0_FirstName")).getText()
					.trim();
			if (!nameError.equals("")) {
				toReturn += "nameError:" + nameError + ";";
			}

			String surnameError = driver
					.findElement(By.id("errormsg_0_LastName")).getText().trim();
			if (!surnameError.equals("")) {
				toReturn += "surnameError:" + surnameError + ";";
			}

			String mailAdressError = driver
					.findElement(By.id("errormsg_0_GmailAddress")).getText()
					.trim();
			if (!mailAdressError.equals("")) {
				toReturn += "mailAdressError:" + mailAdressError + ";";
			}

			String passwordError = driver
					.findElement(By.id("errormsg_0_Passwd")).getText().trim();
			if (!passwordError.equals("")) {
				toReturn += "passwordError:" + passwordError + ";";
			}

			String phoneError = driver
					.findElement(By.id("errormsg_0_RecoveryPhoneNumber"))
					.getText().trim();
			if (!phoneError.equals("")) {
				toReturn += "phoneError:" + phoneError + ";";
			}

			return toReturn;
		}
		// Telefon dogrulamasi
		else if (driver.getCurrentUrl().equals("https://accounts.google.com/UserSignUpIdvChallenge")) {
			wait.until(ExpectedConditions.elementToBeClickable(By
					.id("next-button")));
			WebElement nextbutton = driver.findElement(By.id("next-button"));
			js.executeScript("arguments[0].click();", nextbutton);
			
			wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//input[@name='VerifyPhone']")));

			final PopupGUI popup = new PopupGUI("Verification",
					"A verification code has been sent.", "Enter the code:",
					"Ok", "Resend");
			popup.setVisible(true);
			popup.textInput.addKeyListener(new KeyAdapter() {
				@Override
				public void keyReleased(KeyEvent e) {
					enterPhoneVerificationCode(popup.textInput.getText());
				}
			});
			popup.buttonFirst.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					boolean verified = verifyTelephoneVerificationCode(popup);
					if (verified)
						if (isAccountCreated()) {
							popup.setVisible(false);
							popup.dispose();
							System.out.println("Google Account'u olusturuldu.");
						} else {
							System.out.println("HATA: Google Account'u olusturulamadi.");
						}
				}
			});
			popup.buttonSecond.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					resendPhoneNotification();
					popup.labelFirst.setText("Verification code resent.");
				}
			});
		}
		return toReturn;
	}

	public void resendPhoneNotification() {
		WebElement resend = driver.findElement(By.xpath("//div/a"));
		js.executeScript("arguments[0].click();", resend);
		WebElement nextbutton = driver.findElement(By.id("next-button"));
		js.executeScript("arguments[0].click();", nextbutton);
	}

	public boolean verifyTelephoneVerificationCode(PopupGUI popup) {
		WebElement nextbutton = driver.findElement(By
				.xpath("//input[@name='VerifyPhone']"));
		js.executeScript("arguments[0].click();", nextbutton);
		
		// if code is not correct
		if (driver.findElement(By.xpath("//span[@class='errormsg']")).getText().length() > 3) {
			popup.labelFirst.setText("Incorrect code, please re-enter");
			return false;
		}
		
		return true;
	}

	public boolean isAccountCreated() {
		WebDriverWait wait = new WebDriverWait(driver, 10);
		wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//div[@class='welcome']")));
		try {
			driver.findElement(By.xpath("//div[@class='welcome']"));
			WebElement submitButton = driver.findElement(By.id("submitbutton"));
			js.executeScript("arguments[0].click();", submitButton);
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}

		return true;
	}

	/**
	 * Refreshes the capthca and returns the URL.
	 * 
	 * @return URL of the captcha image.
	 */
	public String refreshCaptcha() {
		driver.findElement(By.id("recaptcha_reload_btn")).click();
		String captchaURL = driver.findElement(
				By.id("recaptcha_challenge_image")).getAttribute("src");
		return captchaURL;
	}
	
	public boolean dropboxSignUp(String name, String surname, String email,
			String password) {
		driver.get("https://www.dropbox.com/");

		String pageContent = driver.getPageSource();

		// TODO Neden calismiyor.
		/*
		 * WebDriverWait wait = new WebDriverWait(driver, 10);
		 * wait.until(ExpectedConditions
		 * .elementToBeClickable(By.cssSelector("button[class=login-button]")));
		 */

		Document doc = Jsoup.parse(pageContent);
		// System.out.println(doc);
		Elements forms = doc.select("div.form-container");

		String fullnameID = "";
		String nameID = "";
		String surnameID = "";
		boolean isFullName = false;
		if (forms.first().select("input.text-input-input[name=fullname]") != null) {
			fullnameID = forms.first()
					.select("input.text-input-input[name=fullname]").first()
					.attr("id");
			isFullName = true;
		} else {
			nameID = forms.first().select("input.text-input-input[name=fname]")
					.first().attr("id");
			surnameID = forms.first()
					.select("input.text-input-input[name=lname]").first()
					.attr("id");
		}
		String emailID = forms.first()
				.select("input.text-input-input[name=email]").first()
				.attr("id");
		String passwordID = forms.first()
				.select("input.text-input-input[name=password]").first()
				.attr("id");
		String checkID = forms.first().select("input[name=tos_agree]").first()
				.attr("id");
		String loginbuttonID = forms.first().select("button.login-button")
				.first().attr("data-js-component-id");

		if (isFullName)
			driver.findElement(By.id(fullnameID))
					.sendKeys(name + " " + surname);
		else {
			driver.findElement(By.id(nameID)).sendKeys(name);
			driver.findElement(By.id(surnameID)).sendKeys(surname);
		}
		driver.findElement(By.id(emailID)).sendKeys(email);
		driver.findElement(By.id(passwordID)).sendKeys(password);
		driver.findElement(By.id(checkID)).click();
		driver.findElement(
				By.cssSelector("button[data-js-component-id=" + loginbuttonID
						+ "]")).click();

		// TODO Acilan sayfaya gore giris basarili mi, ayni e mail var mi gibi
		// durumlar kodlanmali.

		pageContent = driver.getPageSource();
		doc = Jsoup.parse(pageContent);
		// System.out.println(doc);

		driver.close();
		driver.quit();
		return false;
	}
}
