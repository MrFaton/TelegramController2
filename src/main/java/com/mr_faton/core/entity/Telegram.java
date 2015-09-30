package com.mr_faton.core.entity;

import java.io.Serializable;

public class Telegram implements Serializable {
    private String header;
    private String digitalHeader;

    private int beginHour;
    private int beginMin;

    private int periodInMin;
    private int delayInMin;

    private int stopHour;
    private int stopMin;

    private boolean aroundTheClock;
    private boolean monday;
    private boolean tuesday;
    private boolean wednesday;
    private boolean thursday;
    private boolean friday;
    private boolean saturday;
    private boolean sunday;

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

    public int getDelayInMin() {
        return delayInMin;
    }
    public void setDelayInMin(int delayInMin) {
        this.delayInMin = delayInMin;
    }

    public int getStopHour() {
        return stopHour;
    }
    public void setStopHour(int stopHour) {
        this.stopHour = stopHour;
    }

    public int getStopMin() {
        return stopMin;
    }
    public void setStopMin(int stopMin) {
        this.stopMin = stopMin;
    }


    public boolean isAroundTheClock() {
        return aroundTheClock;
    }
    public void setAroundTheClock(boolean aroundTheClock) {
        this.aroundTheClock = aroundTheClock;
    }

    public boolean isMonday() {
        return monday;
    }
    public void setMonday(boolean monday) {
        this.monday = monday;
    }

    public boolean isTuesday() {
        return tuesday;
    }
    public void setTuesday(boolean tuesday) {
        this.tuesday = tuesday;
    }

    public boolean isWednesday() {
        return wednesday;
    }
    public void setWednesday(boolean wednesday) {
        this.wednesday = wednesday;
    }

    public boolean isThursday() {
        return thursday;
    }
    public void setThursday(boolean thursday) {
        this.thursday = thursday;
    }

    public boolean isFriday() {
        return friday;
    }
    public void setFriday(boolean friday) {
        this.friday = friday;
    }

    public boolean isSaturday() {
        return saturday;
    }
    public void setSaturday(boolean saturday) {
        this.saturday = saturday;
    }

    public boolean isSunday() {
        return sunday;
    }
    public void setSunday(boolean sunday) {
        this.sunday = sunday;
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
        return this.header.equals(otherTelegram.header) && this.digitalHeader.equals(otherTelegram.digitalHeader);
    }
}
