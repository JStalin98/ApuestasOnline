package com.example.jstalin.apuestasonline;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

/**
 * Clase que muestra informacion de ayuda al usuario
 */
public class HelpActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help);
    }

    /**
     * Metodo que se ejecutara al pulsar el boton Return
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
}
