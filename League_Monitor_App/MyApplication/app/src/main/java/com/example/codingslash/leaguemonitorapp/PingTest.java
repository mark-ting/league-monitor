package com.example.codingslash.leaguemonitorapp;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Created by Richard on 1/3/2015.
 */
public class PingTest {

    // reference/match strings
    private static String pingpath = "/system/bin/ping -c 8 ";
    private static String timematch = ", time ";

    // store class vars
    private String url;
    private String res;

    public PingTest(String urlin) {
        url = urlin;
        res = "";
    }

    /* code snippet taken from
    http://stackoverflow.com/questions/14576710/ping-application-in-android
    */
    public String ping() {
        try {
            Process process = Runtime.getRuntime().exec(
                    pingpath + url);
            BufferedReader reader = new BufferedReader(new InputStreamReader(
                    process.getInputStream()));
            int i;
            char[] buffer = new char[4096];
            StringBuffer output = new StringBuffer();
            while ((i = reader.read(buffer)) > 0)
                output.append(buffer, 0, i);
            reader.close();

            // body.append(output.toString()+"\n");
            res = output.toString();
            // Log.d(TAG, str);
        } catch (IOException e) {
            // body.append("Error\n");
            e.printStackTrace();
        }
        return res;
    }

    public int ping_time() {
        // if not pinged yet, ping
        if(res.equals("")) {
            ping();
        }

        return Integer.parseInt(res.substring(res.lastIndexOf(timematch) + timematch.length()));
    }

}
