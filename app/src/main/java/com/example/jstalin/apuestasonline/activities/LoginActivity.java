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
 * Clase principal de la cual se ejecutaran las diferentes Actividades
 */
public class LoginActivity extends AppCompatActivity {


    private String userName = "";
    private String userPassword = "";
    private String userEmail = "";

    private Intent intentMain;
    private Intent intentRegistry;

    private OnlineBetsDatabase onlineBetsDatabase;
    private SQLiteDatabase db;

    private SharedPreferences preferences;
    private SharedPreferences.Editor editorPreferences;

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

    private void setLanguage() {

        updateLanguageApplication();
    }

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

                Log.d("ENTRO LOCALE", "ENTRO");

                config.locale = locale;

                getResources().updateConfiguration(config, null);

                reloadActivity();
            }

        }

    }

    private Locale getCurrentLocale(Context context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            return context.getResources().getConfiguration().getLocales().get(0);
        } else {
            //noinspection deprecation
            return context.getResources().getConfiguration().locale;
        }
    }


    private void reloadActivity() {

        Intent refresh = new Intent(this, LoginActivity.class);
        startActivity(refresh);
        closeActivity();

    }

    private boolean isNullLocale(Locale locale) {
        return locale == null;
    }

    private void loginUserPreferences() {


        userEmail = preferences.getString("preferenceEmail", "");
        userPassword = preferences.getString("preferencePassword", "");

        if (validUserInBD()) {
            openMain();
            clear();
            closeActivity();
        }

    }

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

    public void actionRegistry(View v) {
        openRegistry();
    }

    private void clear() {
        inputLayout_email.getEditText().setText(null);
        inputLayout_password.getEditText().setText(null);

    }

    private void closeActivity(){
        finish();
    }

    private void showDialogSimple(String message) {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setMessage(message);

        AlertDialog dialog = builder.create();

        dialog.show();

    }

    private void openMain() {

        intentMain.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        startActivity(intentMain);
    }

    private void openRegistry() {

        intentRegistry = new Intent(this, RegistryActivity.class);
        startActivity(intentRegistry);


    }

    private boolean validateData() {

        if (validateEmail() && validatePassword())
            return true;

        return false;


    }

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

    private boolean validUserInBD() {

        String sqlGetUser = "SELECT name, email, password FROM user WHERE email='" + userEmail + "' AND password='" + userPassword + "'";

        Cursor row = db.rawQuery(sqlGetUser, null);

        if (row.moveToFirst()) {

            String nameDB = row.getString(0);
            String emailDB = row.getString(1);
            String passwordDB = row.getString(2);
            Log.d("NAMEDB", nameDB);
            Log.d("EMAILDB", emailDB);
            Log.d("PASSWORDDB", passwordDB);


            if (userEmail.equals(emailDB) && userPassword.equals(passwordDB)) {
                userName = nameDB;
                userPassword = passwordDB;
                userEmail = emailDB;
                return true;
            }

        }


        return false;
    }

    private boolean isKeepUser() {


        boolean preferenceKeepUser = preferences.getBoolean("preferenceKeepUser", false);
        return preferenceKeepUser;

    }


    private void updatePreferences() {

        editorPreferences.putString("preferenceUser", userName);
        editorPreferences.putString("preferencePassword", userPassword);
        editorPreferences.putString("preferenceEmail", userEmail);
        editorPreferences.commit();
    }

}
