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

	
	//����excel2007
	public static<T> void exportExcel(List<String[]> list, List<String> cells, String fileName, String dialogTitle){
		
		//��һ��������һ��webbook,��Ӧһ��excel�ļ�
		XSSFWorkbook workbook = new XSSFWorkbook();
		SXSSFWorkbook wb = new SXSSFWorkbook(workbook, 100);//Ĭ��100
		excelBase(workbook, wb, list, cells, fileName, dialogTitle);
		/*SXSSFSheet sheet = wb.createSheet("�����ߴ�");
		//�ڶ�������webbook�����һ��sheet,��ӦExcel�ļ���sheet
		//HSSFSheet sheet = wb.createSheet();
		//����������sheet����ӱ�ͷ��0�У�ע���ϰ汾poi��Excel����������������short
		SXSSFRow row = sheet.createRow(0);
		//���Ĳ���������Ԫ�񣬲�����ֵ��ͷ�����ñ�ͷ����
		XSSFCellStyle style = workbook.createCellStyle();
		//��������
		XSSFFont headfont = workbook.createFont();
		headfont.setFontName("����");
		headfont.setFontHeightInPoints((short)18);//�����С
		headfont.setBold(true);//�Ӵ�
		
		//������ͷ��ʽ
		XSSFCellStyle colsStyle = workbook.createCellStyle();
		colsStyle.setAlignment(HorizontalAlignment.CENTER);
		//������ͷ����
		XSSFFont colsfont = workbook.createFont();
		colsfont.setFontName("����");
		colsfont.setFontHeightInPoints((short)11);
		colsfont.setBold(true);
		colsStyle.setFont(colsfont);
		
		//���õ�Ԫ���ʽ
		XSSFCellStyle cellStyle = workbook.createCellStyle();
		cellStyle.setAlignment(HorizontalAlignment.CENTER);

		//���õ�Ԫ������
		XSSFFont cellfont = workbook.createFont();
		cellfont.setFontName("����");
		cellfont.setFontHeightInPoints((short)10);//�����С
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
			if("ʱ��".equals(str)){
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
			//���岽��д��ʵ������
			for(int j = 0; j < list.size(); j++){
				row = sheet.createRow(j+1);
				String[] vals = list.get(j);
				for(int k=0; k < vals.length; k++){
					SXSSFCell contentCell = row.createCell(k);
					if(vals[k] != null && isNumber(vals[k])){//�ж��ַ����Ƿ�ȫ������
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
			String filePath = CommonUtils.selectSavePath("D://"+fileName, "���������־���", FileType.EXCEL_2007);
			
			if(filePath != null)
			{
				out = new FileOutputStream(filePath);
				wb.write(out);
				out.flush();
				out.close();
				System.out.println("�����ɹ�");
				//LMSLog.warningDialog("����Excel�����ʾ", "�����ɹ�������·���ǣ�"+filePath);
				SwsMessageDialog.warningDialog("�����ɹ�������·���ǣ�"+filePath);
			}
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			Log.exception(e);
			//LMSLog.errorDialog("����Excel������ʾ", "���ļ�����ʹ���У��޷��������ݣ���ر��ļ��ٵ�����");
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
		
		//��һ��������һ��webbook,��Ӧһ��excel�ļ�
		XSSFWorkbook workbook = new XSSFWorkbook();
		SXSSFWorkbook wb = new SXSSFWorkbook(workbook, 100);//Ĭ��100
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
			String filePath = CommonUtils.selectSavePath("D://"+fileName, "���������־���", FileType.EXCEL_2007);
			
			if(filePath != null)
			{
				out = new FileOutputStream(filePath);
				wb.write(out);
				out.flush();
				out.close();
				System.out.println("�����ɹ�");
				//LMSLog.warningDialog("����Excel�����ʾ", "�����ɹ�������·���ǣ�"+filePath);
				SwsMessageDialog.warningDialog("�����ɹ�������·���ǣ�"+filePath);
			}
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			Log.exception(e);
			//LMSLog.errorDialog("����Excel������ʾ", "���ļ�����ʹ���У��޷��������ݣ���ر��ļ��ٵ�����");
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
		//�ڶ�������webbook�����һ��sheet,��ӦExcel�ļ���sheet
		//HSSFSheet sheet = wb.createSheet();
		//����������sheet����ӱ�ͷ��0�У�ע���ϰ汾poi��Excel����������������short
		SXSSFRow row = sheet.createRow(0);
		//���Ĳ���������Ԫ�񣬲�����ֵ��ͷ�����ñ�ͷ����
		XSSFCellStyle style = workbook.createCellStyle();
		//��������
		XSSFFont headfont = workbook.createFont();
		headfont.setFontName("����");
		headfont.setFontHeightInPoints((short)18);//�����С
		headfont.setBold(true);//�Ӵ�
		
		//������ͷ��ʽ
		XSSFCellStyle colsStyle = workbook.createCellStyle();
		colsStyle.setAlignment(HorizontalAlignment.CENTER);
		//������ͷ����
		XSSFFont colsfont = workbook.createFont();
		colsfont.setFontName("����");
		colsfont.setFontHeightInPoints((short)11);
		colsfont.setBold(true);
		colsStyle.setFont(colsfont);
		
		//���õ�Ԫ���ʽ
		XSSFCellStyle cellStyle = workbook.createCellStyle();
		cellStyle.setAlignment(HorizontalAlignment.CENTER);

		//���õ�Ԫ������
		XSSFFont cellfont = workbook.createFont();
		cellfont.setFontName("����");
		cellfont.setFontHeightInPoints((short)10);//�����С
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
			if("ʱ��".equals(str)){
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
			//���岽��д��ʵ������
			for(int j = 0; j < list.size(); j++){
				row = sheet.createRow(j+1);
				String[] vals = list.get(j);
				for(int k=0; k < vals.length; k++){
					SXSSFCell contentCell = row.createCell(k);
					if(vals[k] != null && isNumber(vals[k])){//�ж��ַ����Ƿ�ȫ������
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
			
			// ��ȡ��һ���ű�
			Sheet sheet = workbook.getSheetAt(sheetIndex);
			   
			// ��ȡÿ���е��ֶ�
			for (int i = 0; i <= sheet.getLastRowNum(); i++) 
			{
				Row row = sheet.getRow(i);  // ��ȡ��
			  // ��ȡ��Ԫ���е�ֵ
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
