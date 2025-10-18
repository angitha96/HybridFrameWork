package utilities;

import org.testng.annotations.DataProvider;

import java.io.IOException;

public class DataProviderUtil {

    @DataProvider(name = "LoginData")
    public String [][] getData() throws IOException {

        String path=".\\testdata\\datadrivenlogin.xlsx";
        ExcelReader xlread= new ExcelReader(path);

        int totalrows= xlread.getRows("Sheet1");
        int totalcols= xlread.getCells("Sheet1",1);

        //create 2d array matching no of rows and cols in excel used
        String logindata[][]=new String[totalrows][totalcols];
         for(int i=1;i<=totalrows;i++){
             for (int j=0;j<totalcols;j++){
               logindata[i-1][j]= xlread.getCellData("Sheet1",i, j); // array index starts from so i-1
             }
         }
         return logindata;

    }
}
