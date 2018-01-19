package com.aygames.twomonth.aybox.utils;

import java.io.IOException;
import java.util.Enumeration;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

import android.content.Context;
import android.content.pm.ApplicationInfo;

public class Util {
	
	/**
	 * 获取渠道号
	 */
	public static String getChannel(Context context) {  
		// �������ļ��� �ҵ��ļ�
		ApplicationInfo appinfo = context.getApplicationInfo();         
		String sourceDir = appinfo.sourceDir;  
		
		String ret = "";         
		ZipFile zipfile = null;         
		try {             
			zipfile = new ZipFile(sourceDir);             
			Enumeration<?> entries = zipfile.entries();            
			while (entries.hasMoreElements()) {                 
				ZipEntry entry = ((ZipEntry) entries.nextElement()); 
				
				String entryName = entry.getName();     
			
					if (entryName.startsWith("META-INF/AY_")) { 
						ret = entryName;                     
						break;                 
					}
				}
			} catch (IOException e) {             
				e.printStackTrace();         
			} finally {
				if (zipfile != null) { 
					try {                   
							zipfile.close();              
						} 
					catch (IOException e) { 
							e.printStackTrace();                
						}            
					}        
				}         
			String[] split = ret.split("_");       
			if (split != null && split.length >= 2) { 
				 
				  return  split[1];          
				} 
			else {    
				return "CH1149060018297";        
				}    
	}

}
