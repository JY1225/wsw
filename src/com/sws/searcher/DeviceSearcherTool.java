package com.sws.searcher;

import java.net.InetAddress;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DeviceSearcherTool {
	 public static boolean isValidIP(String ipAddress)   
	 {   
		 if(ipAddress != null)
		 {
			 String ip = "([1-9]|[1-9]\\d|1\\d{2}|2[0-4]\\d|25[0-5])(\\.(\\d|[1-9]\\d|1\\d{2}|2[0-4]\\d|25[0-5])){3}";    

			 Pattern pattern = Pattern.compile(ip);    
			 Matcher matcher = pattern.matcher(ipAddress);    
   
			 return matcher.matches();    
		 }
		 else
		 {
			 return false;
		 }
	 } 
	 
	 public static String getLocalHostName() {  
        String hostName;  
        try {  
            InetAddress addr = InetAddress.getLocalHost();  
            hostName = addr.getHostName();  
        } catch (Exception ex) {  
            hostName = "";  
        }  
        return hostName;  
    }  
  
}
