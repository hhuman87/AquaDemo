package forAllTests.components;

import org.openqa.selenium.By;

import static com.codeborne.selenide.Selenide.$;

public class RadioButtonByXPath extends RadioButton {

    public RadioButtonByXPath(String xpath) {
        super();
        element = $(By.xpath((xpath)));
    }
}
