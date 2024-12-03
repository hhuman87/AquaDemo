package dbTests;

import forAllTests.utils.ExcelUtils;
import org.junit.Test;

public class ReadExcel {

    @Test
    public void readDataFromExcel() {
        String excelPath = "./ExcelTestData/Test1Data.xlsx";
        String sheetName = "Users";

        ExcelUtils excel = new ExcelUtils(excelPath,sheetName);

        excel.getRowCount();
        excel.getCellValue(1,0);
    }

}
