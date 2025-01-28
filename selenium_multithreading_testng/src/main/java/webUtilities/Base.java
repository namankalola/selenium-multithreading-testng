package webUtilities;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;

import utilities.WebDriverManagerThread;

public class Base {
	protected WebDriver driver;

	@BeforeClass
	@Parameters("browserType")
	public void setup(String browserType) {
		driver = WebDriverManagerThread.getInstance(browserType).getDriver();
	}

	@AfterClass
	public void tearDown() {
		WebDriverManagerThread.clearThread();
	}

	@AfterSuite
	public void afterSuite() {
		String remoteRepoUrl = "https://github.com/namankalola/AutomationReports.git";
		File repoDir = new File("C:/Users/naman/Downloads/temp-repo");
		String sourceFilePath = "extentreport/extentReport.html";
		String commitMessage = "Automated commit for extent report";
		try {
			initRepoIfNeeded(repoDir);
			resetRepo(repoDir);
			copyReportFile(repoDir, sourceFilePath);
			commitAndPush("C:/Users/naman/Downloads/temp-repo/AutomationReports", commitMessage, remoteRepoUrl);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	// Method to execute git commands
	private static void runCommand(String command, File workingDir) throws Exception {
		ProcessBuilder processBuilder = new ProcessBuilder();
		processBuilder.command("cmd", "/c", command);
		processBuilder.directory(workingDir);
		processBuilder.redirectErrorStream(true);

		Process process = processBuilder.start();
		BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));

		String line;
		while ((line = reader.readLine()) != null) {
			System.out.println(line);
		}

		int exitCode = process.waitFor();
		if (exitCode != 0) {
			throw new RuntimeException("Command failed with exit code " + exitCode);
		}
	}

	// Method to reset repo and discard changes
	private static void resetRepo(File repoDir) throws Exception {
		System.out.println("Resetting repository: " + repoDir.getAbsolutePath());
		runCommand("git reset --hard", repoDir);
		runCommand("git clean -fd", repoDir);
		System.out.println("Repository has been reset and is now clean.");
	}

	// Method to copy the new report file to the repo
	private static void copyReportFile(File repoDir, String reportFilePath) throws IOException {
		File reportFile = new File(System.getProperty("user.dir") + "/" + reportFilePath);
		System.out.println("Source file path: " + reportFile.getAbsolutePath());

		if (reportFile.exists()) {
			Path targetDir = Paths.get(repoDir.getAbsolutePath(), "AutomationReports", "clients", "Devoq");
			System.out.println("Target directory path: " + targetDir.toString());

			if (!Files.exists(targetDir)) {
				Files.createDirectories(targetDir);
				System.out.println("Target directories created.");
			}

			String timestamp = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss").format(new Date());

			Path destinationPath = targetDir.resolve("extentReport_" + timestamp + ".html");

			System.out.println("Target file path: " + destinationPath.toString());

			Files.copy(reportFile.toPath(), destinationPath, StandardCopyOption.REPLACE_EXISTING);
			System.out.println("Report file copied and renamed to: " + destinationPath);
		} else {
			throw new IOException("Report file not found: " + reportFilePath);
		}
	}

	// Method to commit and push changes to remote repo
	private static void commitAndPush(String dir, String commitMessage, String remoteRepoUrl) throws Exception {
		File repoDir = new File(dir);
		runCommand("git add --all", repoDir);
		System.out.println("All changes staged.");

		runCommand("git commit -m \"" + commitMessage + "\"", repoDir);
		System.out.println("Changes committed.");

		runCommand("git push ", repoDir);
	}

	// Method to initialize a Git repository if not already initialized
	private static void initRepoIfNeeded(File repoDir) throws Exception {
		File gitDir = new File(repoDir, ".git");

		if (!gitDir.exists()) {
			System.out.println("Initializing repository...");
			runCommand("git init", repoDir); // Initialize the repo if not already done

			runCommand("git clone https://github.com/namankalola/AutomationReports.git", repoDir);
			System.out.println("Repository initialized and remote origin added.");
		} else {
			System.out.println("Repository already initialized.");
		}
	}
}
