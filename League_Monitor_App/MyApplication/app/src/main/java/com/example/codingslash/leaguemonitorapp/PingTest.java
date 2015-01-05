package com.example.codingslash.leaguemonitorapp;

import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Richard on 1/3/2015.
 */
public class PingTest {

    // reference/match strings
    private static int numpings = 8;
    private static final String TAG = "PingTest";

    private static String pingpath = "/system/bin/ping";
    private static Pattern timepattern = Pattern.compile(" = .+?/(.+?)/");


    // store class vars
    private String url;
    private String res;

    public PingTest(String urlin) {
        url = urlin;
        res = "";
    }

    /* original code snippet taken from
    http://stackoverflow.com/questions/14576710/ping-application-in-android
    */
    public String ping() {
        try {
            Process process = Runtime.getRuntime().exec(
                    pingpath + " -c " + numpings + " " + url);
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
            Log.d(TAG, res);
        } catch (IOException e) {
            // body.append("Error\n");
            e.printStackTrace();
        }
        return res;
    }

    public double ping_time() {
        // if not pinged yet, ping
        if(res.equals("")) {
            ping();
        }

        Matcher matcher = timepattern.matcher(res);
        if(matcher.find()) {
            return Double.parseDouble(matcher.group(1));
        } else {
            return -1;
        }
    }

}
