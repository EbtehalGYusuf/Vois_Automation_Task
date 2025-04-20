package com.utils;

import com.base.homePage;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.*;

// Before and After Method is included here
public class Configurations {

  // ThreadLocal for WebDriver to ensure each thread gets its own WebDriver instance
  private static final ThreadLocal<WebDriver> driver = new ThreadLocal<>();

  // ThreadLocal for homePage to ensure each thread gets its own homePage instance
  private static final ThreadLocal<homePage> threadHomePage = new ThreadLocal<>();

  @Parameters({"browser", "url"})
  @BeforeMethod(alwaysRun = true)
  public void setUp(
      @Optional("chrome") String browser, @Optional("https://www.youtube.com") String url) {
    // Initialize WebDriver using DriverFactory and set it to the current thread
    driver.set(DriverFactory.initDriver(browser));

    // Navigate to the URL
    driver.get().get(url);

    // Initialize homePage for this thread
    threadHomePage.set(new homePage(driver.get())); // Pass the thread-local WebDriver instance
  }

  @AfterMethod(alwaysRun = true)
  public void tearDown() {
    // Quit the WebDriver for the current thread
    if (driver.get() != null) {
      driver.get().quit();
      driver.remove(); // Remove the WebDriver from the current thread
    }

    // Clean up the homePage thread-local instance
    if (threadHomePage.get() != null) {
      threadHomePage.remove(); // Remove the homePage instance from the current thread
    }
  }

  // A method to get the WebDriver instance for the current thread
  public static WebDriver getDriver() {
    return driver.get();
  }

  // A method to get the homePage instance for the current thread
  public static homePage getHomePage() {
    return threadHomePage.get();
  }
}
