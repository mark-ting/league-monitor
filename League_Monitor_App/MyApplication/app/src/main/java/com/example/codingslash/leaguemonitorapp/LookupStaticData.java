package com.example.codingslash.leaguemonitorapp;

import java.util.Map;

import com.robrua.orianna.api.core.RiotAPI;
import com.robrua.orianna.type.core.common.Region;
import com.robrua.orianna.type.core.staticdata.Champion;

/**
 * Created by Operator on 1/13/2015.
 * Looks up static data from API;
 */
public class LookupStaticData {

    private RiotAPI api;

    public LookupStaticData(String apikey) {
        RiotAPI.setAPIKey(apikey);
        RiotAPI.setRegion(Region.NA); //implement switching later
    }

    public Champion lookupChampion(String region, int id) {

        Champion champion = null;

        champion = api.getChampion(id);

        if (champion != null) {
            return champion;
        } else {
            return null;
        }
    }
}