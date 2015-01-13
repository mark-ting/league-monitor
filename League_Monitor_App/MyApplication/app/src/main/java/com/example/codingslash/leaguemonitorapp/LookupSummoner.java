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
 * Created by Operator on 1/12/2015.
 * Looks up Summoner ID for a given summoner name on NA shard
 */
public class LookupSummoner {

    private RiotAPI api;

    public LookupSummoner(String apikey) {
        this.api = new RiotAPI(Region.NA, apikey, RateLimiter.defaultDevelopmentRateLimiter());;
    }

    public SummonerInfo lookupSummoner(String input) {

        // processed input - map key
        String key = (input.replaceAll("\\s+", "")).toLowerCase();

        // get the summoner
        Summoner summoner = api.getSummoner(input);

        // initialize new container for summoner information
        SummonerInfo info = new SummonerInfo();

        if (summoner != null) {
            info.setSummId(summoner.ID);
            info.setSummName(summoner.name);
            info.setSummLevel(summoner.summonerLevel);
        } else {
            // dummy ID indicates not found
            info.setSummId(-1L);
            info.setSummName("SUMMONER NOT FOUND");
            info.setSummLevel(-1L);
        }

        return info;
    }
}
