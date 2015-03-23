
package com.example.administrator.helloandroid.pkg_adapter;

/**
 * Hyun Ki Ung on 2015-03-22
 */
public class GridView_DayInfo {

    private String day; // 달력일자
    private String week_Header; // 달력헤더 (일,월,화...)
    private String add_LastDay; // 지난달 일자
    private String add_NextDay; // 다음달 일자
    private String full_Day;

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

    // 전체 날짜 저장
    public void setFullDay(String fDay) {
        this.full_Day = fDay;
    }

    // 전체 날짜 반환
    public String getFullDay() {
        return full_Day;
    }

}
