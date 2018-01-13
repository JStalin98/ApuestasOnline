package com.example.jstalin.apuestasonline;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.Toast;

/**
 * Clase que va a permitir seleccionar una apuesta
 */
public class BetsActivity extends AppCompatActivity {

    // Codigos de cada tipo de apusesta
    private static final int BET_FOOTBALL = 1;
    private static final int BET_TENNIS = 2;
    private static final int BET_BASKETBALL = 3;
    private static final int BET_HANDBALL = 4;

    // Almacenamiento de los equipos que se pueden seleccionar para la apuesta
    private static final String[] TEAMS_FOOTBALL = {"Real Madrid", "Barcelona", "At. Madrid", "Valencia"};
    private static final String[] TEAMS_TENNIS = {"Nadal", "Ferrer", "Songa", "Djokovic"};
    private static final String[] TEAMS_BASKETBALL = {"Estudiantes", "Barcelona", "Real Madrid", "Joventut"};
    private static final String[] TEAMS_HANDBALL = {"Naturhouse", "Granoller", "Barcelona", "Bidasoa"};

    // Almacena los dos equipos de la apuesta
    private String[] teams;
    // Alamcena la categoria de la apusta
    private String selectedBet;


    // Referencias hacia los chckbox que puede seleccionar el usuario
    private CheckBox checkFootball;
    private CheckBox checkTennis;
    private CheckBox checkBasketball;
    private CheckBox checkHandball;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bets);

        initComponents(); // INiciamos los componentes
    }

    /**
     * Metodo para guardar el estado de la actividad
     *
     * @param state
     */
    @Override
    protected void onSaveInstanceState(Bundle state) {
        super.onSaveInstanceState(state);
        int betSelected = betSelected(); // Guardamos el codigo del
        // del equipo que se encuentre selccionado en ese  instante
        if (betSelected != 0) // Comprobamos si hay algun equipo seleccionado
            state.putInt("betselected", betSelected); // Si lo hay enviamos el codigo del equipo


    }

    /**
     * Metodo para restaurar la actividad
     *
     * @param state
     */
    @Override
    protected void onRestoreInstanceState(Bundle state) {
        super.onRestoreInstanceState(state);
        if (state != null) { // Comprobamos si hay un estado guardado
            int betSelected = state.getInt("betselected");
            setSelectedBet(betSelected); // Volvemos a seleccionar la apuesta que se encontraba
        }

    }

    /**
     * Metodo que permite selccionar una categoria de apuesta dada su codigo
     *
     * @param code
     */
    private void setSelectedBet(int code) {
        switch (code) {
            case BET_FOOTBALL:
                checkFootball.setChecked(true);
                break;
            case BET_TENNIS:
                checkTennis.setChecked(true);
                break;
            case BET_BASKETBALL:
                checkBasketball.setChecked(true);
                break;
            case BET_HANDBALL:
                checkHandball.setChecked(true);
                break;
        }
    }

    /**
     * Metodo que iniciara los componentes
     */
    private void initComponents() {

        checkFootball = (CheckBox) findViewById(R.id.checkBox_football);
        checkTennis = (CheckBox) findViewById(R.id.checkBox_tennis);
        checkBasketball = (CheckBox) findViewById(R.id.checkBox_basketball);
        checkHandball = (CheckBox) findViewById(R.id.checkBox_handball);

    }


    /**
     * Metodo que se ejecuta al pulsar el boton Volver
     *
     * @param v
     */
    public void actionReturn(View v) {
        closeActiity();
    }

    /**
     * Metodo que finaliza la actividad actual
     */
    private void closeActiity() {
        this.finish();
    }


    /**
     * Metodo que se ejecuta al producir el boton Aceptar
     *
     * @param v
     */
    public void actionAccept(View v) {

        String message = "";

        if (validateBet()) {
            sendData(); // Evnaimos los datos

            message = getString(R.string.menssage_one_bet);
            Toast.makeText(BetsActivity.this, message, Toast.LENGTH_SHORT).show();
            closeActiity();
        }

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

        // indicamos que todo ha ido correctamente
        setResult(RESULT_OK, intent);
    }

    /**
     * Metodo que permitevalidar las selecciones en los
     * checkbos del usuario
     *
     * @return TRUE --> si solo se ha seleccionado 1 categoria
     * FALSE --> SI no se han seleccionado categorias o mas de 1
     */
    private boolean validateBet() {

        boolean valid = true;

        String message = ""; // Almacena el mensaje que sera mostrado al usuario

        int countBets = countBets(); // Calculamos la cantidad de selecciones del usuario

        int codeSelectedBet = -1;
        switch (countBets) { // Comprobamos en funcion de las selecciones

            case 0: // Si no hay 0 selecciones
                message = getString(R.string.menssage_no_bet);
                Toast.makeText(BetsActivity.this, message, Toast.LENGTH_SHORT).show();
                valid = false;
                break;
            case 1: // Si hay 1 seleccon
                codeSelectedBet = betSelected(); // Obtenemos el codigo de la categoria que se ha seleccionado

                this.teams = generatedTeams(codeSelectedBet); // Generamos los quipos
                this.selectedBet = getSelectedBet(codeSelectedBet); // Obtenemos la cadena de la categoria


                break;
            default: // Por defecto
                if (countBets > 1) { // Si hay mas de 1 seleccion
                    message = getString(R.string.menssage_some_bet);
                    Toast.makeText(BetsActivity.this, message, Toast.LENGTH_SHORT).show();
                }
                valid = false;
                break;
        }

        return valid;

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
            case BET_FOOTBALL:

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
            case BET_TENNIS:
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
            case BET_BASKETBALL:
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
            case BET_HANDBALL:
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
     * Metodo que permite ver cuantas selecciones ha hecho el usuario
     *
     * @return --> Numero de selecciones del usuario
     */
    private int countBets() {
        int countBets = 0;

        // Comprobamos cada una de la seleccion
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

    /**
     * Metodo que permite obtener la seleccion del usuario
     *
     * @return --> COdigo de la categoria que ha seleccionado el usuario
     */
    private int betSelected() {

        int codBet = 0;

        // Comprobamos la seleccion del usuario
        if (checkFootball.isChecked())
            return BET_FOOTBALL;
        if (checkTennis.isChecked())
            return BET_TENNIS;
        if (checkBasketball.isChecked())
            return BET_BASKETBALL;
        if (checkHandball.isChecked())
            return BET_HANDBALL;

        return codBet;

    }

}
