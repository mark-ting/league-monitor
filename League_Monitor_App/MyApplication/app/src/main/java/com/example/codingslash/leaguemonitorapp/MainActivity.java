package com.example.codingslash.leaguemonitorapp;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.PowerManager;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends ActionBarActivity {

    private ProgressDialog progressdialog;

    private class SummonerLookup extends AsyncTask<String, Void, SummonerInfo> {



        private Context context;
        private String apikey;

        private PowerManager.WakeLock wakelock;

        public SummonerLookup(Context contextin) {
            this.context = contextin;
            apikey = context.getString(R.string.riot_api_key);
        }

        @Override
        protected void onPreExecute() {
            // take CPU lock to prevent CPU from going off if the user
            // presses the power button during download
            PowerManager pm = (PowerManager) context.getSystemService(Context.POWER_SERVICE);
            wakelock = pm.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK,
                    getClass().getName());
            wakelock.acquire();

            // show progress dialog
            progressdialog.show();
        }

        @Override
        protected SummonerInfo doInBackground(String... input) {
            LookupSummonerInfo lookup = new LookupSummonerInfo(apikey);
            return lookup.lookupSummoner(input[0]);
        }

        @Override
        protected void onPostExecute(SummonerInfo info) {

            // hide progress dialog
            progressdialog.hide();

            // release wake lock
            wakelock.release();

            Intent intent = new Intent(context, SummonerInfoActivity.class);

            if (info.getSummId() == -1L) {
                // toast for ID not found
                Toast.makeText(context, "Summoner ID NOT FOUND", Toast.LENGTH_LONG).show();
            } else {
                // toast for ID found
                Toast.makeText(context, "Summoner ID FOUND", Toast.LENGTH_LONG).show();

                intent.putExtra("SUMMONER_INFO", info);
                startActivity(intent);
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // instantiate private fields
        progressdialog = new ProgressDialog(this);
        progressdialog.setMessage("Looking for summoner...");
        progressdialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressdialog.setIndeterminate(true);
        progressdialog.setCanceledOnTouchOutside(false);
    }

    /** Called when the user clicks the Lookup Summoner button **/
    public void querySummoner (View view) {
        EditText summonernamefield = (EditText) findViewById(R.id.field_summoner_name);
        String summonername = summonernamefield.getText().toString();

        // TODO MAKE A MORE ROBUST CHECK FOR USER INPUT

        if (ValidateInput.validateInput(summonername)) {
            new SummonerLookup(this).execute(summonername);
        } else {
            summonernamefield.setError("Please enter a summoner name!");
        }
    }

    public void pingTestActivity(View view)
    {
        Intent intent = new Intent(this, PingTestActivity.class);
        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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




