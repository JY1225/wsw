package com.sws.main;

import java.awt.EventQueue;

import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;
import javax.swing.UnsupportedLookAndFeelException;

import com.sws.base.SwsParameter;
import com.sws.base.SystemInitialization;
import com.sws.events.SWSEventManager;
import com.sws.frame.SmartWeighingLoginFrame;
import com.sws.frame.SmartWeightingFrame;

public class SmartWeightApp {

	public static void main(String[] args) 
	{
		try {
			//UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
			for (LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
			SwsParameter.swsEventManager = new SWSEventManager();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnsupportedLookAndFeelException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		EventQueue.invokeLater(new Runnable() 
		{
			@Override
			public void run() 
			{
				//初始化参数
				new SystemInitialization(args);
				SwsParameter.initParameter();
				//启动界面
				//new SmartWeightingFrame();
				new SmartWeighingLoginFrame();
			}
		});
	}
}
