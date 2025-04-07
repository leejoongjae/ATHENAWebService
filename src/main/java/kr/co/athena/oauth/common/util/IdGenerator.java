package kr.co.athena.oauth.common.util;

import java.util.Calendar;

public class IdGenerator {

	public IdGenerator() {
		super();
	}

	/**
	 * 새로운 ID 생성
	 * @param moduleName (4자 이내의 모듈명 약자)
	 * @return
	 */
	public synchronized static String getNewId(String moduleName) {
		
		if (moduleName == null) {
			moduleName = "UNKN";
		}
		if (moduleName.length() > 4) {
			moduleName = moduleName.substring(0, 4);
		}
		moduleName = moduleName.toUpperCase();
		
		String newId = "";
		Calendar calendar = Calendar.getInstance();

		String yy = Integer.toString(calendar.get(Calendar.YEAR));
		String mm = getDateElementStr(calendar.get(Calendar.MONTH) + 1);
		String dd = getDateElementStr(calendar.get(Calendar.DAY_OF_MONTH));
		String HH = getDateElementStr(calendar.get(Calendar.HOUR_OF_DAY));
		String MM = getDateElementStr(calendar.get(Calendar.MINUTE));
		String SS = getDateElementStr(calendar.get(Calendar.SECOND));

		MediUID uid 	= new MediUID();
		String seqStr 	= uid.toString().replaceAll("-", "");
		int idx 		= seqStr.lastIndexOf(':');
		String last 	= seqStr.substring(idx+1);
		for (int i=last.length(); i<4; i++) {
			last 		= "0"+last;
		}
		seqStr = seqStr.substring(0, idx) + last;
		seqStr = seqStr.substring(seqStr.length()-6);
		
		newId = moduleName + "_" + yy + mm + dd + HH + MM + SS + seqStr;
		
		
		return newId;
	}
	

	/*
	 * 날짜 요소를 문자열로 변환
	 */
	private static String getDateElementStr(int dateElement) {
		String dateStr = Integer.toString(dateElement);
		if (dateStr.length() < 2) {
			dateStr = "0" + dateStr;
		}
		
		return dateStr;
	}
}
