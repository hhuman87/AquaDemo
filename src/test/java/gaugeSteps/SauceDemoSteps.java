package gaugeSteps;

import com.thoughtworks.gauge.Step;
import uiTests.sauceDemo.HomePageSelectors;
import uiTests.sauceDemo.LoginPageSelectors;

public class SauceDemoSteps {

    private final LoginPageSelectors loginPageSelectors = new LoginPageSelectors();
    private final HomePageSelectors homePageSelectors = new HomePageSelectors();

    @Step("Login to DemoSauce")
    public void loginToSauceDemo() {
        loginPageSelectors.input_Username("standard_user");
        loginPageSelectors.input_Password("secret_sauce");
        loginPageSelectors.select_Login();
    }

    @Step("Validate the Burger Menu List on the HomePage")
    public void validateBurgerMenuList() {
        homePageSelectors.validate_Logo();
        homePageSelectors.validate_Title("Products");
        homePageSelectors.select_Burger_Menu();
        homePageSelectors.validate_Burger_Menu_List_Options();
    }
}
