package com.example.codingslash.leaguemonitorapp;

import java.util.Map;

import constant.Region;
import dto.Champion.Champion;
import main.java.riotapi.RiotApi;
import main.java.riotapi.RiotApiException;

/**
 * Created by Operator on 1/13/2015.
 * Looks up static data from API;
 */
public class LookupStaticData {

    private RiotApi api;

    public LookupStaticData(String apikey) {
        this.api = new RiotApi(apikey);
    }

    public Champion lookupChampion(String region, int id) {

        //
        Champion champion = null;

        try {
            champion = api.getChampionById(id);
        } catch (RiotApiException e) {
            e.printStackTrace();
        }

        if (champion != null) {
            return champion;
        } else {
            return null;
        }
    }
}