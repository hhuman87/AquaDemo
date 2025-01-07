package uiTests.sauceDemo;

import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Selenide.$x;

// page_url = https://www.saucedemo.com
public class LoginPageSelectors {
    
    public SelenideElement inputUsername = $x("//input[@id='user-name']");
    
    public SelenideElement inputPassword = $x("//input[@id='password']");

    public SelenideElement inputLoginButton = $x("//input[@id='login-button']");

    public void input_Username(String username) {
        inputUsername.sendKeys(username);
    }

    public void input_Password(String password) {
        inputPassword.sendKeys(password);
    }

    public void select_Login() {
        inputLoginButton.click();
    }
}