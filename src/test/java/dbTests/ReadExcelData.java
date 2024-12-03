package dbTests;

import forAllTests.utils.ExcelUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class ReadExcelData {

    @Test
    public void readDataFromExcel() {
        String excelPath = "./ExcelTestData/Test1Data.xlsx";
        String sheetName = "Users";

        ExcelUtils excel = new ExcelUtils(excelPath,sheetName);

        excel.getRowCount();
        Object name = excel.getCellValue(1,0);
        Object userName = excel.getCellValue(1,1);
        Object password = excel.getCellValue(1,2);

        Assertions.assertEquals("Name1",name);
        Assertions.assertEquals("UserName1",userName);
        Assertions.assertEquals("Password1",password);
    }

}
