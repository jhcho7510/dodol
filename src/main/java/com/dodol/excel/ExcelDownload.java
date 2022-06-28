package com.dodol.excel;

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
    			.address("아시아 - 서수원로577번길 263")
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
	
	public void generateDataToCell(XSSFRow row, XSSFSheet sheet, XSSFCell cell,
		CellStyle bodyStyle, List<Object> objList) {

			int rowCnt = 3;			
			for(Object obj :objList) {
				Method[] methods = obj.getClass().getDeclaredMethods();
				

				row = sheet.createRow(rowCnt++);
				int cellCnt = 0; 
				
				
				
				
				Field[] fields = obj.getClass().getDeclaredFields();
				
				for(Field field : fields) {
					System.out.println("Field Name : " + field.getName());
					if(!"serialVersionUID".equals(field.getName())) {
						System.out.println("Field : " +field.getAnnotation(CellOrderAnnotation2.class).order());
						field.setAccessible(true);
						
					}
				}
				
				for(Method method :methods) {
					try {
						if(method.getName().substring(0, 3).equals("get") && !"getClass".equals(method.getName())) {
							
							
//							System.out.println("method.getName() --->"+method.getName());
							//method = obj.getClass().getDeclaredMethod(method.getName());
							int cellOrder = method.getDeclaredAnnotation(CellOrderAnnotation.class).order();
							method.setAccessible(true);
							Object rtnObject = method.invoke(obj);
			        		cell = row.createCell(cellOrder);							
//			        		cell = row.createCell(cellCnt++);							
							cell.setCellStyle(bodyStyle); // bodyStyle					
							cell.setCellValue(rtnObject.toString());
							
						}
						
						
//					} catch (NoSuchMethodException | SecurityException e) {
//						// TODO Auto-generated catch block
//						e.printStackTrace();
					} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				
//				Method[] methodArray = obj.getClass().getDeclaredMethods();
				
//				System.out.println("methodArray ---->" + methodArray[idx].getName());
				
//				idx++;
				
//				int cellCnt = 0;
//				int rowCnt = 0;
//				for(int i=0;i<methodArray.length;i++) {
//					cellCnt = 0;
//					row = sheet.createRow(rowCnt++);
//					cell.setCellStyle(bodyStyle); // bodyStyle
//					System.out.println("methodArray[i].getName() ---->" + methodArray[i].getName());
//					cell.setCellValue(methodArray[i].);
//				}
			}
//		} catch (NoSuchMethodException | SecurityException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
			
			
//			//데이터 부분 생성
//			for(ExcelVO vo : familyList) {
//				// 넘버링
//				cell = row.createCell(cellCnt++);
//				cell.setCellValue(listCount--);
//				// 성명
//				cell = row.createCell(cellCnt++);
//				cell.setCellStyle(bodyStyle);
//				cell.setCellValue(vo.getName());
//				// 나이
//				cell = row.createCell(cellCnt++);
//				cell.setCellStyle(bodyStyle);
//				cell.setCellValue(vo.getAge());
//				
//				// 주소
//				cell = row.createCell(cellCnt++);
//				cell.setCellStyle(bodyStyle);
//				cell.setCellValue(vo.getAddress());
//			}
			
//		});
		
		
		
	}

    public String excelDownload(HttpServletResponse res) {
        try {

        	/** 1. 엑셀 출력 데이터   */
        	List<Object> familyList = getFamilyList(); 
        	
        	/** 2. 엑셀 헤더 그리기 */
        	List<String[]> headerList = getHeaderList(); 
        	
        	/** 3. 엑셀 헤더 사이즈 */
        	int[] colWidths = getColWidths();
        	
        	XSSFWorkbook workbook = new XSSFWorkbook();
        	XSSFSheet sheet = null;
        	XSSFCell cell = null;
        	XSSFRow row = null;
        	CellStyle[] cellStyleArray = eUtil.excelCellStyle(workbook);
        	//rows
        	int rowCnt = 0;
        	int cellCnt = 0;
        	int listCount = familyList.size();
        	
        	// 엑셀 시트명 설정
        	sheet = workbook.createSheet("사용자현황");
        	
        	//헤더 정보 구성
        	for(String[] header :headerList) {
        		row = sheet.createRow(rowCnt++);
        		for (int i = 0; i < header.length; i++) {
        			cell = row.createCell(i);
        			cell.setCellStyle(cellStyleArray[0]); // headerStyle
        			cell.setCellValue(header[i]);
        			sheet.setColumnWidth(i, colWidths[i]);	//column width 지정
        		}
        	}
        	
        	sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 3));
        	sheet.addMergedRegion(new CellRangeAddress(1, 2, 0,0));
        	sheet.addMergedRegion(new CellRangeAddress(1, 1, 1, 3));
        	
        	//데이터 부분 생성
//        	//데이터 부분 생성
//        	for(ExcelVO vo : familyList) {
//        		cellCnt = 0;
//        		row = sheet.createRow(rowCnt++);
//        		// 넘버링
//        		cell = row.createCell(cellCnt++);
//        		cell.setCellStyle(cellStyleArray[1]); // bodyStyle
//        		cell.setCellValue(listCount--);
//        		// 성명
//        		cell = row.createCell(cellCnt++);
//        		cell.setCellStyle(cellStyleArray[1]);
//        		cell.setCellValue(vo.getName());
//        		// 나이
//        		cell = row.createCell(cellCnt++);
//        		cell.setCellStyle(cellStyleArray[1]);
//        		cell.setCellValue(vo.getAge());
//        		
//        		// 주소
//        		cell = row.createCell(cellCnt++);
//        		cell.setCellStyle(cellStyleArray[1]);
//        		cell.setCellValue(vo.getAddress());
//        	}
        	
        	generateDataToCell(row, sheet, cell, cellStyleArray[1], (List<Object>)familyList); 
        	
        	// 엑셀 파일명 설정
        	String fileName = "꼬꼬여사.xlsx";
        	res.setContentType("application/vnd.ms-excel");
        	String outputFileName = new String(fileName.getBytes("KSC5601"), "8859_1");
        	res.setHeader("Set-Cookie", "fileDownload=true; path=/");
        	res.setHeader("Content-Disposition", "attachment; fileName=\"" + outputFileName + "\"");
        	
        	res.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        	workbook.write(res.getOutputStream());
        	res.getOutputStream().close();
        	
        } catch(IOException ex) {
        	
        }

        return "다운로드 완료";
    }

}
