package utilities;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelReader {

        public static FileInputStream fi;
        public static FileOutputStream fo;
        public static XSSFWorkbook wb;
        public static XSSFSheet ws;
        public static XSSFRow row;
        public static XSSFCell cell;
        public static CellStyle style;
        String path;

        public ExcelReader(String path){

            this.path=path;
        }

        public int getRows(String xlsheet) throws IOException {
            fi = new FileInputStream(path);
            wb = new XSSFWorkbook(fi);
            ws = wb.getSheet(xlsheet);
            int rows = ws.getLastRowNum();
            fi.close();
            wb.close();
            return rows;
        }

        public int getCells(String xlsheet, int rowno) throws IOException {
            fi = new FileInputStream(path);
            wb = new XSSFWorkbook(fi);
            ws = wb.getSheet(xlsheet);
            row = ws.getRow(rowno);
            int cells = row.getLastCellNum();
            fi.close();
            wb.close();
            return cells;
        }

        public  String getCellData(String xlsheet, int rowno, int cellno) throws IOException {
            fi = new FileInputStream(path);
            wb = new XSSFWorkbook(fi);
            ws = wb.getSheet(xlsheet);
            row = ws.getRow(rowno);
            cell = row.getCell(cellno);
            String data;
            try {
//			data = cell.toString();
                DataFormatter formatter = new DataFormatter();
                data = formatter.formatCellValue(cell);  		// alternative method to get cell data in string
            }
            catch(Exception e) {
                data = "";
            }
            finally {
                fi.close();
                wb.close();
            }
            return data;
        }

        public void setCellData(String xlsheet, int rowno, int cellno, String data) throws IOException {
            File file= new File(path);
            if(!file.exists()){
                wb=new XSSFWorkbook();
                fo=new FileOutputStream(path);
                wb.write(fo);
            }
            fi=new FileInputStream(path);
            wb = new XSSFWorkbook(fi);
            if(wb.getSheetIndex(xlsheet)==-1)
                wb.createSheet(xlsheet);
            ws = wb.getSheet(xlsheet);

            if(ws.getRow(rowno)==null)
                ws.createRow(rowno);
            row = ws.getRow(rowno);

            cell = row.createCell(cellno);
            cell.setCellValue(data);
            fo = new FileOutputStream(path);
            wb.write(fo);
            wb.close();
            fo.close();
            fi.close();
        }

        public void fillGreenColor(String xlsheet, int rowno, int cellno) throws IOException {
            fi = new FileInputStream(path);
            wb = new XSSFWorkbook(fi);
            ws = wb.getSheet(xlsheet);
            row = ws.getRow(rowno);
            cell = row.getCell(cellno);
            style = wb.createCellStyle();

            style.setFillForegroundColor(IndexedColors.GREEN.getIndex());
            style.setFillPattern(FillPatternType.SOLID_FOREGROUND);

            cell.setCellStyle(style);
            fo = new FileOutputStream(path);
            wb.write(fo);
            wb.close();
            fi.close();
            fo.close();
        }

        public void fillRedColor(String xlsheet, int rowno, int cellno) throws IOException {
            fi = new FileInputStream(path);
            wb = new XSSFWorkbook(fi);
            ws = wb.getSheet(xlsheet);
            row = ws.getRow(rowno);
            cell = row.getCell(cellno);
            style = wb.createCellStyle();

            style.setFillForegroundColor(IndexedColors.RED.getIndex());
            style.setFillPattern(FillPatternType.SOLID_FOREGROUND);

            cell.setCellStyle(style);
            fo = new FileOutputStream(path);
            wb.write(fo);
            wb.close();
            fi.close();
            fo.close();
        }

    }


