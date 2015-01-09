package com.example.codingslash.leaguemonitorapp;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.PowerManager;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Map;

import constant.Region;
import dto.Summoner.Summoner;
import main.java.riotapi.RiotApi;
import main.java.riotapi.RiotApiException;

public class LookupSummonerActivity extends ActionBarActivity {

    public final static String SUMMONER_NAME = "com.example.codingslash.leaguemonitorapp.MESSAGE";
    public final static String SUMMONER_ID = "come.example.codingslash.leaguemonitorapp.MESSAGE";

    ProgressDialog progressdialog;

//    public long queryID(String input) {
//        RiotApi api = new RiotApi(getResources().getString(R.string.riot_api_key));
//
//        api.setRegion(Region.NA);
//        Map<String, Summoner> summoners = null;
//        try {
//            summoners = api.getSummonersByName(input);
//        } catch (RiotApiException e) {
//            e.printStackTrace();
//        }
//        Summoner summoner = summoners.get(input);
//        long id = summoner.getId();
//
//        return id;
//    }

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

//            String cleaninput = null;
//            try {
//                cleaninput = URLEncoder.encode(input[0], "UTF-8");
//            } catch (UnsupportedEncodingException e) {
//                e.printStackTrace();
//            }

            RiotApi api = new RiotApi(context.getString(R.string.riot_api_key));

            api.setRegion(Region.NA);
            Map<String, Summoner> summoners = null;
            try {
                summoners = api.getSummonersByName(input[0]);
            } catch (RiotApiException e) {
                e.printStackTrace();
            }

            long id = 100;

            Summoner summoner = null;
            if (summoners != null) {
                summoner = summoners.get(input[0]);
                id = summoner.getId();
            } else id = 1234567890;

            return id;
        }

        @Override
        protected void onPostExecute(Long id) {
            // hide progress dialog
            progressdialog.hide();

            // release wake lock
            wakelock.release();

            Toast.makeText(context, "" + id, Toast.LENGTH_LONG).show();
        }
    }

    public void queryRunes(View view) {
        Intent intent = new Intent(this, DummyDisplay.class);
        EditText editText = (EditText) findViewById(R.id.field_summoner_name);
        String summonername = editText.getText().toString();

        new LookupSummonerID(this).execute(summonername);
    }


    // IGNORE ALL SHIT BELOW THIS LINE WIP WIP WIP
    public void queryMasteries(View view) {
        Intent intent = new Intent(this, DummyDisplay.class);
        EditText editText = (EditText) findViewById(R.id.field_summoner_name);
        String summonername = editText.getText().toString();
        intent.putExtra(SUMMONER_NAME, summonername);
        startActivity(intent);
    }

    public void queryLadder(View view) {
        Intent intent = new Intent(this, DummyDisplay.class);
        EditText editText = (EditText) findViewById(R.id.field_summoner_name);
        String summonername = editText.getText().toString();
        intent.putExtra(SUMMONER_NAME, summonername);
        startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lookup_summoner);

        // instantiate private fields
        progressdialog = new ProgressDialog(this);
        progressdialog.setMessage("Running Lookup...");
        progressdialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressdialog.setIndeterminate(true);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_lookup_summoner, menu);
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
