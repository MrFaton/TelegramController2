package com.mr_faton.core.entity;

import java.io.Serializable;

public class Telegram implements Serializable {
    private String header;
    private String digitalHeader;

    private int beginHour;
    private int beginMin;

    private int periodInMin;

    private long nextTime;

    public String getHeader() {
        return header;
    }
    public void setHeader(String header) {
        this.header = header;
    }

    public String getDigitalHeader() {
        return digitalHeader;
    }
    public void setDigitalHeader(String digitalHeader) {
        this.digitalHeader = digitalHeader;
    }

    public int getBeginHour() {
        return beginHour;
    }
    public void setBeginHour(int beginHour) {
        this.beginHour = beginHour;
    }

    public int getBeginMin() {
        return beginMin;
    }
    public void setBeginMin(int beginMin) {
        this.beginMin = beginMin;
    }

    public int getPeriodInMin() {
        return periodInMin;
    }
    public void setPeriodInMin(int periodInMin) {
        this.periodInMin = periodInMin;
    }
}
