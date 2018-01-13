package com.example.jstalin.apuestasonline;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

/**
 * CLase que muestra informacion sobre la apusta del usuario
 */
public class InformationActivity extends AppCompatActivity {

    // Objeto que permite restaurar las configuraciones del usuario
    private SharedPreferences preferences;

    // Referencias a los componentes de la Actividad
    private TextView userIntent;
    private TextView betIntent;
    private TextView moneyBetIntent;
    private TextView teamsIntent;
    private TextView resultIntent;

    // Variables que almacenaran los datos de la configuracion
    private String user;
    private String bet;
    private String moneyBet;
    private String teams;
    private String result;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_information);

        // Inicamos componentes
        initComponents();

        // Asignamos los datos
        setData();
    }


    /**
     * Metodo que inicializa los componentes y los enlaza con el xml
     */
    private void initComponents() {
        this.userIntent = (TextView) findViewById(R.id.textVIew_valueUser);
        this.betIntent = (TextView) findViewById(R.id.textView_valueBet);
        this.moneyBetIntent = (TextView) findViewById(R.id.textView_valueMoney);
        this.teamsIntent = (TextView) findViewById(R.id.textView_valueTeams);
        this.resultIntent = (TextView) findViewById(R.id.textView_valueResult);

        // Instanciamos las preferencias, (Solo podra ser utilizada por la aplicacion)
        preferences = getSharedPreferences("data", this.MODE_PRIVATE);

    }

    /**
     * Metodo que asigna los valores de las variables
     * a los componentes de la actividad
     */
    private void setDataInComponents() {

        this.userIntent.setText(user);
        this.betIntent.setText(bet);
        this.moneyBetIntent.setText(moneyBet);
        this.resultIntent.setText(result);
        this.teamsIntent.setText(teams);
    }

    /**
     * Metodo que obtiene los datos
     * Ya sea pasados del itent, si en estos hay error, los obtendra del archivo
     * de configuracion
     */
    private void setData() {

        try {
            String user = getIntent().getExtras().getString("user");
            this.user = user;
        } catch (NullPointerException e) {
            setSavedUser();
        }

        try {
            String bet = getIntent().getExtras().getString("selectedbet");
            this.bet = bet;
        } catch (NullPointerException e) {
            setSavedBet();
        }

        try {
            String moneyBet = getIntent().getExtras().getString("moneybet");
            this.moneyBet = moneyBet;
        } catch (NullPointerException e) {
            setSavedMoneyBet();
        }

        try {
            String[] stTeams = (String[]) getIntent().getExtras().get("teams");
            this.teams = stTeams[0] + " - " + stTeams[1];
        } catch (NullPointerException e) {
            setSavedTeams();
        }

        try {
            String[] result = (String[]) getIntent().getExtras().get("result");
            this.result = result[0] + " - " + result[1];
        } catch (NullPointerException e) {
            setSavedResult();
        }


        // Asignamos los datos obtenidos a los componentes
        setDataInComponents();

    }


    // METODOS QUE PERMITE ASIGNAR A LAS VARIALES EL VALOR DE EL ARCHIVO DE CONFIGURACION QUE LE CORRESPONDE
    private void setSavedUser() {
        String stUser = preferences.getString("user", "");
        user = stUser;
    }

    private void setSavedBet() {
        String stBet = preferences.getString("bet", "");
        bet = stBet;


    }

    private void setSavedMoneyBet() {
        String stMoneyBet = preferences.getString("moneybet", "");
        moneyBet = stMoneyBet;

    }

    private void setSavedTeams() {
        String stTeams = preferences.getString("teams", "");
        teams = stTeams;
    }

    private void setSavedResult() {

        String stResult = preferences.getString("result", "");
        result = stResult;

    }


    /**
     * MEtodo que permite guaradr el estado de la Actividad
     *
     * @param state
     */
    @Override
    protected void onSaveInstanceState(Bundle state) {
        super.onSaveInstanceState(state);

        // Guardamos el valor de las variables
        state.putString("user", user);


        state.putString("bet", bet);


        state.putString("moneybet", moneyBet);


        state.putString("teams", teams);


        state.putString("result", result);

    }

    /**
     * Mtodo que permite restaurar la activdiad
     *
     * @param state
     */
    @Override
    protected void onRestoreInstanceState(Bundle state) {
        super.onRestoreInstanceState(state);
        if (state != null) { // Comprobamos que hay un estado disponible

            // Asignamos los datos recuperados a las variables
            this.user = state.getString("user");
            this.bet = state.getString("bet");
            this.moneyBet = state.getString("moneybet");
            this.teams = state.getString("teams");
            this.result = state.getString("result");

            // Volvemos a colocarlos en los componentes
            setDataInComponents();

        }


    }


    /**
     * Metodo que se ejcutara cuando se pulse el boton de Volver
     *
     * @param v
     */
    public void actionReturn(View v) {
        closeActivity();
    }

    /**
     * Metodo que finaliza la actividad actual
     */
    private void closeActivity() {
        this.finish();
    }
}
