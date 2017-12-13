package com.example.jstalin.apuestasonline;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

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
     * Metodo que devuelve a la actividad anterior
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
