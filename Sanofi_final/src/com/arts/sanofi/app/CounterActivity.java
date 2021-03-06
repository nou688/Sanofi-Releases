package com.arts.sanofi.app;

import java.util.ArrayList;
import java.util.Calendar;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;

import com.arts.sanofi.app.report.ReportHelper;
import com.arts.sanofi.app.report.ReportHelper.ReportJsonReadListener;
import com.arts.sanofi.app.report.ReportItemWrapper;

public class CounterActivity extends SanofiActivity implements
        ReportJsonReadListener {

    private Context mContext;

    private ReportHelper mReportHelper;
    private boolean isReportReady = false;
    private boolean isQuit = false;
    private long mStartTimeInMillis;
    private long mEndTimeInMillis;
    private String mPeriod;

    private ReportItemWrapper mReportItem;

    protected int mAppId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        mContext = getApplicationContext();

        initReport();

    }

    private void initReport() {
        ReportHelper.reset();
        mReportHelper = ReportHelper.getInstance(mContext);
        mReportHelper.readJsonReportFile(mAppId, this);
        Calendar currentCalendar = Calendar.getInstance();
        mStartTimeInMillis = currentCalendar.getTimeInMillis();
        mPeriod = "" + (currentCalendar.get(Calendar.MONTH) + 1) + "/"
                + currentCalendar.get(Calendar.YEAR);
    }

    @Override
    protected void onDestroy() {
        isQuit = true;
        mEndTimeInMillis = Calendar.getInstance().getTimeInMillis();
        saveReport();

        super.onDestroy();
    }

    private void saveReport() {

        if (isReportReady) {
            mReportItem.setUsersNumber(mReportItem.getUsersNumber() + 1);
            mReportItem.setUseTimeInMillis(mReportItem.getUseTimeInMillis()
                    + (mEndTimeInMillis - mStartTimeInMillis));

            mReportHelper.createJsonReportFile(mAppId);
            // convert Json to report file
            mReportHelper.createGlobalReportFile(mAppId);
        }
    }

    @Override
    public void onJsonReadingFinished(
            ArrayList<ReportItemWrapper> reportItemsList) {

        Log.v("slim", "onJsonReadingFinished");

        isReportReady = true;

        mReportItem = mReportHelper.getReportItemByPeriod(mPeriod);
        if (mReportItem == null) {
            mReportItem = new ReportItemWrapper(mPeriod, 0, 0);
            mReportHelper.addReportItem(mReportItem);
        }

        // this will prevent losing info if quitting the application was
        // performed before finishing reading the report file
        if (isQuit) {
            saveReport();
        }

    }
}
