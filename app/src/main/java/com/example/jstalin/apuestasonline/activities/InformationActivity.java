package com.example.jstalin.apuestasonline.activities;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.jstalin.apuestasonline.R;

/**
 * CLase que muestra informacion sobre la apusta del usuario
 */
public class InformationActivity extends AppCompatActivity {

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
     * Metodo que inicializa los componentes y los enlaza con el xml
     */
    private void initComponents() {

        this.userIntent = (TextView) findViewById(R.id.textVIew_valueUser);
        this.betIntent = (TextView) findViewById(R.id.textView_valueBet);
        this.moneyBetIntent = (TextView) findViewById(R.id.textView_valueMoney);
        this.teamsIntent = (TextView) findViewById(R.id.textView_valueTeams);
        this.resultIntent = (TextView) findViewById(R.id.textView_valueResult);

    }

    /**
     * Metodo que obtiene los datos
     * Ya sea pasados del itent, si en estos hay error, los obtendra del archivo
     * de configuracion
     */
    private void setData() {

        String user = getIntent().getExtras().getString("user");
        this.user = user;

        String bet = getIntent().getExtras().getString("selectedbet");
        this.bet = bet;

        String moneyBet = getIntent().getExtras().getString("moneybet");
        this.moneyBet = moneyBet;

        String teams = getIntent().getExtras().getString("teams");
        this.teams = teams;

        String result = getIntent().getExtras().getString("result");
        this.result = result;

        // Asignamos los datos obtenidos a los componentes
        setDataInComponents();

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
