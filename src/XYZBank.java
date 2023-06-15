import java.time.Duration;
import java.util.List;
import org.asynchttpclient.netty.request.NettyRequestSender;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Select;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import io.github.bonigarcia.wdm.WebDriverManager;

public class XYZBank {
	public WebDriver driver;
	public SoftAssert softassert = new SoftAssert();
	public String depositBalance = "1000";
	public String withdrawBalance = "200";

	public double rupee = 100;

	public double dollar = 0.012;

	public double pound = 0.0096;

	public int numberofdeposit = Integer.parseInt(depositBalance);
	public int withdrawNumber = Integer.parseInt(withdrawBalance);

	public int expectedOfEquation = numberofdeposit - withdrawNumber;

	@BeforeTest
	public void this_is_before_test() {

		WebDriverManager.chromedriver().setup();
		driver = new ChromeDriver();
		driver.get("https://globalsqa.com/angularJs-protractor/BankingProject/#/login");
		driver.manage().window().maximize();

	}

	@Test(priority = 1)
	public void add_customer() throws InterruptedException {

		int userID = (int) (Math.random() * 1000);
		StringBuilder userName = new StringBuilder();
		userName.append(userID);
		String userIDAsString = userName.toString();

		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
		driver.findElement(By.xpath("/html/body/div/div/div[2]/div/div[1]/div[2]/button")).click();
		driver.findElement(By.xpath("/html/body/div/div/div[2]/div/div[1]/button[1]")).click();
		driver.findElement(By.xpath("/html/body/div/div/div[2]/div/div[2]/div/div/form/div[1]/input")).sendKeys("Mr"); // firstName
		driver.findElement(By.xpath("/html/body/div/div/div[2]/div/div[2]/div/div/form/div[2]/input"))
				.sendKeys("user" + userIDAsString); // lastName
		driver.findElement(By.xpath("/html/body/div/div/div[2]/div/div[2]/div/div/form/div[3]/input"))
				.sendKeys(userIDAsString); // postelCode
		driver.findElement(By.xpath("/html/body/div/div/div[2]/div/div[2]/div/div/form/button")).click();

		String alertMsg = driver.switchTo().alert().getText();
//		System.out.println("this is get alert msg ---> " + alertMsg);
		boolean checkMsg = alertMsg.contains("successfully");
//		System.out.println("this is message for check ->  " + checkMsg);

		softassert.assertEquals(checkMsg, true);
		Thread.sleep(2000);
		driver.switchTo().alert().accept();

		driver.navigate().to("https://globalsqa.com/angularJs-protractor/BankingProject/#/manager/list");

		driver.findElement(By.xpath("/html/body/div/div/div[2]/div/div[2]/div/form/div/div/input"))
				.sendKeys("user" + userIDAsString);

		List<WebElement> resultOfUsers = driver.findElements(By.xpath("//tbody/tr[1]"));
//		System.out.println("this is the number of user --->> " + resultOfUsers.size());

		int numberOfusersAfterSearch = resultOfUsers.size();

		softassert.assertEquals(numberOfusersAfterSearch, 1);
		softassert.assertAll();
		Thread.sleep(3000);

	}

	@Test(priority = 2)
	public void add_currency_for_user() {
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
		driver.navigate().to("https://globalsqa.com/angularJs-protractor/BankingProject/#/manager/openAccount");
		driver.findElement(By.xpath("//*[@id=\"userSelect\"]")).click();

		driver.findElement(By.xpath("//*[@id=\"userSelect\"]")).sendKeys("m" + Keys.ENTER);

		driver.findElement(By.xpath("//*[@id=\"currency\"]")).click();
		driver.findElement(By.xpath("//*[@id=\"currency\"]")).sendKeys("d" + Keys.ENTER);
		driver.findElement(By.xpath("/html/body/div/div/div[2]/div/div[2]/div/div/form/button")).click();

		String getMsg = driver.switchTo().alert().getText();
//		System.out.println("this is message aleart that related accout created -> " + getMsg);
		boolean checkMsg = getMsg.contains("Account created successfully");

		softassert.assertEquals(checkMsg, true);

		driver.switchTo().alert().accept();
		softassert.assertAll();
	}

	@Test(priority = 3)
	public void deposit_money() throws InterruptedException {
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
		driver.findElement(By.xpath("/html/body/div/div/div[1]/button[1]")).click();
		driver.findElement(By.xpath("/html/body/div/div/div[2]/div/div[1]/div[1]/button")).click();
		driver.findElement(By.xpath("//*[@id=\"userSelect\"]")).click();
		driver.findElement(By.xpath("//*[@id=\"userSelect\"]")).sendKeys("m" + Keys.ENTER);
		driver.findElement(By.xpath("/html/body/div/div/div[2]/div/form/button")).click();

		driver.findElement(By.xpath("/html/body/div/div/div[2]/div/div[3]/button[2]")).click(); // deposit button
		driver.findElement(By.xpath("/html/body/div/div/div[2]/div/div[4]/div/form/div/input"))
				.sendKeys(depositBalance);
		driver.findElement(By.xpath("/html/body/div/div/div[2]/div/div[4]/div/form/button")).click();
		Thread.sleep(2000);

		String expectedMsg = driver.findElement(By.xpath("/html/body/div/div/div[2]/div/div[4]/div/span")).getText();

//		System.out.println(expectedMsg);
		String actualMsg = "Deposit Successful";

		softassert.assertEquals(actualMsg, expectedMsg);

		String balance = driver.findElement(By.xpath("/html/body/div/div/div[2]/div/div[2]/strong[2]")).getText();
//		System.out.println("this is balance --->  " + balance);

		int updateBalance = Integer.parseInt(balance);
//		System.out.println("This is new int balance ->");
//		System.out.println(updateBalance);

		softassert.assertEquals(updateBalance, numberofdeposit);

		driver.findElement(By.xpath("/html/body/div/div/div[2]/div/div[3]/button[1]")).click();

		List<WebElement> getResultOfTranaction = driver.findElements(By.xpath("//tr[@id='anchor0']"));

		int resultOfTranaction = getResultOfTranaction.size();

		softassert.assertEquals(resultOfTranaction, 1, "this is the number of table row in result of transction");

		String totalDeposit = driver.findElement(By.xpath("//*[@id=\"anchor0\"]/td[2]")).getText();
//		System.out.println(totalDeposit);

		int numberOfTableRow = Integer.parseInt(totalDeposit);

		softassert.assertEquals(numberOfTableRow, numberofdeposit,
				"this is assert between the total number of deposit and the actual number in table row");

		softassert.assertAll();

	}

	@Test(priority = 4)
	public void withdraw_money() {
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
		driver.findElement(By.xpath("/html/body/div/div/div[2]/div/div[1]/button[1]")).click(); // back button
		driver.findElement(By.xpath("/html/body/div/div/div[2]/div/div[3]/button[3]")).click(); // withdraw button

		driver.findElement(By.xpath("/html/body/div/div/div[2]/div/div[4]/div/form/div/input"))
				.sendKeys(withdrawBalance);
		driver.findElement(By.xpath("/html/body/div/div/div[2]/div/div[4]/div/form/button")).click();

		System.out.println(withdrawNumber);

		String getTheTotalnumOfWithdrow = driver.findElement(By.xpath("/html/body/div/div/div[2]/div/div[2]/strong[2]"))
				.getText();

		System.out.println("------>>>> " + getTheTotalnumOfWithdrow);

		int intTheTotalnumOfWithdrow = Integer.parseInt(getTheTotalnumOfWithdrow);

		softassert.assertEquals(intTheTotalnumOfWithdrow, expectedOfEquation, "this is the result of equation");

		WebElement getMsg = driver.findElement(By.xpath("/html/body/div/div/div[2]/div/div[4]/div/span"));

		String getMsgSuccOrNotInwithdrow = getMsg.getText();

		String expectedSuccessMsg = "Transaction successful";
		String actualFaileddMsg = "Transaction Failed. You can not withdraw amount more than the balance.";

		System.out.println("************************");
		System.out.println(getMsgSuccOrNotInwithdrow);
		System.out.println("************************");

		if (numberofdeposit >= withdrawNumber) {

			softassert.assertEquals(getMsgSuccOrNotInwithdrow, expectedSuccessMsg, "this is a valid input");
			System.out.println("TRUE");
		}

		else {
			softassert.assertEquals(getMsgSuccOrNotInwithdrow, actualFaileddMsg, "this is an invalid input");
			System.out.println("FALSE");

		}

		softassert.assertAll();

	}

	@Test(priority = 5)
	public void convert_dollar_to_rupees() throws InterruptedException {

		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
		driver.findElement(By.xpath("/html/body/div/div/div[1]/button[1]")).click();

		driver.findElement(By.xpath("/html/body/div/div/div[2]/div/div[1]/div[1]/button")).click();

		WebElement usersList = driver.findElement(By.id("userSelect"));

		Select selectUser = new Select(usersList);

		selectUser.selectByVisibleText("Harry Potter");
		driver.findElement(By.xpath("/html/body/div/div/div[2]/div/form/button")).click();

		WebElement selectAnotherAccount = driver.findElement(By.id("accountSelect"));
		Select selectFromAccount = new Select(selectAnotherAccount);

		int dropsize = selectFromAccount.getOptions().size();
		System.out.println(dropsize);
		double total = 0;

		for (int i = 0; i < dropsize; i++) {
			selectFromAccount.selectByIndex(i);

			driver.findElement(By.xpath("/html/body/div/div/div[2]/div/div[3]/button[2]")).click();

			driver.findElement(By.xpath("/html/body/div/div/div[2]/div/div[4]/div/form/div/input")).sendKeys("100");

			driver.findElement(By.xpath("/html/body/div/div/div[2]/div/div[4]/div/form/button")).click();

			Thread.sleep(3000);
			String theNumberAdded = driver.findElement(By.xpath("/html/body/div/div/div[2]/div/div[2]/strong[2]"))
					.getText();
			System.out.println(theNumberAdded);

			WebElement getCurruncayName = driver
					.findElement(By.xpath("/html/body/div/div/div[2]/div/div[2]/strong[3]"));
			String currencyName = getCurruncayName.getText();

			System.out.println("*************");

			System.out.println(currencyName);
			System.out.println("*************");

			if (currencyName.contains("Rupee")) {
//				System.out.println("TRUE1");
				String val = driver.findElement(By.xpath("/html/body/div/div/div[2]/div/div[2]/strong[2]")).getText();
				double newDepositR = Double.parseDouble(val);

				System.out.println(newDepositR);
				total = total + newDepositR;

			} else if (currencyName.contains("Dollar")) {
//				System.out.println("TRUE2");
				String val = driver.findElement(By.xpath("/html/body/div/div/div[2]/div/div[2]/strong[2]")).getText();
				double newDepositD = Double.parseDouble(val);

				double convertExchD = newDepositD * dollar;

				total = total + convertExchD;
				System.out.println(convertExchD);

			} else if (currencyName.contains("Pound")) {
//				System.out.println("TRU3");
				String val = driver.findElement(By.xpath("/html/body/div/div/div[2]/div/div[2]/strong[2]")).getText();
				double newDepositDP = Double.parseDouble(val);

				double convertExchP = newDepositDP * pound;
				System.out.println(convertExchP);

				total = total + convertExchP;

			}
		}
		System.out.println("the total is = " + total);

	}

}
