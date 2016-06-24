package com.cardpay.pccredit.QZBankInterface.util;

import java.util.Calendar;
import java.util.Date;

public class DateUtil {
	//时间计算+月份
	public static Date shiftMonth(Date d,int month){
		//Date d = new Date(2015-1900,1-1,31);
		Calendar cal = Calendar.getInstance();
		cal.setTime(d);
		//System.out.println(cal.get(Calendar.YEAR) + " " + cal.get(Calendar.MONTH) + " " + cal.get(Calendar.DAY_OF_MONTH));
		cal.add(Calendar.MONTH, month);
		//System.out.println(cal.get(Calendar.YEAR) + " " + cal.get(Calendar.MONTH) + " " + cal.get(Calendar.DAY_OF_MONTH));
		return cal.getTime();
	}
}
