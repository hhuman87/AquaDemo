package com.demoTest;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.logevents.SelenideLogger;
import com.demoTest.helpers.TestSetup;
import io.github.bonigarcia.wdm.WebDriverManager;
import io.qameta.allure.selenide.AllureSelenide;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import static com.codeborne.selenide.Selenide.open;

public class Setup_TearDown_Page {

    WebDriver driver;

    @BeforeAll
    public static void setUpAll() {
//        WebDriverManager.chromedriver().setup();
//        Configuration.browserSize = "1280x800";
//        SelenideLogger.addListener("allure", new AllureSelenide());
    }

    @BeforeEach
    public void setUp() {
//        driver = new ChromeDriver();
//        driver.get("https://www.saucedemo.com");
//        Configuration.browserCapabilities = new ChromeOptions().addArguments("--remote-allow-origins=*");
//        open("https://www.saucedemo.com");
        TestSetup.setupSelenide();
    }

//    @AfterEach
//    public void tearDown() {
//        driver.quit();
//    }
}
