package com.sws.base;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.lang.management.ManagementFactory;
import java.lang.management.MemoryMXBean;
import java.lang.management.MemoryUsage;
import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;

import javax.swing.JOptionPane;

import com.sws.database.JdbcUtils;

public class SystemInitialization {
	
	public SystemInitialization(String[] args)
	{
		//�������Ƿ��������
		appStartCheck(args);
		//�������ļ����Ƿ����
		checkFolderExist();
		//��������ļ��Ƿ����
		checkConfigExist();
		new SwsHandleEvent();
		//��ʼ�����ݿ�
		JdbcUtils.initDataBase();
	}

	private void appStartCheck(String[] args)
	{
		try{
			File file = new File(".lock");
			RandomAccessFile raf = new RandomAccessFile(file,"rw");
			FileChannel fc = raf.getChannel();
			FileLock lock = fc.tryLock(0, 1, false);
			if (lock == null)
			{	
				JOptionPane.showMessageDialog(null, "ֻ������һ��,��������Ƿ��Ѿ�����!", "��ʾ", JOptionPane.OK_OPTION);
				System.exit(0);
			}
			
		}
		catch(Exception e)
		{
			Log.exceptionDialog(e);
		}
	}
	
	private void checkFolderExist()
	{
		File logFolder = new File("log");
		if(!logFolder.exists() || !logFolder.isDirectory())
		{
			logFolder.mkdir();
		}
	}
	
	private void checkConfigExist()
	{
		try {
			File configFile = new File("config.ini");
			if(!configFile.exists() || !configFile.isFile())
			{
				configFile.createNewFile();
			}
		} catch (IOException e) {
			Log.exception(e);
		}
	}
}
