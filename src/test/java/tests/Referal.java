package tests;

import static org.testng.Assert.assertEquals;

import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.time.Duration;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.WindowType;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Test;

public class Referal {

	@Test(invocationCount = 9)
	public void doRefer() {
		try {
			WebDriver driver = new ChromeDriver();
			driver.manage().window().maximize();
			driver.manage().deleteAllCookies();

			driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(60));

			driver.get("https://tempmailo.com/");

			Thread.sleep(2000);

//			driver.findElement(By.xpath("(//button[contains(@class,'copyIconGreenBtn')])[2]")).click();
			driver.findElement(By.xpath("//button[@class=\"iconx\"]")).click();

			String parentWindow = driver.getWindowHandle();
			System.out.println(parentWindow);

			Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
			Transferable data = clipboard.getContents(null);
			String tempEmail = "";
			if (data != null && data.isDataFlavorSupported(DataFlavor.stringFlavor)) {
				tempEmail = (String) data.getTransferData(DataFlavor.stringFlavor);
				System.out.println("Clipboard content: " + tempEmail);
			} else {
				System.out.println("Clipboard does not contain text.");
			}

			driver.switchTo().newWindow(WindowType.TAB);
//			driver.get("https://member.aorus.com/register/invite/SHREE007");
//			driver.get("https://member.aorus.com/register/invite/MOHAN007");
//			driver.get("https://member.aorus.com/register/invite/RASIKA007");
			driver.get("https://member.aorus.com/register/invite/SHREE420");

			String childWindow = driver.getWindowHandle();
			System.out.println(childWindow);

			driver.findElement(By.id("email")).sendKeys(Keys.CONTROL, "v");
			driver.findElement(By.id("User_Name")).sendKeys(tempEmail.substring(0, 4));

			driver.findElement(By.id("password")).sendKeys("Shubh@123");

			driver.findElement(By.id("password_confirmation")).sendKeys("Shubh@123");

			Thread.sleep(2000);

			WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

			try {
				WebElement cookieBtn = wait.until(
						ExpectedConditions.elementToBeClickable(By.xpath("//button[contains(text(),'Accept')]")));
				cookieBtn.click();
			} catch (Exception e) {
				// ignore if not present
			}

			WebElement acceptBtn = wait.until(
					ExpectedConditions.elementToBeClickable(By.xpath("//button[contains(@class,'GA-Signup-Next')]")));
			acceptBtn.click();

//			driver.findElement(By.xpath("//button[contains(@class,'GA-Signup-Next')]")).click();

			Thread.sleep(2000);

			Select sel = new Select(driver.findElement(By.id("locale")));

			sel.selectByValue("IN");

			driver.findElement(By.id("User_adult")).click();
			driver.findElement(By.id("User_Order")).click();
			driver.findElement(By.id("Privacy_Policty")).click();

			driver.findElement(By.xpath("//button[contains(@class,'GA-Signup-Submit')]")).click();

			driver.switchTo().window(parentWindow);

			// Wait for ad iframe and switch
//			wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(By.cssSelector("iframe[id^='aswift']")));
//
//			// Click Close button
//			WebElement closeBtn = wait
//					.until(ExpectedConditions.elementToBeClickable(By.xpath("//div[@class='continue-prompt-text']") // or use
//					// .close-button
//					));
//			closeBtn.click();

			List<WebElement> frames = driver.findElements(By.cssSelector("iframe[id^='aswift']"));

			for (WebElement frame : frames) {
				driver.switchTo().defaultContent();
				driver.switchTo().frame(frame);

				try {
					List<WebElement> closeBtns = driver
							.findElements(By.xpath("//div[@class='continue-prompt-text' and text()='Close']"));

					if (!closeBtns.isEmpty() && closeBtns.get(0).isDisplayed()) {
						closeBtns.get(0).click();
						System.out.println("Ad closed successfully");
						break;
					}

				} catch (Exception e) {
					// ignore and try next iframe
				}
			}

			// Always return to main DOM
			driver.switchTo().defaultContent();

			// Switch back to main page
			driver.switchTo().defaultContent();

			Thread.sleep(3000);

			driver.findElement(By.xpath("(//button[@class=\"prim-btn\"])[1]")).click();

//			driver.findElement(By.xpath("//ul[@class=\"mail-items-list\"]//li")).click();
//
//			driver.findElement(By.xpath("//a[contains(.,'Verify My Email')]")).click();

			List<WebElement> mails = driver.findElements(By.cssSelector("li.mail-item"));
			System.out.println("Emails found: " + mails.size());

			WebElement firstMail = wait.until(ExpectedConditions
					.elementToBeClickable(By.xpath("//div[contains(text(),'Verify Your GIGABYTE AORUS Email')]")));
			firstMail.click();

			JavascriptExecutor js = (JavascriptExecutor) driver;

			// Remove both aswift iframes AND their host divs
			js.executeScript(
					"document.querySelectorAll(\"iframe[id^='aswift'], div[id^='aswift']\").forEach(e => e.remove());");

			driver.switchTo().frame("fullmessage");

			WebElement verifyLink = wait.until(
					ExpectedConditions.presenceOfElementLocated(By.xpath("//a[contains(text(),'Verify My Email')]")));

			// Use JS click to avoid interception
			js.executeScript("arguments[0].click();", verifyLink);

//			WebElement headerTxt = wait.until(
//					ExpectedConditions.presenceOfElementLocated(By.xpath("//div[@class='h1 page-title']")));
//			String val = headerTxt.getText().trim();
//
//			assertEquals(val, "Account Successfully Activated!");

			Thread.sleep(2000);
			driver.quit();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
