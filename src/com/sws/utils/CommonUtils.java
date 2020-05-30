package com.sws.utils;

import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileFilter;

public class CommonUtils {

	
	public static String getCurrentStrTime()
	{
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss,SSS");
		Date now = new Date();
		return sdf.format(now);
	}
	
	/**
	 * 获取当前的时间(yyyy-MM-dd HH:mm:ss)
	 * @return
	 */
	public static String getCurrentTime()
	{
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date now = new Date();
		return sdf.format(now);
	}
	
	/**
	 * 获取当前的日期(yyyy-MM-dd)
	 * @return
	 */
	public static String getCurrentDate()
	{
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date now = new Date();
		return sdf.format(now);
	}
	
	public static String getTime(Date date, String format)
	{
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		String time = sdf.format(date);
		
		return time;
	}
	
	/**
	 * 根据外径获得寸
	 * @param diameter 外径，单位mm
	 * @return
	 */
	public static String getCunByOuterDiameter(float diameter)
	{
		String cun = "";
		if(diameter <= 21.3)
		{
			cun = "4分";
		}
		else if(diameter <= 26.7)
		{
			cun = "6分";
		}
		else if(diameter <= 33.4)
		{
			cun = "1寸";
		}
		else if(diameter <= 42.2)
		{
			cun = "1.2寸";
		}
		else if(diameter <= 48.3)
		{
			cun = "1.5寸";
		}
		else if(diameter <= 60.3)
		{
			cun = "2寸";
		}
		else if(diameter <= 73)
		{
			cun = "2.5寸";
		}
		else if(diameter <= 88.9)
		{
			cun = "3寸";
		}
		else if(diameter <= 101.6)
		{
			cun = "3.5寸";
		}
		else if(diameter <= 114.3)
		{
			cun = "4寸";
		}
		else if(diameter <= 141.3)
		{
			cun = "5寸";
		}else if(diameter <= 168.3)
		{
			cun = "6寸";
		}
		else if(diameter <= 219.1)
		{
			cun = "8寸";
		}
		
		return cun;
	}
	
	/**
	 * 获取所有尺寸
	 * @return
	 */
	public static List<String> getAllCun()
	{
		List<String> list = new ArrayList<String>();
		list.add("4分");
		list.add("6分");
		list.add("1寸");
		list.add("1.2寸");
		list.add("1.5寸");
		list.add("2寸");
		list.add("2.5寸");
		list.add("3寸");
		list.add("3.5寸");
		list.add("4寸");
		list.add("5寸");
		list.add("6寸");
		list.add("8寸");
		
		return list;
	}
	
	//获取所有计量方式
	public static List<String> getAllMeasureWay()
	{
		List<String> list = new ArrayList<String>();
		list.add("整件");
		list.add("散支");
		list.add("废料");
		
		return list;
	}
	
	//获取所有管口状态
	public static List<String> getAllNozzleStatus()
	{
		List<String> list = new ArrayList<String>();
		list.add("净口");
		list.add("直切");
		list.add("内外倒角");
		
		return list;
	}
	
	//获取所有出入库状态
	public static List<String> getAllInOutStatus()
	{
		List<String> list = new ArrayList<String>();
		list.add("出库");
		list.add("入库");
		list.add("进料");
		list.add("退料");
		
		return list;
	}
	
	//获取所有材质
	public static List<String> getAllMaterialQuality()
	{
		List<String> list = new ArrayList<String>();
		list.add("Q195");
		list.add("Q215");
		list.add("Q235");
		
		return list;
	}
	
	public static String BinaryToHexString(byte[] bytes, int count)
	{
		String hexStr = "0123456789ABCDEF";
		String result = "";
		String hex = "";
		for (int i = 0; i < count; i++)
		{
			hex = String.valueOf(hexStr.charAt((bytes[i] & 0xF0) >> 4));
			hex += String.valueOf(hexStr.charAt(bytes[i] & 0x0F));
			//result += hex + " ";
			result += hex;
		}
		return result;
	}
	
	public static byte[] hexStringToBytes(String hexString)
	{
		if (hexString == null || hexString.equals(""))
		{
			return null;
		}
		hexString = hexString.toUpperCase().replace(" ", "");
		int length = hexString.length() / 2;
		char[] hexChars = hexString.toCharArray();
		byte[] d = new byte[length];
		for (int i = 0; i < length; i++)
		{
			int pos = i * 2;
			d[i] = (byte) (charToByte(hexChars[pos]) << 4 | charToByte(hexChars[pos + 1]));
		}
		return d;
	}
	
	private static byte charToByte(char c)
	{
		return (byte) "0123456789ABCDEF".indexOf(c);
	}
	
	public static boolean isValidCOM(String address)   
	{   
		 if(address != null && address.length() >= 3)
		 {
			 if(address.substring(0,3).toUpperCase().equals("COM"))
				 return true;
			 else
				 return false;
		 }
		 else
		 {
			 return false;
		 }
	 } 
	
	public static String selectSavePath(String name, String dialogTitle, FileType fileType) {
		//构建文件保存对话框
		JFileChooser chooser = new JFileChooser();
		chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
		chooser.setDialogType(JFileChooser.SAVE_DIALOG);
		chooser.setMultiSelectionEnabled(false);
		chooser.setAcceptAllFileFilterUsed(false);
		chooser.setDialogTitle(dialogTitle);
		chooser.setSelectedFile(new File(name));
		
		//添加文件过滤器
		chooser.addChoosableFileFilter(new FileFilter() {
			
			@Override
			public String getDescription() {
				
				if(fileType == FileType.EXCEL_2003)
				{
					return "Excel2003文件(*.xls)";
				}
				else if(fileType == FileType.EXCEL_2007)
				{
					return "Excel2007文件(*.xlsx)";
				}
				else if(fileType == FileType.TXT)
				{
					return "文本文档(*.txt)";
				}
				else 
				{
					return "所有文件(*.*)";
				}
			}
			
			@Override
			public boolean accept(File f) 
			{
				if(fileType == FileType.EXCEL_2003)
				{
					if(f.getName().endsWith("xls")||f.isDirectory())
					{
						return true;
					}
				}
				else if(fileType == FileType.EXCEL_2007)
				{
					if(f.getName().endsWith("xlsx") || f.isDirectory())
					{
						return true;
					}
				}
				else if(fileType == FileType.TXT)
				{
					if(f.getName().endsWith("txt") || f.isDirectory())
					{
						return true;
					}
				}
				
				return false;
			}
		});
		
		chooser.addChoosableFileFilter(new FileFilter() {
			
			public String getDescription() {
				return "所有文件(*.*)";
			}
			
			public boolean accept(File f) {
				return true;
			}
		});

		while(true){
			//打开对话框
			int result = chooser.showSaveDialog(null);
			//文件确定
			if(result==JFileChooser.APPROVE_OPTION)
			{
				String outPath = chooser.getSelectedFile().getAbsolutePath();
				if(fileType == FileType.EXCEL_2003 && !outPath.endsWith(".xls")){//缺少后缀时自动补上后缀
					outPath += ".xls";
				}
				else if(fileType == FileType.EXCEL_2007 && !outPath.endsWith(".xlsx"))
				{
					outPath += ".xlsx";
				}
				else if(fileType == FileType.TXT && !outPath.endsWith(".txt"))
				{
					outPath += ".txt";
				}
				
				if(new File(outPath).exists()){
					if(1==JOptionPane.showConfirmDialog(chooser, "该文件已经存在，是否要覆盖该文件？","重名提示框",JOptionPane.YES_NO_OPTION)){
						continue;
					}
				}
				return outPath;
			}else if(result==JFileChooser.CANCEL_OPTION){
				return null;
			}
		}
	}
	
	public static String selectFilePath(String name, String dialogTitle, FileType fileType) {
		//构建文件保存对话框
		JFileChooser chooser = new JFileChooser();
		chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
		chooser.setDialogType(JFileChooser.OPEN_DIALOG);
		chooser.setMultiSelectionEnabled(false);
		chooser.setAcceptAllFileFilterUsed(false);
		chooser.setDialogTitle(dialogTitle);
		chooser.setSelectedFile(new File(name));
		
		if(fileType == FileType.EXCEL)
		{
			//添加文件过滤器
			chooser.addChoosableFileFilter(new FileFilter() {
				
				@Override
				public String getDescription() 
				{
					return "Excel2007文件(*.xlsx)";
				}
				
				@Override
				public boolean accept(File f) 
				{
					if(f.getName().endsWith("xlsx")||f.isDirectory())
					{
						return true;
					}
					
					return false;
				}
			});
			
			//添加文件过滤器
			chooser.addChoosableFileFilter(new FileFilter() {
				
				@Override
				public String getDescription() 
				{
					return "Excel2003文件(*.xls)";
				}
				
				@Override
				public boolean accept(File f) 
				{
					if(f.getName().endsWith("xls")||f.isDirectory())
					{
						return true;
					}
					
					return false;
				}
			});
		}
		else 
		{
			//添加文件过滤器
			chooser.addChoosableFileFilter(new FileFilter() {
				
				@Override
				public String getDescription() 
				{
					if(fileType == FileType.EXCEL_2003)
					{
						return "Excel2003文件(*.xls)";
					}
					else if(fileType == FileType.EXCEL_2007)
					{
						return "Excel2007文件(*.xlsx)";
					}
					else if(fileType == FileType.TXT)
					{
						return "文本文档(*.txt)";
					}
					
					return "所有文件(*.*)";
				}
				
				@Override
				public boolean accept(File f) 
				{
					if(fileType == FileType.EXCEL_2003)
					{
						if(f.getName().endsWith("xls")||f.isDirectory())
						{
							return true;
						}
					}
					else if(fileType == FileType.EXCEL_2007)
					{
						if(f.getName().endsWith("xlsx") || f.isDirectory())
						{
							return true;
						}
					}
					else if(fileType == FileType.TXT)
					{
						if(f.getName().endsWith("txt") || f.isDirectory())
						{
							return true;
						}
					}
					return false;
				}
			});
		}
		chooser.addChoosableFileFilter(new FileFilter() {
			
			public String getDescription() {
				return "所有文件(*.*)";
			}
			
			public boolean accept(File f) {
				return true;
			}
		});

		while(true){
			//打开对话框
			int result = chooser.showSaveDialog(null);
			//文件确定
			if(result==JFileChooser.APPROVE_OPTION){
				String outPath = chooser.getSelectedFile().getAbsolutePath();
				/*if(fileType == FileType.EXCEL_2003 && !outPath.endsWith(".xls")){//缺少后缀时自动补上后缀
					outPath += ".xls";
				}
				else if(fileType == FileType.EXCEL_2007 && !outPath.endsWith(".xlsx"))
				{
					outPath += ".xlsx";
				}
				else if(fileType == FileType.TXT && !outPath.endsWith(".txt"))
				{
					outPath += ".txt";
				}*/
				return outPath;
			}else if(result==JFileChooser.CANCEL_OPTION){
				return null;
			}
		}
	}
}
