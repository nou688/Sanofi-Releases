package com.arts.sanofi.app.report;

import com.google.gson.annotations.SerializedName;

public class ReportItemWrapper {

    @SerializedName("period")
    private String mPeriod;
    @SerializedName("users_number")
    private long mUsersNumber;
    @SerializedName("use_time_in_millis")
    private long mUseTimeInMillis;

    public ReportItemWrapper(String period, long usersNumber, long useTime) {
        mPeriod = period;
        mUsersNumber = usersNumber;
        mUseTimeInMillis = useTime;
    }

    public String getPeriod() {
        return mPeriod;
    }

    public void setPeriod(String period) {
        this.mPeriod = period;
    }

    public long getUsersNumber() {
        return mUsersNumber;
    }

    public void setUsersNumber(long usersNumber) {
        this.mUsersNumber = usersNumber;
    }

    public long getUseTimeInMillis() {
        return mUseTimeInMillis;
    }

    public void setUseTimeInMillis(long useTimeInMillis) {
        this.mUseTimeInMillis = useTimeInMillis;
    }

}
