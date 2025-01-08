//package forAllTests;
//
//import com.codeborne.selenide.Configuration;
//import org.junit.jupiter.api.BeforeAll;
//import org.junit.jupiter.api.BeforeEach;
//import org.openqa.selenium.WebDriver;
//import org.openqa.selenium.chrome.ChromeOptions;
//
//import static com.codeborne.selenide.Selenide.open;
//
//public class Setup_TearDown_Page {
//
//    WebDriver driver;
//
//    @BeforeAll
//    public static void setUpAll() {
////        WebDriverManager.chromedriver().setup();
//        Configuration.browserSize = "1280x800";
//    }
//
//    @BeforeEach
//    public void setUp() {
////        driver = new ChromeDriver();
////        driver.get("https://www.saucedemo.com");
//        Configuration.browserCapabilities = new ChromeOptions().addArguments("--remote-allow-origins=*");
//        open("https://www.saucedemo.com");
//    }
//
////    @AfterEach
////    public void tearDown() {
////        driver.quit();
////    }
//}