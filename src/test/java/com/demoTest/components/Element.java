package com.demoTest.components;

import com.codeborne.selenide.*;
import org.hamcrest.MatcherAssert;
import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.awt.*;
import java.io.File;
import java.time.Duration;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

import static com.codeborne.selenide.Condition.and;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.fail;

@SuppressWarnings("unchecked")
public class Element<T> {

    protected final Logger log = LoggerFactory.getLogger(this.getClass());
    protected SelenideElement element;

    public Element(String id) {
        this.element = $(By.id(id));
    }

    public Element(By locator) {
        this.element = $(locator);
    }

    protected Element() {
    }

    private static void pollDelay() {
        try {
            Thread.sleep(100);
        } catch (InterruptedException ignored) {
        }
    }

    public void click() {
        element.should(Condition.appear).click();
    }

    public T clickElement() {
        element.should(Condition.appear).click();
        return (T) this;
    }

    public T clickByJavascript() {
        Selenide.executeJavaScript("arguments[0].click();", element);
        return (T) this;
    }

    public void validateClassAttribute(String attribute) {
        checkAttribute("class", attribute);
    }

    public void validateClassAttributeContains(String attribute) {
        String classAtt = element.getAttribute("class");
        if (Objects.isNull(classAtt)) {
            fail("Failed to retrieve class attribute");
        }

        boolean result = classAtt.contains(attribute);
        assertThat("Failed to validate class attribute contains " + attribute, result);
    }

    public void validateClassAttributeDoesNotContain(String attribute) {
        String classAtt = element.getAttribute("class");
        if (Objects.isNull(classAtt)) {
            fail("Failed to retrieve class attribute");
        }

        boolean result = classAtt.contains(attribute);
        assertThat("Failed to validate class attribute contains " + attribute, !result);
    }


    public void clickUntilAttrIsPresent(String attribute,String value) {
        click();
        boolean done = getElementAttribute(attribute).contains(value);
        int count = 0;
        while (!done) {
            click();
            done = getElementAttribute(attribute).contains(value);
            if (count > 10) {
                MatcherAssert.assertThat("Failed to validate element is displayed with attr " + attribute + " and value " + value, done);
            }
            count++;
        }
    }

    public void waitUntilNotDisabled() {
        String disabled = this.getElementAttribute("disabled");
        boolean isDisabled = !Objects.isNull(disabled) && disabled.equals("disabled");
        while (isDisabled) {
            disabled = this.getElementAttribute("disabled");
            isDisabled = !Objects.isNull(disabled) && disabled.equals("disabled");
        }
    }

    public void doubleClick() {
        element.should(Condition.appear).doubleClick();
    }

    public void rightClick() {
        element.should(Condition.appear).contextClick();
    }


    public void doubleContextClick() {
        element.should(Condition.appear).contextClick();
        for (int count = 0; count < 10; count++) {
            dynamicDelay();
        }
        element.should(Condition.appear).contextClick();
    }

    public T appearedOnPage() {
        element.shouldBe(Condition.appear, Duration.ofMillis(5000));
        return (T) this;
    }

    public T visibleOnPage() {
        element.shouldBe(Condition.visible, Duration.ofMillis(5000));
        return (T) this;
    }

    public T disappearedFromPage() {
        element.shouldBe(Condition.disappear, Duration.ofMillis(5000));
        return (T) this;
    }

    public T disappearedFromPage(int timeoutMilliseconds) {
        element.shouldBe(Condition.disappear, Duration.ofMillis(timeoutMilliseconds));
        return (T) this;
    }

    public T notAppearedOnPage() {
        element.shouldNotBe(Condition.appear);
        return (T) this;
    }

    public T doesNotExist() {
        element.shouldNotBe(Condition.exist);
        return (T) this;
    }

    public T isEnabled() {
        element.should(Condition.enabled);
        return (T) this;
    }

    public T isDisabled() {
        element.should(Condition.disabled);
        return (T) this;
    }

    public T andContainsText() {
        element.getText();
        assertThat("Failed to Validate that element contains text", true);
        return (T) this;
    }

    public T andContainsText(String text) {
        element.shouldBe(Condition.appear, Duration.ofMillis(5000)).shouldHave(Condition.text(text));
        return (T) this;
    }

    public T andContainsTextWithSensitivity(String text) {
        element.shouldBe(Condition.appear, Duration.ofMillis(5000)).shouldHave(Condition.textCaseSensitive(text));
        return (T) this;
    }

    public T andExactText(String text) {
        element.shouldHave(Condition.exactText(text));
        return (T) this;
    }

    public T andDoesntContainsText(String text) {
        element.shouldNotHave(Condition.text(text));
        return (T) this;
    }

    public T scrollTo() {
        this.element.scrollTo();
        return (T) this;
    }

    public T hoverOver() {
        element.should(Condition.appear).hover();
        return (T) this;
    }

    public T isElementType(String type) {
        element.shouldBe(Condition.attribute("type", type));
        return (T) this;
    }

    public T checkAttribute(String attribute, String value) {
        element.shouldBe(Condition.attribute(attribute, value));
        return (T) this;
    }

    public T elementRoleIs(String role) {
        element.shouldBe(Condition.attribute("role", role));
        return (T) this;
    }

    public T sendKeyToElement(Keys key) {
        element.should(Condition.appear).sendKeys(key);
        return (T) this;
    }

    public T waitForPageElement() {
        //wait 10 seconds
        element.shouldBe(Condition.exist, Duration.ofMillis(10000));
        return (T) this;
    }

    public T waitForPageElement(int timeout) {
        //pass timeout in milliseconds
        element.shouldBe(Condition.exist, Duration.ofMillis(timeout));
        return (T) this;
    }

    public void innerTagWithText(String tag, String text) {
        element.find(By.tagName(tag)).should(Condition.exist).shouldHave(Condition.matchText(text));
    }

    public T innerElementById(String id) {
        this.element = element.find(By.id(id)).should(Condition.exist);
        return (T) this;
    }

    public SelenideElement getElement() {
        return element;
    }

    public SelenideElement getElementChild(String childXpath) {
        return element.find(By.xpath(childXpath));
    }

    public ElementsCollection getElementChildren(String childrenXpath) {
        return element.findAll(By.xpath(childrenXpath));
    }

    public String getElementText() {
        return element.getText();
    }

    public String getTextNonBlank() {
        String text = element.getText();
        while (text.isEmpty()) {
            pollDelay();
            text = element.getText();
        }
        return text;
    }

    public String getElementAttribute(String attribute) {
        return element.getAttribute(attribute);
    }

    public T shouldHaveCss(String propertyName, String expectedValue) {
        element.shouldHave(Condition.cssValue(propertyName, expectedValue));
        return (T) this;
    }

    public T validateCssByHex(String hex) {
        element.shouldHave(Condition.cssValue("background-color", getRGBAColor(hex)));
        return (T) this;
    }

    private String getRGBAColor(String hex) {
        Color color = Color.decode(hex);
        return "rgba(" + color.getRed() + ", " + color.getGreen() + ", " + color.getBlue() + ", 1)";
    }

    public T checkAttributeContains(String attribute, String value) {
        Objects.requireNonNull(element.getAttribute(attribute)).contains(value);
        return (T) this;
    }

    public T sendKeys(String value) {
        element.sendKeys(value);
        return (T) this;
    }

    public T uploadFile(File file) {
        element.uploadFile(file);
        return (T) this;
    }

    public T isVisible(int timeout) {
        //pass timeout in milliseconds
        element.shouldBe(Condition.visible, Duration.ofMillis(10000));
        return (T) this;
    }

    public T input(String text) {
        this.element.should(Condition.appear).setValue(text);
        return (T) this;
    }

    public T waitForDisplayed(int timeoutInSeconds) {
        boolean isElementPresent = false;
        int counter = 0;
        timeoutInSeconds = timeoutInSeconds * 10;
        while (!isElementPresent && counter < timeoutInSeconds) {
            pollDelay();
            isElementPresent = this.element.isDisplayed();
            counter++;
        }
        if (!isElementPresent) {
            fail("Failed to validate element is displayed");
        }
        return (T) this;
    }

    public T scrollIntoView() {
        this.element.scrollIntoView(true);
        return (T) this;
    }

    public T scrollIntoViewBottom() {
        this.element.scrollIntoView(false);
        return (T) this;
    }

    public T waitForClickable(By selector) {
        WebElementCondition clickable = and("can be clicked", Condition.visible, Condition.enabled);
        int waitTime = 400;
        for (int count = 0; count < waitTime; count++) {
            boolean ready = $$(selector).findBy(clickable).isDisplayed();
            if (ready) {
                return (T) this;
            }
            pollDelay();
        }
        fail("Failed to wait for element to be ready for interaction after " + TimeUnit.MILLISECONDS.toSeconds(waitTime * 100));
        return (T) this;
    }

    public T waitUntilTextEqualTo(String textToMatch) {
        int waitTime = 300;
        for (int count = 0; count < waitTime; count++) {
            boolean ready = this.element.getText().equalsIgnoreCase(textToMatch);
            if (ready) {
                return (T) this;
            }
            pollDelay();
        }
        fail("Failed to wait for element to be ready for interaction after " + TimeUnit.MILLISECONDS.toSeconds(waitTime * 100));
        return (T) this;
    }

    public T waitUntilTextContain(String textToMatch) {
        int waitTime = 300;
        for (int count = 0; count < waitTime; count++) {
            boolean ready = this.element.getText().contains(textToMatch);
            if (ready) {
                return (T) this;
            }
            pollDelay();
        }
        fail("Failed to wait for element to be ready for interaction after " + TimeUnit.MILLISECONDS.toSeconds(waitTime * 100));
        return (T) this;
    }

    public T waitUntilTextNotEqualTo(String textToMatch) {
        int waitTime = 300;
        for (int count = 0; count < waitTime; count++) {
            boolean ready = this.element.getText().contains(textToMatch);
            if (!ready) {
                return (T) this;
            }
            pollDelay();
        }
        fail("Failed to wait for element to be ready for interaction after " + TimeUnit.MILLISECONDS.toSeconds(waitTime * 100));
        return (T) this;
    }

    public T validateInputText(String expectedText) {
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
        return (T) this;
    }

    public void dynamicDelay() {
        try {
            TimeUnit.MILLISECONDS.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void isToggled(boolean toggled) {
        this.element.shouldHave(Condition.attribute("aria-checked", String.valueOf(toggled)));
    }

    public void isScrollable() {
        String JS_ELEMENT_IS_SCROLLABLE =
                "return arguments[0].scrollHeight > arguments[0].offsetHeight;";
        Object isScrollable = Selenide.executeJavaScript(JS_ELEMENT_IS_SCROLLABLE, element);
        if (Objects.isNull(isScrollable)) {
            fail("Failed to execute script for method isScrollable()");
        }
        assertThat("Failed to validate element is scrollable", (Boolean) isScrollable);
    }

    public void isNotScrollable() {
        String JS_ELEMENT_IS_SCROLLABLE =
                "return arguments[0].scrollHeight > arguments[0].offsetHeight;";
        Object isScrollable = Selenide.executeJavaScript(JS_ELEMENT_IS_SCROLLABLE, element);
        if (Objects.isNull(isScrollable)) {
            fail("Failed to execute script for method isScrollable()");
        }
        Assertions.assertFalse((Boolean) isScrollable);
    }

    public String getValue() {
        String value = element.getAttribute("value");
        if (value != null && value.isEmpty()) return null;
        return value;
    }

    public void isSortable() {
        this.element.should(Condition.cssClass("sortable"));
    }

    public void isNotSortable() {
        this.element.shouldNot(Condition.cssClass("sortable"));
    }

}
