package com.example.jstalin.apuestasonline.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;

import com.example.jstalin.apuestasonline.lessons.PreferencesFragment;

/**
 * Actividad en la cual se van a escoger las preferencias del usuario
 */
public class PreferencesActivity extends AppCompatActivity {

    // Intene que va abrir
    private Intent intentMain;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);



        getFragmentManager().beginTransaction().replace(android.R.id.content, new PreferencesFragment()).commit();


    }

    /**
     * SPbreescribimos el metodo que se ejecuta al pulsar el boton de volver atras
     * @param keyCode
     * @param event
     * @return
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        // Comporbamos que se haya pulsado el boton de retroceso
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {

            openMain();
            return true;
        }

        return super.onKeyDown(keyCode, event);
    }


    /**
     * Metodo que permite abrir la actividad Main
     */
    private void openMain(){


        intentMain = new Intent(getBaseContext(), MainActivity.class);
        startActivity(intentMain);
        finish();
    }


}


