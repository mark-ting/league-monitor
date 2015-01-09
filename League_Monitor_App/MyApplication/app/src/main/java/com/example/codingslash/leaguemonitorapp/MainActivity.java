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

import java.util.Map;

import constant.Region;
import dto.Summoner.Summoner;
import main.java.riotapi.RiotApi;
import main.java.riotapi.RiotApiException;


public class MainActivity extends ActionBarActivity {

//    public final static String SUMMONER_NAME = "com.example.codingslash.leaguemonitorapp.SUMMONERNAME";
//    public final static String SUMMONER_ID = "come.example.codingslash.leaguemonitorapp.SUMMONERID";

    ProgressDialog progressdialog;

    private class LookupSummonerID extends AsyncTask<String, Void, Long> {

        private Context context;
        private PowerManager.WakeLock wakelock;
        public LookupSummonerID(Context contextin) {
            context = contextin;
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
        protected Long doInBackground(String... input) {

            RiotApi api = new RiotApi(context.getString(R.string.riot_api_key));

            api.setRegion(Region.NA);

            Map<String, Summoner> summoners = null;
            try {
                summoners = api.getSummonersByName(input[0]);
            } catch (RiotApiException e) {
                e.printStackTrace();
            }

            // default ID indicates below segment not running
            Summoner summoner;
            long id;

            if (summoners != null) {
                summoner = summoners.get(input[0]);
                id = summoner.getId();
            } else {
                // dummy ID indicates not found
                id = 1234567890L;
            }

            return id;
        }

        @Override
        protected void onPostExecute(Long summonerid) {

            // hide progress dialog
            progressdialog.hide();

            // release wake lock
            wakelock.release();

            // TODO: FILL OUT INTENT SO IT ISNT BROKEN
            Intent intent = new Intent(getApplicationContext(), SummonerInfoActivity.class);
            EditText editText = (EditText) findViewById(R.id.field_summoner_name);

            String summonername = editText.getText().toString();

            if (summonerid == 1234567890L) {
                // toast for ID not found
                Toast.makeText(context, "Summoner ID NOT FOUND", Toast.LENGTH_LONG).show();

                intent.putExtra("SUMMONER_NAME", "SUMMONER NOT FOUND");
            } else {
                // toast for ID found
                Toast.makeText(context, "Summoner ID FOUND", Toast.LENGTH_LONG).show();

                intent.putExtra("SUMMONER_NAME", summonername);
                intent.putExtra("SUMMONER_ID", summonerid);
            }
            startActivity(intent);

        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // instantiate private fields
        progressdialog = new ProgressDialog(this);
        progressdialog.setMessage("Running ID Lookup...");
        progressdialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressdialog.setIndeterminate(true);
    }

    /** Called when the user clicks the Send button **/
    public void querySummoner (View view) {
        EditText summonernamefield = (EditText) findViewById(R.id.field_summoner_name);
        String summonername = summonernamefield.getText().toString();
        new LookupSummonerID(this).execute(summonername);
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
