package com.example.jstalin.apuestasonline.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Toast;

import com.example.jstalin.apuestasonline.Interfaces.OnSelectSportListener;
import com.example.jstalin.apuestasonline.R;
import com.example.jstalin.apuestasonline.lessons.Sport;
import com.example.jstalin.apuestasonline.lessons.SportAdapter;

import java.util.ArrayList;

/**
 * Clase que va a permitir seleccionar una apuesta
 */
public class BetsActivity extends AppCompatActivity {

    // Codigos de cada tipo de apusesta
    private final int CODE_FOOTBALL = Sport.CODE_FOOTBALL;
    private final int CODE_BASKETBALL = Sport.CODE_BASKETBALL;
    private final int CODE_TENNIS = Sport.CODE_TENNIS;
    private final int CODE_HANDBALL = Sport.CODE_HANDBALL;

    //Deportes que se van a utilizar
    private Sport football;
    private Sport basketball;
    private Sport tennis;
    private Sport handball;

    // Almacenamiento de los equipos que se pueden seleccionar para la apuesta
    private final String[] TEAMS_FOOTBALL = {"Real Madrid", "Barcelona", "At. Madrid", "Valencia"};
    private final String[] TEAMS_TENNIS = {"Nadal", "Ferrer", "Songa", "Djokovic"};
    private final String[] TEAMS_BASKETBALL = {"Estudiantes", "Barcelona", "Real Madrid", "Joventut"};
    private final String[] TEAMS_HANDBALL = {"Naturhouse", "Granoller", "Barcelona", "Bidasoa"};

    // Almacena los dos equipos de la apuesta
    private String[] teams;
    // Alamcena la categoria de la apusta
    private String selectedBet;

    // RecyclerView que contiene la lista
    private RecyclerView recyclcerSports;

    // Arraya Que contiene los deportes
    private ArrayList<Sport> sports;


    private int codeSport = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bets);

        initComponents(); // Iniciamos los componentes
    }

    private void initComponents() {

        // Instancimaos el recycler view a partir del XML
        recyclcerSports = (RecyclerView) findViewById(R.id.recyclerSports);

        // Intanciamos cada unos de los deportes que se va autilizar
        this.football = new Sport(Sport.CODE_FOOTBALL, getString(R.string.text_football), R.drawable.logo_bbva);
        this.basketball = new Sport(Sport.CODE_BASKETBALL, getString(R.string.text_basketball), R.drawable.logo_endesa);
        this.tennis = new Sport(Sport.CODE_TENNIS, getString(R.string.text_tennis), R.drawable.logo_atp);
        this.handball = new Sport(Sport.CODE_HANDBALL, getString(R.string.text_handball), R.drawable.logo_asobal);

        // Instanciamos el array que los almacena
        this.sports = new ArrayList<>();

        setData();

        configureRecyclerView();
    }

    /**
     * Metodo que añade los deportes
     */
    private void setData() {

        this.sports.add(football);
        this.sports.add(basketball);
        this.sports.add(tennis);
        this.sports.add(handball);

    }

    /**
     * Metodo que configura el recycler view
     */
    private void configureRecyclerView() {

        // Creamos el adaptador que va a utilizar
        final SportAdapter adapter = new SportAdapter(sports);

        // Creamos el Layout Manayer que va a utilizar
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);

        // Le asignamos el evento que se produce al seleccionar un item
        SportAdapter.SportViewHolder.setOnSelectSportListener(new OnSelectSportListener() {
            @Override
            public void onSelectedSport(View v, int code) {

                actionSelectedBet(code);
            }
        });

        // Asignamos el adaptador y el layout manager
        recyclcerSports.setLayoutManager(linearLayoutManager);
        recyclcerSports.setAdapter(adapter);

    }

    /**
     * Metodo que se ejecutara al pulsar sobre un item del recycler view
     */
    private void actionSelectedBet(int codeSelectedBet) {



        codeSport = codeSelectedBet;

        Log.d("CODE SPOR" , codeSport + "------------");

        String message = ""; // Almacena el mensaje que sera mostrado al usuario

        // Almacenamos la seleccion
        selectedBet = getSelectedBet(codeSelectedBet);

        // Generamos los equipos
        teams = generatedTeams(codeSelectedBet);

        // Creamos el mensaje que sera mostrado
        message = getString(R.string.menssage_one_bet) + ": " + selectedBet;

        // Enviamos la informacion
        sendData();

        // Mostramos mensaje informativo
        Toast.makeText(BetsActivity.this, message, Toast.LENGTH_SHORT).show();

        // Cerramos la actividad
        closeActivity();

    }

    /**
     * Metodo que permite obtener la seleccion del usuario
     *
     * @param codeBet --> Codigo de la selccion
     * @return ---> Cadena con el valor de la categoria seleccionada
     */
    private String getSelectedBet(int codeBet) {

        String stSelectedBet = "";

        switch (codeBet) { // Comprobamos con los distinto codigos
            case CODE_FOOTBALL:
                return getString(R.string.text_football);

            case CODE_TENNIS:
                return getString(R.string.text_tennis);

            case CODE_BASKETBALL:
                return getString(R.string.text_basketball);

            case CODE_HANDBALL:
                return getString(R.string.text_handball);

        }

        return stSelectedBet;
    }

    /**
     * Metodo que genera los equipos
     *
     * @param codeBet --> Codigo de la categoria que tiene que generar los equipos
     * @return --> Devuelve un array con el nombre de los 2 equipos
     */
    private String[] generatedTeams(int codeBet) {

        String[] teams = new String[2];
        String team1 = "";
        String team2 = "";
        int index = -1;
        int aux = -1;

        switch (codeBet) { // Comprobamos de que categoria debemos generar los equipos
            case CODE_FOOTBALL:

                int teamsFootball = TEAMS_FOOTBALL.length - 1; // Calculamos cuantos equipos hay
                index = (int) (Math.random() * teamsFootball); // Generamos numero aleatoria
                team1 = TEAMS_FOOTBALL[index]; // Asignamos el equipo 1
                aux = index;
                index = (int) (Math.random() * teamsFootball); // Generamos otro numero aleatorio
                while (index == aux) { // Comprobamos que el equipo no se repite
                    index = (int) (Math.random() * teamsFootball); // Genereamos numero aleaotriao
                }
                team2 = TEAMS_FOOTBALL[index]; // Asignamos el equipo 2
                break;

            /// REPETIMSO EL PROCESO PARA CADA CATEGORIA
            case CODE_TENNIS:
                int teamsTennis = TEAMS_TENNIS.length - 1;
                index = (int) (Math.random() * teamsTennis);
                team1 = TEAMS_TENNIS[index];
                aux = index;
                index = (int) (Math.random() * teamsTennis);
                while (index == aux) {
                    index = (int) (Math.random() * teamsTennis);
                }
                team2 = TEAMS_TENNIS[index];
                break;
            case CODE_BASKETBALL:
                int teamsBasketball = TEAMS_BASKETBALL.length - 1;
                index = (int) (Math.random() * teamsBasketball);
                team1 = TEAMS_BASKETBALL[index];
                aux = index;
                index = (int) (Math.random() * teamsBasketball);
                while (index == aux) {
                    index = (int) (Math.random() * teamsBasketball);
                }
                team2 = TEAMS_BASKETBALL[index];
                break;
            case CODE_HANDBALL:
                int teamsHandball = TEAMS_HANDBALL.length - 1;
                index = (int) (Math.random() * teamsHandball);
                team1 = TEAMS_HANDBALL[index];
                aux = index;
                index = (int) (Math.random() * teamsHandball);
                while (index == aux) {
                    index = (int) (Math.random() * teamsHandball);
                }
                team2 = TEAMS_HANDBALL[index];
                break;

        }

        // Asignamos los equipos al array
        teams[0] = team1;
        teams[1] = team2;

        return teams;

    }


    /**
     * Metodo que permite enviar el resultado
     * a la actividad que la lanzo
     */
    private void sendData() {


        Intent intent = new Intent();

        // Enviamos los datos necesarios
        intent.putExtra("teams", this.teams);
        intent.putExtra("selectedbet", this.selectedBet);


        savedPreferenceSport();

        // indicamos que todo ha ido correctamente
        setResult(RESULT_OK, intent);
    }

    private void savedPreferenceSport(){

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editorPreferences = preferences.edit();

        editorPreferences.putInt("preferenceSport", codeSport);

        editorPreferences.commit();

    }


    /**
     * Sobrescripcion del metodo que se produce al pulsar el boton de atras
     *
     * @param keyCode
     * @param event
     * @return
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        // Comporbamos que se haya pulsado el boton de retroceso
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {

            actionReturn(); // Llamada a metodo
            return true;
        }

        return super.onKeyDown(keyCode, event);
    }


    /**
     * Metodo que se ejecuta al pulsar el boton Volver
     **/
    public void actionReturn() {
        Toast.makeText(BetsActivity.this, getString(R.string.menssage_no_bet), Toast.LENGTH_SHORT).show();
        closeActivity();
    }

    /**
     * Metodo que finaliza la actividad actual
     */
    private void closeActivity() {
        this.finish();
    }


}
