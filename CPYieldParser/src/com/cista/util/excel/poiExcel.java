package com.cista.util.excel;

import java.util.*;

//POI
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFCell;

public class poiExcel {
    public int getColumnCount(HSSFSheet sheet) {
        HSSFRow row = null;
        int columnCount = 0;

        columnCount = 0;
        if (sheet == null) {
            return 0;
        } else {
            if (sheet.getLastRowNum() > 0) {
                Iterator iterator = null;

                iterator = sheet.rowIterator();
                while (iterator.hasNext()) {
                    row = (HSSFRow) iterator.next();
                    if (row.getLastCellNum() > columnCount) {
                        columnCount = row.getLastCellNum() + 1;
                    }

                }
            }
            return columnCount;
        }

    }

    public int getRowCount(HSSFSheet sheet) {
        if (sheet == null) {
            return 0;
        } else {
            return sheet.getLastRowNum() + 1;
        }

    }

    public String getColumnName(int col) {
        Character character = null;

        character = new Character((char) ('A' + col));
        return character.toString();
    }

    public Object getValueAt(HSSFSheet sheet, int rowNum, int col) {
        HSSFRow row = null;
        HSSFCell cell = null;
        Object value = null;

        value = "";
        if (sheet != null) {
            row = sheet.getRow(rowNum);
            if (row != null) {
                cell = row.getCell((short) col);
                if (cell != null) {
                    if (cell.getCellType() == HSSFCell.CELL_TYPE_BLANK) {
                        value = "";
                    } else if (cell.getCellType() == HSSFCell.CELL_TYPE_BOOLEAN) {
                        value = "" + cell.getBooleanCellValue();
                    } else if (cell.getCellType() == HSSFCell.CELL_TYPE_ERROR) {
                        value = "ERROR";
                    } else if (cell.getCellType() == HSSFCell.CELL_TYPE_FORMULA) {
                        value = "" + cell.getNumericCellValue();
                    } else if (cell.getCellType() == HSSFCell.CELL_TYPE_NUMERIC) {
                        value = "" + cell.getNumericCellValue();
                    } else if (cell.getCellType() == HSSFCell.CELL_TYPE_STRING) {
                        value = cell.getStringCellValue();
                    }
                }
            }
        }

        return value;
    }
}
