package gaugeSteps;

import com.codeborne.selenide.Configuration;
import com.thoughtworks.gauge.Step;
import org.openqa.selenium.chrome.ChromeOptions;

import static com.codeborne.selenide.Selenide.closeWebDriver;
import static com.codeborne.selenide.Selenide.open;

public class BrowserSteps {

    @Step("Open <url>")
    public void openBrowser(String url) {
        Configuration.browserSize = "1280x800";
        Configuration.browserCapabilities = new ChromeOptions().addArguments("--remote-allow-origins=*");
        if (url.equalsIgnoreCase("SauceDemo")) {
            url = "https://www.saucedemo.com";
        }
        open(url);
    }

    @Step("Close browser")
    public void closeBrowser() {
        closeWebDriver();
    }
}
