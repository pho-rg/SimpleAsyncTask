package com.example.simpleasynctask;

import android.os.AsyncTask;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.lang.ref.WeakReference;
import java.util.Random;

public class SimpleAsyncTask extends AsyncTask <Void, Void, String> {
    private WeakReference<TextView> mTextView;
    private WeakReference<ProgressBar> mProgressBar;

    SimpleAsyncTask(TextView tv, ProgressBar progressBar) {
        mTextView = new WeakReference<>(tv);
        mProgressBar = new WeakReference<>(progressBar);
    }

    @Override
    protected String doInBackground(Void... voids) {
        Random r = new Random();
        int n = r.nextInt(11);
        int s = n * 200;
        int progress = 0;
        int sleepTime = s / 100;

        for (int i = 0; i < 100; i++) {
            try {
                Thread.sleep(sleepTime);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            progress++;
            publishProgress(progress);
        }

        try {
            Thread.sleep(s - progress * sleepTime);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return "Enfin réveillé après avoir dormi pendant " + s + " millisecondes !";
    }

    private void publishProgress(int progress) {
        mProgressBar.get().setProgress(progress);
    }
    protected void onPostExecute(String result) {
        mTextView.get().setText(result);
    }
}