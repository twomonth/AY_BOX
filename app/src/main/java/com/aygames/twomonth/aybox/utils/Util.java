package com.aygames.twomonth.aybox.utils;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
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

	/**
	 * 时间工具类
	 * @param cc_time
	 * @return
	 */
	public static String getStrTime(String cc_time) {
		String re_StrTime = null;
		SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
		long lcc_time = Long.valueOf(cc_time);
		re_StrTime = sdf.format(new Date(lcc_time * 1000L));
		return re_StrTime;
	}

	public static String getStrTimeString(String cc_time) {
		String re_StrTime = null;
		SimpleDateFormat sdf = new SimpleDateFormat("MM月dd日HH:mm");
		long lcc_time = Long.valueOf(cc_time);
		re_StrTime = sdf.format(new Date(lcc_time * 1000L));
		return re_StrTime;
	}

	public static int getStrDay(String cc_time){
		String re_StrTime = null;
		SimpleDateFormat sdf = new SimpleDateFormat("dd");
		long lcc_time = Long.valueOf(cc_time);
		re_StrTime = sdf.format(new Date(lcc_time * 1000L));
		int timeDay = Integer.parseInt(re_StrTime);
		return timeDay;
	}

}
