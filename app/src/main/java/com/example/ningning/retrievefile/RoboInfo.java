package com.example.ningning.retrievefile;

/**
 * Created by Ningning on 2/19/2017.
 */
public class RoboInfo {
    String displayName;
    String displayNumber;
    String displayScore;
    String displayResults;
    String singleTeam;
    String matchNumber;

    static final RoboInfo me = new RoboInfo();
    public static RoboInfo getInstance() {
        return me;
    }

    public void setDisplayName(String str) {
        displayName = str;
    }

    public String getDisplayName() {
       return displayName;
    }

    public void setDisplayNumber(String str) {
        displayNumber = str;
    }

    public String getDisplayNumber() {
        return displayNumber;
    }

    public void setDisplayScore(String str) {
        displayScore = str;
    }

    public String getDisplayScore() {
        return displayScore;
    }

    public void setDisplayResults(String str) {
        displayResults = str;
    }

    public String getDisplayResults() {
        return displayResults;
    }

    public void setSingleTeam(String str) {
        singleTeam = str;
    }

    public String getSingleTeam() {
        return singleTeam;
    }

    public void setMatchNumber(String str) {
        matchNumber = str;
    }

    public String getMatchNumber() {
        return matchNumber;
    }
}
