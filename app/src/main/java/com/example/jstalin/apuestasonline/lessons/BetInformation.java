package com.example.jstalin.apuestasonline.lessons;

/**
 * Created by JStalin on 07/02/2018.
 */

public class BetInformation {

    private int id;
    private String sport;
    private String team1;
    private String team2;
    private int result1;
    private int result2;


    public BetInformation() {
    }

    public BetInformation(String sport, String team1, String team2, int result1, int result2) {
        this.sport = sport;
        this.team1 = team1;
        this.team2 = team2;
        this.result1 = result1;
        this.result2 = result2;
    }

    public BetInformation(int id, String sport, String team1, String team2, int result1, int result2) {
        this.id = id;
        this.sport = sport;
        this.team1 = team1;
        this.team2 = team2;
        this.result1 = result1;
        this.result2 = result2;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSport() {
        return sport;
    }

    public void setSport(String sport) {
        this.sport = sport;
    }

    public String getTeam1() {
        return team1;
    }

    public void setTeam1(String team1) {
        this.team1 = team1;
    }

    public String getTeam2() {
        return team2;
    }

    public void setTeam2(String team2) {
        this.team2 = team2;
    }

    public int getResult1() {
        return result1;
    }

    public void setResult1(int result1) {
        this.result1 = result1;
    }

    public int getResult2() {
        return result2;
    }

    public void setResult2(int result2) {
        this.result2 = result2;
    }
}
