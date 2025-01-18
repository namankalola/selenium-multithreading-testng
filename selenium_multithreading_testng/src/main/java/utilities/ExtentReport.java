package utilities;

import java.util.logging.Logger;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

public class ExtentReport implements ITestListener {
	private static ExtentReports extent;
	private static ThreadLocal<ExtentTest> test = new ThreadLocal<>();
	protected static final Logger logger = LoggerManager.getLogger();

	@Override
	public void onStart(ITestContext context) {
		synchronized (ExtentReport.class) { // Synchronize to avoid multiple initializations
			if (extent == null) {
				ExtentSparkReporter htmlReporter = new ExtentSparkReporter("extentreport/extentReport.html");
				htmlReporter.config().setTheme(Theme.STANDARD);
				htmlReporter.config().setDocumentTitle("Automation Results - Multithreading framework");
				htmlReporter.config().setReportName("Demo Project - Automation Results");

				extent = new ExtentReports();
				extent.attachReporter(htmlReporter);
				extent.setSystemInfo("Developed By", "Namankumar Kalola");
				extent.setSystemInfo("Application URL", "https://www.mailinator.com/");
				extent.setSystemInfo("Environment", "Demo");
			}
		}
	}

	@Override
	public void onTestStart(ITestResult result) {
		String browserType = result.getTestContext().getCurrentXmlTest().getParameter("browserType");
		String testDescription = result.getMethod().getDescription();
		logger.info("TEST STARTED : " + testDescription);
		String testName = (testDescription != null && !testDescription.isEmpty()) ? testDescription
				: result.getMethod().getMethodName();

		ExtentTest extentTest = extent.createTest(testName + " - [" + browserType + "]");
		extentTest.assignCategory(browserType);
		test.set(extentTest);
	}

	@Override
	public void onTestSuccess(ITestResult result) {
		test.get().pass("Test Passed");
	}

	@Override
	public void onTestFailure(ITestResult result) {
		try {
			String methodName = result.getMethod().getMethodName();
			String browserType = result.getTestContext().getCurrentXmlTest().getParameter("browserType");
			String base64Screenshot = ((TakesScreenshot) WebDriverManagerThread.getInstance(browserType).getDriver())
					.getScreenshotAs(OutputType.BASE64);

			// Attach screenshot to the report with the step name as the title
			test.get().fail("Test Failed: " + result.getThrowable(),
					MediaEntityBuilder.createScreenCaptureFromBase64String(base64Screenshot, methodName).build());

		} catch (Exception e) {
			test.get().fail("Test Failed but screenshot capture failed. Error: " + e.getMessage());
		}
	}

	@Override
	public void onTestSkipped(ITestResult result) {
		test.get().skip("Test Skipped: " + result.getThrowable());
	}

	@Override
	public void onFinish(ITestContext context) {
		if (extent != null) {
			extent.flush();
		}
	}
}
