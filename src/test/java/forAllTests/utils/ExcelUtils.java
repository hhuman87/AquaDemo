package forAllTests.utils;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.util.Arrays;

public class ExcelUtils {

    private static final Logger log = LogManager.getLogger(ExcelUtils.class);

    static XSSFWorkbook workbook;
    static XSSFSheet sheet;

    public ExcelUtils(String excelPath, String sheetName) {
        try {
            workbook = new XSSFWorkbook(excelPath);
            sheet = workbook.getSheet("Users");
        } catch (Exception e) {
            System.out.println(e.getMessage());
            log.error(String.valueOf(e.getCause()));
            System.out.println(Arrays.toString(e.getStackTrace()));
        }
    }

    public void getRowCount() {
        int rowCount = sheet.getPhysicalNumberOfRows();
        System.out.println("No of rows: " + rowCount);
    }

    public Object getCellValue(int rowNum, int colNum) {
        DataFormatter formatter = new DataFormatter();
        Object cellValue = formatter.formatCellValue(sheet.getRow(rowNum).getCell(colNum));
        System.out.println("The cell value is: " + cellValue);
        return cellValue;
    }
}
