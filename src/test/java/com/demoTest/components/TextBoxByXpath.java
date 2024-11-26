package com.demoTest.components;

import org.openqa.selenium.By;

import static com.codeborne.selenide.Selenide.$;

public class TextBoxByXpath extends TextBox {

    public TextBoxByXpath(String xpath) {
        super(xpath);
        element = $(By.xpath((xpath)));
    }
}
