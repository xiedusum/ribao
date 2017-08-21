package com.whz.ribao.utils;

import org.apache.commons.lang3.time.DateUtils;

import java.time.Year;
import java.util.Calendar;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

/**
 * 日期实用工具类 从org.apache.commons.lang3.time.DateUtils派生，扩展日期比较等的方法
 * 
 * @version 1.0 2012-12-13
 * @author Sunment Develop 
 * 黄坚：huangjian@gdcattsoft.com
 *         2013-09-27
 * @since jdk版本：jdk1.6
 */
public class DateUtil {
	/**
	 * 私有化构造方法
	 */
	private DateUtil() {
	}

	/**
	 * 1秒.以毫秒为单位
	 * 
	 * @since 2.1
	 */
	public static final long MILLIS_PER_SECOND = 1000;
	/**
	 * 1分钟.以毫秒为单位
	 * 
	 * @since 2.1
	 */
	public static final long MILLIS_PER_MINUTE = 60 * MILLIS_PER_SECOND;
	/**
	 * 1小时.以毫秒为单位
	 * 
	 * @since 2.1
	 */
	public static final long MILLIS_PER_HOUR = 60 * MILLIS_PER_MINUTE;
	/**
	 * 1天.以毫秒为单位
	 * 
	 * @since 2.1
	 */
	public static final long MILLIS_PER_DAY = 24 * MILLIS_PER_HOUR;

	/**
	 * This is half a month, so this represents whether a date is in the top or
	 * bottom half of the month.
	 */
	public static final int SEMI_MONTH = 1001;

	/**
	 * 日期类标志二位数组
	 */
	private static final int[][] FIELDS = { { Calendar.MILLISECOND },
			{ Calendar.SECOND }, { Calendar.MINUTE },
			{ Calendar.HOUR_OF_DAY, Calendar.HOUR },
			{ Calendar.DATE, Calendar.DAY_OF_MONTH, Calendar.AM_PM
			/*
			 * Calendar.DAY_OF_YEAR, Calendar.DAY_OF_WEEK,
			 * Calendar.DAY_OF_WEEK_IN_MONTH
			 */
			}, { Calendar.MONTH, DateUtil.SEMI_MONTH }, { Calendar.YEAR },
			{ Calendar.ERA } };

	/**
	 * 周日
	 */
	public static final int RANGE_WEEK_SUNDAY = 1;
	/**
	 * 周一
	 */
	public static final int RANGE_WEEK_MONDAY = 2;
	/**
	 * 周二
	 */
	public static final int RANGE_WEEK_RELATIVE = 3;
	/**
	 * 周三
	 */
	public static final int RANGE_WEEK_CENTER = 4;
	/**
	 * 周四
	 */
	public static final int RANGE_MONTH_SUNDAY = 5;
	/**
	 * 周五
	 */
	public static final int RANGE_MONTH_MONDAY = 6;

	/**
	 * Constant marker for truncating.
	 * 
	 * @since 3.0
	 */
	private static final int MODIFY_TRUNCATE = 0;
	/**
	 * Constant marker for rounding.
	 * 
	 * @since 3.0
	 */
	private static final int MODIFY_ROUND = 1;
	/**
	 * Constant marker for ceiling.
	 * 
	 * @since 3.0
	 */
	private static final int MODIFY_CEILING = 2;

	/** 日期默认格式：yyyy-MM-dd */
	public static final String PATTERN_DAY = "yyyy-MM-dd";

	/** 时间默认格式：yyyy-MM-dd HH:mm:ss */
	public static final String PATTERN_TIME = "yyyy-MM-dd HH:mm:ss";

	/** 时间默认格式：yyyy-MM-dd HH:mm:ss.SSS */
	public static final String PATTERN_MILLISECOND = "yyyy-MM-dd HH:mm:ss.SSS";

	private static final String[] PARSEPATTERNS = new String[] { "yyyy-MM-dd HH:mm:ss", "yyyy-MM-dd", "yyyy-MM-dd HH:mm:ss.S", "yyyy-MM-dd HH:mm:ss.SS", "yyyy/MM/dd HH:mm:ss", "yyyy.MM.dd HH:mm:ss", "yyyy-MM-dd HH:mm", "yyyy/MM/dd HH:mm", "yyyy.MM.dd HH:mm", "yyyy-MM-dd HH", "yyyy/MM/dd HH", "yyyy.MM.dd HH", "yyyy/MM/dd", "yyyy.MM.dd" };

	// -----------------------------------------------------------------------
	/**
	 * <p>
	 * 是否为同一天
	 * </p>
	 * 
	 * <p>
	 * 28 Mar 2002 13:45 and 28 Mar 2002 06:01 would return true. 28 Mar 2002
	 * 13:45 and 12 Mar 2002 13:45 would return false.
	 * </p>
	 * 
	 * @param date1
	 *            the first date, not altered, not null
	 * @param date2
	 *            the second date, not altered, not null
	 * @return true if they represent the same day
	 * @throws IllegalArgumentException
	 *             if either date is <code>null</code>
	 */
	public static boolean isSameDay(Date date1, Date date2) {
		if (date1 == null || date2 == null) {
			throw new IllegalArgumentException("The date must not be null");
		}
		Calendar cal1 = Calendar.getInstance();
		cal1.setTime(date1);
		Calendar cal2 = Calendar.getInstance();
		cal2.setTime(date2);
		return isSameDay(cal1, cal2);
	}

	/**
	 * <p>
	 * 是否为同一天
	 * </p>
	 * 
	 * <p>
	 * 28 Mar 2002 13:45 and 28 Mar 2002 06:01 would return true. 28 Mar 2002
	 * 13:45 and 12 Mar 2002 13:45 would return false.
	 * </p>
	 * 
	 * @param cal1
	 *            the first calendar, not altered, not null
	 * @param cal2
	 *            the second calendar, not altered, not null
	 * @return true if they represent the same day
	 * @throws IllegalArgumentException
	 *             if either calendar is <code>null</code>
	 */
	public static boolean isSameDay(Calendar cal1, Calendar cal2) {
		if (cal1 == null || cal2 == null) {
			throw new IllegalArgumentException("The date must not be null");
		}
		return (cal1.get(Calendar.ERA) == cal2.get(Calendar.ERA)
				&& cal1.get(Calendar.YEAR) == cal2.get(Calendar.YEAR) && cal1
					.get(Calendar.DAY_OF_YEAR) == cal2
				.get(Calendar.DAY_OF_YEAR));
	}

	// -----------------------------------------------------------------------
	/**
	 * <p>
	 * 是否为相同时间
	 * </p>
	 * 
	 * <p>
	 * This method compares the long millisecond time of the two objects.
	 * </p>
	 * 
	 * @param date1
	 *            the first date, not altered, not null
	 * @param date2
	 *            the second date, not altered, not null
	 * @return true if they represent the same millisecond instant
	 * @throws IllegalArgumentException
	 *             if either date is <code>null</code>
	 */
	public static boolean isSameInstant(Date date1, Date date2) {
		if (date1 == null || date2 == null) {
			throw new IllegalArgumentException("The date must not be null");
		}
		return date1.getTime() == date2.getTime();
	}

	/**
	 * <p>
	 * 是否为相同时间
	 * </p>
	 * 
	 * <p>
	 * This method compares the long millisecond time of the two objects.
	 * </p>
	 * 
	 * @param cal1
	 *            the first calendar, not altered, not null
	 * @param cal2
	 *            the second calendar, not altered, not null
	 * @return true if they represent the same millisecond instant
	 * @throws IllegalArgumentException
	 *             if either date is <code>null</code>
	 */
	public static boolean isSameInstant(Calendar cal1, Calendar cal2) {
		if (cal1 == null || cal2 == null) {
			throw new IllegalArgumentException("The date must not be null");
		}
		return cal1.getTime().getTime() == cal2.getTime().getTime();
	}

	// -----------------------------------------------------------------------
	/**
	 * <p>
	 * 是否为相同的本地时间
	 * </p>
	 * 
	 * <p>
	 * This method compares the values of the fields of the two objects. In
	 * addition, both calendars must be the same of the same type.
	 * </p>
	 * 
	 * @param cal1
	 *            the first calendar, not altered, not null
	 * @param cal2
	 *            the second calendar, not altered, not null
	 * @return true if they represent the same millisecond instant
	 * @throws IllegalArgumentException
	 *             if either date is <code>null</code>
	 */
	public static boolean isSameLocalTime(Calendar cal1, Calendar cal2) {
		if (cal1 == null || cal2 == null) {
			throw new IllegalArgumentException("The date must not be null");
		}
		return (cal1.get(Calendar.MILLISECOND) == cal2
				.get(Calendar.MILLISECOND)
				&& cal1.get(Calendar.SECOND) == cal2.get(Calendar.SECOND)
				&& cal1.get(Calendar.MINUTE) == cal2.get(Calendar.MINUTE)
				&& cal1.get(Calendar.HOUR_OF_DAY) == cal2
						.get(Calendar.HOUR_OF_DAY)
				&& cal1.get(Calendar.DAY_OF_YEAR) == cal2
						.get(Calendar.DAY_OF_YEAR)
				&& cal1.get(Calendar.YEAR) == cal2.get(Calendar.YEAR)
				&& cal1.get(Calendar.ERA) == cal2.get(Calendar.ERA) && cal1
					.getClass() == cal2.getClass());
	}

	// -----------------------------------------------------------------------
	/**
	 * <p>
	 * 尝试使用多种格式将字符串转换成为日期对象，返回成功转换后的对象
	 * </p>
	 * 
	 * <p>
	 * The parse will try each parse pattern in turn. A parse is only deemed
	 * successful if it parses the whole of the input string. If no parse
	 * patterns match, a ParseException is thrown.
	 * </p>
	 * The parser will be lenient toward the parsed date.
	 * 
	 * @param str
	 *            the date to parse, not null
	 * @param parsePatterns
	 *            the date format patterns to use, see SimpleDateFormat, not
	 *            null
	 * @return the parsed date
	 * @throws IllegalArgumentException
	 *             if the date string or pattern array is null
	 * @throws ParseException
	 *             if none of the date patterns were suitable (or there were
	 *             none)
	 */
	public static Date parseDate(String str, String... parsePatterns)
			throws ParseException {
		return parseDateWithLeniency(str, parsePatterns, true);
	}

	// -----------------------------------------------------------------------
	/**
	 * <p>
	 * 尝试使用多种格式将字符串转换成为日期对象，返回成功转换后的对象，并严格按照实际日期标准转换
	 * </p>
	 * 
	 * <p>
	 * 本方法遇到非法日期将抛出异常，例如2月30日是非法的
	 * </p>
	 * 
	 * @param str
	 *            the date to parse, not null
	 * @param parsePatterns
	 *            the date format patterns to use, see SimpleDateFormat, not
	 *            null
	 * @return the parsed date
	 * @throws IllegalArgumentException
	 *             if the date string or pattern array is null
	 * @throws ParseException
	 *             if none of the date patterns were suitable
	 */
	public static Date parseDateStrictly(String str, String... parsePatterns)
			throws ParseException {
		return parseDateWithLeniency(str, parsePatterns, false);
	}

	/**
	 * <p>
	 * Parses a string representing a date by trying a variety of different
	 * parsers.
	 * </p>
	 * 
	 * <p>
	 * The parse will try each parse pattern in turn. A parse is only deemed
	 * successful if it parses the whole of the input string. If no parse
	 * patterns match, a ParseException is thrown.
	 * </p>
	 * 
	 * @param str
	 *            the date to parse, not null
	 * @param parsePatterns
	 *            the date format patterns to use, see SimpleDateFormat, not
	 *            null
	 * @param lenient
	 *            Specify whether or not date/time parsing is to be lenient.
	 * @return the parsed date
	 * @throws IllegalArgumentException
	 *             if the date string or pattern array is null
	 * @throws ParseException
	 *             if none of the date patterns were suitable
	 */
	private static Date parseDateWithLeniency(String str,
			String[] parsePatterns, boolean lenient) throws ParseException {
		if (str == null || parsePatterns == null) {
			throw new IllegalArgumentException(
					"Date and Patterns must not be null");
		}

		SimpleDateFormat parser = new SimpleDateFormat();
		parser.setLenient(lenient);
		ParsePosition pos = new ParsePosition(0);
		for (String parsePattern : parsePatterns) {

			String pattern = parsePattern;

			// LANG-530 - need to make sure 'ZZ' output doesn't get passed to
			// SimpleDateFormat
			if (parsePattern.endsWith("ZZ")) {
				pattern = pattern.substring(0, pattern.length() - 1);
			}

			parser.applyPattern(pattern);
			pos.setIndex(0);

			String str2 = str;
			// LANG-530 - need to make sure 'ZZ' output doesn't hit
			// SimpleDateFormat as it will ParseException
			if (parsePattern.endsWith("ZZ")) {
				str2 = str.replaceAll("([-+][0-9][0-9]):([0-9][0-9])$", "$1$2");
			}

			Date date = parser.parse(str2, pos);
			if (date != null && pos.getIndex() == str2.length()) {
				return date;
			}
		}
		throw new ParseException("Unable to parse the date: " + str, -1);
	}

	// -----------------------------------------------------------------------
	/**
	 * <p>
	 * 日期的年份增加或减少
	 * </p>
	 * 
	 * @param date
	 *            the date, not null
	 * @param amount
	 *            the amount to add, may be negative
	 * @return the new {@code Date} with the amount added
	 * @throws IllegalArgumentException
	 *             if the date is null
	 */
	public static Date addYears(Date date, int amount) {
		return add(date, Calendar.YEAR, amount);
	}

	// -----------------------------------------------------------------------
	/**
	 * <p>
	 * 日期的月份增加或减少，大于12将进位
	 * </p>
	 * 
	 * @param date
	 *            the date, not null
	 * @param amount
	 *            the amount to add, may be negative
	 * @return the new {@code Date} with the amount added
	 * @throws IllegalArgumentException
	 *             if the date is null
	 */
	public static Date addMonths(Date date, int amount) {
		return add(date, Calendar.MONTH, amount);
	}

	// -----------------------------------------------------------------------
	/**
	 * <p>
	 * 日期增加或减少以周为单位的时间
	 * </p>
	 * 
	 * @param date
	 *            the date, not null
	 * @param amount
	 *            the amount to add, may be negative
	 * @return the new {@code Date} with the amount added
	 * @throws IllegalArgumentException
	 *             if the date is null
	 */
	public static Date addWeeks(Date date, int amount) {
		return add(date, Calendar.WEEK_OF_YEAR, amount);
	}

	// -----------------------------------------------------------------------
	/**
	 * <p>
	 * 日期的天数增加或减少
	 * </p>
	 * 
	 * @param date
	 *            the date, not null
	 * @param amount
	 *            the amount to add, may be negative
	 * @return the new {@code Date} with the amount added
	 * @throws IllegalArgumentException
	 *             if the date is null
	 */
	public static Date addDays(Date date, int amount) {
		return add(date, Calendar.DAY_OF_MONTH, amount);
	}

	// -----------------------------------------------------------------------
	/**
	 * <p>
	 * 日期的小时增加或减少
	 * </p>
	 * 
	 * @param date
	 *            the date, not null
	 * @param amount
	 *            the amount to add, may be negative
	 * @return the new {@code Date} with the amount added
	 * @throws IllegalArgumentException
	 *             if the date is null
	 */
	public static Date addHours(Date date, int amount) {
		return add(date, Calendar.HOUR_OF_DAY, amount);
	}

	// -----------------------------------------------------------------------
	/**
	 * <p>
	 * 日期的分钟增加或减少
	 * </p>
	 * 
	 * @param date
	 *            the date, not null
	 * @param amount
	 *            the amount to add, may be negative
	 * @return the new {@code Date} with the amount added
	 * @throws IllegalArgumentException
	 *             if the date is null
	 */
	public static Date addMinutes(Date date, int amount) {
		return add(date, Calendar.MINUTE, amount);
	}

	// -----------------------------------------------------------------------
	/**
	 * <p>
	 * 日期的秒钟增加或减少
	 * </p>
	 * 
	 * @param date
	 *            the date, not null
	 * @param amount
	 *            the amount to add, may be negative
	 * @return the new {@code Date} with the amount added
	 * @throws IllegalArgumentException
	 *             if the date is null
	 */
	public static Date addSeconds(Date date, int amount) {
		return add(date, Calendar.SECOND, amount);
	}

	// -----------------------------------------------------------------------
	/**
	 * <p>
	 * 日期的毫秒增加或减少
	 * </p>
	 * 
	 * @param date
	 *            the date, not null
	 * @param amount
	 *            the amount to add, may be negative
	 * @return the new {@code Date} with the amount added
	 * @throws IllegalArgumentException
	 *             if the date is null
	 */
	public static Date addMilliseconds(Date date, int amount) {
		return add(date, Calendar.MILLISECOND, amount);
	}

	// -----------------------------------------------------------------------
	/**
	 * 增加日期字符串对应字段的值 如：add(dt,Calendar.DATE,5);
	 * 
	 * @param date
	 *            日期型字符串
	 * @param calendarField
	 *            Calendar字段
	 * @param amount
	 *            要增加的额度
	 * @return 值增加后的日期
	 */
	private static Date add(Date date, int calendarField, int amount) {
		if (date == null) {
			throw new IllegalArgumentException("The date must not be null");
		}
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		c.add(calendarField, amount);
		return c.getTime();
	}

	// -----------------------------------------------------------------------
	/**
	 * 设置日期的年份
	 * 
	 * @param date
	 *            the date, not null
	 * @param amount
	 *            the amount to set
	 * @return a new {@code Date} set with the specified value
	 * @throws IllegalArgumentException
	 *             if the date is null
	 */
	public static Date setYears(Date date, int amount) {
		return set(date, Calendar.YEAR, amount);
	}

	// -----------------------------------------------------------------------
	/**
	 * 设置日期的月份
	 * 
	 * @param date
	 *            the date, not null
	 * @param amount
	 *            the amount to set
	 * @return a new {@code Date} set with the specified value
	 * @throws IllegalArgumentException
	 *             if the date is null
	 * @since 2.4
	 */
	public static Date setMonths(Date date, int amount) {
		return set(date, Calendar.MONTH, amount);
	}

	// -----------------------------------------------------------------------
	/**
	 * 设置日期的在月分中的号数
	 * 
	 * @param date
	 *            the date, not null
	 * @param amount
	 *            the amount to set
	 * @return a new {@code Date} set with the specified value
	 * @throws IllegalArgumentException
	 *             if the date is null
	 * @since 2.4
	 */
	public static Date setDays(Date date, int amount) {
		return set(date, Calendar.DAY_OF_MONTH, amount);
	}

	// -----------------------------------------------------------------------
	/**
	 * 设置日期的在一天中的小时数
	 * 
	 * @param date
	 *            the date, not null
	 * @param amount
	 *            the amount to set
	 * @return a new {@code Date} set with the specified value
	 * @throws IllegalArgumentException
	 *             if the date is null
	 * @since 2.4
	 */
	public static Date setHours(Date date, int amount) {
		return set(date, Calendar.HOUR_OF_DAY, amount);
	}

	// -----------------------------------------------------------------------
	/**
	 * 设置日期的在一天中的分钟数
	 * 
	 * @param date
	 *            the date, not null
	 * @param amount
	 *            the amount to set
	 * @return a new {@code Date} set with the specified value
	 * @throws IllegalArgumentException
	 *             if the date is null
	 * @since 2.4
	 */
	public static Date setMinutes(Date date, int amount) {
		return set(date, Calendar.MINUTE, amount);
	}

	// -----------------------------------------------------------------------
	/**
	 * 设置日期的在一天中的秒数
	 * 
	 * @param date
	 *            the date, not null
	 * @param amount
	 *            the amount to set
	 * @return a new {@code Date} set with the specified value
	 * @throws IllegalArgumentException
	 *             if the date is null
	 * @since 2.4
	 */
	public static Date setSeconds(Date date, int amount) {
		return set(date, Calendar.SECOND, amount);
	}

	// -----------------------------------------------------------------------
	/**
	 * 设置日期的在一天中的毫秒数
	 * 
	 * @param date
	 *            the date, not null
	 * @param amount
	 *            the amount to set
	 * @return a new {@code Date} set with the specified value
	 * @throws IllegalArgumentException
	 *             if the date is null
	 * @since 2.4
	 */
	public static Date setMilliseconds(Date date, int amount) {
		return set(date, Calendar.MILLISECOND, amount);
	}

	// -----------------------------------------------------------------------
	/**
	 * Sets the specified field to a date returning a new object. This does not
	 * use a lenient calendar. The original {@code Date} is unchanged.
	 * 
	 * @param date
	 *            the date, not null
	 * @param calendarField
	 *            the {@code Calendar} field to set the amount to
	 * @param amount
	 *            the amount to set
	 * @return a new {@code Date} set with the specified value
	 * @throws IllegalArgumentException
	 *             if the date is null
	 * @since 2.4
	 */
	private static Date set(Date date, int calendarField, int amount) {
		if (date == null) {
			throw new IllegalArgumentException("The date must not be null");
		}
		// getInstance() returns a new object, so this method is thread safe.
		Calendar c = Calendar.getInstance();
		c.setLenient(false);
		c.setTime(date);
		c.set(calendarField, amount);
		return c.getTime();
	}

	// -----------------------------------------------------------------------
	/**
	 * 得到日期类对象实例
	 * 
	 * @param date
	 *            the date to convert to a Calendar
	 * @return the created Calendar
	 * @throws NullPointerException
	 *             if null is passed in
	 * @since 3.0
	 */
	public static Calendar toCalendar(Date date) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		return c;
	}

	// -----------------------------------------------------------------------
	/**
	 * <p>
	 * 四舍五入，将时间精确到指定区间（如果所处时区实行夏令时制， 则时间在某些时段会拨快一个小时）
	 * </p>
	 * 
	 * <p>
	 * 夏令时的意思请搜索其他资料
	 * </p>
	 * 
	 * <p>
	 * For a date in a timezone that handles the change to daylight saving time,
	 * rounding to Calendar.HOUR_OF_DAY will behave as follows. Suppose daylight
	 * saving time begins at 02:00 on March 30. Rounding a date that crosses
	 * this time would produce the following values:
	 * <ul>
	 * <li>March 30, 2003 01:10 rounds to March 30, 2003 01:00</li>
	 * <li>March 30, 2003 01:40 rounds to March 30, 2003 03:00</li>
	 * <li>March 30, 2003 02:10 rounds to March 30, 2003 03:00</li>
	 * <li>March 30, 2003 02:40 rounds to March 30, 2003 04:00</li>
	 * </ul>
	 * </p>
	 * 
	 * @param date
	 *            the date to work with, not null
	 * @param field
	 *            the field from {@code Calendar} or {@code SEMI_MONTH}
	 * @return the different rounded date, not null
	 * @throws ArithmeticException
	 *             if the year is over 280 million
	 */
	public static Date round(Date date, int field) {
		if (date == null) {
			throw new IllegalArgumentException("The date must not be null");
		}
		Calendar gval = Calendar.getInstance();
		gval.setTime(date);
		modify(gval, field, MODIFY_ROUND);
		return gval.getTime();
	}

	/**
	 * <p>
	 * 四舍五入，将时间精确到指定区间（如果所处时区实行夏令时制，则时间在某些时段会拨快一个小时）
	 * </p>
	 * 
	 * <p>
	 * 夏令时的意思请搜索其他资料
	 * </p>
	 * 
	 * <p>
	 * For example, if you had the datetime of 28 Mar 2002 13:45:01.231, if this
	 * was passed with HOUR, it would return 28 Mar 2002 14:00:00.000. If this
	 * was passed with MONTH, it would return 1 April 2002 0:00:00.000.
	 * </p>
	 * 
	 * <p>
	 * For a date in a timezone that handles the change to daylight saving time,
	 * rounding to Calendar.HOUR_OF_DAY will behave as follows. Suppose daylight
	 * saving time begins at 02:00 on March 30. Rounding a date that crosses
	 * this time would produce the following values:
	 * <ul>
	 * <li>March 30, 2003 01:10 rounds to March 30, 2003 01:00</li>
	 * <li>March 30, 2003 01:40 rounds to March 30, 2003 03:00</li>
	 * <li>March 30, 2003 02:10 rounds to March 30, 2003 03:00</li>
	 * <li>March 30, 2003 02:40 rounds to March 30, 2003 04:00</li>
	 * </ul>
	 * </p>
	 * 
	 * @param date
	 *            the date to work with, not null
	 * @param field
	 *            the field from {@code Calendar} or <code>SEMI_MONTH</code>
	 * @return the different rounded date, not null
	 * @throws IllegalArgumentException
	 *             if the date is <code>null</code>
	 * @throws ArithmeticException
	 *             if the year is over 280 million
	 */
	public static Calendar round(Calendar date, int field) {
		if (date == null) {
			throw new IllegalArgumentException("The date must not be null");
		}
		Calendar rounded = (Calendar) date.clone();
		modify(rounded, field, MODIFY_ROUND);
		return rounded;
	}

	/**
	 * <p>
	 * 四舍五入，将时间精确到指定区间（如果所处时区实行夏令时制，则时间在某些时段会拨快一个小时）
	 * </p>
	 * 
	 * <p>
	 * 夏令时的意思请搜索其他资料
	 * </p>
	 * 
	 * <p>
	 * For a date in a timezone that handles the change to daylight saving time,
	 * rounding to Calendar.HOUR_OF_DAY will behave as follows. Suppose daylight
	 * saving time begins at 02:00 on March 30. Rounding a date that crosses
	 * this time would produce the following values:
	 * <ul>
	 * <li>March 30, 2003 01:10 rounds to March 30, 2003 01:00</li>
	 * <li>March 30, 2003 01:40 rounds to March 30, 2003 03:00</li>
	 * <li>March 30, 2003 02:10 rounds to March 30, 2003 03:00</li>
	 * <li>March 30, 2003 02:40 rounds to March 30, 2003 04:00</li>
	 * </ul>
	 * </p>
	 * 
	 * @param date
	 *            the date to work with, either {@code Date} or {@code Calendar}
	 *            , not null
	 * @param field
	 *            the field from {@code Calendar} or <code>SEMI_MONTH</code>
	 * @return the different rounded date, not null
	 * @throws IllegalArgumentException
	 *             if the date is <code>null</code>
	 * @throws ClassCastException
	 *             if the object type is not a {@code Date} or {@code Calendar}
	 * @throws ArithmeticException
	 *             if the year is over 280 million
	 */
	public static Date round(Object date, int field) {
		if (date == null) {
			throw new IllegalArgumentException("The date must not be null");
		}
		if (date instanceof Date) {
			return round((Date) date, field);
		} else if (date instanceof Calendar) {
			return round((Calendar) date, field).getTime();
		} else {
			throw new ClassCastException("Could not round " + date);
		}
	}

	// -----------------------------------------------------------------------
	/**
	 * <p>
	 * 将时间精确到指定区间，不四舍五入，后面字段清零
	 * </p>
	 * 
	 * 
	 * <p>
	 * For example, if you had the datetime of 28 Mar 2002 13:45:01.231, if you
	 * passed with HOUR, it would return 28 Mar 2002 13:00:00.000. If this was
	 * passed with MONTH, it would return 1 Mar 2002 0:00:00.000.
	 * </p>
	 * 
	 * @param date
	 *            the date to work with, not null
	 * @param field
	 *            the field from {@code Calendar} or <code>SEMI_MONTH</code>
	 * @return the different truncated date, not null
	 * @throws IllegalArgumentException
	 *             if the date is <code>null</code>
	 * @throws ArithmeticException
	 *             if the year is over 280 million
	 */
	public static Date truncate(Date date, int field) {
		if (date == null) {
			throw new IllegalArgumentException("The date must not be null");
		}
		Calendar gval = Calendar.getInstance();
		gval.setTime(date);
		modify(gval, field, MODIFY_TRUNCATE);
		return gval.getTime();
	}

	/**
	 * <p>
	 * 将时间精确到指定区间，不四舍五入，后面字段清零
	 * </p>
	 * 
	 * <p>
	 * For example, if you had the datetime of 28 Mar 2002 13:45:01.231, if you
	 * passed with HOUR, it would return 28 Mar 2002 13:00:00.000. If this was
	 * passed with MONTH, it would return 1 Mar 2002 0:00:00.000.
	 * </p>
	 * 
	 * @param date
	 *            the date to work with, not null
	 * @param field
	 *            the field from {@code Calendar} or <code>SEMI_MONTH</code>
	 * @return the different truncated date, not null
	 * @throws IllegalArgumentException
	 *             if the date is <code>null</code>
	 * @throws ArithmeticException
	 *             if the year is over 280 million
	 */
	public static Calendar truncate(Calendar date, int field) {
		if (date == null) {
			throw new IllegalArgumentException("The date must not be null");
		}
		Calendar truncated = (Calendar) date.clone();
		modify(truncated, field, MODIFY_TRUNCATE);
		return truncated;
	}

	/**
	 * <p>
	 * 将时间精确到指定区间，不四舍五入，后面字段清零
	 * </p>
	 * 
	 * <p>
	 * For example, if you had the datetime of 28 Mar 2002 13:45:01.231, if you
	 * passed with HOUR, it would return 28 Mar 2002 13:00:00.000. If this was
	 * passed with MONTH, it would return 1 Mar 2002 0:00:00.000.
	 * </p>
	 * 
	 * @param date
	 *            the date to work with, either {@code Date} or {@code Calendar}
	 *            , not null
	 * @param field
	 *            the field from {@code Calendar} or <code>SEMI_MONTH</code>
	 * @return the different truncated date, not null
	 * @throws IllegalArgumentException
	 *             if the date is <code>null</code>
	 * @throws ClassCastException
	 *             if the object type is not a {@code Date} or {@code Calendar}
	 * @throws ArithmeticException
	 *             if the year is over 280 million
	 */
	public static Date truncate(Object date, int field) {
		if (date == null) {
			throw new IllegalArgumentException("The date must not be null");
		}
		if (date instanceof Date) {
			return truncate((Date) date, field);
		} else if (date instanceof Calendar) {
			return truncate((Calendar) date, field).getTime();
		} else {
			throw new ClassCastException("Could not truncate " + date);
		}
	}

	// -----------------------------------------------------------------------
	/**
	 * <p>
	 * 五入取整，指定时间区域，对后面时间清零
	 * </p>
	 * 
	 * <p>
	 * For example, if you had the datetime of 28 Mar 2002 13:45:01.231, if you
	 * passed with HOUR, it would return 28 Mar 2002 14:00:00.000. If this was
	 * passed with MONTH, it would return 1 Apr 2002 0:00:00.000.
	 * </p>
	 * 
	 * @param date
	 *            the date to work with, not null
	 * @param field
	 *            the field from {@code Calendar} or <code>SEMI_MONTH</code>
	 * @return the different ceil date, not null
	 * @throws IllegalArgumentException
	 *             if the date is <code>null</code>
	 * @throws ArithmeticException
	 *             if the year is over 280 million
	 */
	public static Date ceiling(Date date, int field) {
		if (date == null) {
			throw new IllegalArgumentException("The date must not be null");
		}
		Calendar gval = Calendar.getInstance();
		gval.setTime(date);
		modify(gval, field, MODIFY_CEILING);
		return gval.getTime();
	}

	/**
	 * <p>
	 * 五入取整，指定时间区域，对后面时间清零
	 * </p>
	 * 
	 * <p>
	 * For example, if you had the datetime of 28 Mar 2002 13:45:01.231, if you
	 * passed with HOUR, it would return 28 Mar 2002 13:00:00.000. If this was
	 * passed with MONTH, it would return 1 Mar 2002 0:00:00.000.
	 * </p>
	 * 
	 * @param date
	 *            the date to work with, not null
	 * @param field
	 *            the field from {@code Calendar} or <code>SEMI_MONTH</code>
	 * @return the different ceil date, not null
	 * @throws IllegalArgumentException
	 *             if the date is <code>null</code>
	 * @throws ArithmeticException
	 *             if the year is over 280 million
	 */
	public static Calendar ceiling(Calendar date, int field) {
		if (date == null) {
			throw new IllegalArgumentException("The date must not be null");
		}
		Calendar ceiled = (Calendar) date.clone();
		modify(ceiled, field, MODIFY_CEILING);
		return ceiled;
	}

	/**
	 * <p>
	 * 五入取整，指定时间区域，对后面时间清零
	 * </p>
	 * 
	 * <p>
	 * For example, if you had the datetime of 28 Mar 2002 13:45:01.231, if you
	 * passed with HOUR, it would return 28 Mar 2002 13:00:00.000. If this was
	 * passed with MONTH, it would return 1 Mar 2002 0:00:00.000.
	 * </p>
	 * 
	 * @param date
	 *            the date to work with, either {@code Date} or {@code Calendar}
	 *            , not null
	 * @param field
	 *            the field from {@code Calendar} or <code>SEMI_MONTH</code>
	 * @return the different ceil date, not null
	 * @throws IllegalArgumentException
	 *             if the date is <code>null</code>
	 * @throws ClassCastException
	 *             if the object type is not a {@code Date} or {@code Calendar}
	 * @throws ArithmeticException
	 *             if the year is over 280 million
	 */
	public static Date ceiling(Object date, int field) {
		if (date == null) {
			throw new IllegalArgumentException("The date must not be null");
		}
		if (date instanceof Date) {
			return ceiling((Date) date, field);
		} else if (date instanceof Calendar) {
			return ceiling((Calendar) date, field).getTime();
		} else {
			throw new ClassCastException("Could not find ceiling of for type: "
					+ date.getClass());
		}
	}

	// -----------------------------------------------------------------------
	/**
	 * <p>
	 * Internal calculation method.
	 * </p>
	 * 
	 * @param val
	 *            the calendar, not null
	 * @param field
	 *            the field constant
	 * @param modType
	 *            type to truncate, round or ceiling
	 * @throws ArithmeticException
	 *             if the year is over 280 million
	 */
	private static void modify(Calendar val, int field, int modType) {
		if (val.get(Calendar.YEAR) > 280000000) {
			throw new ArithmeticException(
					"Calendar value too large for accurate calculations");
		}

		if (field == Calendar.MILLISECOND) {
			return;
		}

		// ----------------- Fix for LANG-59 ---------------------- START
		// ---------------
		// see http://issues.apache.org/jira/browse/LANG-59
		//
		// Manually truncate milliseconds, seconds and minutes, rather than
		// using
		// Calendar methods.

		Date date = val.getTime();
		long time = date.getTime();
		boolean done = false;

		// truncate milliseconds
		int millisecs = val.get(Calendar.MILLISECOND);
		if (MODIFY_TRUNCATE == modType || millisecs < 500) {
			time = time - millisecs;
		}
		if (field == Calendar.SECOND) {
			done = true;
		}

		// truncate seconds
		int seconds = val.get(Calendar.SECOND);
		if (!done && (MODIFY_TRUNCATE == modType || seconds < 30)) {
			time = time - (seconds * 1000L);
		}
		if (field == Calendar.MINUTE) {
			done = true;
		}

		// truncate minutes
		int minutes = val.get(Calendar.MINUTE);
		if (!done && (MODIFY_TRUNCATE == modType || minutes < 30)) {
			time = time - (minutes * 60000L);
		}

		// reset time
		if (date.getTime() != time) {
			date.setTime(time);
			val.setTime(date);
		}
		// ----------------- Fix for LANG-59 ----------------------- END
		// ----------------

		boolean roundUp = false;
		for (int[] aField : FIELDS) {
			for (int element : aField) {
				if (element == field) {
					// This is our field... we stop looping
					if (modType == MODIFY_CEILING
							|| (modType == MODIFY_ROUND && roundUp)) {
						if (field == DateUtil.SEMI_MONTH) {
							// This is a special case that's hard to generalize
							// If the date is 1, we round up to 16, otherwise
							// we subtract 15 days and add 1 month
							if (val.get(Calendar.DATE) == 1) {
								val.add(Calendar.DATE, 15);
							} else {
								val.add(Calendar.DATE, -15);
								val.add(Calendar.MONTH, 1);
							}
							// ----------------- Fix for LANG-440
							// ---------------------- START ---------------
						} else if (field == Calendar.AM_PM) {
							// This is a special case
							// If the time is 0, we round up to 12, otherwise
							// we subtract 12 hours and add 1 day
							if (val.get(Calendar.HOUR_OF_DAY) == 0) {
								val.add(Calendar.HOUR_OF_DAY, 12);
							} else {
								val.add(Calendar.HOUR_OF_DAY, -12);
								val.add(Calendar.DATE, 1);
							}
							// ----------------- Fix for LANG-440
							// ---------------------- END ---------------
						} else {
							// We need at add one to this field since the
							// last number causes us to round up
							val.add(aField[0], 1);
						}
					}
					return;
				}
			}
			// We have various fields that are not easy roundings
			int offset = 0;
			boolean offsetSet = false;
			// These are special types of fields that require different rounding
			// rules
			switch (field) {
			case DateUtil.SEMI_MONTH:
				if (aField[0] == Calendar.DATE) {
					// If we're going to drop the DATE field's value,
					// we want to do this our own way.
					// We need to subtrace 1 since the date has a minimum of 1
					offset = val.get(Calendar.DATE) - 1;
					// If we're above 15 days adjustment, that means we're in
					// the
					// bottom half of the month and should stay accordingly.
					if (offset >= 15) {
						offset -= 15;
					}
					// Record whether we're in the top or bottom half of that
					// range
					roundUp = offset > 7;
					offsetSet = true;
				}
				break;
			case Calendar.AM_PM:
				if (aField[0] == Calendar.HOUR_OF_DAY) {
					// If we're going to drop the HOUR field's value,
					// we want to do this our own way.
					offset = val.get(Calendar.HOUR_OF_DAY);
					if (offset >= 12) {
						offset -= 12;
					}
					roundUp = offset >= 6;
					offsetSet = true;
				}
				break;
			}
			if (!offsetSet) {
				int min = val.getActualMinimum(aField[0]);
				int max = val.getActualMaximum(aField[0]);
				// Calculate the offset from the minimum allowed value
				offset = val.get(aField[0]) - min;
				// Set roundUp if this is more than half way between the minimum
				// and maximum
				roundUp = offset > ((max - min) / 2);
			}
			// We need to remove this field
			if (offset != 0) {
				val.set(aField[0], val.get(aField[0]) - offset);
			}
		}
		throw new IllegalArgumentException("The field " + field
				+ " is not supported");

	}

	// -----------------------------------------------------------------------
	/**
	 * <p>
	 * 截取指定单位（秒，分钟，小时，月，年）以下的时间，大于该单位的被舍弃，返回的数字以毫秒为单位
	 * </p>
	 * 
	 * <p>
	 * Asking the milliseconds of any date will only return the number of
	 * milliseconds of the current second (resulting in a number between 0 and
	 * 999). This method will retrieve the number of milliseconds for any
	 * fragment. For example, if you want to calculate the number of
	 * milliseconds past today, your fragment is Calendar.DATE or
	 * Calendar.DAY_OF_YEAR. The result will be all milliseconds of the past
	 * hour(s), minutes(s) and second(s).
	 * </p>
	 * 
	 * <p>
	 * Valid fragments are: Calendar.YEAR, Calendar.MONTH, both
	 * Calendar.DAY_OF_YEAR and Calendar.DATE, Calendar.HOUR_OF_DAY,
	 * Calendar.MINUTE, Calendar.SECOND and Calendar.MILLISECOND A fragment less
	 * than or equal to a SECOND field will return 0.
	 * </p>
	 * 
	 * <p>
	 * <ul>
	 * <li>January 1, 2008 7:15:10.538 with Calendar.SECOND as fragment will
	 * return 538</li>
	 * <li>January 6, 2008 7:15:10.538 with Calendar.SECOND as fragment will
	 * return 538</li>
	 * <li>January 6, 2008 7:15:10.538 with Calendar.MINUTE as fragment will
	 * return 10538 (10*1000 + 538)</li>
	 * <li>January 16, 2008 7:15:10.538 with Calendar.MILLISECOND as fragment
	 * will return 0 (a millisecond cannot be split in milliseconds)</li>
	 * </ul>
	 * </p>
	 * 
	 * @param date
	 *            the date to work with, not null
	 * @param fragment
	 *            the {@code Calendar} field part of date to calculate
	 * @return number of milliseconds within the fragment of date
	 * @throws IllegalArgumentException
	 *             if the date is <code>null</code> or fragment is not supported
	 */
	public static long getFragmentInMilliseconds(Date date, int fragment) {
		return getFragment(date, fragment, Calendar.MILLISECOND);
	}

	/**
	 * <p>
	 * 截取指定单位（分钟，小时，月，年）以下的时间，大于该单位的被舍弃，返回的数字以秒为单位
	 * </p>
	 * 
	 * <p>
	 * Asking the seconds of any date will only return the number of seconds of
	 * the current minute (resulting in a number between 0 and 59). This method
	 * will retrieve the number of seconds for any fragment. For example, if you
	 * want to calculate the number of seconds past today, your fragment is
	 * Calendar.DATE or Calendar.DAY_OF_YEAR. The result will be all seconds of
	 * the past hour(s) and minutes(s).
	 * </p>
	 * 
	 * <p>
	 * Valid fragments are: Calendar.YEAR, Calendar.MONTH, both
	 * Calendar.DAY_OF_YEAR and Calendar.DATE, Calendar.HOUR_OF_DAY,
	 * Calendar.MINUTE, Calendar.SECOND and Calendar.MILLISECOND A fragment less
	 * than or equal to a SECOND field will return 0.
	 * </p>
	 * 
	 * <p>
	 * <ul>
	 * <li>January 1, 2008 7:15:10.538 with Calendar.MINUTE as fragment will
	 * return 10 (equivalent to deprecated date.getSeconds())</li>
	 * <li>January 6, 2008 7:15:10.538 with Calendar.MINUTE as fragment will
	 * return 10 (equivalent to deprecated date.getSeconds())</li>
	 * <li>January 6, 2008 7:15:10.538 with Calendar.DAY_OF_YEAR as fragment
	 * will return 26110 (7*3600 + 15*60 + 10)</li>
	 * <li>January 16, 2008 7:15:10.538 with Calendar.MILLISECOND as fragment
	 * will return 0 (a millisecond cannot be split in seconds)</li>
	 * </ul>
	 * </p>
	 * 
	 * @param date
	 *            the date to work with, not null
	 * @param fragment
	 *            the {@code Calendar} field part of date to calculate
	 * @return number of seconds within the fragment of date
	 * @throws IllegalArgumentException
	 *             if the date is <code>null</code> or fragment is not supported
	 */
	public static long getFragmentInSeconds(Date date, int fragment) {
		return getFragment(date, fragment, Calendar.SECOND);
	}

	/**
	 * <p>
	 * 截取指定单位（小时，月，年）以下的时间，大于该单位的被舍弃，返回的数字以分钟为单位
	 * </p>
	 * 
	 * <p>
	 * Asking the minutes of any date will only return the number of minutes of
	 * the current hour (resulting in a number between 0 and 59). This method
	 * will retrieve the number of minutes for any fragment. For example, if you
	 * want to calculate the number of minutes past this month, your fragment is
	 * Calendar.MONTH. The result will be all minutes of the past day(s) and
	 * hour(s).
	 * </p>
	 * 
	 * <p>
	 * Valid fragments are: Calendar.YEAR, Calendar.MONTH, both
	 * Calendar.DAY_OF_YEAR and Calendar.DATE, Calendar.HOUR_OF_DAY,
	 * Calendar.MINUTE, Calendar.SECOND and Calendar.MILLISECOND A fragment less
	 * than or equal to a MINUTE field will return 0.
	 * </p>
	 * 
	 * <p>
	 * <ul>
	 * <li>January 1, 2008 7:15:10.538 with Calendar.HOUR_OF_DAY as fragment
	 * will return 15 (equivalent to deprecated date.getMinutes())</li>
	 * <li>January 6, 2008 7:15:10.538 with Calendar.HOUR_OF_DAY as fragment
	 * will return 15 (equivalent to deprecated date.getMinutes())</li>
	 * <li>January 1, 2008 7:15:10.538 with Calendar.MONTH as fragment will
	 * return 15</li>
	 * <li>January 6, 2008 7:15:10.538 with Calendar.MONTH as fragment will
	 * return 435 (7*60 + 15)</li>
	 * <li>January 16, 2008 7:15:10.538 with Calendar.MILLISECOND as fragment
	 * will return 0 (a millisecond cannot be split in minutes)</li>
	 * </ul>
	 * </p>
	 * 
	 * @param date
	 *            the date to work with, not null
	 * @param fragment
	 *            the {@code Calendar} field part of date to calculate
	 * @return number of minutes within the fragment of date
	 * @throws IllegalArgumentException
	 *             if the date is <code>null</code> or fragment is not supported
	 */
	public static long getFragmentInMinutes(Date date, int fragment) {
		return getFragment(date, fragment, Calendar.MINUTE);
	}

	/**
	 * <p>
	 * 截取指定单位（月，年）以下的时间，大于该单位的被舍弃，返回的数字以小时为单位
	 * </p>
	 * 
	 * <p>
	 * Asking the hours of any date will only return the number of hours of the
	 * current day (resulting in a number between 0 and 23). This method will
	 * retrieve the number of hours for any fragment. For example, if you want
	 * to calculate the number of hours past this month, your fragment is
	 * Calendar.MONTH. The result will be all hours of the past day(s).
	 * </p>
	 * 
	 * <p>
	 * Valid fragments are: Calendar.YEAR, Calendar.MONTH, both
	 * Calendar.DAY_OF_YEAR and Calendar.DATE, Calendar.HOUR_OF_DAY,
	 * Calendar.MINUTE, Calendar.SECOND and Calendar.MILLISECOND A fragment less
	 * than or equal to a HOUR field will return 0.
	 * </p>
	 * 
	 * <p>
	 * <ul>
	 * <li>January 1, 2008 7:15:10.538 with Calendar.DAY_OF_YEAR as fragment
	 * will return 7 (equivalent to deprecated date.getHours())</li>
	 * <li>January 6, 2008 7:15:10.538 with Calendar.DAY_OF_YEAR as fragment
	 * will return 7 (equivalent to deprecated date.getHours())</li>
	 * <li>January 1, 2008 7:15:10.538 with Calendar.MONTH as fragment will
	 * return 7</li>
	 * <li>January 6, 2008 7:15:10.538 with Calendar.MONTH as fragment will
	 * return 127 (5*24 + 7)</li>
	 * <li>January 16, 2008 7:15:10.538 with Calendar.MILLISECOND as fragment
	 * will return 0 (a millisecond cannot be split in hours)</li>
	 * </ul>
	 * </p>
	 * 
	 * @param date
	 *            the date to work with, not null
	 * @param fragment
	 *            the {@code Calendar} field part of date to calculate
	 * @return number of hours within the fragment of date
	 * @throws IllegalArgumentException
	 *             if the date is <code>null</code> or fragment is not supported
	 * @since 2.4
	 */
	public static long getFragmentInHours(Date date, int fragment) {
		return getFragment(date, fragment, Calendar.HOUR_OF_DAY);
	}

	/**
	 * <p>
	 * 截取指定单位（月，年）以下的时间，大于该单位的被舍弃，返回的数字以天为单位
	 * </p>
	 * 
	 * <p>
	 * Asking the days of any date will only return the number of days of the
	 * current month (resulting in a number between 1 and 31). This method will
	 * retrieve the number of days for any fragment. For example, if you want to
	 * calculate the number of days past this year, your fragment is
	 * Calendar.YEAR. The result will be all days of the past month(s).
	 * </p>
	 * 
	 * <p>
	 * Valid fragments are: Calendar.YEAR, Calendar.MONTH, both
	 * Calendar.DAY_OF_YEAR and Calendar.DATE, Calendar.HOUR_OF_DAY,
	 * Calendar.MINUTE, Calendar.SECOND and Calendar.MILLISECOND A fragment less
	 * than or equal to a DAY field will return 0.
	 * </p>
	 * 
	 * <p>
	 * <ul>
	 * <li>January 28, 2008 with Calendar.MONTH as fragment will return 28
	 * (equivalent to deprecated date.getDay())</li>
	 * <li>February 28, 2008 with Calendar.MONTH as fragment will return 28
	 * (equivalent to deprecated date.getDay())</li>
	 * <li>January 28, 2008 with Calendar.YEAR as fragment will return 28</li>
	 * <li>February 28, 2008 with Calendar.YEAR as fragment will return 59</li>
	 * <li>January 28, 2008 with Calendar.MILLISECOND as fragment will return 0
	 * (a millisecond cannot be split in days)</li>
	 * </ul>
	 * </p>
	 * 
	 * @param date
	 *            the date to work with, not null
	 * @param fragment
	 *            the {@code Calendar} field part of date to calculate
	 * @return number of days within the fragment of date
	 * @throws IllegalArgumentException
	 *             if the date is <code>null</code> or fragment is not supported
	 * @since 2.4
	 */
	public static long getFragmentInDays(Date date, int fragment) {
		return getFragment(date, fragment, Calendar.DAY_OF_YEAR);
	}

	/**
	 * <p>
	 * 截取指定单位（秒，分钟，小时，月，年）以下的时间，大于该单位的被舍弃，返回的数字以毫秒为单位
	 * </p>
	 * 
	 * <p>
	 * Asking the milliseconds of any date will only return the number of
	 * milliseconds of the current second (resulting in a number between 0 and
	 * 999). This method will retrieve the number of milliseconds for any
	 * fragment. For example, if you want to calculate the number of seconds
	 * past today, your fragment is Calendar.DATE or Calendar.DAY_OF_YEAR. The
	 * result will be all seconds of the past hour(s), minutes(s) and second(s).
	 * </p>
	 * 
	 * <p>
	 * Valid fragments are: Calendar.YEAR, Calendar.MONTH, both
	 * Calendar.DAY_OF_YEAR and Calendar.DATE, Calendar.HOUR_OF_DAY,
	 * Calendar.MINUTE, Calendar.SECOND and Calendar.MILLISECOND A fragment less
	 * than or equal to a MILLISECOND field will return 0.
	 * </p>
	 * 
	 * <p>
	 * <ul>
	 * <li>January 1, 2008 7:15:10.538 with Calendar.SECOND as fragment will
	 * return 538 (equivalent to calendar.get(Calendar.MILLISECOND))</li>
	 * <li>January 6, 2008 7:15:10.538 with Calendar.SECOND as fragment will
	 * return 538 (equivalent to calendar.get(Calendar.MILLISECOND))</li>
	 * <li>January 6, 2008 7:15:10.538 with Calendar.MINUTE as fragment will
	 * return 10538 (10*1000 + 538)</li>
	 * <li>January 16, 2008 7:15:10.538 with Calendar.MILLISECOND as fragment
	 * will return 0 (a millisecond cannot be split in milliseconds)</li>
	 * </ul>
	 * </p>
	 * 
	 * @param calendar
	 *            the calendar to work with, not null
	 * @param fragment
	 *            the {@code Calendar} field part of calendar to calculate
	 * @return number of milliseconds within the fragment of date
	 * @throws IllegalArgumentException
	 *             if the date is <code>null</code> or fragment is not supported
	 */
	public static long getFragmentInMilliseconds(Calendar calendar, int fragment) {
		return getFragment(calendar, fragment, Calendar.MILLISECOND);
	}

	/**
	 * <p>
	 * 截取指定单位（分钟，小时，月，年）以下的时间，大于该单位的被舍弃，返回的数字以秒为单位
	 * </p>
	 * 
	 * <p>
	 * Asking the seconds of any date will only return the number of seconds of
	 * the current minute (resulting in a number between 0 and 59). This method
	 * will retrieve the number of seconds for any fragment. For example, if you
	 * want to calculate the number of seconds past today, your fragment is
	 * Calendar.DATE or Calendar.DAY_OF_YEAR. The result will be all seconds of
	 * the past hour(s) and minutes(s).
	 * </p>
	 * 
	 * <p>
	 * Valid fragments are: Calendar.YEAR, Calendar.MONTH, both
	 * Calendar.DAY_OF_YEAR and Calendar.DATE, Calendar.HOUR_OF_DAY,
	 * Calendar.MINUTE, Calendar.SECOND and Calendar.MILLISECOND A fragment less
	 * than or equal to a SECOND field will return 0.
	 * </p>
	 * 
	 * <p>
	 * <ul>
	 * <li>January 1, 2008 7:15:10.538 with Calendar.MINUTE as fragment will
	 * return 10 (equivalent to calendar.get(Calendar.SECOND))</li>
	 * <li>January 6, 2008 7:15:10.538 with Calendar.MINUTE as fragment will
	 * return 10 (equivalent to calendar.get(Calendar.SECOND))</li>
	 * <li>January 6, 2008 7:15:10.538 with Calendar.DAY_OF_YEAR as fragment
	 * will return 26110 (7*3600 + 15*60 + 10)</li>
	 * <li>January 16, 2008 7:15:10.538 with Calendar.MILLISECOND as fragment
	 * will return 0 (a millisecond cannot be split in seconds)</li>
	 * </ul>
	 * </p>
	 * 
	 * @param calendar
	 *            the calendar to work with, not null
	 * @param fragment
	 *            the {@code Calendar} field part of calendar to calculate
	 * @return number of seconds within the fragment of date
	 * @throws IllegalArgumentException
	 *             if the date is <code>null</code> or fragment is not supported
	 */
	public static long getFragmentInSeconds(Calendar calendar, int fragment) {
		return getFragment(calendar, fragment, Calendar.SECOND);
	}

	/**
	 * <p>
	 * 截取指定单位（小时，月，年）以下的时间，大于该单位的被舍弃，返回的数字以分钟为单位
	 * </p>
	 * 
	 * <p>
	 * Asking the minutes of any date will only return the number of minutes of
	 * the current hour (resulting in a number between 0 and 59). This method
	 * will retrieve the number of minutes for any fragment. For example, if you
	 * want to calculate the number of minutes past this month, your fragment is
	 * Calendar.MONTH. The result will be all minutes of the past day(s) and
	 * hour(s).
	 * </p>
	 * 
	 * <p>
	 * Valid fragments are: Calendar.YEAR, Calendar.MONTH, both
	 * Calendar.DAY_OF_YEAR and Calendar.DATE, Calendar.HOUR_OF_DAY,
	 * Calendar.MINUTE, Calendar.SECOND and Calendar.MILLISECOND A fragment less
	 * than or equal to a MINUTE field will return 0.
	 * </p>
	 * 
	 * <p>
	 * <ul>
	 * <li>January 1, 2008 7:15:10.538 with Calendar.HOUR_OF_DAY as fragment
	 * will return 15 (equivalent to calendar.get(Calendar.MINUTES))</li>
	 * <li>January 6, 2008 7:15:10.538 with Calendar.HOUR_OF_DAY as fragment
	 * will return 15 (equivalent to calendar.get(Calendar.MINUTES))</li>
	 * <li>January 1, 2008 7:15:10.538 with Calendar.MONTH as fragment will
	 * return 15</li>
	 * <li>January 6, 2008 7:15:10.538 with Calendar.MONTH as fragment will
	 * return 435 (7*60 + 15)</li>
	 * <li>January 16, 2008 7:15:10.538 with Calendar.MILLISECOND as fragment
	 * will return 0 (a millisecond cannot be split in minutes)</li>
	 * </ul>
	 * </p>
	 * 
	 * @param calendar
	 *            the calendar to work with, not null
	 * @param fragment
	 *            the {@code Calendar} field part of calendar to calculate
	 * @return number of minutes within the fragment of date
	 * @throws IllegalArgumentException
	 *             if the date is <code>null</code> or fragment is not supported
	 */
	public static long getFragmentInMinutes(Calendar calendar, int fragment) {
		return getFragment(calendar, fragment, Calendar.MINUTE);
	}

	/**
	 * <p>
	 * 截取指定单位（月，年）以下的时间，大于该单位的被舍弃，返回的数字以小时为单位
	 * </p>
	 * 
	 * <p>
	 * Asking the hours of any date will only return the number of hours of the
	 * current day (resulting in a number between 0 and 23). This method will
	 * retrieve the number of hours for any fragment. For example, if you want
	 * to calculate the number of hours past this month, your fragment is
	 * Calendar.MONTH. The result will be all hours of the past day(s).
	 * </p>
	 * 
	 * <p>
	 * Valid fragments are: Calendar.YEAR, Calendar.MONTH, both
	 * Calendar.DAY_OF_YEAR and Calendar.DATE, Calendar.HOUR_OF_DAY,
	 * Calendar.MINUTE, Calendar.SECOND and Calendar.MILLISECOND A fragment less
	 * than or equal to a HOUR field will return 0.
	 * </p>
	 * 
	 * <p>
	 * <ul>
	 * <li>January 1, 2008 7:15:10.538 with Calendar.DAY_OF_YEAR as fragment
	 * will return 7 (equivalent to calendar.get(Calendar.HOUR_OF_DAY))</li>
	 * <li>January 6, 2008 7:15:10.538 with Calendar.DAY_OF_YEAR as fragment
	 * will return 7 (equivalent to calendar.get(Calendar.HOUR_OF_DAY))</li>
	 * <li>January 1, 2008 7:15:10.538 with Calendar.MONTH as fragment will
	 * return 7</li>
	 * <li>January 6, 2008 7:15:10.538 with Calendar.MONTH as fragment will
	 * return 127 (5*24 + 7)</li>
	 * <li>January 16, 2008 7:15:10.538 with Calendar.MILLISECOND as fragment
	 * will return 0 (a millisecond cannot be split in hours)</li>
	 * </ul>
	 * </p>
	 * 
	 * @param calendar
	 *            the calendar to work with, not null
	 * @param fragment
	 *            the {@code Calendar} field part of calendar to calculate
	 * @return number of hours within the fragment of date
	 * @throws IllegalArgumentException
	 *             if the date is <code>null</code> or fragment is not supported
	 * @since 2.4
	 */
	public static long getFragmentInHours(Calendar calendar, int fragment) {
		return getFragment(calendar, fragment, Calendar.HOUR_OF_DAY);
	}

	/**
	 * <p>
	 * 截取指定单位（月，年）以下的时间，大于该单位的被舍弃，返回的数字以天为单位
	 * </p>
	 * 
	 * <p>
	 * Asking the days of any date will only return the number of days of the
	 * current month (resulting in a number between 1 and 31). This method will
	 * retrieve the number of days for any fragment. For example, if you want to
	 * calculate the number of days past this year, your fragment is
	 * Calendar.YEAR. The result will be all days of the past month(s).
	 * </p>
	 * 
	 * <p>
	 * Valid fragments are: Calendar.YEAR, Calendar.MONTH, both
	 * Calendar.DAY_OF_YEAR and Calendar.DATE, Calendar.HOUR_OF_DAY,
	 * Calendar.MINUTE, Calendar.SECOND and Calendar.MILLISECOND A fragment less
	 * than or equal to a DAY field will return 0.
	 * </p>
	 * 
	 * <p>
	 * <ul>
	 * <li>January 28, 2008 with Calendar.MONTH as fragment will return 28
	 * (equivalent to calendar.get(Calendar.DAY_OF_MONTH))</li>
	 * <li>February 28, 2008 with Calendar.MONTH as fragment will return 28
	 * (equivalent to calendar.get(Calendar.DAY_OF_MONTH))</li>
	 * <li>January 28, 2008 with Calendar.YEAR as fragment will return 28
	 * (equivalent to calendar.get(Calendar.DAY_OF_YEAR))</li>
	 * <li>February 28, 2008 with Calendar.YEAR as fragment will return 59
	 * (equivalent to calendar.get(Calendar.DAY_OF_YEAR))</li>
	 * <li>January 28, 2008 with Calendar.MILLISECOND as fragment will return 0
	 * (a millisecond cannot be split in days)</li>
	 * </ul>
	 * </p>
	 * 
	 * @param calendar
	 *            the calendar to work with, not null
	 * @param fragment
	 *            the {@code Calendar} field part of calendar to calculate
	 * @return number of days within the fragment of date
	 * @throws IllegalArgumentException
	 *             if the date is <code>null</code> or fragment is not supported
	 * @since 2.4
	 */
	public static long getFragmentInDays(Calendar calendar, int fragment) {
		return getFragment(calendar, fragment, Calendar.DAY_OF_YEAR);
	}

	/**
	 * Date-version for fragment-calculation in any unit
	 * 
	 * @param date
	 *            the date to work with, not null
	 * @param fragment
	 *            the Calendar field part of date to calculate
	 * @param unit
	 *            the {@code Calendar} field defining the unit
	 * @return number of units within the fragment of the date
	 * @throws IllegalArgumentException
	 *             if the date is <code>null</code> or fragment is not supported
	 * @since 2.4
	 */
	private static long getFragment(Date date, int fragment, int unit) {
		if (date == null) {
			throw new IllegalArgumentException("The date must not be null");
		}
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		return getFragment(calendar, fragment, unit);
	}

	/**
	 * Calendar-version for fragment-calculation in any unit
	 * 
	 * @param calendar
	 *            the calendar to work with, not null
	 * @param fragment
	 *            the Calendar field part of calendar to calculate
	 * @param unit
	 *            the {@code Calendar} field defining the unit
	 * @return number of units within the fragment of the calendar
	 * @throws IllegalArgumentException
	 *             if the date is <code>null</code> or fragment is not supported
	 * @since 2.4
	 */
	private static long getFragment(Calendar calendar, int fragment, int unit) {
		if (calendar == null) {
			throw new IllegalArgumentException("The date must not be null");
		}
		long millisPerUnit = getMillisPerUnit(unit);
		long result = 0;

		// Fragments bigger than a day require a breakdown to days
		switch (fragment) {
		case Calendar.YEAR:
			result += (calendar.get(Calendar.DAY_OF_YEAR) * MILLIS_PER_DAY)
					/ millisPerUnit;
			break;
		case Calendar.MONTH:
			result += (calendar.get(Calendar.DAY_OF_MONTH) * MILLIS_PER_DAY)
					/ millisPerUnit;
			break;
		}

		switch (fragment) {
		// Number of days already calculated for these cases
		case Calendar.YEAR:
		case Calendar.MONTH:

			// The rest of the valid cases
		case Calendar.DAY_OF_YEAR:
		case Calendar.DATE:
			result += (calendar.get(Calendar.HOUR_OF_DAY) * MILLIS_PER_HOUR)
					/ millisPerUnit;
			//$FALL-THROUGH$
		case Calendar.HOUR_OF_DAY:
			result += (calendar.get(Calendar.MINUTE) * MILLIS_PER_MINUTE)
					/ millisPerUnit;
			//$FALL-THROUGH$
		case Calendar.MINUTE:
			result += (calendar.get(Calendar.SECOND) * MILLIS_PER_SECOND)
					/ millisPerUnit;
			//$FALL-THROUGH$
		case Calendar.SECOND:
			result += (calendar.get(Calendar.MILLISECOND) * 1) / millisPerUnit;
			break;
		case Calendar.MILLISECOND:
			break;// never useful
		default:
			throw new IllegalArgumentException("The fragment " + fragment
					+ " is not supported");
		}
		return result;
	}

	/**
	 * <p>
	 * 判断两个时间在指定进度之上的时间是否相等
	 * </p>
	 * 
	 * @param cal1
	 *            the first calendar, not <code>null</code>
	 * @param cal2
	 *            the second calendar, not <code>null</code>
	 * @param field
	 *            the field from {@code Calendar}
	 * @return <code>true</code> if equal; otherwise <code>false</code>
	 * @throws IllegalArgumentException
	 *             if any argument is <code>null</code>
	 * @see #truncate(Calendar, int)
	 * @see #truncatedEquals(Date, Date, int)
	 */
	public static boolean truncatedEquals(Calendar cal1, Calendar cal2,
			int field) {
		return truncatedCompareTo(cal1, cal2, field) == 0;
	}

	/**
	 * <p>
	 * 判断两个时间在指定进度之上的时间是否相等
	 * </p>
	 * 
	 * @param date1
	 *            the first date, not <code>null</code>
	 * @param date2
	 *            the second date, not <code>null</code>
	 * @param field
	 *            the field from {@code Calendar}
	 * @return <code>true</code> if equal; otherwise <code>false</code>
	 * @throws IllegalArgumentException
	 *             if any argument is <code>null</code>
	 * @see #truncate(Date, int)
	 * @see #truncatedEquals(Calendar, Calendar, int)
	 */
	public static boolean truncatedEquals(Date date1, Date date2, int field) {
		return truncatedCompareTo(date1, date2, field) == 0;
	}

	/**
	 * <p>
	 * 比较两个时间在指定进度之上时间的大小
	 * </p>
	 * 
	 * @param cal1
	 *            the first calendar, not <code>null</code>
	 * @param cal2
	 *            the second calendar, not <code>null</code>
	 * @param field
	 *            the field from {@code Calendar}
	 * @return a negative integer, zero, or a positive integer as the first
	 *         calendar is less than, equal to, or greater than the second.
	 * @throws IllegalArgumentException
	 *             if any argument is <code>null</code>
	 * @see #truncate(Calendar, int)
	 * @see #truncatedCompareTo(Date, Date, int)
	 */
	public static int truncatedCompareTo(Calendar cal1, Calendar cal2, int field) {
		Calendar truncatedCal1 = truncate(cal1, field);
		Calendar truncatedCal2 = truncate(cal2, field);
		return truncatedCal1.compareTo(truncatedCal2);
	}

	/**
	 * <p>
	 * 比较两个时间在指定进度之上时间的大小
	 * </p>
	 * 
	 * @param date1
	 *            the first date, not <code>null</code>
	 * @param date2
	 *            the second date, not <code>null</code>
	 * @param field
	 *            the field from <code>Calendar</code>
	 * @return a negative integer, zero, or a positive integer as the first date
	 *         is less than, equal to, or greater than the second.
	 * @throws IllegalArgumentException
	 *             if any argument is <code>null</code>
	 * @see #truncate(Calendar, int)
	 * @see #truncatedCompareTo(Date, Date, int)
	 */
	public static int truncatedCompareTo(Date date1, Date date2, int field) {
		Date truncatedDate1 = truncate(date1, field);
		Date truncatedDate2 = truncate(date2, field);
		return truncatedDate1.compareTo(truncatedDate2);
	}

	/**
	 * <p>
	 * 获取每个单位的毫秒数,例如小时有60*60*1000毫秒
	 * </p>
	 * 
	 * @param unit
	 *            a {@code Calendar} field constant which is a valid unit for a
	 *            fragment
	 * @return the number of milliseconds in the field
	 * @throws IllegalArgumentException
	 *             if date can't be represented in milliseconds
	 */
	private static long getMillisPerUnit(int unit) {
		long result = Long.MAX_VALUE;
		switch (unit) {
		case Calendar.DAY_OF_YEAR:
		case Calendar.DATE:
			result = MILLIS_PER_DAY;
			break;
		case Calendar.HOUR_OF_DAY:
			result = MILLIS_PER_HOUR;
			break;
		case Calendar.MINUTE:
			result = MILLIS_PER_MINUTE;
			break;
		case Calendar.SECOND:
			result = MILLIS_PER_SECOND;
			break;
		case Calendar.MILLISECOND:
			result = 1;
			break;
		default:
			throw new IllegalArgumentException("The unit " + unit
					+ " cannot be represented is milleseconds");
		}
		return result;
	}

	// -----------------------------------------------------------------------
	/**
	 * <pre>
	 * 比较两个日历对象
	 * 
	 * 示例：
	 * Calendar c1 = Calendar.getInstance();
	 * 		try {
	 * 			Thread.sleep(1000);
	 * 			Calendar c2 = Calendar.getInstance();
	 * 			System.out.println(DateUtil.compare(c2, c1, Calendar.SECOND));//1
	 * 		} catch (InterruptedException e) {
	 * 			e.printStackTrace();
	 * 		}
	 * </pre>
	 * 
	 * @param cal1
	 *            日历对象1
	 * @param cal2
	 *            日历对象2
	 * @param field
	 *            Calendar的字段类型 例如：Calendar.SECOND 更多类型请查询相关API
	 * @return 第一个日历对象与第二个日历对象的差值
	 */
	public static int compare(Calendar cal1, Calendar cal2, int field) {
		int n1 = cal1.get(field);
		int n2 = cal2.get(field);
		return n1 - n2;
		// return DateUtil.truncatedCompareTo(cal1, cal2, field);
	}

	/**
	 * <pre>
	 * 比较两个日期对象
	 * 
	 * 示例：
	 * public void testcompare2() {
	 * 		Date date1 = new Date();
	 * 		try {
	 * 			Thread.sleep(1000);
	 * 		} catch (InterruptedException e) {
	 * 			e.printStackTrace();
	 * 		}
	 * 			Date date2 = new Date();
	 * 			int i = DateUtil.compare(date2, date1, Calendar.SECOND);
	 * 			System.out.println(i); //1
	 * 		}
	 * </pre>
	 * 
	 * @param dt1
	 *            日期对象1
	 * @param dt2
	 *            日期对象2
	 * @param field
	 *            Calendar的字段类型 例如：Calendar.SECOND 更多类型请查询相关API
	 * @return 第一个日期对象与第二个日期对象的差值
	 */
	public static int compare(Date dt1, Date dt2, int field) {
		Calendar cal1 = Calendar.getInstance();
		cal1.setTime(dt1);
		Calendar cal2 = Calendar.getInstance();
		cal2.setTime(dt2);

		int n1 = cal1.get(field);
		int n2 = cal2.get(field);
		return n1 - n2;
		// return DateUtil.truncatedCompareTo(dt1, dt2, field);
	}

	/**
	 * <pre>
	 * 增加日期字符串对应字段的值 
	 * 
	 * 示例：
	 * DateUtil.add("2012-10-23", "yyyy-MM-dd", Calendar.YEAR, 5)
	 * returns "Mon Oct 23 00:00:00 CST 2017";
	 * 
	 * @param dateStr
	 *            日期型字符串
	 * @param pattern
	 *            上述字符串对应的日期格式说明，参考java的日期格式说明
	 * @param field
	 *            Calendar字段
	 * @param amount
	 *            要增加的额度
	 * @return 值增加后的日期
	 * @throws ParseException
	 */
	public static Date add(String dateStr, String pattern, int field, int amount)
			throws ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat(pattern);

		Date dt = sdf.parse(dateStr);
		Calendar cal = Calendar.getInstance();
		cal.setTime(dt);

		cal.add(field, amount);
		return cal.getTime();

	}

	/**
	 * <pre>
	 * 将日期格式化为本地格式
	 * 
	 * 示例：DateUtil.format(new Date())
	 * returns "2013-6-4 16:08:05"
	 * </pre>
	 * 
	 * @param dt
	 *            日期
	 * @return 本地格式的日期
	 */
	@SuppressWarnings("deprecation")
	public static String format(Date dt) {
		return dt.toLocaleString();
	}

	/**
	 * <pre>
	 * 将日期格式化为指定的日期格式
	 * 
	 * 示例：
	 * DateUtil.format(new Date(), "yyyy-MM-dd");
	 * returns "2013-6-4"
	 * </pre>
	 * 
	 * @param dt
	 *            日期
	 * @param pattern
	 *            日期格式
	 * @return 转换后的日期
	 */
	public static String format(Date dt, String pattern) {
		SimpleDateFormat sdf = new SimpleDateFormat(pattern);
		return sdf.format(dt);
	}

	/**
	 * <pre>
	 * 按给定格式解析字符串为时间对象
	 * 
	 * 示例：
	 * DateUtil.parse("2012-12-25", "yyyy-MM-dd");
	 * 
	 * returns 2012-12-25 0:00:00
	 * </pre>
	 * 
	 * @param strDate
	 *            要转换的字符串时间
	 * @param pattern
	 *            转换的格式
	 * @return 转换后的日期
	 * @throws ParseException
	 */
	public static Date parses(String strDate, String pattern)
			throws ParseException {
		return new SimpleDateFormat(pattern).parse(strDate);
	}

	/**
	 * <pre>
	 * 将日期型的字符串解析为日期型
	 * 
	 *  示例：
	 * DateUtil.parse("2012-12-25", "yyyy-MM-dd");
	 * 
	 * returns 2012-12-25 0:00:00
	 * </pre>
	 * 
	 * @param dateStr
	 *            日期型的字符串
	 * @param pattern
	 *            日期型的格式
	 * @return 转换后的日期
	 * @throws ParseException
	 */
	public static Date parse(String dateStr, String pattern)
			throws ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat(pattern);
		return sdf.parse(dateStr);
	}

	/**
	 * 对时间的月份部分进行加减操作，返回yyyy-MM-dd HH:mm:ss格式字符串
	 * 
	 * @param ts
	 *            要进行操作的时间字符串
	 * @param i
	 *            要增加或减少的月份数
	 * @return 日期字符串
	 */
	public static String addMonths(String ts, int i) {
		Calendar now = Calendar.getInstance();
		Timestamp t = Timestamp.valueOf(ts);
		now.setTime(t);
		SimpleDateFormat formatter = new SimpleDateFormat(PATTERN_TIME);
		now.add(Calendar.MONTH, +(i));
		String dt = formatter.format(now.getTime());
		return dt;
	}

	/**
	 * 对时间的分钟部分进行加减操作，返回yyyy-MM-dd HH:mm:ss格式字符串
	 * 
	 * @param ts
	 *            要进行操作的时间字符串
	 * @param i
	 *            要增加或减少的分钟数
	 * @return 日期字符串
	 */
	public static String addMinutes(String ts, int i) {
		Calendar now = Calendar.getInstance();
		Timestamp t = Timestamp.valueOf(ts);
		now.setTime(t);
		SimpleDateFormat formatter = new SimpleDateFormat(PATTERN_TIME);
		now.add(Calendar.MINUTE, +(i));
		String dt = formatter.format(now.getTime());
		return dt;
	}

	/**
	 * 返回当前日期为yyyy-MM-dd格式字符串
	 * 
	 * @return str
	 */
	public static String getNowDate() {
		Date nowDate = new Date();
		// Calendar now = Calendar.getInstance();
		// now.setTime(nowDate);
		SimpleDateFormat formatter = new SimpleDateFormat(PATTERN_DAY);
		String str = formatter.format(nowDate);
		// str=getNextDate(str,-1);
		return str;
	}

	/**
	 * long 转字符时间
	 * 
	 * @param time
	 * @return
	 */
	public static String getDate(long time) {
		Date date = new Date(time);
		SimpleDateFormat formatter = new SimpleDateFormat(PATTERN_TIME);
		return formatter.format(date);
	}

	/**
	 * 返回当前时间的yyyy-MM-dd HH:mm:ss格式字符串
	 * 
	 * @return str
	 */
	public static String getNowTime() {
		Date nowDate = new Date();
		// Calendar now = Calendar.getInstance();
		// now.setTime(nowDate);
		SimpleDateFormat formatter = new SimpleDateFormat(PATTERN_TIME);
		String str = formatter.format(nowDate);
		return str;
	}

	// /**
	// * 将给定的时间字符串格式化为yyyy-MM-dd HH:mm格式的字符串
	// * @param dTime
	// * @return str
	// */
	// public static String formatDateTime(String dTime) {
	// String dateTime = "";
	// if (dTime != null && !"".equals(dTime)
	// && !dTime.startsWith("1900-01-01")) {
	// Timestamp t = Timestamp.valueOf(dTime);
	// SimpleDateFormat formatter = new SimpleDateFormat(
	// "yyyy-MM-dd HH:mm");
	// dateTime = formatter.format(t);
	// }
	// return dateTime;
	// }
	//
	// /**
	// * 将给定的时间字符串格式化为HH:mm:ss格式的字符串
	// * @param dTime
	// * @return str
	// */
	// public static String formatTime(String dTime) {
	// String dateTime = "";
	// if (dTime != null && !"".equals(dTime)) {
	// Timestamp t = Timestamp.valueOf(dTime);
	// SimpleDateFormat formatter = new SimpleDateFormat("HH:mm:ss");
	// dateTime = formatter.format(t);
	// }
	// return dateTime;
	// }

	/**
	 * UTC时间转换
	 * 
	 * @param utcTime
	 *            utc格式时间
	 * @param utcTimePatten
	 *            utc时间格式，可以不带后缀<BR>
	 *            2015-01-08T15:11:11.000+08:00<BR>
	 *            yyyy-MM-dd'T'HH:mm:ss.SSS'+08:00' 格式写成<BR>
	 *            yyyy-MM-dd'T'HH:mm:ss.SSS即可
	 * @param localTimePatten
	 *            本地时间格式
	 * @return 本地时间字符串
	 * @throws ParseException
	 */
	public static String utc2Local(String utcTime, String utcTimePatten,
			String localTimePatten) throws ParseException {
		SimpleDateFormat utcFormater = new SimpleDateFormat(utcTimePatten);
		utcFormater.setTimeZone(TimeZone.getTimeZone("UTC"));
		Date gpsUTCDate = utcFormater.parse(utcTime);
		SimpleDateFormat localFormater = new SimpleDateFormat(localTimePatten);
		localFormater.setTimeZone(TimeZone.getDefault());
		String localTime = localFormater.format(gpsUTCDate.getTime());
		return localTime;
	}


	/**
	 * 将字符串转换成日期类型,自动匹配格式
	 *
	 * @param date
	 * @return
	 */
	public static Date stringToDate(String date) {
		try {
			return org.apache.commons.lang3.time.DateUtils.parseDate(date, PARSEPATTERNS);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 字符串格式转日期
	 *
	 * @param date
	 * @param parsePatterns
	 * @return
	 */
	public static Date stringToDate(String date, String... parsePatterns) {
		try {
			return org.apache.commons.lang3.time.DateUtils.parseDate(date, parsePatterns);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 日期转字符串
	 *
	 * @param date
	 * @param pattern
	 * @return
	 */
	public static String dateToString(Date date, String pattern) {
		if (date != null) {
			SimpleDateFormat sdf = new SimpleDateFormat(pattern);
			return sdf.format(date);
		}
		return "";
	}

	/**
	 * 日期转字符串
	 *
	 * @param date 日期
	 * @return 日期
	 */
	public static String dateToString(Date date) {
		return dateToString(date, PATTERN_TIME);
	}

	/**
	 * 增加n天后的日期
	 *
	 * @param date 日期
	 * @param n 数值
	 * @return 日期
	 */
	public static Date addDay(Date date, int n) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.DATE, n);// 增加n天
		return calendar.getTime();
	}

	/**
	 * 增加n个月后的日期
	 *
	 * @param date 日期
	 * @param n 数值
	 * @return 日期
	 */
	public static Date addMonth(Date date, int n) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.MONTH, n);// 增加n个月
		return calendar.getTime();
	}

	/**
	 * 获取当前月第一天
	 *
	 * @return Date
	 */
	public static Date firstDayOfMonth() {
		Calendar c = Calendar.getInstance();
		c.setTime(new Date());
		c.set(Calendar.DAY_OF_MONTH, 1);// 设置为1号,当前日期既为本月第一天
		return c.getTime();
	}

	public static Date firstDayOfWeek(Date date) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		c.setFirstDayOfWeek(Calendar.MONDAY);//设置一个星期的第一天，按中国的习惯一个星期的第一天是星期一
		int day = c.get(Calendar.DAY_OF_WEEK);//获得当前日期是一个星期的第几天
		c.add(Calendar.DATE, c.getFirstDayOfWeek()-day);//根据日历的规则，给当前日期减去星期几与一个星期第一天的差值
		return c.getTime();
	}

	/**
	 * 获取是第几周
	 * @param date
	 * @return
	 */
	public static long getFragmentInWeek(Date date){
		Calendar c=Calendar.getInstance();
        c.setTime(DateUtils.addDays(date, -1));
        return c.get(Calendar.WEEK_OF_YEAR);
	}

    public static String getNextWeek(String weekString){
        if (weekString == null || !weekString.matches("\\d{5,6}"))
            throw new IllegalArgumentException("参数不合法,week="+weekString);
        String year = weekString.substring(0,4);
        String week = weekString.substring(4);
        long lastWeek = getFragmentInWeek(stringToDate(year+"-12-31"));
        if (lastWeek -1 == Long.valueOf(week))
            return Long.valueOf(year)+1+"1";
        return year+(Long.valueOf(week) + 1);
    }

	/**
	 * 获取这年的第几周
	 * @param week
	 * @return
	 */
	public static Date getFirstDateAfterWeek(Year year, long week){
		Date date = stringToDate(year + "-01-01");
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		int i = cal.get(Calendar.DAY_OF_WEEK);
		int k = 2 - i;
		i = k>0?k:7+k;
		return DateUtils.addWeeks(DateUtils.addDays(date, i), (int)(week - 1));
	}
}
