package utilities;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.*;

import java.io.File;
import java.io.IOException;

public class ScreenshotUtil {

    public static String takeScreenshot(WebDriver driver, String scenarioName) {
        
    	TakesScreenshot ts = (TakesScreenshot) driver;
        File src = ts.getScreenshotAs(OutputType.FILE);

        String dest = "Screenshots/" + scenarioName.replaceAll(" ", "_") + "_" + System.currentTimeMillis() + ".png";
        File destFile = new File(dest);
        try {
            FileUtils.copyFile(src, destFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return dest;
    }
}
