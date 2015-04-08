package com.arts.sanofi.app.report;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Environment;
import android.util.Log;

import com.arts.sanofi.app.AppsActivity;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

public class ReportHelper {

    private static final String JSON_HYGIENE_REPORT_FILE_NAME = "report_hygiene.json";
    private static final String JSON_REGLE_REPORT_FILE_NAME = "report_regles.json";
    private static final String JSON_MAUX_REPORT_FILE_NAME = "report_maux.json";
    private static final String JSON_VITAMINES_REPORT_FILE_NAME = "report_vitamines.json";
    private static final String JSON_DOULEURS_REPORT_FILE_NAME = "report_douleurs.json";

    private static final String HYGIENE_REPORT_FILE_NAME = "report_hygiene.txt";
    private static final String REGLE_REPORT_FILE_NAME = "report_regle.txt";
    private static final String MAUX_REPORT_FILE_NAME = "report_maux.txt";
    private static final String VITAMINES_REPORT_FILE_NAME = "report_vitamines.txt";
    private static final String DOULEURS_REPORT_FILE_NAME = "report_douleurs.txt";

    private static volatile ReportHelper mInstance = null;

    private Context mContext;

    private ArrayList<ReportItemWrapper> mReportItemsList;

    public static ReportHelper getInstance(Context context) {

        synchronized (ReportHelper.class) {
            if (mInstance == null) {
                mInstance = new ReportHelper(context);
            }
        }
        return mInstance;
    }

    private ReportHelper(Context context) {
        mReportItemsList = new ArrayList<ReportItemWrapper>();
        mContext = context;
    }

    public void addReportItem(ReportItemWrapper reportItemWrapper) {
        mReportItemsList.add(reportItemWrapper);
    }

    public void createGlobalReportFile(int appId) {
        String fileName = "";
        switch (appId) {
        case AppsActivity.SEQUENCE_HYGIENE_ID:
            fileName = HYGIENE_REPORT_FILE_NAME;
            break;
        case AppsActivity.SEQUENCE_REGLES_DOULEREUSES_ID:
            fileName = REGLE_REPORT_FILE_NAME;
            break;
        case AppsActivity.SEQUENCE_MAUX_ESTOMAC_ID:
            fileName = MAUX_REPORT_FILE_NAME;
            break;
        case AppsActivity.SEQUENCE_VITAMINE_ID:
            fileName = VITAMINES_REPORT_FILE_NAME;
            break;
        case AppsActivity.SEQUENCE_DOULEUR_ID:
            fileName = DOULEURS_REPORT_FILE_NAME;
            break;

        default:
            break;
        }

        Log.v("slim", "createGlobalReportFile:app id = " + appId);

        WriteReportFileAsyncTask writeReportFileAsyncTask = new WriteReportFileAsyncTask(
                Environment.getExternalStorageDirectory().getAbsolutePath()
                        + File.separator + fileName, prepareReportContent());
        writeReportFileAsyncTask.execute();
    }

    public void createJsonReportFile(int appId) {
        Log.v("slim", "createJsonReportFile : app id = " + appId);
        WriteJsonReportAsyncTask writeJsonReportAsyncTask = new WriteJsonReportAsyncTask(
                getJsonFilePath(appId), mReportItemsList);
        writeJsonReportAsyncTask.execute();
    }

    public void readJsonReportFile(int appId, ReportJsonReadListener listener) {
        Log.v("slim", "readJsonReportFile : app id = " + appId);
        ReadJsonFileAsyncTask readJsonFileAsyncTask = new ReadJsonFileAsyncTask(
                getJsonFilePath(appId), listener);
        readJsonFileAsyncTask.execute();
    }

    private String getJsonFilePath(int appId) {

        String filePath = "";
        switch (appId) {
        case AppsActivity.SEQUENCE_HYGIENE_ID:
            filePath = JSON_HYGIENE_REPORT_FILE_NAME;
            break;
        case AppsActivity.SEQUENCE_REGLES_DOULEREUSES_ID:
            filePath = JSON_REGLE_REPORT_FILE_NAME;
            break;
        case AppsActivity.SEQUENCE_MAUX_ESTOMAC_ID:
            filePath = JSON_MAUX_REPORT_FILE_NAME;
            break;
        case AppsActivity.SEQUENCE_VITAMINE_ID:
            filePath = JSON_VITAMINES_REPORT_FILE_NAME;
            break;
        case AppsActivity.SEQUENCE_DOULEUR_ID:
            filePath = JSON_DOULEURS_REPORT_FILE_NAME;
            break;
        default:
            break;
        }
        return mContext.getFilesDir().getAbsolutePath() + File.separator
                + filePath;
    }

    public ReportItemWrapper getReportItemByPeriod(String period) {
        ReportItemWrapper reportItemWrapper = null;

        for (ReportItemWrapper reportItem : mReportItemsList) {
            if (period.equals(reportItem.getPeriod())) {
                reportItemWrapper = reportItem;
                break;
            }
        }

        return reportItemWrapper;
    }

    private String prepareReportContent() {
        String content = "";
        for (ReportItemWrapper reportItem : mReportItemsList) {
            content = content + reportItem.getPeriod() + "\n";
            content = content + "Nombre utilisateurs: "
                    + reportItem.getUsersNumber() + "\n";
            content = content + "Temps d'utilisation: "
                    + convertTimetoReadable(reportItem.getUseTimeInMillis())
                    + "\n\n";
        }

        return content;
    }

    private String convertTimetoReadable(long timeInMillis) {
        String result = "";
        long timeInSeconds = timeInMillis / 1000;
        int hours = (int) timeInSeconds / 3600;
        int minutes = (int) (timeInSeconds % 3600) / 60;
        int seconds = (int) timeInSeconds % 60;
        result = hours + "h" + String.format("%02d", minutes) + "min"
                + String.format("%02d", seconds) + "s";
        return result;
    }

    public interface ReportJsonReadListener {
        public void onJsonReadingFinished(
                ArrayList<ReportItemWrapper> reportItemsList);
    }

    /* *************************************
     * *********** private classes *********
     * *************************************
     */

    private class WriteJsonReportAsyncTask extends AsyncTask<Void, Void, Void> {

        private String mJsonFilePath;
        private ArrayList<ReportItemWrapper> mReportItemsArrayList;

        public WriteJsonReportAsyncTask(String contentFilePath,
                ArrayList<ReportItemWrapper> reportItemsList) {
            mJsonFilePath = contentFilePath;
            mReportItemsArrayList = reportItemsList;
        }

        @Override
        protected Void doInBackground(Void... params) {

            Gson gson = new GsonBuilder().setPrettyPrinting().create();

            // convert java object to JSON format,
            // and returned as JSON formatted string
            String json = gson.toJson(mReportItemsArrayList);

            // write converted JSON data to content file
            FileWriter writer;
            try {
                writer = new FileWriter(mJsonFilePath);
                writer.write(json);
                writer.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }
    }

    /**
     * 
     * @author Slim BENHAMMOUDA
     * 
     */
    private class WriteReportFileAsyncTask extends AsyncTask<Void, Void, Void> {

        private String mFilePath;
        private String mFileContent;

        public WriteReportFileAsyncTask(String filePath, String content) {
            mFilePath = filePath;
            mFileContent = content;
        }

        @Override
        protected Void doInBackground(Void... params) {

            try {

                File file = new File(mFilePath);

                // if file doesn't exist, then create it
                if (!file.exists()) {
                    file.createNewFile();
                }

                FileWriter fileWriter = new FileWriter(file.getAbsoluteFile());
                BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
                bufferedWriter.write(mFileContent);
                bufferedWriter.close();
                fileWriter.close();

            } catch (IOException e) {
                e.printStackTrace();
            }

            return null;
        }
    }

    private class ReadJsonFileAsyncTask extends
            AsyncTask<Void, Void, ArrayList<ReportItemWrapper>> {

        private String mFilePath;
        private ReportJsonReadListener mItemReadingListener;

        public ReadJsonFileAsyncTask(String filePath,
                ReportJsonReadListener listener) {

            mFilePath = filePath;
            mItemReadingListener = listener;
        }

        @Override
        protected ArrayList<ReportItemWrapper> doInBackground(Void... params) {
            ArrayList<ReportItemWrapper> reportItemsArrayList = new ArrayList<ReportItemWrapper>();

            Gson gson = new Gson();

            File contentFile = new File(mFilePath);
            if (contentFile.exists()) {
                try {
                    BufferedReader bufferedReader = new BufferedReader(
                            new FileReader(mFilePath));

                    // convert the JSON string back to
                    reportItemsArrayList = gson.fromJson(bufferedReader,
                            new TypeToken<ArrayList<ReportItemWrapper>>() {
                            }.getType());
                    bufferedReader.close();
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {

                    e.printStackTrace();

                }
            }
            return reportItemsArrayList;
        }

        @Override
        protected void onPostExecute(
                ArrayList<ReportItemWrapper> reportItemsList) {

            mReportItemsList.addAll(reportItemsList);
            mItemReadingListener.onJsonReadingFinished(mReportItemsList);

        }
    }

    public static void reset() {
        mInstance = null;
    }

}
