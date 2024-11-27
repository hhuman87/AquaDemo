package com.demoTest.helpers;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.Selenide;
import org.openqa.selenium.chrome.ChromeOptions;

public class TestSetup {
    public static void setupSelenide() {
        // Set the path for ChromeDriver if needed
        System.setProperty("webdriver.chrome.driver", "/usr/local/bin/chromedriver");

        // Set Chrome options for headless mode
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--headless"); // Run Chrome in headless mode
        options.addArguments("--no-sandbox"); // Disable the sandbox mode (useful in CI)
        options.addArguments("--disable-dev-shm-usage"); // Overcome DevShm issue
        options.addArguments("--remote-allow-origins=*"); // To avoid WebDriver connection issues
        options.addArguments("--disable-gpu"); // Disable GPU acceleration, not needed for headless mode

        // Configure Selenide to use the specified Chrome options
        Configuration.browser = "chrome";
        Configuration.headless = true; // Explicitly tell Selenide to run headless
        Configuration.browserSize = "1920x1080"; // Set a default resolution (you can adjust)
        //Configuration.chromeOptions = options;

        // Open the browser (this will now run headlessly)
        Selenide.open("https://www.saucedemo.com");
    }
}
