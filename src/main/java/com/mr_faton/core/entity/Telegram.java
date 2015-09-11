package com.mr_faton.core.entity;

import java.io.Serializable;

public class Telegram implements Serializable {
    private String header;
    private String digitalHeader;

    private int beginHour;
    private int beginMin;

    private int periodInMin;

    private boolean state;

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

    public boolean getState() {
        return state;
    }
    public void setState(boolean state) {
        this.state = state;
    }

    public long getNextTime() {
        return nextTime;
    }
    public void setNextTime(long nextTime) {
        this.nextTime = nextTime;
    }

    @Override
    public String toString() {
        return header;
    }

    @Override
    public int hashCode() {
        return header.hashCode() + digitalHeader.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) return false;
        if (this.getClass() != obj.getClass()) return false;
        Telegram otherTelegram = (Telegram) obj;
        return this.header == otherTelegram.header && this.digitalHeader == otherTelegram.digitalHeader;
    }
}
