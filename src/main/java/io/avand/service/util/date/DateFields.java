package io.avand.service.util.date;


public class DateFields {

  private String year;
  private String month;
  private String day;
  private String hour;
  private String minute;
  private String second;
  private String AM_PM;

  public String getYear() {
    return year;
  }

  public void setYear(String year) {
    this.year = year;
  }

  public String getMonth() {
    return month;
  }

  public void setMonth(String month) {
    this.month = month;
  }

  public String getDay() {
    return day;
  }

  public void setDay(String day) {
    this.day = day;
  }

  public String getHour() {
    return hour;
  }

  public void setHour(String hour) {
    this.hour = hour;
  }

  public String getMinute() {
    return minute;
  }

  public void setMinute(String minute) {
    this.minute = minute;
  }

  public String getSecond() {
    return second;
  }

  public void setSecond(String second) {
    this.second = second;
  }

  public String getAM_PM() {
    return AM_PM;
  }

  public void setAM_PM(String AM_PM) {
    this.AM_PM = AM_PM;
  }

  @Override
  public String toString() {
    return "DateFields{" +
      "year=" + year +
      ", month=" + month +
      ", day=" + day +
      ", hour=" + hour +
      ", minute=" + minute +
      ", second=" + second +
      ", AM_PM=" + AM_PM +
      '}';
  }
}
