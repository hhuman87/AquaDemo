package com.demoTest.components;

import com.codeborne.selenide.Condition;

import static com.codeborne.selenide.Selenide.$;
import static org.hamcrest.MatcherAssert.assertThat;

public class RadioButton extends Element<RadioButton> {

    public RadioButton(String id) {
        super(id);
    }

    protected RadioButton(){

    }

    public void toggle() {
        element.parent().click();
    }

    public void isVisible() {
        element.parent().should(Condition.appear);
    }

    public void ensureToggledStateIs(boolean isChecked) {
        boolean currentState = Boolean.parseBoolean(element.getAttribute("aria-checked"));
        if(currentState!=isChecked) {
            toggle();
            currentState = Boolean.parseBoolean(element.getAttribute("aria-checked"));
        }
        assertThat("Failed to ensure Button's Toggled State is ",currentState==isChecked);
    }
}
