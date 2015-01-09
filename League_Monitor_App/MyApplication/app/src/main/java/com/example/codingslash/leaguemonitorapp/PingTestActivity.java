package com.example.codingslash.leaguemonitorapp;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.PowerManager;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;


public class PingTestActivity extends ActionBarActivity {

    public static final String TAG = "PingTestActivity";
    public static final String RIOT_SERVER_IP = "216.52.241.254";

    ProgressDialog progressdialog;

    private class PingTask extends AsyncTask<String, Void, Double> {

        private Context context;
        private PowerManager.WakeLock wakelock;

        public PingTask(Context contextin) {
            context = contextin;
        }

        // wake lock, show the progress dialog, disable button
        @Override
        public void onPreExecute() {
            // take CPU lock to prevent CPU from going off if the user
            // presses the power button during download
            PowerManager pm = (PowerManager) context.getSystemService(Context.POWER_SERVICE);
            wakelock = pm.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK,
                getClass().getName());
            wakelock.acquire();

            // show progress dialog
            progressdialog.show();

            // disable button
            findViewById(R.id.button_ping_test).setClickable(false);
        }

        // run the ping test
        @Override
        public Double doInBackground(String... str) {
            PingTest p = new PingTest(str[0]);
            return p.ping_time();
        }

        // display average ping time, hide progress dialog, enable button, release wakelock
        @Override
        public void onPostExecute(Double num) {
            // display the times
            TextView t = (TextView)findViewById(R.id.text_ping_time);
            t.setText(num + " ms");

            // hide progress dialog
            progressdialog.hide();

            // release wake lock
            wakelock.release();

            // enable button
            findViewById(R.id.button_ping_test).setClickable(true);
        }
    }

    public void run_ping_test(View view) {
        // execute the PingTask
        new PingTask(this).execute(RIOT_SERVER_IP);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ping_test);

        // instantiate private fields
        progressdialog = new ProgressDialog(this);
        progressdialog.setMessage("Running Ping Test...");
        progressdialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressdialog.setIndeterminate(true);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_ping_test, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
