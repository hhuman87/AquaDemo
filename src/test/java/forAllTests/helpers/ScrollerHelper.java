package forAllTests.helpers;

import com.codeborne.selenide.SelenideElement;
import com.codeborne.selenide.WebDriverRunner;
import org.openqa.selenium.By;

import static com.codeborne.selenide.Selenide.$;
import static org.hamcrest.MatcherAssert.assertThat;

public class ScrollerHelper {
    private By selector;
    private SelenideElement element = null;

    public ScrollerHelper(By selector) {
        this.selector = selector;
    }

    public ScrollerHelper(SelenideElement element) {
        this.element = element;
    }

    public void scrollWhenNotAvailable() {
        element = (element == null) ? $(selector) : element;
        if (!element.getWrappedElement().isDisplayed()) {
            WebDriverRunner.driver().executeJavaScript("window.scrollBy(0,0)");
            element.scrollIntoView(false);
        }
    }

    public void scrollToElementInView() {
        element = (element == null) ? $(selector) : element;
        WebDriverRunner.driver().executeJavaScript("arguments[0].scrollIntoView(true);", element.toWebElement());
        assertThat("Failed to Scroll to Element - " + selector, element.getWrappedElement().isDisplayed());
    }
}
