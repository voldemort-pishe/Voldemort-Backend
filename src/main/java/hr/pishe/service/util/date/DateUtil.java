package hr.pishe.service.util.date;


import com.ibm.icu.text.DateFormat;
import com.ibm.icu.util.Calendar;
import com.ibm.icu.util.PersianCalendar;
import com.ibm.icu.util.ULocale;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import java.time.*;
import java.time.temporal.TemporalAdjusters;
import java.util.Date;
import java.util.GregorianCalendar;


public class DateUtil {

  public static Date xmlCalendarToGregorianDate(XMLGregorianCalendar xmlGregorianCalendar) {
    if (xmlGregorianCalendar == null) {
      try {
        Date date = new Date();
        GregorianCalendar c = new GregorianCalendar();
        c.setTime(date);
        return DateUtil.xmlCalendarToGregorianDate(DatatypeFactory.newInstance().newXMLGregorianCalendar(c));
      } catch (DatatypeConfigurationException e) {
        e.printStackTrace();
      }
    }
    return xmlGregorianCalendar.toGregorianCalendar().getTime();
  }

  public static XMLGregorianCalendar gregorianDateToXMLGregorianCalendar(Date date) {
    GregorianCalendar gCalendar = new GregorianCalendar();
    gCalendar.setTime(date);
    XMLGregorianCalendar xmlCalendar = null;
    try {
      xmlCalendar = DatatypeFactory.newInstance().newXMLGregorianCalendar(gCalendar);
    } catch (DatatypeConfigurationException e) {
      e.printStackTrace();
    }
    return xmlCalendar;
  }


    /**
     * Convert Gregorian date to Persian date
     *
     * @param date
     * @return
     */
  public static String gDateToPersianDate(Date date) {
    GregorianCalendar gCalendar = new GregorianCalendar();
    gCalendar.setTime(date);
    PersianCalendar persianCalendar = new PersianCalendar(gCalendar.getTime());
    StringBuffer stringBuffer = new StringBuffer();
    stringBuffer.append(persianCalendar.get(Calendar.YEAR)).append(String.format("%02d", persianCalendar.get(Calendar.MONTH) + 1))
      .append(String.format("%02d", persianCalendar.get(Calendar.DATE)));
    return stringBuffer.toString();
  }


  public static DateFields gregorianDateToPersianDateFields(Date date, boolean... withAM_PM) {
    GregorianCalendar gCalendar = new GregorianCalendar();
    gCalendar.setTime(date);
    PersianCalendar persianCalendar = new PersianCalendar(gCalendar.getTime());
    DateFields dateFields = new DateFields();
    dateFields.setYear(String.valueOf(persianCalendar.get(Calendar.YEAR)));
    dateFields.setMonth(String.format("%02d", persianCalendar.get(Calendar.MONTH) + 1));
    dateFields.setDay(String.format("%02d", persianCalendar.get(Calendar.DATE)));
    dateFields.setHour(String.format("%02d", persianCalendar.get(Calendar.HOUR_OF_DAY)));
    dateFields.setMinute(String.format("%02d", persianCalendar.get(Calendar.MINUTE)));
    dateFields.setSecond(String.format("%02d", persianCalendar.get(Calendar.SECOND)));
    if (withAM_PM.length > 0) {
      dateFields.setHour(String.format("%02d", persianCalendar.get(Calendar.HOUR)));
      if (persianCalendar.get(Calendar.AM_PM) > 0) {
        dateFields.setAM_PM("ب.ظ");
      } else {
        dateFields.setAM_PM("ق.ظ");
      }
    }
    return dateFields;
  }


    /**
     * Convert Gregorian date to Persian date
     *
     * @param date
     * @return
     */
  public static String gToPersianWithTime(Date date) {
    if (date == null) date = new Date();
    GregorianCalendar gCalendar = new GregorianCalendar();
    gCalendar.setTime(date);
    PersianCalendar persianCalendar = new PersianCalendar(gCalendar.getTime());
    StringBuffer stringBuffer = new StringBuffer();
    stringBuffer.append(persianCalendar.get(Calendar.YEAR)).append("/").append(String.format("%02d", persianCalendar.get(Calendar.MONTH) + 1))
      .append("/").append(String.format("%02d", persianCalendar.get(Calendar.DATE))).append(" ").append(String.format("%02d", persianCalendar.get(Calendar.HOUR_OF_DAY)))
      .append(":").append(String.format("%02d", persianCalendar.get(Calendar.MINUTE)));
    return stringBuffer.toString();
  }

  public static Date persianDateToGregorianDate(Date persianDate) {
    GregorianCalendar gregorianCalendar = new GregorianCalendar();
    gregorianCalendar.setTime(persianDate);
    return gregorianCalendar.getTime();
  }

  public static Date stringToPersianDate(String stringDate) {
    PersianCalendar persianCalendar = new PersianCalendar();
    String[] dateAndHoursArray = stringDate.split(" ");
    String date = dateAndHoursArray[0];
    String[] dateArray = date.split("/");
    if (dateAndHoursArray.length == 1) {
      persianCalendar.set(Integer.valueOf(dateArray[0]), Integer.valueOf(dateArray[1]) - 1, Integer.valueOf(dateArray[2])
        , 0, 0);
      return persianCalendar.getTime();

    } else if (dateAndHoursArray.length == 2) {
      String hours = dateAndHoursArray[1];
      String[] hoursArray = hours.split(":");
      persianCalendar.set(Integer.valueOf(dateArray[0]), Integer.valueOf(dateArray[1]) - 1, Integer.valueOf(dateArray[2])
        , Integer.valueOf(hoursArray[0]), Integer.valueOf(hoursArray[1]));
      return persianCalendar.getTime();
    }

    return null;
  }

  public static Date fromDaysAgo(int day) {
    Date today = new Date();
    GregorianCalendar calendar = new GregorianCalendar();
    calendar.setTime(today);
    calendar.add(Calendar.DAY_OF_MONTH, -day);
    return calendar.getTime();
  }

  public static String gregorianDateToFullPersianText(Date date) {
    try {
      ULocale locale = new ULocale("fa_IR@calendar=persian");
      Calendar calendar = Calendar.getInstance(locale);
      calendar.setTime(date);
      DateFormat df = DateFormat.getDateInstance(DateFormat.FULL, locale);
      String output = df.format(calendar).toString();
      return output.substring(0, output.length() - 6);
    } catch (Exception e) {
      return null;
    }
  }

  public static String gregorianDateToFullPersianTextWithoutDayName(Date date) {
    try {
      ULocale locale = new ULocale("fa_IR@calendar=persian");
      Calendar calendar = Calendar.getInstance(locale);
      calendar.setTime(date);
      DateFormat df = DateFormat.getDateInstance(DateFormat.FULL, locale);
      String output = df.format(calendar).toString();
      String[] strings = output.split(" ");
      return output.substring(strings[0].length() + 1, output.length() - 6);
    } catch (Exception e) {
      return null;
    }
  }


	public static Date getEndOfDay(Date date) {
		LocalDateTime localDateTime = dateToLocalDateTime(date);
		LocalDateTime endOfDay = localDateTime.with(LocalTime.MAX);
		return localDateTimeToDate(endOfDay);
	}

	public static Date getStartOfDay(Date date) {
		LocalDateTime localDateTime = dateToLocalDateTime(date);
		 LocalDateTime startOfDay = localDateTime.with(LocalTime.MIN);
		return localDateTimeToDate(startOfDay);
	}

	public static Date getStartOfMonth(Date date) {
		LocalDateTime localDateTime = dateToLocalDateTime(date);
		localDateTime = localDateTime.withDayOfMonth(1);
		LocalDateTime startOfDay = localDateTime.with(LocalTime.MIN);
		return localDateTimeToDate(startOfDay);
	}

	public static Date getStartOfYear(Date date) {
		LocalDateTime localDateTime = dateToLocalDateTime(date);
		LocalDateTime startOfDay = localDateTime.with(TemporalAdjusters.firstDayOfYear());
		return localDateTimeToDate(startOfDay);
	}

	private static Date localDateTimeToDate(LocalDateTime startOfDay) {
		return Date.from(startOfDay.atZone(ZoneId.systemDefault()).toInstant());
	}

	private static LocalDateTime dateToLocalDateTime(Date date) {
		return LocalDateTime.ofInstant(Instant.ofEpochMilli(date.getTime()), ZoneId.systemDefault());
	}

	public static Date toDate(LocalDateTime localDateTime){
  		return Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
	}

	public static Date toDate(ZonedDateTime zonedDateTime){
		return Date.from(zonedDateTime.toInstant());
	}

	public static Date plusHour(Date date,Integer hours){
		java.util.Calendar calendar = java.util.Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(java.util.Calendar.HOUR,hours);

		return calendar.getTime();
	}

	public static Date getFirstDayOfPersianMonth(Date date) {

		GregorianCalendar gCalendar = new GregorianCalendar();
		gCalendar.setTime(date);
		PersianCalendar persianCalendar = new PersianCalendar(gCalendar.getTime());

		Calendar cal = new PersianCalendar();
		cal.set(PersianCalendar.YEAR, persianCalendar.get(PersianCalendar.YEAR));
		cal.set(PersianCalendar.DAY_OF_MONTH, 1);

		return cal.getTime();
	}

	public static Date getLastDayOfPersianMonth(Date date) {

		GregorianCalendar gCalendar = new GregorianCalendar();
		gCalendar.setTime(date);
		PersianCalendar persianCalendar = new PersianCalendar(gCalendar.getTime());

		Calendar cal = new PersianCalendar();
		cal.set(PersianCalendar.YEAR, persianCalendar.get(PersianCalendar.YEAR));
		if (persianCalendar.get(PersianCalendar.MONTH) > 5) {
			cal.set(PersianCalendar.DAY_OF_MONTH, 30);
		} else {
			cal.set(PersianCalendar.DAY_OF_MONTH, 31);
		}

		return cal.getTime();
	}

	public static Date getFirstDayOfPersianYear(Date date) {

		GregorianCalendar gCalendar = new GregorianCalendar();
		gCalendar.setTime(date);
		PersianCalendar persianCalendar = new PersianCalendar(gCalendar.getTime());

		Calendar cal = new PersianCalendar();
		cal.set(PersianCalendar.YEAR, persianCalendar.get(PersianCalendar.YEAR));
		cal.set(PersianCalendar.DAY_OF_YEAR, 1);

		return cal.getTime();
	}

	public static Date getLastDayOfPersianYear(Date date) {

		GregorianCalendar gCalendar = new GregorianCalendar();
		gCalendar.setTime(date);
		PersianCalendar persianCalendar = new PersianCalendar(gCalendar.getTime());


		Calendar cal = new PersianCalendar();
		cal.getActualMaximum(Calendar.DAY_OF_MONTH);
		cal.set(PersianCalendar.YEAR, persianCalendar.get(PersianCalendar.YEAR));
		cal.set(PersianCalendar.MONTH, 11);
		if (date.getYear() % 4 != 0) {
			cal.set(PersianCalendar.DAY_OF_MONTH, 29);
		} else {
			cal.set(PersianCalendar.DAY_OF_MONTH, 30);
		}

		return cal.getTime();
	}
}
