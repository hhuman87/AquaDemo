//package forAllTests.helpers;
//
//import java.text.ParseException;
//import java.text.SimpleDateFormat;
//import java.time.LocalDateTime;
//import java.time.ZoneId;
//import java.time.ZonedDateTime;
//import java.time.format.DateTimeFormatter;
//import java.util.Date;
//
//import static org.hamcrest.MatcherAssert.assertThat;
//
///**
// * TimeZoneHelper method converts whatever Date and / or Time you provide it with, to the current system time.
// * e.g. It can convert South African Time to Irish time.
// * <p>
// * You must provide it with your starting datetime, the format that time is in (e.g. "yyyy-MM-dd HH:mm") and the TimeZoneID of that time.
// * <p>
// * References:
// * <li>List of all TimeZoneIDs: https://mkyong.com/java/java-display-list-of-timezone-with-gmt/
// * <li>List of DateTime Format Patterns: https://docs.oracle.com/javase/8/docs/api/java/time/format/DateTimeFormatter.html
// * <li>Converting Date Object to LocalDateTime Objects: http://blog.progs.be/542/date-to-java-time
// * <li>Converting DateTimes between Time Zones: https://mkyong.com/java/java-convert-date-and-time-between-timezone/
// */
//public class TimeZoneHelper {
//    public static final String TIMEZONE_AFRICA_JOHANNESBURG = "Africa/Johannesburg";
//    public static final String TIMEZONE_DUBLIN = "Europe/Dublin";
//    public static final String TIMEZONE_UTC = "UTC";
//
//    private String userDateTime = null;
//    private String userTimeZone = null;
//    private String dateTimeFormat = null;
//
//    /**
//     * <li>TimeZoneHelper("05:15", "HH:mm", TimeZoneHelper.TIMEZONE_AFRICA_JOHANNESBURG); //Just using Time
//     * <li>TimeZoneHelper("2020-09-07","yyyy-MM-dd", TimeZoneHelper.TIMEZONE_AFRICA_JOHANNESBURG); //Just using Date
//     * <li>TimeZoneHelper("2020-09-07 05:15","yyyy-MM-dd HH:mm", TimeZoneHelper.TIMEZONE_AFRICA_JOHANNESBURG); //Just using Date and Time
//     */
//    public TimeZoneHelper(String dateTime, String dateTimeFormat, String TIMEZONEID) {
//        this.userTimeZone = TIMEZONEID;
//        this.dateTimeFormat = dateTimeFormat;
//        this.userDateTime = dateTime;
//    }
//
//    /**
//     * This method can create a LocalDateTime object using just date or time or datetime as we make use of the Date object.
//     */
//    private LocalDateTime getLocalDateTimeUsingDateObj() {
//        LocalDateTime ldt = null;
//        try {
//            Date date = new SimpleDateFormat(dateTimeFormat).parse(userDateTime);
//            ldt = LocalDateTime.ofInstant(date.toInstant(), ZoneId.of(userTimeZone));
//        } catch (ParseException e) {
//            e.printStackTrace();
//            assertThat("Failed to getLocalDateTimeFromDateTime - " + e.getMessage(), false);
//        }
//        return ldt;
//    }
//    // </editor-fold>
//
//    private LocalDateTime getLocalDateTimeUsingDateTimeObj() {
//        LocalDateTime ldt = null;
//        try {
//            ldt = LocalDateTime.parse(userDateTime, DateTimeFormatter.ofPattern(dateTimeFormat));
//        } catch (Exception e) {
//            assertThat("Failed to getLocalDateTimeFromDateTime - " + e.getMessage(), false);
//        }
//        return ldt;
//    }
//
//    /**
//     * You must provide a date and time for this to work currently.
//     */
//    public String getRelativeDateTime() {
//        System.out.println("\n****************************");
//        System.out.println("\nStarting getRelativeDateTime()");
//
//        ZoneId zoneId1 = ZoneId.of(userTimeZone);
//        System.out.println("TimeZoneId1 : " + zoneId1);
//
//        //LocalDateTime + ZoneId = ZonedDateTime
//        ZonedDateTime zonedDateTime1 = getLocalDateTimeUsingDateTimeObj().atZone(zoneId1);
//        System.out.println("zonedDateTime1 : " + zonedDateTime1);
//
//        //Currently, tests will only be running between Johannesburg and Dublin.
//        String otherTimeZoneID = (userTimeZone.toLowerCase().contains("johannesburg")) ? TIMEZONE_DUBLIN : TIMEZONE_AFRICA_JOHANNESBURG;
//        ZoneId zoneId2 = ZoneId.of(otherTimeZoneID);
//        System.out.println("zoneId2 : " + zoneId2);
//
//        ZonedDateTime zonedDateTime2 = zonedDateTime1.withZoneSameInstant(zoneId2);
//        System.out.println("zonedDateTime2 : " + zonedDateTime2);
//
//        //Should the system time not be Johannesburg or Dublin, convert the time to the current system timezone.
//        ZoneId systemZoneID = ZoneId.systemDefault();
//        if (!systemZoneID.equals(zoneId2)) {
//            zonedDateTime2 = zonedDateTime1.withZoneSameInstant(systemZoneID);
//            System.out.println("Corrected Date (" + systemZoneID + ") : " + zonedDateTime2);
//        }
//
//        DateTimeFormatter format = DateTimeFormatter.ofPattern(dateTimeFormat);
//        System.out.println("\n---DateTimeFormatter---");
//        System.out.println("Input (" + userTimeZone + ") : " + userDateTime + " " + dateTimeFormat);
//        System.out.println("Date (" + zoneId1 + ") : " + format.format(zonedDateTime1));
//        System.out.println("Date (" + systemZoneID + ") : " + format.format(zonedDateTime2));
//
//        System.out.println("\nEnding getRelativeDateTime()");
//        System.out.println("\n****************************");
//        return format.format(zonedDateTime2);
//    }
//}

