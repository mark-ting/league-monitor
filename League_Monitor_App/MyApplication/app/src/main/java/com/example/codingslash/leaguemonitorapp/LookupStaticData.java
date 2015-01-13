package com.example.codingslash.leaguemonitorapp;

import java.util.Map;

import com.robrua.orianna.api.RateLimiter;
import com.robrua.orianna.api.RiotAPI;
import com.robrua.orianna.api.queryspecs.Region;
import com.robrua.orianna.type.league.League;
import com.robrua.orianna.type.league.LeagueType;
import com.robrua.orianna.type.staticdata.Champion;
import com.robrua.orianna.type.summoner.Summoner;

/**
 * Created by Operator on 1/13/2015.
 * Looks up static data from API;
 */
public class LookupStaticData {

    private RiotAPI api;

    public LookupStaticData(String apikey) {
        this.api = new RiotAPI(Region.NA, apikey, RateLimiter.defaultDevelopmentRateLimiter());
    }

    public Champion lookupChampion(String region, int id) {

        //
        Champion champion = null;

        champion = api.getChampion(id);

        if (champion != null) {
            return champion;
        } else {
            return null;
        }
    }
}