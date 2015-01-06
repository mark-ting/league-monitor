package com.example.codingslash.leaguemonitorapp;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Debug;
import android.os.PowerManager;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.gson.internal.bind.ReflectiveTypeAdapterFactory;


public class PingTestActivity extends ActionBarActivity {

    public static final String TAG = "PingTestActivity";

    public static final String RIOT_SERVER_IP_NA = "216.52.241.254";
    public static final String RIOT_SERVER_IP_EUW = "95.172.65.1";
    public static final String RIOT_SERVER_IP_EUNE = "64.7.194.1";

    private String server = "NA";

    private ProgressDialog progressdialog;

    private class SpinnerActivity extends Activity implements AdapterView.OnItemSelectedListener {

        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
            server = (String)parent.getItemAtPosition(pos);
            Log.d(TAG, server);
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {
            // nothing, since default is NA
        }
    }

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
        public  void onPostExecute(Double num) {
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

    public void runPingTest(View view) {
        PingTask pt = new PingTask(this);
        // execute the PingTask
        switch(server) {
            case "NA":
                pt.execute(RIOT_SERVER_IP_NA);
                break;
            case "EUW":
                pt.execute(RIOT_SERVER_IP_EUW);
                break;
            case "EUNE":
                pt.execute(RIOT_SERVER_IP_EUNE);
                break;
            default:
                pt.execute("www.google.com");
        }
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
        progressdialog.setCanceledOnTouchOutside(false);

        // set default fields for spinner
        Spinner spinner = (Spinner) findViewById(R.id.riot_server_region_spinner);
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.riot_server_regions_array, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        spinner.setAdapter(adapter);
        // Set spinners item listener
        spinner.setOnItemSelectedListener(new SpinnerActivity());
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
