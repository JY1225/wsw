package com.sws.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.xssf.streaming.SXSSFCell;
import org.apache.poi.xssf.streaming.SXSSFRow;
import org.apache.poi.xssf.streaming.SXSSFSheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.sws.base.Log;
import com.sws.base.SwsMessageDialog;

public class ExcelUtil {

	
	//导出excel2007
	public static<T> void exportExcel(List<String[]> list, List<String> cells, String fileName, String dialogTitle){
		
		//第一步，创建一个webbook,对应一个excel文件
		XSSFWorkbook workbook = new XSSFWorkbook();
		SXSSFWorkbook wb = new SXSSFWorkbook(workbook, 100);//默认100
		excelBase(workbook, wb, list, cells, fileName, dialogTitle);
		/*SXSSFSheet sheet = wb.createSheet("外廓尺寸");
		//第二步，在webbook中添加一个sheet,对应Excel文件中sheet
		//HSSFSheet sheet = wb.createSheet();
		//第三步，在sheet中添加表头第0行，注意老版本poi对Excel的行数列数有限制short
		SXSSFRow row = sheet.createRow(0);
		//第四步，创建单元格，并设置值表头，设置表头居中
		XSSFCellStyle style = workbook.createCellStyle();
		//设置字体
		XSSFFont headfont = workbook.createFont();
		headfont.setFontName("宋体");
		headfont.setFontHeightInPoints((short)18);//字体大小
		headfont.setBold(true);//加粗
		
		//设置列头格式
		XSSFCellStyle colsStyle = workbook.createCellStyle();
		colsStyle.setAlignment(HorizontalAlignment.CENTER);
		//设置列头字体
		XSSFFont colsfont = workbook.createFont();
		colsfont.setFontName("宋体");
		colsfont.setFontHeightInPoints((short)11);
		colsfont.setBold(true);
		colsStyle.setFont(colsfont);
		
		//设置单元格格式
		XSSFCellStyle cellStyle = workbook.createCellStyle();
		cellStyle.setAlignment(HorizontalAlignment.CENTER);

		//设置单元格字体
		XSSFFont cellfont = workbook.createFont();
		cellfont.setFontName("宋体");
		cellfont.setFontHeightInPoints((short)10);//字体大小
		cellStyle.setFont(cellfont);

		style.setFont(headfont);
		SXSSFCell titleCell = row.createCell(0);
		titleCell.setCellValue(fileName);
		titleCell.setCellStyle(style);
		
		int i = 0;
		cellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
		//row = sheet.createRow(0);
		row.setHeight((short)300);
		for(String str : cells){
			if("时间".equals(str)){
				sheet.setColumnWidth(i, 26*256);
			}else{
				sheet.setColumnWidth(i, 12*256);
			}
			titleCell = row.createCell(i++);
			titleCell.setCellValue(str);
			titleCell.setCellStyle(colsStyle);
		}
		
		if(list != null && list.size() > 0)
		{
			//第五步，写入实体数据
			for(int j = 0; j < list.size(); j++){
				row = sheet.createRow(j+1);
				String[] vals = list.get(j);
				for(int k=0; k < vals.length; k++){
					SXSSFCell contentCell = row.createCell(k);
					if(vals[k] != null && isNumber(vals[k])){//判断字符串是否全是数字
						contentCell.setCellValue(Integer.parseInt(vals[k]));
					}else{
						contentCell.setCellValue(vals[k]);
					}
					contentCell.setCellStyle(cellStyle);
				}
			}
		}*/
		FileOutputStream out = null;
		try{
			String filePath = CommonUtils.selectSavePath("D://"+fileName, "保存过车日志表格", FileType.EXCEL_2007);
			
			if(filePath != null)
			{
				out = new FileOutputStream(filePath);
				wb.write(out);
				out.flush();
				out.close();
				System.out.println("导出成功");
				//LMSLog.warningDialog("导出Excel表格提示", "导出成功！导出路径是："+filePath);
				SwsMessageDialog.warningDialog("导出成功！导出路径是："+filePath);
			}
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			Log.exception(e);
			//LMSLog.errorDialog("导出Excel错误提示", "该文件正在使用中，无法导出数据，请关闭文件再导出！");
		} catch (IOException e) {
			e.printStackTrace();
			Log.exception(e);
		} finally {
			try {
				if(out != null)
				{
					out.close();
				}
			} catch (Exception e) {
				e.printStackTrace();
				Log.exception(e);
			}
		}
	}
	
	public static<T> void exportExcel(List<List<String[]>> list, List<List<String>> cells, List<String> sheetNames, int sheetCount, String fileName, String dialogTitle){
		
		//第一步，创建一个webbook,对应一个excel文件
		XSSFWorkbook workbook = new XSSFWorkbook();
		SXSSFWorkbook wb = new SXSSFWorkbook(workbook, 100);//默认100
		for(int i=0; i<sheetCount; i++)
		{
			List<String[]> contentList = null;
			if(list != null && list.size() > 0)
			{
				contentList = list.get(i);
			}
			List<String> cellList = null;
			if(cells != null && cells.size() > 0)
			{
				cellList = cells.get(i);
			}

			String sheetName = sheetNames.get(i);
			excelBase(workbook, wb, contentList, cellList, sheetName, dialogTitle);
		}

		FileOutputStream out = null;
		try{
			String filePath = CommonUtils.selectSavePath("D://"+fileName, "保存过车日志表格", FileType.EXCEL_2007);
			
			if(filePath != null)
			{
				out = new FileOutputStream(filePath);
				wb.write(out);
				out.flush();
				out.close();
				System.out.println("导出成功");
				//LMSLog.warningDialog("导出Excel表格提示", "导出成功！导出路径是："+filePath);
				SwsMessageDialog.warningDialog("导出成功！导出路径是："+filePath);
			}
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			Log.exception(e);
			//LMSLog.errorDialog("导出Excel错误提示", "该文件正在使用中，无法导出数据，请关闭文件再导出！");
		} catch (IOException e) {
			e.printStackTrace();
			Log.exception(e);
		} finally {
			try {
				if(out != null)
				{
					out.close();
				}
			} catch (Exception e) {
				e.printStackTrace();
				Log.exception(e);
			}
		}
	}
	
	private static void excelBase(XSSFWorkbook workbook, SXSSFWorkbook wb, List<String[]> list, List<String> cells, String sheetName, String dialogTitle)
	{
		SXSSFSheet sheet = wb.createSheet(sheetName);
		//第二步，在webbook中添加一个sheet,对应Excel文件中sheet
		//HSSFSheet sheet = wb.createSheet();
		//第三步，在sheet中添加表头第0行，注意老版本poi对Excel的行数列数有限制short
		SXSSFRow row = sheet.createRow(0);
		//第四步，创建单元格，并设置值表头，设置表头居中
		XSSFCellStyle style = workbook.createCellStyle();
		//设置字体
		XSSFFont headfont = workbook.createFont();
		headfont.setFontName("宋体");
		headfont.setFontHeightInPoints((short)18);//字体大小
		headfont.setBold(true);//加粗
		
		//设置列头格式
		XSSFCellStyle colsStyle = workbook.createCellStyle();
		colsStyle.setAlignment(HorizontalAlignment.CENTER);
		//设置列头字体
		XSSFFont colsfont = workbook.createFont();
		colsfont.setFontName("宋体");
		colsfont.setFontHeightInPoints((short)11);
		colsfont.setBold(true);
		colsStyle.setFont(colsfont);
		
		//设置单元格格式
		XSSFCellStyle cellStyle = workbook.createCellStyle();
		cellStyle.setAlignment(HorizontalAlignment.CENTER);

		//设置单元格字体
		XSSFFont cellfont = workbook.createFont();
		cellfont.setFontName("宋体");
		cellfont.setFontHeightInPoints((short)10);//字体大小
		cellStyle.setFont(cellfont);

		style.setFont(headfont);
		SXSSFCell titleCell = row.createCell(0);
		//titleCell.setCellValue(fileName);
		titleCell.setCellStyle(style);
		
		int i = 0;
		cellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
		//row = sheet.createRow(0);
		row.setHeight((short)300);
		for(String str : cells){
			if("时间".equals(str)){
				sheet.setColumnWidth(i, 26*256);
			}else{
				sheet.setColumnWidth(i, 12*256);
			}
			titleCell = row.createCell(i++);
			titleCell.setCellValue(str);
			titleCell.setCellStyle(colsStyle);
		}
		
		if(list != null && list.size() > 0)
		{
			//第五步，写入实体数据
			for(int j = 0; j < list.size(); j++){
				row = sheet.createRow(j+1);
				String[] vals = list.get(j);
				for(int k=0; k < vals.length; k++){
					SXSSFCell contentCell = row.createCell(k);
					if(vals[k] != null && isNumber(vals[k])){//判断字符串是否全是数字
						contentCell.setCellValue(Integer.parseInt(vals[k]));
					}else{
						contentCell.setCellValue(vals[k]);
					}
					contentCell.setCellStyle(cellStyle);
				}
			}
		}
	}
	
	public static List<String[]> readExcel(File file, int sheetIndex, int cellCount)
	{
		List<String[]> list = new ArrayList<String[]>();
		Workbook workbook = null;
		try {

			workbook = WorkbookFactory.create(new FileInputStream(file));
			
			// 获取第一个张表
			Sheet sheet = workbook.getSheetAt(sheetIndex);
			   
			// 获取每行中的字段
			for (int i = 0; i <= sheet.getLastRowNum(); i++) 
			{
				Row row = sheet.getRow(i);  // 获取行
			  // 获取单元格中的值
			  //String studentNum = row.getCell(0).getStringCellValue();  
			  //String name = row.getCell(1).getStringCellValue();
			  //String phone = row.getCell(2).getStringCellValue();
				String[] cellValues = new String[cellCount];
				for(int j=0; j<cellCount; j++)
				{
					String cellValue = printCell(row.getCell(j));
					cellValues[j] = cellValue;
				}
			  	
				list.add(cellValues);
			}
		      
		} catch (IOException e) {
			e.printStackTrace();
		} catch (EncryptedDocumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvalidFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return list;
	}
	
	private static String printCell(Cell cell)
	{
		if(cell.getCellTypeEnum() == CellType.NUMERIC)
		{
			String cellValue = cell.getNumericCellValue()+"";
			System.out.println(cellValue);
			return cellValue;
		}
		else if(cell.getCellTypeEnum() == CellType.STRING)
		{
			String cellValue = cell.getStringCellValue();
			
			System.out.println(cellValue);
			
			return cellValue;
		}
		else if(cell.getCellTypeEnum() == CellType.BOOLEAN)
		{
			String cellValue = cell.getBooleanCellValue()+"";
			System.out.println(cellValue);
			return cellValue;
		}
		
		return "";
	}
		
	public static boolean isNumber(String str){
		if(str == null || str.equals(""))
		{
			return false;
		}
		Pattern pattern = Pattern.compile("[0-9]*");
		Matcher isNum = pattern.matcher(str);
		if(!isNum.matches()){
			return false;
		}
		return true;
	}
}
