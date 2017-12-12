package com.example.jstalin.apuestasonline;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.Toast;

public class BetsActivity extends AppCompatActivity {

    private static final int BET_FOOTBALL = 1;
    private static final int BET_TENNIS = 2;
    private static final int BET_BASKETBALL = 3;
    private static final int BET_HANDBALL = 4;

    private static final String[] TEAMS_FOOTBALL = {"Real Madrid", "Barcelona", "At. Madrid", "Valencia"};
    private static final String[] TEAMS_TENNIS = {"Nadal", "Ferrer", "Songa", "Djokovic"};
    private static final String[] TEAMS_BASKETBALL = {"Estudiantes", "Barcelona", "Real Madrid", "Joventut"};
    private static final String[] TEAMS_HANDBALL = {"Naturhouse", "Granoller", "Barcelona", "Bidasoa"};

    private String[] teams;
    private String selectedBet;

    private CheckBox checkFootball;
    private CheckBox checkTennis;
    private CheckBox checkBasketball;
    private CheckBox checkHandball;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bets);

        initComponents();
    }

    private void initComponents() {

        checkFootball = (CheckBox) findViewById(R.id.checkBox_football);
        checkTennis = (CheckBox) findViewById(R.id.checkBox_tennis);
        checkBasketball = (CheckBox) findViewById(R.id.checkBox_basketball);
        checkHandball = (CheckBox) findViewById(R.id.checkBox_handball);

    }

    public void actionReturn(View v) {
        closeActiity();
    }

    private void closeActiity() {
        this.finish();
    }

    public void actionAccept(View v) {

        String message = "";


        if(validateBet()){
            sendData();
            message = getString(R.string.menssage_one_bet);
            Toast.makeText(BetsActivity.this, message, Toast.LENGTH_SHORT).show();
            closeActiity();
        }

    }

    private void sendData(){

        Intent intent = new Intent();

        intent.putExtra("teams", teams);
        intent.putExtra("selectedbet", selectedBet);

        setResult(RESULT_OK, intent);
    }

    private boolean validateBet() {

        boolean valid = true;

        String message = "";

        int countBets = countBets();

        int codeSelectedBet = -1;
        switch (countBets) {

            case 0:
                message = getString(R.string.menssage_no_bet);
                Toast.makeText(BetsActivity.this, message, Toast.LENGTH_SHORT).show();
                valid = false;
            break;
            case 1:
                codeSelectedBet = betSelected();
                teams = generatedTeams(codeSelectedBet);
                selectedBet = getSelectedBet(codeSelectedBet);
                break;
            default:
                if (countBets > 1) {
                    message = getString(R.string.menssage_some_bet);
                    Toast.makeText(BetsActivity.this, message, Toast.LENGTH_SHORT).show();
                }
                valid = false;
            break;
        }

        return valid;

    }

    private String getSelectedBet(int codeBet){

        String stSelectedBet = "";

        switch (codeBet) {
            case BET_FOOTBALL:
                return getString(R.string.text_football);

            case BET_TENNIS:
                return getString(R.string.text_tennis);

            case BET_BASKETBALL:
                return getString(R.string.text_basketball);

            case BET_HANDBALL:
                return getString(R.string.text_handball);

        }

        return stSelectedBet;
    }

    private String[] generatedTeams(int codeBet) {

        String[] teams = new String[2];
        String team1 = "";
        String team2 = "";
        int index = -1;
        int aux = -1;

        switch (codeBet) {
            case BET_FOOTBALL:
                int teamsFootball = TEAMS_FOOTBALL.length-1;
                index = (int)(Math.random()*teamsFootball);
                team1 = TEAMS_FOOTBALL[index];
                aux = index;
                index = (int)(Math.random()*teamsFootball);
                while(index==aux){
                    index = (int)(Math.random()*teamsFootball);
                }
                team2 = TEAMS_FOOTBALL[index];
                break;
            case BET_TENNIS:
                int teamsTennis = TEAMS_TENNIS.length-1;
                index = (int)(Math.random()*teamsTennis);
                team1 = TEAMS_TENNIS[index];
                aux = index;
                index = (int)(Math.random()*teamsTennis);
                while(index==aux){
                    index = (int)(Math.random()*teamsTennis);
                }
                team2 = TEAMS_TENNIS[index];
                break;
            case BET_BASKETBALL:
                int teamsBasketball = TEAMS_BASKETBALL.length-1;
                index = (int)(Math.random()*teamsBasketball);
                team1 = TEAMS_BASKETBALL[index];
                aux = index;
                index = (int)(Math.random()*teamsBasketball);
                while(index==aux){
                    index = (int)(Math.random()*teamsBasketball);
                }
                team2 = TEAMS_BASKETBALL[index];
                break;
            case BET_HANDBALL:
                int teamsHandball = TEAMS_HANDBALL.length-1;
                index = (int)(Math.random()*teamsHandball);
                team1 = TEAMS_HANDBALL[index];
                aux = index;
                index = (int)(Math.random()*teamsHandball);
                while(index==aux){
                    index = (int)(Math.random()*teamsHandball);
                }
                team2 = TEAMS_HANDBALL[index];
                break;

        }

        teams[0] = team1;
        teams[1] = team2;

        return teams;

    }

    private int countBets() {
        int countBets = 0;

        if (checkFootball.isChecked())
            countBets++;
        if (checkTennis.isChecked())
            countBets++;
        if (checkBasketball.isChecked())
            countBets++;
        if (checkHandball.isChecked())
            countBets++;

        return countBets;
    }

    private int betSelected() {
        int codBet = 0;
        if (checkFootball.isSelected())
            return BET_FOOTBALL;
        if (checkTennis.isSelected())
            return BET_TENNIS;
        if (checkBasketball.isSelected())
            return BET_BASKETBALL;
        if (checkHandball.isSelected())
            return BET_HANDBALL;

        return codBet;

    }

}
