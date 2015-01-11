package com.example.codingslash.leaguemonitorapp;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
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

    ProgressDialog progressdialog;

    private class LookupSummonerID extends AsyncTask<String, Void, SummonerInfo> {

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
        protected SummonerInfo doInBackground(String... input) {

            RiotApi api = new RiotApi(context.getString(R.string.riot_api_key));

            // raw user input
            String query = input[0];

            // processed map key
            String key = (query.replaceAll("\\s+","")).toLowerCase();

            Map<String, Summoner> summoners = null;
            try {
                summoners = api.getSummonersByName(Region.NA, query);
            } catch (RiotApiException e) {
                e.printStackTrace();
            }

            // initialize new container for summoner information
            SummonerInfo info = new SummonerInfo();

            // default ID indicates below segment not running
            Summoner summoner;

            if (summoners != null) {
                summoner = summoners.get(key);
                info.setSummId(summoner.getId());
                info.setSummName(summoner.getName());
                info.setSummLevel(summoner.getSummonerLevel());
            } else {
                // dummy ID indicates not found
                info.setSummId(-1L);
                info.setSummName("SUMMONER NOT FOUND");
                info.setSummLevel(-1L);
            }

            return info;
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

        // TODO MAKE A MORE ROBUST CHECK FOR USER INPUT

        // checkUserInput();

        if (summonername != "") {
            new LookupSummonerID(this).execute(summonername);
        } else {
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                    getApplicationContext());

            // set title
            alertDialogBuilder.setTitle("Warning!");

            // set dialog message
            alertDialogBuilder
                    .setMessage("Please enter a summoner to look up!")
                    .setCancelable(false)
                    .setPositiveButton("Yes",new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog,int id) {
                            // if this button is clicked, close
                            // current activity
                            MainActivity.this.finish();
                        }
                    })
                    .setNegativeButton("No",new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog,int id) {
                            // if this button is clicked, just close
                            // the dialog box and do nothing
                            dialog.cancel();
                        }
                    });

            // create alert dialog
            AlertDialog alertDialog = alertDialogBuilder.create();

            // show it
            alertDialog.show();
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




