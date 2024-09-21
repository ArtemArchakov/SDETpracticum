package utils;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class Wait {

    private static Wait instance;

    private static Wait getInstance(WebDriver driver) {
        if (instance == null) {
            instance = new Wait(driver);
        }
        return instance;
    }

    private final WebDriverWait wait;

    public Wait(WebDriver driver) {
        int timeout = Integer.parseInt(PropertyProvider.getInstance().getProperty("explict.timeout"));
        wait = new WebDriverWait(driver, Duration.ofSeconds(timeout));
    }

    public static void waitUntilVisible(WebDriver driver, final WebElement webElement) {
        getInstance(driver).wait.until(ExpectedConditions.visibilityOf(webElement));
    }

    public static void waitUntilClickable(WebDriver driver, final WebElement webElement) {
        getInstance(driver).wait.until(ExpectedConditions.elementToBeClickable(webElement));
    }

    public static void waitUntilAlertIsPresent(WebDriver driver) {
        new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(ExpectedConditions.alertIsPresent());
    }

    public static void waitUntilVisible(WebDriver driver, By locator, int timeoutInSeconds) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeoutInSeconds));
        wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
    }

}