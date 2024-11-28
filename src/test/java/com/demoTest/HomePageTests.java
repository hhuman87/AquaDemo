package com.demoTest;

import org.junit.jupiter.api.Test;

public class HomePageTests extends Setup_TearDown_Page {

    private final LoginPageTests loginPageTests = new LoginPageTests();
    private final HomePageSelectors homePageSelectors = new HomePageSelectors();

    @Test
    public void validate_Burger_Menu_List() {
        loginPageTests.login_To_SauceDemo();
        homePageSelectors.validate_Logo();
        homePageSelectors.validate_Title("Products");
        homePageSelectors.select_Burger_Menu();
        homePageSelectors.validate_Burger_Menu_List_Options();
    }
}
