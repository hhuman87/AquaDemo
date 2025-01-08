//package forAllTests.helpers;
//
//import org.openqa.selenium.By;
//
//public class SelectorHelper {
//    public static By findByText(String text) {
//        return baseGenericTextFinder(text, "*", false);
//    }
//
//    public static By findByContainsText(String text) {
//        return baseGenericTextFinder(text, "*", true);
//    }
//
//    public static By findByText(String element, String text) {
//        return baseGenericTextFinder(text, element, false);
//    }
//
//    public static By findByContainsText(String element, String text) {
//        return baseGenericTextFinder(text, element, true);
//    }
//
//    public static By selectorByXpath(String xpath) {
//        return By.xpath(xpath);
//    }
//
//    public static By selectorById(String id) {
//        return By.id(id);
//    }
//
//    public static By selectorByClass(String className) {
//        return By.className(className);
//    }
//
//    private static By baseGenericTextFinder(String text, String element, boolean contains) {
//        String xpath;
//        if (contains) {
//            xpath = String.format("//%s[contains(text(),'%s')]", element, text);
//        } else {
//            xpath = String.format("//%s[text() = '%s']", element, text);
//        }
//        return By.xpath(xpath);
//    }
//}
