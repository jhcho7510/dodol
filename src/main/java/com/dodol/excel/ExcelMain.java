package com.dodol.excel;

import java.io.FileNotFoundException;
import java.io.IOException;

public class ExcelMain {
    public static void main(String[] args) throws FileNotFoundException, IOException {

        /*
        List<Map<String, Object>> datas = new ArrayList<>();

        Map<String, Object> data1 = new HashMap<>();
        data1.put("id", 1);
        data1.put("name", "kim");

        Map<String, Object> data2 = new HashMap<>();
        data2.put("id", 2);
        data2.put("name", "park");

        datas.add(data1);
        datas.add(data2);

        Excel excelUtil = new Excel();

        // String filepath = "D:/data.xlsx";
        String filepath = "/Users/junhyoungcho/data.xlsx";
        excelUtil.createExcelToFile(datas, filepath);
    */
        ExcelDownload excelDownload = new ExcelDownload();

        // excelDownload.excelDownload();
    }

}
