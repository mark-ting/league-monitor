package com.example.codingslash.leaguemonitorapp;

import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


public class PingTestActivity extends ActionBarActivity {

    private class PingTask extends AsyncTask<String, Void, Integer> {

        // set up the progress dialog, disable button
        public void onPreExecute() {
            // set up progress dialog
                // TODO

            // disable button
            ((Button)findViewById(R.id.button_ping_test)).setClickable(false);
        }

        // run the ping test
        public Integer doInBackground(String... str) {
            PingTest p = new PingTest(str[0]);
            return p.ping_time();
        }

        // display average ping time, hide progress dialog, enable button
        public  void onPostExecute(Integer num) {
            // display the times
            TextView t = (TextView)findViewById(R.id.button_ping_test);
            t.setText(num + " ms");

            // hide progress dialog
                // TODO

            // enable button
            ((Button)findViewById(R.id.button_ping_test)).setClickable(true);
        }
    }

    public void run_ping_test(View view) {
        // TODO: do the asynctask call
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ping_test);
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
