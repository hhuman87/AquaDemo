package forAllTests.components;

import org.openqa.selenium.By;

import static com.codeborne.selenide.Selenide.$;

public class LabelByXpath extends Label {

    public LabelByXpath(String xpath) {
        element = $(By.xpath((xpath)));
    }
}
