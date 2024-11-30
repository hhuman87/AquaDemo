package com.demoTest;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;

import java.time.Duration;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.*;

// page_url = https://www.saucedemo.com/inventory.html
public class HomePageSelectors {
    
    public SelenideElement subTitle = $x("//span[@class='title']");

    public SelenideElement burgerMenu = $x("//button[@id='react-burger-menu-btn']");

    public SelenideElement pageLogo = $x("//div[@class='app_logo']");

    public SelenideElement sidebar_AllItems_Link = $x("//a[@data-test='inventory-sidebar-link']");

    public SelenideElement sidebar_About_Link = $x("//a[@data-test='about-sidebar-link']");

    public SelenideElement sidebar_Logout_Link = $x("//a[@data-test='logout-sidebar-link']");

    public SelenideElement sidebar_ResetAppState_Link = $x("//a[@data-test='reset-sidebar-link']");

    public SelenideElement burgerMenu_CloseButton = $x("//button[@id='react-burger-cross-btn']");

    void validate_Logo() {
        pageLogo.shouldBe(Condition.visible, Duration.ofMillis(5000));
    }

    void validate_Title(String title) {
        subTitle.shouldBe(Condition.visible, Duration.ofMillis(5000));
        subTitle.shouldHave(text(title));
    }
    
    void select_Burger_Menu() {
        burgerMenu.click();
    }

    void validate_Burger_Menu_List_Options() {
        sidebar_AllItems_Link.shouldBe(Condition.visible, Duration.ofMillis(5000));
        sidebar_AllItems_Link.shouldHave(text("All Items"));

        sidebar_About_Link.shouldBe(Condition.visible, Duration.ofMillis(5000));
        sidebar_About_Link.shouldHave(text("About"));
        sidebar_Logout_Link.shouldBe(Condition.visible, Duration.ofMillis(5000));
        sidebar_Logout_Link.shouldHave(text("Logout"));
        sidebar_ResetAppState_Link.shouldBe(Condition.visible, Duration.ofMillis(5000));
        sidebar_ResetAppState_Link.shouldHave(text("Reset App State"));
        burgerMenu_CloseButton.click();
    }
}