package com.example.jstalin.apuestasonline.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.jstalin.apuestasonline.R;

/**
 * Clase principal de la cual se ejecutaran las diferentes Actividades
 */
public class MainActivity extends AppCompatActivity {

    // Variables que se alamacenaran en el fichero de configruacion
    private String user = "";
    private String bet = "";
    private String moneyBet = "";
    private String teams = "";
    private String result = "";


    // Intents que se utilizaran para abrir las diferente actividades
    private Intent intentRegistry;
    private Intent intentBets;
    private Intent intentSettings;
    private Intent intentDraw;
    private Intent intentInformation;
    private Intent intentAbout;
    private Intent intentHelp;

    // Refernecia del id de cada una de las opciones del Menu
    private final int OPTION_ABOUT = R.id.option_about;
    private final int OPTION_HELP = R.id.option_help;
    private final int OPTION_INFORMATION = R.id.option_informationBet;


    // Referencia a un campo de texto que contendra un mensaje de bienvenida
    private TextView welcome;

    // COdigos de los diferentes Intents
    private static final int REGISTRY_CODE = 1;
    private static final int BETS_CODE = 2;
    private static final int SETTINGS_CODE = 3;
    private static final int DRAW_CODE = 4;

    // Variables que permiten saber en que estado se encuentra la actividad
    private boolean isRegistry = false;
    private boolean isBet = false;
    private boolean canDraw = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initComponents(); // Iniciamos componentes
    }

    /**
     * Metodo que inicia los componentes y los enlaza con el XML
     * Inicializa los intents
     */
    private void initComponents() {

        // Intanciamos cada unos de los intents que van a ser lanzados

        this.intentRegistry = new Intent(this, RegistryActivity.class);
        this.intentBets = new Intent(this, BetsActivity.class);
        this.intentSettings = new Intent(this, SettingsActivity.class);
        this.intentDraw = new Intent(this, DrawActivity.class);
        this.intentAbout = new Intent(this, AboutActivity.class);
        this.intentHelp = new Intent(this, HelpActivity.class);
        this.intentInformation = new Intent(this, InformationActivity.class);

        // Intanciamos el TextView
        this.welcome = (TextView) findViewById(R.id.textView_welcome);

    }


    /**
     * MEtodo que permite guardar el estado de la actividad
     *
     * @param state
     */
    @Override
    protected void onSaveInstanceState(Bundle state) {
        super.onSaveInstanceState(state);

        // GUardamos el mensaje de vienvenida
        String stWelcome = this.welcome.getText().toString();
        if (stWelcome.length() != 0)// Comprobamos que hay texto
            state.putString("welcome", stWelcome);

        // Guardamos el estado de las variables
        if (user != null)
            state.putString("user", user);

        if (bet != null)
            state.putString("bet", bet);

        if (moneyBet != null)
            state.putString("moneybet", moneyBet);

        if (teams != null)
            state.putString("teams", teams);

        if (result != null)
            state.putString("result", result);

        if (isRegistry)
            state.putBoolean("isregistry", isRegistry);

        if (isBet)
            state.putBoolean("isbet", isBet);

        // LLamamos al metodo sendPreferences
        //sendPreferences();
    }

    /**
     * Metodo que permite restaurar una actividad
     *
     * @param state
     */
    @Override
    protected void onRestoreInstanceState(Bundle state) {
        super.onRestoreInstanceState(state);
        if (state != null) { // Comprobamos que se encuentre un estado guardado
            String stWelcome = state.getString("welcome");
            this.welcome.setText(stWelcome); // Resturamos el mensaje de bienvenida

            // Restauramos el valor de las variables
            this.user = state.getString("user");
            this.bet = state.getString("bet");
            this.moneyBet = state.getString("moneybet");
            this.teams = state.getString("teams");
            this.result = state.getString("result");

            isRegistry = state.getBoolean("isregistry");
            isBet = state.getBoolean("isbet");
        }


    }

    /**
     * Metodo que permite crear un menu de opciones
     *
     * @param menu
     * @return
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu); // Asignamos el xml con el menu
        return true;
    }

    /**
     * Metodo que permite gestionar las acciones sobre los items del menu
     *
     * @param item
     * @return
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int selection = item.getItemId(); // Obtenemos el item seleccionado

        switch (selection) { // Comprobamos cual ha sido
            case OPTION_ABOUT:
                openAbout();
                return true;
            case OPTION_HELP:
                openHelp();
                return true;
            case OPTION_INFORMATION:
                openInformation();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }

    /**
     * Metodo que se ejecutar al pulsar en el boton Acerca de...
     */
    private void openAbout() {
        startActivity(intentAbout);
    }

    /**
     * Metodo que se ejecutar al pulsar en el boton AYuda
     */
    private void openHelp() {
        startActivity(intentHelp);
    }

    /**
     * Metodo que se ejecutar al pulsar en el boton Informacion
     */
    private void openInformation() {
        String messageError = "";

        if (isRegistry) { // Comprobamos si se ha registrado
            updateIntentInformation();
            startActivity(intentInformation);
        } else {
            messageError = getString(R.string.error_noregistred);
            Toast.makeText(MainActivity.this, messageError, Toast.LENGTH_SHORT).show();
        }

    }

    /**
     * Metodo que actualiza la informacion que va ser enviada por el intent
     */
    private void updateIntentInformation() {

        this.intentInformation.putExtra("user", this.user);
        this.intentInformation.putExtra("selectedbet", this.bet);
        this.intentInformation.putExtra("moneybet", this.moneyBet);
        this.intentInformation.putExtra("teams", this.teams);
        this.intentInformation.putExtra("result", this.result);

    }


    /**
     * Metodo que permite tratar los resultados que devuelven las actividades
     *
     * @param requestCode
     * @param resultCode
     * @param data
     */
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        tryAnswers(requestCode, resultCode, data); // Llamamos al emtodo tryAnswers
    }

    /**
     * Metodo que permite tratar la respuesta de las actividades
     * en funcion de su codigo
     *
     * @param requestCode --> Codigo que ha devuelto la actividad
     * @param resultCode  --> Codigo si ha ido correcto el proceso
     * @param data        --> Objeto con datos devueltos
     */
    private void tryAnswers(int requestCode, int resultCode, Intent data) {

        switch (requestCode) { // Comprobamos el cdogio de respuesta y tratamos en funcion de eello
            case (REGISTRY_CODE):
                if (isResultOk(resultCode)) {
                    responseActionRegistry(data);
                }
                break;
            case (BETS_CODE):
                if (isResultOk(resultCode)) {
                    responseActionBets(data);
                }
                break;
            case (SETTINGS_CODE):
                if (isResultOk(resultCode)) {
                    responseActionSettings(data);
                }
                break;
            default:
                break;
        }

    }

    /**
     * Metodo que comprueba si el resultaod ha ido bien o no
     *
     * @param resultCode
     * @return
     */
    private boolean isResultOk(int resultCode) {
        return resultCode == RESULT_OK;
    }

    /**
     * Metodo que permite tratar los datos devueltos por la actividad Registro
     *
     * @param data
     */
    private void responseActionRegistry(Intent data) {

        // Obtenemos el nombre
        String name = data.getExtras().getString("name");
        // ASignamos el nombre a la variable user
        this.user = name;

        // Mostramos mensaje de bienvenida
        setWelcome(name);

        // Cambiamos el valor de si esta registrado a verdadero
        isRegistry = true;

    }

    /**
     * MEtodo que permite ejecutar un mensaje de bienvenida
     *
     * @param name
     */
    private void setWelcome(String name) {
        String stWelcome = getString(R.string.text_welcome);
        String messageWelcome = stWelcome + "  " + name;

        welcome.setText(messageWelcome);

    }

    /**
     * Metodo que permite tratar los datos devueltos por la actividad Apuestas
     *
     * @param data
     */
    private void responseActionBets(Intent data) {

        // Ontenemos los valores devueltos
        String[] teams = (String[]) data.getExtras().get("teams");
        String selectedBet = data.getExtras().getString("selectedbet");

        // Asignamos los valores obtenidos a las variables
        this.bet = selectedBet;
        this.teams = teams[0] + " - " + teams[1];


        // Cambiamos el valor de si ha hecho apuesta a verdadero
        isBet = true;

    }

    /**
     * Metodo que permite tratar los datos devueltos por la actividad Ajustes
     *
     * @param data
     */
    private void responseActionSettings(Intent data) {

        // Obtenemos los valores devueltos
        String moneyBet = data.getExtras().getString("moneybet");
        String[] result = (String[]) data.getExtras().get("result");

        // Los tasignamos a las variables
        this.moneyBet = moneyBet;
        this.result = result[0] + " - " + result[1];

    }

    /**
     * Metodo que se ejecutar al pulsar en el boton Registro
     *
     * @param v
     */
    public void openRegistry(View v) {
        startActivityForResult(intentRegistry, REGISTRY_CODE);

    }

    /**
     * Metodo que se ejecutar al pulsar en el boton Apuestas
     *
     * @param v
     */
    public void openBets(View v) {

        String messageError = "";

        if (isRegistry) { // Comprobamos si se ha registrado
            startActivityForResult(intentBets, BETS_CODE);
        } else {
            messageError = getString(R.string.error_registry);
            Toast.makeText(MainActivity.this, messageError, Toast.LENGTH_SHORT).show();
        }

    }


    /**
     * Metodo que se ejecutar al pulsar en el boton Ajustes
     *
     * @param v
     */
    public void openSettings(View v) {

        String messageError = "";

        if (isBet) {// Comprobamos si ha hecho apuestas antes
            updateIntenSettings();
            startActivityForResult(intentSettings, SETTINGS_CODE);
        } else {
            messageError = getString(R.string.error_bet);
            Toast.makeText(MainActivity.this, messageError, Toast.LENGTH_SHORT).show();
        }

    }

    /**
     * Metodo actualiza la informacion que se va  enviar por el intent
     */
    private void updateIntenSettings() {

        this.intentSettings.putExtra("teams", this.teams);

    }


    /**
     * Metodo que se ejecutar al pulsar en el boton Sorteo
     *
     * @param v
     */
    public void openDraw(View v) {

        String messageError = "";

        if (canDraw) {// Comprobamos si se puede realizar el sorteo
            startActivity(intentDraw);
        } else {
            messageError = getString(R.string.error_draw);
            Toast.makeText(MainActivity.this, messageError, Toast.LENGTH_SHORT).show();
        }

    }


}
