package com.saucedemo.qa.utils;

import org.apache.poi.ss.usermodel.*;

import java.io.FileInputStream;
import java.io.FileOutputStream;

public class ExcelUtils {
    private static final String EXCEL_FILE_PATH = "path/to/your/excel/file.xlsx";

    public static Object[][] readTestData(String sheetName) {
        Object[][] testData = null;
        try {
            Workbook workbook = WorkbookFactory.create(new FileInputStream(EXCEL_FILE_PATH));
            Sheet sheet = workbook.getSheet(sheetName);
            int rowCount = sheet.getLastRowNum();
            int colCount = sheet.getRow(0).getLastCellNum();
            testData = new Object[rowCount][colCount];
            for (int i = 0; i < rowCount; i++) {
                Row row = sheet.getRow(i + 1);
                for (int j = 0; j < colCount; j++) {
                    Cell cell = row.getCell(j, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);
                    testData[i][j] = getCellValue(cell);
                }
            }
            workbook.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return testData;
    }

    private static Object getCellValue(Cell cell) {
        Object cellValue = null;
        switch (cell.getCellType()) {
            case STRING:
                cellValue = cell.getStringCellValue();
                break;
            case NUMERIC:
                if (DateUtil.isCellDateFormatted(cell)) {
                    cellValue = cell.getDateCellValue();
                } else {
                    cellValue = cell.getNumericCellValue();
                }
                break;
            case BOOLEAN:
                cellValue = cell.getBooleanCellValue();
                break;
            case FORMULA:
                cellValue = cell.getCellFormula();
                break;
            default:
                cellValue = "";
        }
        return cellValue;
    }

    public static void writeTestData(String sheetName, Object[][] data) {
        try {
            Workbook workbook = WorkbookFactory.create(new FileInputStream(EXCEL_FILE_PATH));
            Sheet sheet = workbook.getSheet(sheetName);
            int rowCount = data.length;
            int colCount = data[0].length;
            for (int i = 0; i < rowCount; i++) {
                Row row = sheet.createRow(i + 1);
                for (int j = 0; j < colCount; j++) {
                    Cell cell = row.createCell(j);
                    setCellValue(cell, data[i][j]);
                }
            }
            FileOutputStream outputStream = new FileOutputStream(EXCEL_FILE_PATH);
            workbook.write(outputStream);
            workbook.close();
            outputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
    private static void setCellValue(Cell cell, Object value) {
        if (value instanceof String) {
            cell.setCellValue((String) value);
        } else if (value instanceof Double) {
            cell.setCellValue((Double) value);
        } else if (value instanceof Boolean) {
            cell.setCellValue((Boolean) value);
        } else if (value instanceof java.util.Date) {
            cell.setCellValue((java.util.Date) value);
        } else {
            cell.setCellValue(value.toString());
        }
    }

    public static int getRowCount(String sheetName) {
        int rowCount = 0;
        try {
            Workbook workbook = WorkbookFactory.create(new FileInputStream(EXCEL_FILE_PATH));
            Sheet sheet = workbook.getSheet(sheetName);
            rowCount = sheet.getLastRowNum();
            workbook.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return rowCount;
    }
    public static int getColumnCount(String sheetName) {
        int colCount = 0;
        try {
            Workbook workbook = WorkbookFactory.create(new FileInputStream(EXCEL_FILE_PATH));
            Sheet sheet = workbook.getSheet(sheetName);
            if (sheet.getRow(0) != null) {
                colCount = sheet.getRow(0).getLastCellNum();
            }
            workbook.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return colCount;

    }

    public static Object getCellValue(String sheetName, int rowNum, int colNum) {
        Object cellValue = null;
        try {
            Workbook workbook = WorkbookFactory.create(new FileInputStream(EXCEL_FILE_PATH));
            Sheet sheet = workbook.getSheet(sheetName);
            Row row = sheet.getRow(rowNum);
            if (row != null) {
                Cell cell = row.getCell(colNum, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);
                cellValue = getCellValue(cell);
            }
            workbook.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return cellValue;
    }

    public static void setCellValue(String sheetName, int rowNum, int colNum, Object value) {
        try {
            Workbook workbook = WorkbookFactory.create(new FileInputStream(EXCEL_FILE_PATH));
            Sheet sheet = workbook.getSheet(sheetName);
            Row row = sheet.getRow(rowNum);
            if (row == null) {
                row = sheet.createRow(rowNum);
            }
            Cell cell = row.createCell(colNum);
            setCellValue(cell, value);
            FileOutputStream outputStream = new FileOutputStream(EXCEL_FILE_PATH);
            workbook.write(outputStream);
            workbook.close();
            outputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
