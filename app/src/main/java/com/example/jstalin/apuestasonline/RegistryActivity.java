package com.example.jstalin.apuestasonline;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Clase que permite registrar a un suaurio
 */
public class RegistryActivity extends AppCompatActivity {


    // VARIABLES PARA RELLENAR LOS DIGITOS
    private static final int TWO_DIGITS = 2;
    private static final int FOUR_DIGITS = 4;

    // VAriables que hacen referencia a los componentes
    private EditText name;
    private EditText email;
    private EditText birthdate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registry);

        initComponents(); // Iniciamos los componentes
    }


    /**
     * Metodo que permite guardar el estado de la actividad
     * @param state
     */
    @Override
    protected void onSaveInstanceState(Bundle state) {
        super.onSaveInstanceState(state);

        // Guaradmos los datos de los campos
        String stName = name.getText().toString();
        String stEmail = email.getText().toString();
        String stBirthdate = birthdate.getText().toString();

        // Comprobamos si hay datos
        if(stName.length()!=0)
            state.putString("name", stName);

        if(stEmail.length()!=0)
            state.putString("emaul", stEmail);

        if(stBirthdate.length()!=0)
            state.putString("birthdate", stBirthdate);

    }

    @Override
    protected void onRestoreInstanceState(Bundle state) {
        super.onRestoreInstanceState(state);
        if(state!=null){ // Comprobamos si hay un estado guardado

            // Obtenemos los datos
            String stName = state.getString("name");
            String stEmail = state.getString("email");
            String stBirthdate = state.getString("birthdate");

            // Restauramos los datos
            this.name.setText(stName);
            this.email.setText(stEmail);
            this.birthdate.setText(stBirthdate);
        }

    }


    /**
     * Metodo que permite abrir el calendario
     * @param view
     */
    public void openCalendar(View view) {
        showDatePickerDialog();
    }

    /**
     * Metodo que muestra el dailogo con el date picker
     */
    public void showDatePickerDialog() {
        // Creamos el dialogo y lo instanciamos
        DatePickerFragment newFragment = DatePickerFragment.newInstance(new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int day) {
                setDate(year, month, day); // LLamamos al metodo setDate
            }
        });

        // Mostramos el dialogo
        newFragment.show(getFragmentManager(), "datePicker");


    }

    /**
     * Metodo meustra la fecha seleccionado en el campo de texto
     * @param year
     * @param month
     * @param day
     */
    public void setDate(int year, int month, int day) {

        String selectedDate = "";

        String stYear = addDigits(year, FOUR_DIGITS);
        String stMonth = addDigits(month + 1, TWO_DIGITS);
        String stDay = addDigits(day, TWO_DIGITS);

        selectedDate = stDay + "/" + stMonth + "/" + stYear;

        this.birthdate.setText(selectedDate);

    }


    /**
     * Metodo que añade una cantidad de digitos al entero que se le pase
     * por parametro
     * @param digit
     * @param digits
     * @return
     */
    public String addDigits(int digit, int digits) {

        String number = String.valueOf(digit);
        int quantity = digits - number.length();

        String aux = "";

        for (int i = 0; i < quantity; i++) {
            aux += "0";

        }

        aux += number;
        return aux;

    }


    /**
     * Iniciamos los componentes
     */
    private void initComponents() {
        this.name = (EditText) findViewById(R.id.editText_name);
        this.email = (EditText) findViewById(R.id.editText_email);
        this.birthdate = (EditText) findViewById(R.id.editText_birthdate);
    }

    /**
     * Metodo que permite validar la edad del usuario
     * @return TRUE si es mayot de 18 FALSE si es menor de 18
     */
    private boolean validateAge() {

        // Obtenemos la fecha de nacimiento
        String birthdate = this.birthdate.getText().toString();

        // Calculamos la edad
        int edad = calculateAge(birthdate);
        boolean valid = true;

        if (edad < 18)
            return false;


        return valid;

    }

    /**
     * Metodo que permite calcular la edad dada una fecha en formato String
     * @param stBirthdate --> Fecha
     * @return --> Edad
     */
    private int calculateAge(String stBirthdate) {

        Date birthdate = null;
        try {
            // Intaciamos un formato para pasar de texto a objeto de tipo Dato
            birthdate = new SimpleDateFormat("dd/MM/yyyy").parse(stBirthdate);
        } catch (Exception ex) {
            System.out.println("Error:" + ex);
        }

        Calendar cBirthdate = Calendar.getInstance();
        //Se crea un objeto con la fecha actual
        Calendar rightnow = Calendar.getInstance();
        //Se asigna la fecha recibida a la fecha de nacimiento.
        cBirthdate.setTime(birthdate);
        //Se restan la fecha actual y la fecha de nacimiento
        int year = rightnow.get(Calendar.YEAR) - cBirthdate.get(Calendar.YEAR);
        int month = rightnow.get(Calendar.MONTH) - cBirthdate.get(Calendar.MONTH);
        int day = rightnow.get(Calendar.DATE) - cBirthdate.get(Calendar.DATE);
        //Se ajusta el año dependiendo el mes y el día
        if (month < 0 || (month == 0 && day < 0)) {
            year--;
        }

        return year;

    }


    /**
     * MEtodo que permite validar si faltan datos
     * @return TRUE si no faltan datos FALS si faltan datos
     */
    private boolean validateMissingData() {
        boolean valid = true;

        String name = this.name.getText().toString();
        String email = this.email.getText().toString();
        String birthdate = this.birthdate.getText().toString();

        // Comprobamos cada uno de los campos
        if (name.length() == 0)
            return false;
        if (email.length() == 0)
            return false;
        if (birthdate.length() == 0)
            return false;


        return valid;
    }

    /**
     * Metodo que  permite validar los datos introducidos por el usuario
     * @return TRUE si todos los datos son validos, FALSE si no lo son
     */
    private boolean validateData() {

        boolean valid = true;

        String mesaggeError = "";


        if (!validateMissingData()) { //Comprobamos si se encuentran todos los datos
            mesaggeError = getString(R.string.error_missignDate);
            Toast.makeText(RegistryActivity.this, mesaggeError, Toast.LENGTH_SHORT).show();
            return false;

        } else {

            String email = this.email.getText().toString();
            if(!validateEmail(email)){ // Comprobamos si el email es valido
                mesaggeError = getString(R.string.error_email);
                Toast.makeText(RegistryActivity.this, mesaggeError, Toast.LENGTH_SHORT).show();
                return false;
            }

            if (!validateAge()) {// Comprobamos si la edad es valida
                mesaggeError = getString(R.string.error_younger);
                Toast.makeText(RegistryActivity.this, mesaggeError, Toast.LENGTH_SHORT).show();
                return false;
            }
        }

        return valid;
    }

    /**
     * Metodoq eu permite validar el email
     * COn un formato minimo "@."
     * @param email Email a evaluar
     * @return TRUE si es valido, FALSE si no lo es
     */
    private boolean validateEmail(String email) {

        boolean valid = false;
        boolean bandera = false;
        int aux = 0;

        //Comprbomaos si se encuentra un @
        for (int i = 0; i < email.length() && !bandera; i++) {
            if (email.charAt(i) == '@') {
                bandera = true;
                aux = i;
            }

        }
        if(bandera){// Si se encuentra un @

            // Comprobamos si despues se encuentra un "-"
            for(; aux<email.length(); aux++){
                if(email.charAt(aux)=='.')
                    return true;
            }
        }

        return valid;

    }

    /**
     * Metodo que permite devolver los datos a la actividad principal
     */
    public void sendData() {

        Intent intent = new Intent();

        // Obtenemos los datos de los campos
        String name = this.name.getText().toString();
        String email = this.email.getText().toString();
        String birthdate = this.birthdate.getText().toString();

        // Los devolvemos
        intent.putExtra("name", name);
        intent.putExtra("email", email);
        intent.putExtra("birthdate", birthdate);

        // Indicamos que el proceso ha ido correcto
        setResult(RESULT_OK, intent);

    }

    /**
     * Metodo que se ejcutara al pulsar el boton Validar
     * @param v
     */
    public void actionValidate(View v) {

        String menssageOk = "";

        if (validateData()) {// Validamos los datos
            menssageOk = getString(R.string.text_acceptRegistry);
            sendData();// Enviamos los datos
            Toast.makeText(RegistryActivity.this, menssageOk, Toast.LENGTH_SHORT).show();
            closeActivity();// Cerramos la actividad
        }

    }

    /**
     * Metodo que se ejecutara al pulsar el boton Volver
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
