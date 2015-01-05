package com.example.codingslash.leaguemonitorapp;

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

    private static String pingpath = "/system/bin/ping -c ";
    private static Pattern timepattern = Pattern.compile(", time (\\d+)ms");


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
                    pingpath + numpings + " " + url);
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

        Matcher matcher = timepattern.matcher(res);
        if(matcher.find()) {
            return (int)(Integer.parseInt(matcher.group(1))/numpings);
        } else {
            return -1;
        }
    }

}
