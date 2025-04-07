package kr.co.athena.oauth.common.util;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.apache.commons.lang.StringUtils;

public class CommonUtil {
	
	public static String dateLengthCheck(String date, int length) {
		
		if(date == null) date = "";
		
		if(date.length() < length) {
			date = StringUtils.rightPad(date, length, "0");
		} else if (date.length() > length) {
			date = date.substring(0, length);
		}
		
		return date;
	}	
	
	public static String getDate() {
		LocalDateTime now = LocalDateTime.now();
		
		String formatedNow = now.format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"));
		
		return formatedNow;
	}
	
	/**
	 * 파일 확장자에서 점을 제외하고 반환한다.
	 * @param fileName
	 * @return
	 */
	public static String getExtNoneDot(String fileName) {
		if(fileName == null) return "";

		String fname = "";
		if (fileName.indexOf(".") != -1) {
			fname = fileName.substring(fileName.lastIndexOf(".")+1);
			return fname;
		} else {
			return "";
		}
	}

}
