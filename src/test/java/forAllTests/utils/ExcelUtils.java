package forAllTests.utils;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.Test;

import java.io.IOException;
import java.util.Arrays;

public class ExcelUtils {

    private static final Logger log = LogManager.getLogger(ExcelUtils.class);

    @Test
    public void getRowCount() {

        try {
            String excelPath = "./ExcelTestData/Test1Data.xlsx";
            XSSFWorkbook workbook = new XSSFWorkbook(excelPath);
            XSSFSheet sheet = workbook.getSheet("Users");

            int rowCount = sheet.getPhysicalNumberOfRows();
            System.out.println("No of rows: " + rowCount);



        } catch (Exception e) {
            System.out.println(e.getMessage());
            log.error(String.valueOf(e.getCause()));
            System.out.println(Arrays.toString(e.getStackTrace()));
        }
    }
}
