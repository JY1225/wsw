package com.sws.base;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.StringWriter;
import javax.swing.JOptionPane;
import com.sws.utils.CommonUtils;

public class Log {

	public static void print(String log)
	 {
		 String logLine = CommonUtils.getCurrentStrTime() + " " +log;
		 writeString("log\\interface.log", logLine);
	 }
	 
	 public static void exception(Throwable throwable)
	 {
		 String logLine = CommonUtils.getCurrentStrTime() + " " + getExceptionStack(throwable);
		 writeString("log\\exception.log", logLine);
		 //throwable.printStackTrace();
	 }
	 
	 public static void exceptionDialog(Throwable throwable)
	 {
		 String logLine = CommonUtils.getCurrentStrTime() + " " + getExceptionStack(throwable);
		 writeString("log\\exception.log", logLine);
		 throwable.printStackTrace();
		 JOptionPane.showMessageDialog(null, logLine, null, JOptionPane.ERROR_MESSAGE);
	 }
	 
	 public static void exceptionDialog(Throwable throwable, String showText)
	 {
		 String logLine = CommonUtils.getCurrentStrTime() + " " + getExceptionStack(throwable);
		 writeString("log\\exception.log", logLine);
		 throwable.printStackTrace();
		 showText = CommonUtils.getCurrentStrTime() + "£º" + showText;
		 JOptionPane.showMessageDialog(null, showText, null, JOptionPane.ERROR_MESSAGE);
	 }
	
	private static File rollFile(String path, int maxFileLength, int maxFileNum)
	{
		File file = new File(path);
	    if ((file.exists()) && (file.length() > maxFileLength))
	    {
	    	boolean full = false;
	    	for (int i = 0; i <= maxFileNum; ++i)
	    	{
	    		if (i == maxFileNum)
	    		{
	    			full = true;
	    			break;
	    		}
	    		File iFile = new File(path + "." + i);
	    		if (iFile.exists())
	    			continue;
	    		file.renameTo(new File(path + "." + i));
	    		break;
	    	}

	    	if (full)
	    	{
	    		File file0 = new File(path + "." + 0);
	    		if (file0.exists())
	    		{
	    			file0.delete();
	    		}
	    		for (int i = 1; i < maxFileNum; ++i)
	    		{
	    			File iFile = new File(path + "." + i);
	    			if (!(iFile.exists()))
	    				continue;
	    			iFile.renameTo(new File(path + "." + (i - 1)));
	    		}

	    		file.renameTo(new File(path + "." + (maxFileNum - 1)));
	    	}
	    }

	    return file;
	 }
	 
	 private static void writeString(String path, String data)
	 {
		 BufferedWriter bufferedWtriter = null;
		 try
		 {
			 File file = rollFile(path, 52428800, 10);
			 bufferedWtriter = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file, true), "gbk"));
			 bufferedWtriter.append(data).append("\r\n");
			 bufferedWtriter.flush();
		 }
		 catch (Exception e)
		 {
			 e.printStackTrace();
		 }
		 finally
		 {
			 if (bufferedWtriter != null)
			 {
				 try
				 {
					 bufferedWtriter.close();
				 }
				 catch (IOException e)
				 {
					 e.printStackTrace();
				 }
			 }
		 }
	 }
	 
	 private static String getExceptionStack(Throwable throwable)
	 {
		 StringWriter sw = new StringWriter();
		 PrintWriter pw = new PrintWriter(sw);
		 throwable.printStackTrace(pw);
		 return sw.toString();
	 }
}
