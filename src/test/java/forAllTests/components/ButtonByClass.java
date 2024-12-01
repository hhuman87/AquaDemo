package forAllTests.components;

import org.openqa.selenium.By;

import static com.codeborne.selenide.Selenide.$;

public class ButtonByClass extends Button {

    public ButtonByClass(String _class) {
        super();
        element = $(By.className((_class)));
    }
}
