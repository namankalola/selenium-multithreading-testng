package utilities;

import java.io.IOException;
import java.util.logging.ConsoleHandler;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public class LoggerManager {
	private static final Logger logger = Logger.getLogger(LoggerManager.class.getName());

	static {
		try {
			// Set up a console handler for logging to the console
			ConsoleHandler consoleHandler = new ConsoleHandler();
			consoleHandler.setLevel(Level.ALL);
			logger.addHandler(consoleHandler);

			// Set up a file handler for logging to a file
			FileHandler fileHandler = new FileHandler("extentreport/test-automation.log", true); // 'true' for appending
			fileHandler.setFormatter(new SimpleFormatter());
			logger.addHandler(fileHandler);

			// Set the default logging level
			logger.setLevel(Level.ALL);

		} catch (IOException e) {
			logger.log(Level.SEVERE, "Error occurred while setting up logging handlers", e);
		}
	}

	public static Logger getLogger() {
		return logger;
	}
}
