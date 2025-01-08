//package forAllTests.helpers;
//
//import forAllTests.components.Element;
//import java.util.List;
//import java.util.Objects;
//
//import static org.hamcrest.MatcherAssert.assertThat;
//
//public class ValidationHelper {
//    public static boolean validateEquals(String field, String expected, String actual) {
//        assertThat(messageValidationFormat(field, expected, actual), actual.equals(expected));
//        return true;
//    }
//
//    public static void validateNoDOBAgeWithRange(String field, String expectedAge, String actualAge) {
//        if (actualAge.equals(expectedAge)) {
//            assertThat(messageValidationFormat(field, expectedAge, actualAge), true);
//        } else {
//            //determine another month needed;
//            String[] ageSplit = expectedAge.split(" ");
//            String monthFromExpected = ageSplit[1].substring(0, 1);
//            int month = Integer.parseInt(monthFromExpected);
//            int oneLessMonth = month;
//            if (month > 1) {
//                oneLessMonth = month - 1;
//            }
//            String adjustedAge = String.format("%s %s", ageSplit[0], oneLessMonth + "m");
//            assertThat(messageValidationFormat(field, expectedAge, actualAge), actualAge.equals(adjustedAge));
//        }
//    }
//
//
//    public static void validateAgeWithRange(String field, String expectedAge, String actualAge) {
//        //determine another month needed;
//        String[] ageSplit = expectedAge.split(" ");
//        String monthFromExpected = ageSplit[2].substring(0, 1);
//        int month = Integer.parseInt(monthFromExpected);
//        int oneLessMonth = month;
//        if (month > 1) {
//            oneLessMonth = month - 1;
//        }
//        String adjustedAge = String.format("%s %s %s", ageSplit[0], ageSplit[1], oneLessMonth + "m)");
//
//        if (actualAge.equals(expectedAge)) {
//            assertThat(messageValidationFormat(field, expectedAge, actualAge), true);
//        } else {
//            assertThat(messageValidationFormat(field, expectedAge, actualAge), actualAge.equals(adjustedAge));
//        }
//    }
//
//    public static void validateSizeEquals(String field, int expected, int actual) {
//        assertThat(messageValidationFormat(field, "" + expected, "" + actual), actual == expected);
//    }
//
//    public static void validateDoesNotEquals(String field, String expected, String actual) {
//        assertThat(messageValidationFormat(field, expected, actual), !actual.equals(expected));
//    }
//
//    public static void validateObjectEquals(String field, Object expected, Object actual) {
//        assertThat(messageValidationFormat(field, expected.toString(), actual.toString()), actual.equals(expected));
//    }
//
//    public static void validateContains(String field, String expected, String actual) {
//        boolean result = expected.contains(actual);
//        assertThat(messageValidationFormat(field, expected, actual), result);
//    }
//
//    public static void anyMatchContains(String field, List<String> validationList, String actual) {
//        boolean result = validationList.stream().anyMatch(s -> s.contains(actual));
//        assertThat(messageValidationFormat(field, validationList.toString(), actual), result);
//    }
//
//    public static void validateNotNull(String field, String value) {
//        assertThat(messageValidationFormatNull(field, value), !Objects.isNull(value));
//    }
//
//    public static String messageValidationFormat(String field, String expected, String actual) {
//        return String.format("Failed to validate '%s' expected '%s' got '%s'", field, expected, actual);
//    }
//
//    public static String messageValidationFormatNull(String field, String value) {
//        return String.format("Failed to validate '%s' is null actual value is '%s'", field, value);
//    }
//
//    public static void validateInputSkipIfEmpty(Element textBox, String data) {
//        if (!skipIfEmpty(data)) {
//            textBox.validateInputText(data);
//        }
//    }
//
//    public static void validateLabelSkipIfEmpty(Element dropdown, String data) {
//        if (!skipIfEmpty(data)) {
//            dropdown.andContainsText(data);
//        }
//    }
//
//    private static boolean skipIfEmpty(String value) {
//        return value.length() < 1;
//    }
//}
