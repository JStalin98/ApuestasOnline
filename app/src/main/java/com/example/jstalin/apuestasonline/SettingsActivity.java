package com.example.jstalin.apuestasonline;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Clase que permite configurar los datos de la apuesta
 */
public class SettingsActivity extends AppCompatActivity {

    // Objeto que permite restaurar las configuraciones del usuario
    private SharedPreferences preferences;

    // Referencia a los componentes de la actividad
    private Spinner moneyBet;
    private TextView teams;
    private EditText number1;
    private EditText number2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        initComponents(); // Iniciamos los componentes
        addTabHost(); // Añadimos las pestañas

    }

    /**
     * Metdoo que permite guardar el estado de la actividad
     * @param state
     */
    @Override
    protected void onSaveInstanceState(Bundle state) {
        super.onSaveInstanceState(state);

        // Obtenemos los datos de los campos

        int indexMoneyBet = this.moneyBet.getSelectedItemPosition();
        String stTeams = this.teams.getText().toString();
        String stNumber1 = this.number1.getText().toString();
        String stNumber2 = this.number2.getText().toString();

        // COmprobramos si no estan vacios, y se guardan
        if(indexMoneyBet!=0)
            state.putInt("moneybet", indexMoneyBet);

        if(stTeams.length()!=0)
            state.putString("teams", stTeams);

        if(stNumber1.length()!=0)
            state.putString("number1", stNumber1);

        if(stNumber2.length()!=0)
            state.putString("number2", stNumber2);



    }

    /**
     * Metodo que permite restaurar el estado guardado
     * @param state
     */
    @Override
    protected void onRestoreInstanceState(Bundle state) {
        super.onRestoreInstanceState(state);
        if(state!=null){ // Comprobamos que hay un etsado guardado

            // Obtenemos los datos
            int indexMoneyBet = state.getInt("moneybet");
            String stTeams = state.getString("teams");
            String stNumber1 = state.getString("number1");
            String stNumber2 = state.getString("number2");

            // Los introducimos en los componentes
            this.moneyBet.setSelection(indexMoneyBet);
            this.teams.setText(stTeams);
            this.number1.setText(stNumber1);
            this.number2.setText(stNumber2);

        }

    }

    /**
     * Metodo que permite mostrar los teams
     */
    private void setTeams(){


        try {
            // Obtenemos los equipos del intent
            String[] teams = (String[]) getIntent().getExtras().get("teams");
            String st = "";
            // Lo pasamos a cadena con formato
            st += teams[0] + " - ";
            st += teams[1];

            // Se lo asignamos al campo correspondiente
            this.teams.setText(st);
        }catch (RuntimeException e){
            setTeamsSaved();
        }
    }

    /**
     * Metodo que permite configurar el tab host
     */
    private void addTabHost() {

        Resources res = getResources();

        // Nombre de las pestañas
        String textTab1 = getString(R.string.text_tab1);
        String textTab2 = getString(R.string.text_tab2);

        // Obtenemos el tabhost del xml
        TabHost tabs = (TabHost) findViewById(R.id.tabhost);
        tabs.setup();

        // Obtenemos las pestañas y se la asignamos al tabhost

        TabHost.TabSpec spec = tabs.newTabSpec("mitab1");
        spec.setContent(R.id.tab1);
        spec.setIndicator(textTab1);
        tabs.addTab(spec);

        spec = tabs.newTabSpec("mitab2");
        spec.setContent(R.id.tab2);
        spec.setIndicator(textTab2);
        tabs.addTab(spec);

        tabs.setCurrentTab(0);


        // Creamos un adaprtado para el tabhost
        ArrayAdapter adapterMoneyBet = ArrayAdapter.createFromResource(this, R.array.MoneyBet, android.R.layout.simple_spinner_item);
        // Se los asignmos
        moneyBet.setAdapter(adapterMoneyBet);

    }

    /**
     * Metodo que permite enlazar los componentes con el xml y muestra los equipos
     */
    private void initComponents() {

        moneyBet = (Spinner) findViewById(R.id.spinner_moneybet);
        teams = (TextView) findViewById(R.id.textView_teams);
        number1 = (EditText) findViewById(R.id.editText_number1);
        number2 = (EditText) findViewById(R.id.editText_number2);

        // Instanciamos las preferencias, (Solo podra ser utilizada por la aplicacion)
        preferences = getSharedPreferences("data", this.MODE_PRIVATE);

        setTeams();

    }

    /**
     * Metodo que permite validar si la apuesta es correcta
     * @return TRUE si es correcta, FALSE si no lo es
     */
    private boolean validateMoneyBet() {

        boolean valid = true;

        int indexSpinetBets = this.moneyBet.getSelectedItemPosition();

        if (indexSpinetBets == 0)// Comprobamos que la selecion sea distinta del indice 0
            valid = false;

        return valid;
    }

    /**
     * Metodo que permite validar los numeros introducidos
     * @return TRUE si son validos FALSE si no lo son
     */
    private boolean validateNumbers() {

        boolean valid = true;

        String number1 = this.number1.getText().toString();
        String number2 = this.number2.getText().toString();

        //Comprobamos que haya datos en los numeros

        if (number1.length() == 0)
            return false;

        if (number2.length() == 0)
            return false;

        return valid;

    }


    /**
     * Metodo que valida si el rango de los numeros es valido
     * @return TRUE si son validos, FALSO si no lo son
     */
    private boolean validateUnderstoodNumbers() {

        boolean valid = true;

        int value1 = Integer.parseInt(this.number1.getText().toString());
        int value2 = Integer.parseInt(this.number2.getText().toString());

        // Comprobamos el rango
        if (value1 < 0 || value1 > 300)
            return false;

        if (value2 < 0 || value2 > 300)
            return false;

        return valid;

    }

    /**
     * MEtodoq que permtie validar los datos introducidos por el usuario
     * @return TRUE si son validos, FALSe si no lo son
     */
    private boolean validateData() {


        boolean valid = true;
        String message = "";

        // Validamos si el dinero de la apuesta es valido
        if (!validateMoneyBet()) {

            message = getString(R.string.error_no_bets);
            Toast.makeText(SettingsActivity.this, message, Toast.LENGTH_SHORT).show();
            return false;
        }
        // Validamos si los numeros son correctos
        if (!validateNumbers()) {

            message = getString(R.string.error_no_numbers);
            Toast.makeText(SettingsActivity.this, message, Toast.LENGTH_SHORT).show();
            return false;
        }
        // Validamos si los numeros se encuentran en el rango correcto
        if (!validateUnderstoodNumbers()) {

            message = getString(R.string.error_no_between);
            Toast.makeText(SettingsActivity.this, message, Toast.LENGTH_SHORT).show();
            return false;
        }


        return valid;

    }

    /**
     * Metodo que se ejecutara al pulsar sobre el boton Guardar
     * @param v
     */
    public void actionSave(View v) {

        String message = "";

        if (validateData()) {
            senData();
            message = getString(R.string.text_acceptSettings);
            Toast.makeText(SettingsActivity.this, message, Toast.LENGTH_SHORT).show();
            closeActivity();
        }


    }

    private void setTeamsSaved(){
        String stTeams = preferences.getString("teams","");

        this.teams.setText(stTeams);
    }

    /**
     * Metodo que se ejecutara al pulsar el boton Volver
     * @param v
     */
    public void actionReturn(View v){
        closeActivity();
    }

    /**
     * Metodo que finaliza la actividad actual
     */
    public void closeActivity(){
        this.finish();
    }


    /**
     * Metodo que permite enviar datos de respuesta
     */
    private void senData() {

        Intent intent = new Intent();

        // Obtenemos los datos
        String moneyBet = (String) this.moneyBet.getSelectedItem();
        String[] result = new String[2];
        String valueNumber1 = this.number1.getText().toString();
        String valueNumber2 = this.number2.getText().toString();
        result[0] = valueNumber1;
        result[1] = valueNumber2;

        // Los enviamos
        intent.putExtra("moneybet", moneyBet);
        intent.putExtra("result", result);

        // Indicamos que el proceso ha ido correctamente
        setResult(RESULT_OK, intent);

    }


}
