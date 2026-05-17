package utils;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.testng.ITestResult;
import org.testng.TestListenerAdapter;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;

import tests.TestBase;

public class TestListener extends TestListenerAdapter {
    @Override
    public void onTestFailure(ITestResult tr) {
        String methodName = tr.getMethod().getMethodName();
        String fileWithPath = methodName + "_error.png";

        Object testClass = tr.getInstance();
        WebDriver driver = ((TestBase) testClass).getDriver();

        if (driver != null) {
            try {
                TakesScreenshot scrShot = ((TakesScreenshot) driver);
                File SrcFile = scrShot.getScreenshotAs(OutputType.FILE);
                File DestFile = new File(fileWithPath);
                
                Files.copy(SrcFile.toPath(), DestFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
                System.out.println("Screenshot saved to: " + DestFile.getAbsolutePath());
                
            } catch (IOException e) {
                System.out.println("Error: screenshot could not be saved.");
                e.printStackTrace();
            }
        }
    }
}