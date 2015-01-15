package com.example.codingslash.leaguemonitorapp;

import android.util.Log;

import java.util.Map;

import com.robrua.orianna.api.APIException;
import com.robrua.orianna.api.RateLimiter;
import com.robrua.orianna.api.RiotAPI;
import com.robrua.orianna.api.queryspecs.Region;
import com.robrua.orianna.type.league.League;
import com.robrua.orianna.type.league.LeagueType;
import com.robrua.orianna.type.staticdata.Champion;
import com.robrua.orianna.type.summoner.Summoner;

/**
 * Created by Operator on 1/12/2015.
 * Looks up Summoner ID for a given summoner name on NA shard
 */
public class LookupSummoner {

    public static final String TAG = "LookupSummoner";

    private RiotAPI api;

    public LookupSummoner(String apikey) {
        this.api = new RiotAPI(Region.NA, apikey, RateLimiter.defaultDevelopmentRateLimiter());
    }

    public Summoner lookupSummoner(String input) {

        // get the summoner and return
        Summoner summoner = null;

        try {
            summoner = api.getSummoner(input);
        } catch(APIException e) {
            Log.e(TAG, "Error occurred while requesting summoner info: ", e);
        }

        return api.getSummoner(input);
    }
}
