package com.dodol.excel;

import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Component;

@Component
public class ExcelUtil {

	public CellStyle[]  excelCellStyle(XSSFWorkbook workbook) {
		CellStyle[] cellStyleArray = new CellStyle[3];
		
		//Font
    	Font fontHeader = workbook.createFont();
    	fontHeader.setFontName("맑은 고딕");	//글씨체
    	fontHeader.setFontHeight((short)(9 * 20));	//사이즈
    	fontHeader.setBold(true);	//볼드(굵게)
    	Font font9 = workbook.createFont();
    	font9.setFontName("맑은 고딕");	//글씨체
    	font9.setFontHeight((short)(9 * 20));	//사이즈
    	// 엑셀 헤더 셋팅
    	CellStyle headerStyle = workbook.createCellStyle();
    	headerStyle.setAlignment(HorizontalAlignment.CENTER);
    	headerStyle.setVerticalAlignment(VerticalAlignment.CENTER);
    	headerStyle.setBorderRight(BorderStyle.THIN);
    	headerStyle.setBorderLeft(BorderStyle.THIN);
    	headerStyle.setBorderTop(BorderStyle.THIN);
    	headerStyle.setBorderBottom(BorderStyle.THIN);
    	headerStyle.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.index); // HSSFColor.GREY_25_PERCENT.index
    	headerStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND); // HSSFCellStyle.SOLID_FOREGROUND
    	headerStyle.setFont(fontHeader);
    	// 엑셀 바디 셋팅
    	CellStyle bodyStyle = workbook.createCellStyle();
    	bodyStyle.setAlignment(HorizontalAlignment.CENTER);
    	bodyStyle.setVerticalAlignment(VerticalAlignment.CENTER);
    	bodyStyle.setBorderRight(BorderStyle.THIN);
    	bodyStyle.setBorderLeft(BorderStyle.THIN);
    	bodyStyle.setBorderTop(BorderStyle.THIN);
    	bodyStyle.setBorderBottom(BorderStyle.THIN);
    	bodyStyle.setFont(font9);
    	// 엑셀 왼쪽 설정
    	CellStyle leftStyle = workbook.createCellStyle();
    	leftStyle.setAlignment(HorizontalAlignment.LEFT);
    	leftStyle.setVerticalAlignment(VerticalAlignment.CENTER);
    	leftStyle.setBorderRight(BorderStyle.THIN);
    	leftStyle.setBorderLeft(BorderStyle.THIN);
    	leftStyle.setBorderTop(BorderStyle.THIN);
    	leftStyle.setBorderBottom(BorderStyle.THIN);
    	leftStyle.setFont(font9);
    	
    	cellStyleArray[0] = headerStyle;
    	cellStyleArray[1] = bodyStyle;
    	cellStyleArray[2] = leftStyle;
    	
    	return cellStyleArray;
	}
}
