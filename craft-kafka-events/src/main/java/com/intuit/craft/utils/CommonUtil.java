package com.intuit.craft.utils;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;


public class CommonUtil {

  public static long getCurrentTime() {
    Date currentTimestamp = new Date();
    return currentTimestamp.getTime();
  }

  public static long getTimeAfterMonth() {
    Calendar calendar = new GregorianCalendar(/* remember about timezone! */);
    calendar.setTime(new Date());
    calendar.add(Calendar.DATE, 30);
    return calendar.getTime().getTime();
  }
}
