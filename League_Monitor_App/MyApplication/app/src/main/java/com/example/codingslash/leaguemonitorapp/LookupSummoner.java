package com.example.codingslash.leaguemonitorapp;

import android.util.Log;

import com.robrua.orianna.api.core.RiotAPI;
import com.robrua.orianna.type.api.RateLimiter;
import com.robrua.orianna.type.core.common.Region;
import com.robrua.orianna.type.core.summoner.Summoner;

import java.util.Map;



/**
 * Created by Operator on 1/12/2015.
 * Looks up Summoner ID for a given summoner name on NA shard
 */
public class LookupSummoner {

    public static final String TAG = "LookupSummoner";

    private RiotAPI api;

    public LookupSummoner(String apikey) {

    }

    public Summoner lookupSummoner(String input) {

        // get the summoner and return
        Summoner summoner = null;

        summoner = api.getSummonerByName(input);

        return summoner;
    }
}
