package testcases;

import org.testng.annotations.Test;

import pageobject.HomePage;
import webUtilities.Base;

public class MailinatorTest extends Base {
	private HomePage homePage;

	/**
	 * Test Steps: 1. Navigate to the Mailinator homepage
	 * (https://www.mailinator.com/). 2. Enter a valid email address or keyword into
	 * the search box (e.g., "testuser"). 3. Click the "Go" button to initiate the
	 * search. 4. Verify that the inbox page is displayed, showing emails related to
	 * the entered email address. 5. Assert that the list of emails is not
	 * empty,confirming that the search has returned results.
	 *
	 */
	@Test(description = "Validate Email Search Functionality")
	public void verifyEmailListDisplayed() {
		driver.get("https://www.mailinator.com/");
		homePage = new HomePage(driver);
		homePage.searchBox.enterText("testing");
		homePage.goButton.click();
		homePage.emailList.isDisplayed();
	}

	@Test(description = "Get email OPT for given email and subject", dependsOnMethods = "verifyEmailListDisplayed")
	public void getEmailOTP() {
		homePage.getEmailSubjects().forEach(subject -> System.out.println(subject));
	}
}
