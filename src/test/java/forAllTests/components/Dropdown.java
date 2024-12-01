package forAllTests.components;

import com.codeborne.selenide.CollectionCondition;
import com.codeborne.selenide.Condition;
import com.codeborne.selenide.WebDriverRunner;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.interactions.Actions;
import forAllTests.helpers.ValidationListHelper;
import java.time.Duration;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static com.codeborne.selenide.Selenide.$;
import static org.hamcrest.MatcherAssert.assertThat;


public class Dropdown extends Element<Dropdown> {

    public Dropdown(String id) {
        super(id);
    }

    protected Dropdown() {
    }

    public Dropdown scrollInsideDropdownToOption(String id) {
        $(By.id(id)).scrollIntoView("false");
        return this;
    }

    public Dropdown scrollInsideDropdownToOptionByXpath(String xpath) {
        $(By.xpath(xpath)).scrollIntoView("false");
        return this;
    }

    public Dropdown openDropdown() {
        element.should(Condition.appear).parent().click();
        return this;
    }

    public Dropdown closeDropdown() {
        this.sendKeyToElement(Keys.ESCAPE);
        return this;
    }

    public void containsAlertText(String text) {
        element.shouldHave(Condition.text(text));
    }

    public Dropdown andCheckOptionsAppears(String... options) {
        $(By.className("menuable__content__active")).shouldBe(Condition.exist, Duration.ofMillis(5000)).find(By.className("v-list")).findAll(By.className("v-list-item")).shouldHave(CollectionCondition.textsInAnyOrder(options));
        return this;
    }

    public Dropdown andCheckOneOfOptionIs(String option) {
        List<String> optionsList = $(By.className("menuable__content__active"))
                .find(By.className("v-list"))
                .findAll(By.className("v-list-item__content")).texts();
        assertThat(option + " is not among the options", optionsList.contains(option));
        return this;
    }

    public Dropdown andCheckTaskAssigneeOneOfOptionIs(String option) {
        List<String> optionsList = $(By.className("menuable__content__active"))
                .find(By.className("v-list"))
                .findAll(By.xpath(".//div[@class='v-list-item v-list-item--link theme--light']//strong[contains(@id,'task-assignee')]")).texts();
        assertThat(option + " is not among the options", optionsList.contains(option));
        return this;
    }

    public void andCheckOptionsAreInCertainOrder(String... options) {
        $(By.className("menuable__content__active")).find(By.className("v-list")).findAll(By.className("v-list-item")).shouldHave(CollectionCondition.exactTexts(options));
    }

    public Dropdown andSelectOptionViaParent(String option) {
        $(By.id(option)).parent().click();
        return this;
    }

    public void andSelectOption(String option) {
        $(By.id(option)).click();
    }

    public Dropdown andSelectOptionByText(String value) {
        String xpath = String.format("//*[contains(text(),'%s')]", value);
        new ButtonByXpath(xpath)
                .scrollIntoView()
                .waitForPageElement()
                .click();
        return this;
    }

    public void andSelectOptionByTextFocused(String value) {
        String xpath = String.format("//*[contains(@class, 'focused')]//*[text() = '%s']", value);
        $(By.xpath(xpath)).click();
    }

    public void clearValue() {
        element.find(By.tagName("i")).should(Condition.appear).click();
    }

    public Dropdown findOptionByValue(String value) {
        this.openDropdown();
        try {
            element.getWrappedElement().sendKeys(value);
        } catch (Exception ignored) {
        }
        return this;
    }

    /**
     * Provide this method with a generic xpath to all the dropdown options.
     * Provide the option as it is visible on the website.
     */
    public Dropdown selectOptionByXpath(String xpathOption) {
        this.openDropdown();
        new LabelByXpath(xpathOption).waitForPageElement().click();
        return (this);
    }

    public Dropdown selectDropdownOptionFromGenericXpath(String genericXpathToOptions, String option) {
        this.openDropdown();
        ValidationListHelper idList = new ValidationListHelper(genericXpathToOptions).waitForListSizeToBeMoreThan(0,10);

        if (idList.getSize() > 18) {
            //This is added due to large Lists not fully populating in the HTML until you scroll. then more gets populated.
            boolean isEndReached = false;
            int counter = 0;
            int listSize = idList.getSize();
            while (!isEndReached && counter < 18) {
                this.scrollInsideDropdownToOption(idList.getAttributeList("id").get(listSize - 1));
                pause(500);
                if (listSize == idList.getSize()) {
                    isEndReached = true;
                }
                listSize = idList.getSize();
                counter++;
            }
            idList = new ValidationListHelper(genericXpathToOptions);
        }

        String sanitisedOption = option.toLowerCase().replaceAll("[^A-Za-z0-9()]", "-");

        String optionID = idList.getAnyMatchContainsAttribute("id", sanitisedOption);

        if (optionID == null || optionID.isEmpty()) {
            assertThat("Failed matching Option Provided to Dropdown Option IDs " + option, false);
        }
        this.scrollInsideDropdownToOption(optionID);
        this.andSelectOptionViaParent(optionID);
        return this;
    }


    public Dropdown searchAndSelectDropdownOption(String genericXpathToOptions, String optionValue) {
        this.findOptionByValue(optionValue);
        new ValidationListHelper(genericXpathToOptions).
                waitForListSizeToBeMoreThan(0, 5).
                getElementAnyMatchContainsText(optionValue).click();
        return this;
    }

    public void scrollTillOptionIsVisibleAndSelectDropdownOption(String dropdownXpath, String option) {
        int size = 0;
        WebDriver driver = WebDriverRunner.getWebDriver();
        Actions actions = new Actions(driver);

        Dropdown element = new DropdownByXpath(dropdownXpath);
        element.openDropdown();
        while (size == 0) {
            for (int i = 0; i <10;i++) {
                actions.sendKeys(Keys.ARROW_DOWN).perform();
            }
            size = new ValidationListHelper("//strong[contains(text(),'"+option+"')]").getSize();
        }
        element.andSelectOptionByText(option);
    }

    public void scrollTillOptionIsVisibleAndSelectDropdownXpathOption(String dropdownXpath, String xpath, String option) {
        int size = 0;
        WebDriver driver = WebDriverRunner.getWebDriver();
        Actions actions = new Actions(driver);

        Dropdown element = new DropdownByXpath(dropdownXpath);
        element.openDropdown();
        while (size == 0) {
            for (int i = 0; i <10;i++) {
                actions.sendKeys(Keys.ARROW_DOWN).perform();
            }
            size = new ValidationListHelper(xpath).getSize();
        }
        element.andSelectOptionByText(option);
    }

    public void pause(int ms) {
        try {
            TimeUnit.MILLISECONDS.sleep(ms);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
