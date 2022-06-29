package com.excel;

import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class ExcelDownload {
	
	@Autowired
	public ExcelUtil eUtil;
	
	/**
	 *엑셀데이터
	 * @return
	 */
	public List<Object> getFamilyList() {
    	List<Object> familyList = new ArrayList<Object>();
    	
    	ExcelVO headerVo = ExcelVO.builder()
    			.no("2020-SA")
    			.name("꽥꽥이")
    			.age("3")
    			.address("남아메리카 - 서수원로577번길 263")
    			.build();
    	familyList.add(headerVo);
    	
    	headerVo = ExcelVO.builder()
    			.no("2021-A")
    			.name("딩딩이")
    			.age("2")
    			.address("아프리카 - 서수원로577번길 263")
    			.build();
    	familyList.add(headerVo);
    	
    	headerVo = ExcelVO.builder()
    			.no("2010-K")
    			.name("조가연")
    			.age("13")
    			.address("한국 - 서수원로577번길 263")
    			.build();
    	familyList.add(headerVo);
    	
    	return familyList;
	}
	
	public List<String[]> getHeaderList() {
		/* 엑셀 그리기 */
    	List<String[]> headerList = new ArrayList<>();
    	String[] colNames = {
    			"샤랄라 월드","샤랄라 월드","샤랄라 월드","샤랄라 월드"
    	};
    	headerList.add(colNames);
    	String[] colNames2 = {
    			"No", "개인정보", "개인정보", "개인정보"
    	};
    	headerList.add(colNames2);
    	String[] colNames3 = {
    			"No", "성명", "나이", "거주지"
    	};
    	headerList.add(colNames3);
		
		return headerList;
	}
	
	public int[] getColWidths() {
    	// 헤더 사이즈
    	final int[] colWidths = {
    			3000, 5000, 5000, 10000
    	};
    	
    	return colWidths;
		
	}
	
	/**
	 * 엑셀헤더 생성
	 * 
	 * @param headerList
	 * @param row
	 * @param sheet
	 * @param cell
	 * @param bodyCellStyle
	 * @param colWidths
	 */
	public void createExcelHeader(
			List<String[]> headerList, XSSFRow row, XSSFSheet sheet , XSSFCell cell , CellStyle bodyCellStyle, int[] colWidths ) {

		int rowCnt = 0;
    	for(String[] header :headerList) {
    		row = sheet.createRow(rowCnt++);
    		for (int i = 0; i < header.length; i++) {
    			cell = row.createCell(i);
    			cell.setCellStyle(bodyCellStyle); // headerStyle
    			cell.setCellValue(header[i]);
    			sheet.setColumnWidth(i, colWidths[i]);	//column width 지정
    		}
    	}
	}
	
	/**
	 * 엑셀데이터 생성
	 * 
	 * @param row
	 * @param sheet
	 * @param cell
	 * @param bodyStyle
	 * @param objList
	 * @param dataRowOffset
	 */
	public void generateDataToCell(XSSFRow row, XSSFSheet sheet, XSSFCell cell,
			CellStyle bodyStyle, List<Object> objList, int dataRowOffset) {
		try {
			int rowCnt = dataRowOffset; // 3;			
			for(Object obj :objList) {
				Method[] methods = obj.getClass().getDeclaredMethods();
				row = sheet.createRow(rowCnt++);
				
				for(Method method :methods) {
						if(method.getName().substring(0, 3).equals("get") && !"getClass".equals(method.getName())) {
							int cellOrder = method.getDeclaredAnnotation(CellOrderAnnotation.class).order();
							method.setAccessible(true);
							Object rtnObject = method.invoke(obj);
			        		cell = row.createCell(cellOrder);							
							cell.setCellStyle(bodyStyle); // bodyStyle					
							cell.setCellValue(rtnObject.toString());
						}
				}
			}
		} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
			e.printStackTrace();
		}
	}
	
	public void mergeExcel(XSSFSheet sheet) {
    	/** 엑셀헤더 Merge */
    	sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 3));
    	sheet.addMergedRegion(new CellRangeAddress(1, 2, 0,0));
    	sheet.addMergedRegion(new CellRangeAddress(1, 1, 1, 3));
	}

	public String excelDownload(HttpServletResponse res) {
		/** 1. 엑셀 출력 데이터   */ List<Object> dataList = getFamilyList();
		/** 2. 엑셀 헤더 그리기 */ List<String[]> headerList = getHeaderList(); 
		/** 3. 엑셀 헤더 사이즈 */ int[] cellWidths = getColWidths();
		/** 4. 엑셀데이터 시작 Row number*/ 	int dataRowOffset = 3;
		/** 5. 엑셀 시트명 설정 */ String sheetName = "사용자현황";
		/** 6. 엑셀 파일명 설정 */ String fileName = "꼬꼬여사.xlsx";
		return generateExcel(res, dataList, headerList, cellWidths, dataRowOffset, sheetName, fileName);
	}
	
    public String generateExcel(HttpServletResponse res, List<Object> dataList, List<String[]> headerList,
    		int[] cellWidths, int dataRowOffset,String sheetName, String fileName) {
        try {
        	XSSFWorkbook workbook = new XSSFWorkbook();
        	XSSFSheet sheet = null;
        	XSSFCell cell = null;
        	XSSFRow row = null;
        	CellStyle[] cellStyleArray = eUtil.excelCellStyle(workbook);
        	
        	/** 엑셀 시트명 설정 */ sheet = workbook.createSheet(sheetName);
        	/** 엑셀헤더 생성 */ createExcelHeader(headerList, row, sheet, cell, cellStyleArray[0], cellWidths );
        	/** 엑셀헤더 Merge */ mergeExcel(sheet);
        	/** 엑셀데이터  생성 */ generateDataToCell(row, sheet, cell, cellStyleArray[1], (List<Object>)dataList, dataRowOffset); 

        	res.setContentType("application/vnd.ms-excel");
        	String outputFileName = new String(fileName.getBytes("KSC5601"), "8859_1");
        	res.setHeader("Set-Cookie", "fileDownload=true; path=/");
        	res.setHeader("Content-Disposition", "attachment; fileName=\"" + outputFileName + "\"");
        	
        	res.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        	workbook.write(res.getOutputStream());
        	res.getOutputStream().close();
        	
        } catch(IOException ex) {
        	ex.printStackTrace();
        }

        return "다운로드 완료";
    }

}

////rows
//int rowCnt = 0;
//// 엑셀 헤더 생성
//for(String[] header :headerList) {
//	row = sheet.createRow(rowCnt++);
//	for (int i = 0; i < header.length; i++) {
//		cell = row.createCell(i);
//		cell.setCellStyle(cellStyleArray[0]); // headerStyle
//		cell.setCellValue(header[i]);
//		sheet.setColumnWidth(i, colWidths[i]);	//column width 지정
//	}
//}
//
//sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 3));
//sheet.addMergedRegion(new CellRangeAddress(1, 2, 0,0));
//sheet.addMergedRegion(new CellRangeAddress(1, 1, 1, 3));

//Field[] fields = obj.getClass().getDeclaredFields();
//
//for(Field field : fields) {
//	System.out.println("Field Name : " + field.getName());
//	if(!"serialVersionUID".equals(field.getName())) {
//		System.out.println("Field : " +field.getAnnotation(CellOrderAnnotation2.class).order());
//		field.setAccessible(true);
//	}
//}


