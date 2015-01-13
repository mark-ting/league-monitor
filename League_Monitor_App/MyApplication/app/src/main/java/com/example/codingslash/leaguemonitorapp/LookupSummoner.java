package com.example.codingslash.leaguemonitorapp;

import java.util.Map;

import constant.Region;
import dto.Summoner.Summoner;
import main.java.riotapi.RiotApi;
import main.java.riotapi.RiotApiException;

/**
 * Created by Operator on 1/12/2015.
 * Looks up Summoner ID for a given summoner name on NA shard
 */
public class LookupSummoner {

    private RiotApi api;

    public LookupSummoner(String apikey) {
        this.api = new RiotApi(apikey);
    }

    public SummonerInfo lookupSummoner(String input) {

        // processed input - map key
        String key = (input.replaceAll("\\s+", "")).toLowerCase();

        Map<String, Summoner> summoners = null;
        try {
            summoners = api.getSummonersByName(Region.NA, input);
        } catch (RiotApiException e) {
            e.printStackTrace();
        }

        // initialize new container for summoner information
        SummonerInfo info = new SummonerInfo();

        // default ID indicates below segment not running
        Summoner summoner = null;

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
}
