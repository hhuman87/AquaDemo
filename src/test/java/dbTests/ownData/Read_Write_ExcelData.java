package dbTests.ownData;

import forAllTests.utils.ExcelUtils;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Arrays;

public class Read_Write_ExcelData {

    private final String excelPath = "./ExcelTestData/Test1Data.xlsx";
    private final String sheetName = "Users";

    @Test
    public void readCellDataFromExcel() {
        ExcelUtils excel = new ExcelUtils(excelPath,sheetName);

        excel.getRowCount();
        excel.getColumnCount();
        Object name = excel.getCellValue(1,0);
        Object userName = excel.getCellValue(1,1);
        Object password = excel.getCellValue(1,2);

        Assertions.assertEquals("Name1",name);
        Assertions.assertEquals("UserName1",userName);
        Assertions.assertEquals("Password1",password);
    }

    @Test
    public void readAllDataFromExcel() {
        try {
            ExcelUtils excel = new ExcelUtils(excelPath,sheetName);
            FileInputStream inputStream = new FileInputStream(excelPath);
            XSSFWorkbook workbook = new XSSFWorkbook(inputStream);
            XSSFSheet sheet = workbook.getSheet(sheetName);
            int rowCount = excel.getRowCount();
            int colCount = excel.getColumnCount();

            for(int i=0; i<rowCount; i++) {
                for(int j=0; j<colCount;j++) {
                    String values = sheet.getRow(i).getCell(j).getStringCellValue();
                    System.out.print(values+" ");
                }
                System.out.println();
                workbook.close();
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.out.println(Arrays.toString(e.getStackTrace()));
        }
    }

    @Test
    public void writeCellDataInExcel() throws IOException {
        ExcelUtils excel = new ExcelUtils(excelPath,sheetName);
        FileInputStream inputStream = new FileInputStream(excelPath);
        XSSFWorkbook workbook = new XSSFWorkbook(inputStream);
        XSSFSheet sheet = workbook.getSheet(sheetName);


    }

    @Test
    public void createExcelWorkBookWithData() throws IOException {
        XSSFWorkbook workbook = new XSSFWorkbook();
        XSSFSheet sheet = workbook.createSheet("Members");

        Object[][] excelData =
                {
                    {"FirstName","LastName","Age"},
                    {"John","James",25},
                    {"Vicky","Van",31},
                    {"Ben","Body",14},
                };

        int rows = excelData.length;
        int columns = excelData[0].length;

        for(int r=0; r<rows; r++) {
            XSSFRow row = sheet.createRow(r);
            for(int c=0; c<columns;  c++) {
                XSSFCell cell = row.createCell(c);
                Object value = excelData[r][c];

                if(value instanceof String)
                    cell.setCellValue((String)value);
                if(value instanceof Integer)
                    cell.setCellValue((Integer)value);
                if(value instanceof Boolean)
                    cell.setCellValue((Boolean) value);
            }
        }
        String filePath = "./ExcelTestData/Test2Data.xlsx";
        FileOutputStream outputStream = new FileOutputStream(filePath);
        workbook.write(outputStream);
        outputStream.close();
    }

}
