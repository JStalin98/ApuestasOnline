package com.example.jstalin.apuestasonline.activities;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.preference.PreferenceManager;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.jstalin.apuestasonline.R;
import com.example.jstalin.apuestasonline.databases.OnlineBetsDatabase;

import java.util.Locale;

/**
 * Actividad para logear al usuario en el sistema
 */
public class LoginActivity extends AppCompatActivity {


    // Variables que almacena
    private String userName = "";
    private String userPassword = "";
    private String userEmail = "";


    // Intenet que va abrir
    private Intent intentMain;
    private Intent intentRegistry;


    // Conexion a BD
    private OnlineBetsDatabase onlineBetsDatabase;
    private SQLiteDatabase db;

    // Preferencias
    private SharedPreferences preferences;
    private SharedPreferences.Editor editorPreferences;

    // Componentes
    private TextInputLayout inputLayout_email;
    private TextInputLayout inputLayout_password;
    private Button button_login;

    //private String sqlGetUser = "SELECT user, password FROM users WHERE user=? AND pasword=? ";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        initComponents();

        setLanguage();

        if (isKeepUser()) {
            loginUserPreferences();
        }

    }

    /**
     * Metodo que asigna el lenguage
     */
    private void setLanguage() {

        updateLanguageApplication();
    }


    /**
     * Metodo que actualiza el lenguage de la aplicacion en funcion de la preferencia
     * que tenga puesta el usuario
     */
    private void updateLanguageApplication() {

        Locale localePhone = getCurrentLocale(this);
        Locale locale = null;
        Configuration config = new Configuration();

        String selected = preferences.getString("preferenceLanguage", "");
        switch (selected) {

            case "ES":
                locale = new Locale("es");
                break;
            case "EN":
                locale = new Locale("en");
                break;
        }

        if (!localePhone.equals(locale)) {


            if (!isNullLocale(locale)) {

                config.locale = locale;

                getResources().updateConfiguration(config, null);

                reloadActivity();
            }

        }

    }

    /**
     * Metodo que devuelve un objeto Locale con la configuacion del idioma local
     * que tiene el sistema
     * @param context
     * @return
     */
    private Locale getCurrentLocale(Context context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            return context.getResources().getConfiguration().getLocales().get(0);
        } else {
            //noinspection deprecation
            return context.getResources().getConfiguration().locale;
        }
    }


    /**
     * Metodo que permite reiniciar la actividad
     */
    private void reloadActivity() {

        Intent refresh = new Intent(this, LoginActivity.class);
        startActivity(refresh);
        closeActivity();

    }

    /**
     * Metodo que comprueba si un objeto Locales es nulo
     * @param locale
     * @return TRUE si lo es, FALSE si no lo es
     */
    private boolean isNullLocale(Locale locale) {
        return locale == null;
    }

    /**
     * MEtodo que inicia sesion con los datos que tiene alamcenado en las preferencias
     */
    private void loginUserPreferences() {


        userEmail = preferences.getString("preferenceEmail", "");
        userPassword = preferences.getString("preferencePassword", "");

        if (validUserInBD()) {
            openMain();
            clear();
            closeActivity();
        }

    }

    /**
     * Metodo que inicializa los componentes
     */
    private void initComponents() {


        onlineBetsDatabase = new OnlineBetsDatabase(this, OnlineBetsDatabase.NAME_BD, null, OnlineBetsDatabase.VERSION);
        db = onlineBetsDatabase.getReadableDatabase();

        preferences = PreferenceManager.getDefaultSharedPreferences(this);
        editorPreferences = preferences.edit();

        intentMain = new Intent(this, MainActivity.class);
        inputLayout_email = (TextInputLayout) findViewById(R.id.inputLayout_email);
        inputLayout_password = (TextInputLayout) findViewById(R.id.inputLayout_password);

        button_login = (Button) findViewById(R.id.button_login);


    }

    /**
     * Metodo que se ejecutara cuando se pulse el boton de Iniciar Sesion
     * @param v
     */
    public void actionLogin(View v) {

        if (validateData()) {
            if (validUserInBD()) {
                updatePreferences();
                openMain();
                clear();
                closeActivity();
            } else {
                showDialogSimple(getString(R.string.text_errorNoUserInBD));
            }
        }

    }


    /**
     * Metodo que se ejecutara si se pulsa en el ttexto inferior
     * @param v
     */
    public void actionRegistry(View v) {
        openRegistry();
    }

    private void clear() {
        inputLayout_email.getEditText().setText(null);
        inputLayout_password.getEditText().setText(null);

    }

    /**
     * Metodo que cierra la actividad
     */
    private void closeActivity(){
        finish();
    }

    /**
     * Metodo que permite mostrar un dialogo simple con un mensaje
     * @param message
     */
    private void showDialogSimple(String message) {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setMessage(message);

        AlertDialog dialog = builder.create();

        dialog.show();

    }

    /**
     * Metodo que abre la actividad Main
     */
    private void openMain() {

        intentMain.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        startActivity(intentMain);
    }

    /**
     * Metodo que abre la actividad Registro
     */
    private void openRegistry() {

        intentRegistry = new Intent(this, RegistryActivity.class);
        startActivity(intentRegistry);


    }

    /**
     * Metodo que valida los datos
     * @return
     */
    private boolean validateData() {

        if (validateEmail() && validatePassword())
            return true;

        return false;


    }


    /**
     * Metodo que valida el email
     * @return
     */
    private boolean validateEmail() {

        userEmail = inputLayout_email.getEditText().getText().toString();

        if (userEmail.isEmpty()) {
            inputLayout_email.setError(getString(R.string.text_emailInvalid));
            return false;
        } else {
            inputLayout_email.setError(null);
        }


        return true;

    }

    /**
     * Metodo que valida la contraseña
     * @return
     */
    private boolean validatePassword() {

        userPassword = inputLayout_password.getEditText().getText().toString();

        if (userPassword.isEmpty()) {
            inputLayout_password.setError(getString(R.string.text_passwordInvalid));
            return false;
        } else {
            inputLayout_email.setError(null);
        }

        return true;
    }

    /**
     * Metodo que valida el usuario en la base de datos
     * @return
     */
    private boolean validUserInBD() {

        // Sentencia de consulta
        String sqlGetUser = "SELECT name, email, password FROM user WHERE email='" + userEmail + "' AND password='" + userPassword + "'";

        Cursor row = db.rawQuery(sqlGetUser, null);

        if (row.moveToFirst()) {

            String nameDB = row.getString(0);
            String emailDB = row.getString(1);
            String passwordDB = row.getString(2);

            if (userEmail.equals(emailDB) && userPassword.equals(passwordDB)) {
                userName = nameDB;
                userPassword = passwordDB;
                userEmail = emailDB;
                return true;
            }

        }
        return false;
    }

    /**
     * Metodo que permite comprobar si el usuario tiene la preferencia de mantener sesion activada
     * @return
     */
    private boolean isKeepUser() {


        boolean preferenceKeepUser = preferences.getBoolean("preferenceKeepUser", false);
        return preferenceKeepUser;

    }


    /**
     * Metodo que permite actualizar los datos de las preferencias
     */
    private void updatePreferences() {

        editorPreferences.putString("preferenceUser", userName);
        editorPreferences.putString("preferencePassword", userPassword);
        editorPreferences.putString("preferenceEmail", userEmail);
        editorPreferences.commit();
    }

}
