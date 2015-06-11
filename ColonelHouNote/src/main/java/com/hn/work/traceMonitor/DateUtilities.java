package com.hn.work.traceMonitor;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Calendar: the Calendar class of JAVA.util TimeString: 20121225145523234 :
 * YYYYMMDDHHmmssSSS DateString: 20121225 : YYYYMMDD DisplayString : yyyy-MM-dd
 * 
 * @author cmcc xuzhao
 */
public class DateUtilities
{
	private static SimpleDateFormat dateFormat = new SimpleDateFormat(
			"yyyy-MM-dd");

	/* DisplayString ========================================================= */
	/**
	 * JAVA.util.Date --> DisplayString
	 * 
	 * @param paramDate
	 * @return
	 */
	public static String date_display_string(Date paramDate)
	{
		if (null == paramDate)
		{
			return null;
		}
		String tmpDateString = dateFormat.format(paramDate);
		return tmpDateString;
	}

	/**
	 * JAVA.util.Date <-- DisplayString
	 * 
	 * @param paramString
	 * @return
	 * @throws ParseException
	 */
	public static Date date_from_string(String paramString)
			throws ParseException
	{
		if (null == paramString)
		{
			return null;
		}
		return dateFormat.parse(paramString);
	}

	/* TimeString ========================================================= */
	/**
	 * JAVA.util.Calendar-->TimeString
	 * 
	 * @param paramCalendar
	 * @return
	 */
	public static long Calendar2timeString(Calendar paramCalendar)
	{
		return date_time_string(paramCalendar);
	}

	public static long date_time_string(Calendar paramCalendar)
	{
		if (null == paramCalendar)
		{
			return 0;
		}
		String tme_string = String.valueOf(paramCalendar.get(Calendar.YEAR));
		int localmonth = paramCalendar.get(Calendar.MONTH) + 1;
		if (localmonth < 10)
		{
			tme_string = tme_string + "0" + String.valueOf(localmonth);
		} else
		{
			tme_string = tme_string + String.valueOf(localmonth);
		}
		int localday = paramCalendar.get(Calendar.DAY_OF_MONTH);
		if (localday < 10)
		{
			tme_string = tme_string + "0" + String.valueOf(localday);
		} else
		{
			tme_string = tme_string + String.valueOf(localday);
		}
		int localhour = paramCalendar.get(Calendar.HOUR_OF_DAY);
		if (localhour < 10)
		{
			tme_string = tme_string + "0" + String.valueOf(localhour);
		} else
		{
			tme_string = tme_string + String.valueOf(localhour);
		}
		int localmin = paramCalendar.get(Calendar.MINUTE);
		if (localmin < 10)
		{
			tme_string = tme_string + "0" + String.valueOf(localmin);
		} else
		{
			tme_string = tme_string + String.valueOf(localmin);
		}
		int localsec = paramCalendar.get(Calendar.SECOND);
		if (localsec < 10)
		{
			tme_string = tme_string + "0" + String.valueOf(localsec);
		} else
		{
			tme_string = tme_string + String.valueOf(localsec);
		}
		int localmili = paramCalendar.get(Calendar.MILLISECOND);
		if (localmili < 10)
		{
			tme_string = tme_string + "00" + String.valueOf(localmili);
		} else if (localmili < 100)
		{
			tme_string = tme_string + "0" + String.valueOf(localmili);
		} else
		{
			tme_string = tme_string + String.valueOf(localmili);
		}
		return Long.valueOf(tme_string);
	}

	/**
	 * TimeString -->DateString
	 * 
	 * @param timeString
	 * @return
	 */
	public static long timeString2dateString(long timeString)
	{
		return timeString / 1000000000L;
	}

	/**
	 * TimeString <-- DateString
	 * 
	 * @param dateString
	 * @return
	 */
	public static long dateString2TimeString(long dateString)
	{
		return dateString * 1000000000L;
	}

	/**
	 * TimeString --> JAVA.util.Calendar
	 * 
	 * @param paramTimeString
	 * @return
	 */
	public static Calendar timeString2Calendar(long paramTimeString)
	{
		return calendar_from_time_string(paramTimeString);
	}

	public static Calendar calendar_from_time_string(long paramTimeString)
	{
		Calendar localCal = Calendar.getInstance();
		int localmili = (int) (paramTimeString % 1000);
		paramTimeString = paramTimeString / 1000;
		int localsec = (int) (paramTimeString % 100);
		paramTimeString = paramTimeString / 100;
		int localmin = (int) (paramTimeString % 100);
		paramTimeString = paramTimeString / 100;
		int localhour = (int) (paramTimeString % 100);
		paramTimeString = paramTimeString / 100;
		int localday = (int) (paramTimeString % 100);
		paramTimeString = paramTimeString / 100;
		int localmon = (int) (paramTimeString % 100);
		localmon -= 1;
		if (localmon < 0)
		{
			localmon = 0;
		}
		paramTimeString = paramTimeString / 100;

		localCal.set(Calendar.YEAR, (int) paramTimeString);
		localCal.set(Calendar.MONTH, localmon);
		localCal.set(Calendar.DAY_OF_MONTH, localday);
		localCal.set(Calendar.HOUR_OF_DAY, localhour);
		localCal.set(Calendar.MINUTE, localmin);
		localCal.set(Calendar.SECOND, localsec);
		localCal.set(Calendar.MILLISECOND, localmili);

		return localCal;
	}

	/* DateString ========================================================= */
	public static Calendar dateString2Calendar(long paramDateString)
	{
		return timeString2Calendar(dateString2TimeString(paramDateString));
	}

	public static long Calendar2dateString(Calendar paramCalendar)
	{
		return timeString2dateString(Calendar2timeString(paramCalendar));
	}

	/*
	 * JAVA.UTIL.CALENDAR
	 * =========================================================
	 */
	/**
	 * JAVA.UTIL.CALENDAR toStartOfDay
	 * 
	 * @param paramCal
	 * @return
	 */
	public static Calendar toStartOfDay(Calendar paramCal)
	{
		Calendar tmpCal = Calendar.getInstance();
		if (null != paramCal)
		{
			tmpCal.setTime(paramCal.getTime());
		}
		tmpCal.set(Calendar.HOUR_OF_DAY, 0);
		tmpCal.set(Calendar.MINUTE, 0);
		tmpCal.set(Calendar.SECOND, 0);
		tmpCal.set(Calendar.MILLISECOND, 0);
		return tmpCal;
	}

	/**
	 * JAVA.UTIL.CALENDAR toEndofDay
	 * 
	 * @param paramCal
	 * @return
	 */
	public static Calendar toEndOfDay(Calendar paramCal)
	{
		Calendar tmpCal = Calendar.getInstance();
		if (null != paramCal)
		{
			tmpCal.setTime(paramCal.getTime());
		}
		tmpCal.set(Calendar.HOUR_OF_DAY, 23);
		tmpCal.set(Calendar.MINUTE, 59);
		tmpCal.set(Calendar.SECOND, 59);
		tmpCal.set(Calendar.MILLISECOND, 999);
		return tmpCal;
	}

	/**
	 * JAVA.UTIL.CALENDAR toEndofDay
	 * 
	 * @param paramCal
	 * @return
	 */
	public static Calendar toNoonOfDay(Calendar paramCal)
	{
		Calendar tmpCal = Calendar.getInstance();
		if (null != paramCal)
		{
			tmpCal.setTime(paramCal.getTime());
		}
		tmpCal.set(Calendar.HOUR_OF_DAY, 12);
		tmpCal.set(Calendar.MINUTE, 00);
		tmpCal.set(Calendar.SECOND, 00);
		tmpCal.set(Calendar.MILLISECOND, 0);
		return tmpCal;
	}

	/**
	 * JAVA.UTIL.CALENDAR: tmpCal.set(Calendar.DAY_OF_YEAR,
	 * tmpCal.get(Calendar.DAY_OF_YEAR) - dayCount);
	 * 
	 * @param paramCal
	 * @param dayCount
	 * @return
	 */
	public static Calendar dayBefore(Calendar paramCal, int dayCount)
	{
		Calendar tmpCal = Calendar.getInstance();
		if (null != paramCal)
		{
			tmpCal.setTime(paramCal.getTime());
		}
		tmpCal.set(Calendar.DAY_OF_YEAR, tmpCal.get(Calendar.DAY_OF_YEAR)
				- dayCount);
		return tmpCal;
	}

	/**
	 * JAVA.UTIL.CALENDAR: tmpCal.set(Calendar.WEEK_OF_YEAR,
	 * tmpCal.get(Calendar.WEEK_OF_YEAR) - weekCount);
	 * 
	 * @param paramCal
	 * @param dayCount
	 * @return
	 */
	public static Calendar weekBefore(Calendar paramCal, int weekCount)
	{
		Calendar tmpCal = Calendar.getInstance();
		if (null != paramCal)
		{
			tmpCal.setTime(paramCal.getTime());
		}
		tmpCal.set(Calendar.WEEK_OF_YEAR, tmpCal.get(Calendar.WEEK_OF_YEAR)
				- weekCount);
		return tmpCal;
	}

	/**
	 * get Next - First = day count, minus if reverse
	 * 
	 * @return
	 */
	public static long getDaySpan(Calendar paramFirst, Calendar paramNext)
	{
		long tickFirst = paramFirst.getTimeInMillis();
		long tickNext = paramNext.getTimeInMillis();
		return (tickNext - tickFirst) / (1000 * 60 * 60 * 24);
	}

	/**
	 * TimeString -->MonthString
	 * 
	 * @param timeString
	 * @return
	 */
	public static long timeString2MonthString(long timeString)
	{
		return timeString / 100000000000L;
	}
}// DateUtilities
