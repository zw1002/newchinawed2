package com.hnqj.core;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;


public class Const {
	/**
	 * Date转String
	 * @param date
	 * @param strFormat
	 * @return
	 */
	public static String DateToString(Date date, String strFormat) {
		SimpleDateFormat formater = new SimpleDateFormat();
		formater.applyPattern(strFormat);
		String time = formater.format(date);
		return time;
	}
    /**
     * String转Date
     * @param strTime
     * @return
     */
	public static Date StringToDate(String strTime) {
		SimpleDateFormat formater = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date date = null;
		try {
			date = formater.parse(strTime);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return date;
	}

}
