
package com.example.administrator.helloandroid.pkg_adapter;

/**
 * Hyun Ki Ung on 2015-03-22
 */
public class GridView_DayInfo {

    private String day;
    private String week_Header;
    private String add_LastDay;
    private String add_NextDay;
    private boolean inMonth;

    // 요일 헤더 반환
    public String getWeek() {
        return week_Header;
    }

    // 요일 헤더 저장
    public void setWeek(String week)
    {
        this.week_Header = week;
    }

    // 지난달 추가 셀 반환
    public String getLastDay() {
        return add_LastDay;
    }

    // 지난달 추가 셀 저장
    public void setLastDay(String lastDay)
    {
        this.add_LastDay = lastDay;
    }

    // 다음달 추가 셀 반환
    public String getNextDay() {
        return add_NextDay;
    }

    // 다음달 추가 셀 저장
    public void setNextDay(String nextDay)
    {
        this.add_NextDay = nextDay;
    }

    // 날짜 반환
    public String getDay() {
        return day;
    }

    // 날짜 저장
    public void setDay(String day)
    {
        this.day = day;
    }

    // 이번달의 날짜인지 정보 반환 inMonth(true/false)
    public boolean isInMonth()
    {
        return inMonth;
    }

    // 이번달의 날짜인지 정보 저장 inMonth(true/false)
    public void setInMonth(boolean inMonth)
    {
        this.inMonth = inMonth;
    }

}
