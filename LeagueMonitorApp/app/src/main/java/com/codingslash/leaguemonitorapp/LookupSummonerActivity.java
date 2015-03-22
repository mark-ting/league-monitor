package com.codingslash.leaguemonitorapp;

import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.robrua.orianna.api.core.AsyncRiotAPI;
import com.robrua.orianna.type.api.Action;
import com.robrua.orianna.type.core.summoner.Summoner;
import com.robrua.orianna.type.exception.APIException;


public class LookupSummonerActivity extends ActionBarActivity {

    // String of user-input summoner name (can be mis-capitalised)
    private static String summonername;

    // Views to be populated via API calls
    private ImageView displayProfileIcon;
    private TextView displayName;
    private TextView displayLevel;
    private TextView displayID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lookup_summoner);

        Intent intent = getIntent();
        summonername = intent.getStringExtra(getString(R.string.SUMMONER_NAME));

        displayProfileIcon = (ImageView) findViewById(R.id.display_summoner_profile_icon);
        displayName = (TextView) findViewById(R.id.display_summoner_name);
        displayLevel = (TextView) findViewById(R.id.display_summoner_level);
        displayID = (TextView) findViewById(R.id.display_summoner_id);

        AsyncRiotAPI.getSummonerByName(new Action<Summoner>() {
            @Override
            public void perform(Summoner summoner) {

                final long summonerProfileIconID = summoner.getProfileIconID();
                final String summonerName = summoner.getName();
                final long summonerLevel= summoner.getLevel();
                final long summonerID = summoner.getID();


                runOnUiThread(new Runnable() {

                    @Override
                    public void run() {

                        // TODO: implement summoner profile icon populating - need static API call.

//                        displayProfileIcon.setImageBitmap(*IMAGEBITMAPHERE*);
                        displayName.setText(String.valueOf(summonerName));
                        displayLevel.setText("Level " + String.valueOf(summonerLevel));
                        displayID.setText("SID: " + String.valueOf(summonerID));
                    }
                });

                System.out.println(summoner.getName() + " is a level " + summoner.getLevel() + " summoner on the NA server.");
            }

            @Override
            public void handle(APIException e) {
                System.out.println("Unable to find summoner \"" + summonername + "\"");
            }
        }, summonername);
    }

    // TODO: implement lookup and render
    public void lookupRunePages(View view) {
        //Intent intent = new Intent(this, dummy.class);
        //intent.putExtra(getString(R.string.SUMMONER_NAME), summonername);
        //startActivity(intent);
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
