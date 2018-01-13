package com.example.jstalin.apuestasonline.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.example.jstalin.apuestasonline.R;

/**
 * Clase que va a mostrar informacion respecto a la aplicacion
 */
public class AboutActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
    }

    /**
     * Metodo que se ejecute al pulsar el boton Return
     *
     * @param v
     */
    public void actionReturn(View v) {
        closeActivity();
    }

    /**
     * Metodo que finalize la actividad que se encuentre
     * en ese deteminado momento como visible
     */
    public void closeActivity() {
        this.finish();
    }

}
