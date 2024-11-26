package com.demoTest.components;

import com.codeborne.selenide.Condition;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;

import java.time.Duration;
import java.util.concurrent.TimeUnit;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.fail;

public class TextBox extends Element<TextBox> {
    public TextBox(String id) {
        super(id);
    }

    public void containsAlertText(String text) {
        element.shouldHave(Condition.text(text));
    }

    public TextBox clearValue() {
        element.find(By.tagName("i")).should(Condition.appear).click();
        return this;
    }

    public TextBox enterText(String text) {
        this.element.shouldBe(Condition.exist, Duration.ofMillis(10000)).setValue(text);
        return this;
    }

    public TextBox clearTextField() {
        int InputLength;
        this.element.should(Condition.appear);
        if (this.element.getValue() == null){
             InputLength = this.element.getText().length();
        } else {
             InputLength = this.element.getValue().length();
        }
        for (int i = 0; i < InputLength; i++) {
            this.element.should(Condition.appear).sendKeys(Keys.BACK_SPACE);
        }
        return this;
    }

    public TextBox validateTextFieldEmpty() {
        String value = element.getAttribute("value");
        if (value.isEmpty()) return this;
        fail("Failed to validate field is empty : " + value);
        return this;
    }

    public TextBox validateInputText(String expectedText) {
        String value = element.getAttribute("value");
        for (int count = 0; count < 20; count++) {
            if (value != null && value.length() > 0) {
                assertThat("Failed to validate Input Text - " + expectedText, value.equals(expectedText));
                break;
            } else {
                dynamicDelay();
                value = element.getAttribute("value");
            }
        }
        return this;
    }

    public String getElementValue() {
        String value = element.getAttribute("value");
        for (int count = 0; count < 20; count++) {
            if (value != null && value.length() > 0) {
                return value;
            } else {
                dynamicDelay();
                value = element.getAttribute("value");
            }
        }
        return null;
    }

    public void dynamicDelay() {
        try {
            TimeUnit.MILLISECONDS.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
