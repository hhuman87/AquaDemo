package com.demoTest;

import org.junit.jupiter.api.Test;

public class LoginPageTests extends Setup_TearDown_Page {

    private final LoginPageSelectors loginPageSelectors = new LoginPageSelectors();

    @Test
    public void login_To_SauceDemo() {
        loginPageSelectors.input_Username("standard_user");
        loginPageSelectors.input_Password("secret_sauce");
        loginPageSelectors.select_Login();
    }
}
