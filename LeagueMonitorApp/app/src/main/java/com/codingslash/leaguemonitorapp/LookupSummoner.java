package com.codingslash.leaguemonitorapp;

import java.util.List;
import com.robrua.orianna.api.core.AsyncRiotAPI;
import com.robrua.orianna.type.api.Action;
import com.robrua.orianna.type.core.common.QueueType;
import com.robrua.orianna.type.core.common.Region;
import com.robrua.orianna.type.core.league.League;
import com.robrua.orianna.type.core.staticdata.Champion;
import com.robrua.orianna.type.core.summoner.Summoner;
import com.robrua.orianna.type.exception.APIException;

/**
 * Created by Operator on 3/18/2015.
 */
public class LookupSummoner {

    // class vars
    private String summonerName;
    private long summonerId;

    public LookupSummoner(String summonerNamein, long summonerIdin) {
        summonerName = summonerNamein;
        summonerId = summonerIdin;
    }
}
