package com.arts.sanofi.quiz.report;

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

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

public class ReportHelper {

    private static final String JSON_REPORT_FILE_NAME = "report.json";
    private static final String GLOBAL_REPORT_FILE_NAME = "report_quiz.txt";

    private static volatile ReportHelper mInstance = null;

    private String mInternalFolderPath;
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
        mInternalFolderPath = context.getFilesDir().getAbsolutePath()
                + File.separator + JSON_REPORT_FILE_NAME;
        mReportItemsList = new ArrayList<ReportItemWrapper>();
    }

    public void addReportItem(ReportItemWrapper reportItemWrapper) {
        mReportItemsList.add(reportItemWrapper);
    }

    public void createGlobalReportFile() {

        WriteReportFileAsyncTask writeReportFileAsyncTask = new WriteReportFileAsyncTask(
                Environment.getExternalStorageDirectory().getAbsolutePath()
                        + File.separator + GLOBAL_REPORT_FILE_NAME,
                prepareReportContent());
        writeReportFileAsyncTask.execute();
    }

    public void createJsonReportFile() {
        WriteJsonReportAsyncTask writeJsonReportAsyncTask = new WriteJsonReportAsyncTask(
                mInternalFolderPath, mReportItemsList);
        writeJsonReportAsyncTask.execute();
    }

    public void readJsonReportFile(ReportJsonReadListener listener) {
        ReadJsonFileAsyncTask readJsonFileAsyncTask = new ReadJsonFileAsyncTask(
                listener);
        readJsonFileAsyncTask.execute();
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

        private ReportJsonReadListener mItemReadingListener;

        public ReadJsonFileAsyncTask(ReportJsonReadListener listener) {

            mItemReadingListener = listener;
        }

        @Override
        protected ArrayList<ReportItemWrapper> doInBackground(Void... params) {
            ArrayList<ReportItemWrapper> reportItemsArrayList = new ArrayList<ReportItemWrapper>();

            Gson gson = new Gson();

            File contentFile = new File(mInternalFolderPath);
            if (contentFile.exists()) {
                try {
                    BufferedReader bufferedReader = new BufferedReader(
                            new FileReader(mInternalFolderPath));

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
