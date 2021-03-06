package com.example.jstalin.apuestasonline.lessons;

import android.content.ContentValues;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;

import com.example.jstalin.apuestasonline.R;
import com.example.jstalin.apuestasonline.activities.PreferencesActivity;
import com.example.jstalin.apuestasonline.databases.OnlineBetsDatabase;

import java.util.Locale;

/**
 * Created by JStalin on 06/02/2018.
 */

/**
 * CLase que sirve para la realizacion de la actividad preferencias
 */
public class PreferencesFragment extends android.preference.PreferenceFragment implements SharedPreferences.OnSharedPreferenceChangeListener {

    // Nombre de las preferencias
    private static final String PREFERENCE_USER = "preferenceUser";
    private static final String PREFERENCE_PASSWORD = "preferencePassword";
    private static final String PREFERENCE_LANGUAGE = "preferenceLanguage";

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate((savedInstanceState));

        addPreferencesFromResource(R.xml.preferences);


    }


    @Override
    public void onResume() {
        super.onResume();

        getPreferenceScreen().getSharedPreferences().registerOnSharedPreferenceChangeListener(this);
    }

    @Override
    public void onPause() {
        super.onPause();

        getPreferenceScreen().getSharedPreferences().unregisterOnSharedPreferenceChangeListener(this);
    }


    /**
     * Evento que se ejcuta al cambiar el valor de una preferencia
     *
     * @param sharedPreferences
     * @param key
     */
    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {


        switch (key) {
            case PREFERENCE_USER:

                updateUserInDB();
                break;
            case PREFERENCE_PASSWORD:
                updatePasswordInDB();
                break;
            case PREFERENCE_LANGUAGE:

                updateLanguageApplication();
                break;

        }

    }

    /**
     *Metodo que actualiza el nombre de usuario en la BD
     */
    private void updateUserInDB() {

        OnlineBetsDatabase onlineBetsDatabase = new OnlineBetsDatabase(getActivity(), OnlineBetsDatabase.NAME_BD, null, OnlineBetsDatabase.VERSION);
        SQLiteDatabase db = onlineBetsDatabase.getWritableDatabase();

        String email = getPreferenceManager().getSharedPreferences().getString("preferenceEmail", "");
        String user = getPreferenceManager().getSharedPreferences().getString("preferenceUser", "");

        ContentValues newUser = new ContentValues();
        newUser.put("name", user);

        String[] args = new String[]{email};

        db.update(OnlineBetsDatabase.Tables.USER, newUser, "email=?", args);
        db.close();

    }

    /**
     * Metodo que actualiza el password en la BD
     */
    private void updatePasswordInDB() {

        OnlineBetsDatabase onlineBetsDatabase = new OnlineBetsDatabase(getActivity(), OnlineBetsDatabase.NAME_BD, null, OnlineBetsDatabase.VERSION);
        SQLiteDatabase db = onlineBetsDatabase.getWritableDatabase();

        String email = getPreferenceManager().getSharedPreferences().getString("preferenceEmail", "");
        String password = getPreferenceManager().getSharedPreferences().getString("preferencePassword", "");

        ContentValues newPassword = new ContentValues();
        newPassword.put("password", password);

        String[] args = new String[]{email};

        db.update(OnlineBetsDatabase.Tables.USER, newPassword, "email=?", args);
        db.close();

    }

    /**
     * Metodo que actualiza el lenguage de la aplicacion
     */
    private void updateLanguageApplication() {

        Locale locale = null;
        Configuration config = new Configuration();

        String selected = getPreferenceManager().getSharedPreferences().getString("preferenceLanguage", "");
        switch (selected) {

            case "ES":
                locale = new Locale("es");
                Log.d("ENTRO_", "ES    ******************");
                break;
            case "EN":
                locale = new Locale("en");

                break;
        }


        if (!isNullLocale(locale)) {

            config.locale = locale;

            getResources().updateConfiguration(config, null);
            updateActivity();

        }


    }

    /**
     * Metodo que comprueba si un objeto result es null
     * @param locale
     * @return
     */
    private boolean isNullLocale(Locale locale) {
        return locale == null;
    }

    /**
     * Metodo que permite recargar la actividad
     */
    private void updateActivity() {
        Intent refresh = new Intent(getActivity(), PreferencesActivity.class);
        getActivity().finish();
        startActivity(refresh);
    }
}

