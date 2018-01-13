package com.example.jstalin.apuestasonline.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import android.util.Log;
import android.widget.Toast;

import com.example.jstalin.apuestasonline.Interfaces.OnValidateListener;
import com.example.jstalin.apuestasonline.controls.ControlRegistry;
import com.example.jstalin.apuestasonline.Interfaces.OnReturnListener;
import com.example.jstalin.apuestasonline.R;
import com.example.jstalin.apuestasonline.lessons.DataRegistry;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Clase que permite registrar a un suaurio
 */
public class RegistryActivity extends AppCompatActivity {

    // Variables con la informacion de la actividad
    private String name;
    private String email;
    private String birthdate;

    // Control que contiene la actividad
    private ControlRegistry controlRegistry;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registry);

        initComponents(); // Iniciamos los componentes
    }

    private void initComponents() {

        // Intanciamos las variabels a una cadena vacia
        this.name = "";
        this.email = "";
        this.birthdate = "";

        // Obtenemos el control del xml
        this.controlRegistry = (ControlRegistry) findViewById(R.id.controlRegistry);

        setEventsControlRegistry();

    }

    /**
     * Metodo que maneja los eventos proporcionado por el evento
     */
    private void setEventsControlRegistry() {
        // Evento que se produce al pulsar el boton Return del control
        controlRegistry.setOnReturnListener(new OnReturnListener() {
            @Override
            public void onReturn() {
                actionReturn();
            }
        });

        // Evento que se produce al pulsar el boton Validate del control
        controlRegistry.setOnValidateListener(new OnValidateListener() {
            @Override
            public void onValidate(DataRegistry dataReistry) {
                updateData(dataReistry);
                actionValidate();
            }
        });
    }

    /**
     * Actualiza las variables con los datos que devuelve el vento
     *
     * @param data
     */
    private void updateData(DataRegistry data) {

        this.name = data.getName();
        this.email = data.getEmail();
        this.birthdate = data.getBirthdate();

    }

    /**
     * Metodo que se ejcutara al pulsar el boton Validar del control
     */
    public void actionValidate() {

        String menssageOk = ""; // Almacena el mensaje que se va a mostrar

        if (validateData()) {// Validamos los datos
            menssageOk = getString(R.string.text_acceptRegistry); // Asignamos mensaje
            sendData();// Enviamos los datos
            Toast.makeText(RegistryActivity.this, menssageOk, Toast.LENGTH_SHORT).show(); // Mostramos mensaje
            closeActivity();// Cerramos la actividad
        }

    }

    /**
     * Metodo que  permite validar los datos introducidos por el usuario
     *
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


            if (!validateEmail(email)) { // Comprobamos si el email es valido
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
     * MEtodo que permite validar si faltan datos
     *
     * @return TRUE si no faltan datos FALS si faltan datos
     */
    protected boolean validateMissingData() {
        boolean valid = true;


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
     * Metodoq eu permite validar el email
     * COn un formato minimo "@."
     *
     * @param email Email a evaluar
     * @return TRUE si es valido, FALSE si no lo es
     */
    private boolean validateEmail(String email) {

        Log.d("ERROR", email + "_------------------");

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
        if (bandera) {// Si se encuentra un @

            // Comprobamos si despues se encuentra un "-"
            for (; aux < email.length(); aux++) {
                if (email.charAt(aux) == '.')
                    return true;
            }
        }

        return valid;

    }


    /**
     * Metodo que permite validar la edad del usuario
     *
     * @return TRUE si es mayot de 18 FALSE si es menor de 18
     */
    private boolean validateAge() {

        // Calculamos la edad
        int edad = calculateAge(birthdate);

        return isAdult(edad);

    }


    /**
     * Metodo que permite calcular la edad dada una fecha en formato String
     *
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
     * Metodo que comprueba si es mayor de edad >=18 dada una edad
     *
     * @param edad
     * @return
     */
    private boolean isAdult(int edad) {
        boolean valid = true;

        if (edad < 18)
            valid = false;


        return valid;

    }

    /**
     * Metodo que permite devolver los datos a la actividad principal
     */
    public void sendData() {

        Intent intent = new Intent();

        // Los devolvemos
        intent.putExtra("name", name);
        intent.putExtra("email", email);
        intent.putExtra("birthdate", birthdate);

        // Indicamos que el proceso ha ido correcto
        setResult(RESULT_OK, intent);

    }


    /**
     * Metodo que se ejecutara al pulsar el boton Volver
     */
    public void actionReturn() {
        closeActivity();
    }

    /**
     * Metodo que finaliza la actividad actual
     */
    private void closeActivity() {
        this.finish();
    }


}
