package kr.ac.shu.util;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateManagement {
	
	/*
	 * 타임스탬프 1개씩을 날짜 형식 문자열
     * @param seconds 정확 초 문자열
     * @param formatStr 반환받을 포맷
     * @return
     */
	public static String timeStamp2Date(String seconds, String format) {
		if(seconds == null || seconds.isEmpty() || seconds.equals("null")){
			return "";
		}
		if(format == null || format.isEmpty()) format = "yyyy-MM-dd HH:mm:ss";
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		return sdf.format(new Date(Long.valueOf(seconds+"000")));
	}
}
