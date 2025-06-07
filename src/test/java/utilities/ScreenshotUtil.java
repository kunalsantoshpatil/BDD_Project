package utilities;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.*;

import java.io.File;
import java.io.IOException;

public class ScreenshotUtil {

    public static String takeScreenshot(WebDriver driver, String scenarioName) {
        TakesScreenshot ts = (TakesScreenshot) driver;
        File src = ts.getScreenshotAs(OutputType.FILE);

        // Create Screenshots folder if not exists
        String screenshotsDir = System.getProperty("user.dir") + "/Screenshots";
        File folder = new File(screenshotsDir);
        if (!folder.exists()) {
            folder.mkdirs();
        }

        // Build filename
        String fileName = scenarioName.replaceAll("[^a-zA-Z0-9_-]", "_") + "_" + System.currentTimeMillis() + ".png";
        String destPath = screenshotsDir + "/" + fileName;
        File destFile = new File(destPath);

        try {
            FileUtils.copyFile(src, destFile);
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Return relative path (from Spark.html in Report/)
        return "Screenshots/" + fileName;
    }
}
