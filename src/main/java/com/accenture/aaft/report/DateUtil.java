package com.accenture.aaft.report;

import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Class is used to get current date using SimpleDateFormat
 *
 * @author vijay.venkatappa
 *
 */
public class DateUtil {

  /**
   * Method is used to get current date
   *
   * @param dateFormat - represents date format
   * @return current date
   */
  public static String now(String dateFormat) {
	Calendar cal = Calendar.getInstance();
	SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);
	return sdf.format(cal.getTimeInMillis());
  }
}
