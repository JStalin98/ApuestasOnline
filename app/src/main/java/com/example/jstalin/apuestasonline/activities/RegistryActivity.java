package com.example.jstalin.apuestasonline;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import android.util.Log;

import com.example.jstalin.apuestasonline.Interfaces.OnReturnListener;

/**
 * Clase que permite registrar a un suaurio
 */
public class RegistryActivity extends AppCompatActivity {

    private ControlRegistry controlRegistry;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registry);
        Log.d("ENTRO", "ENTRAMOS EN REGISTRY ACTIVITY");
        initComponents(); // Iniciamos los componentes
    }

    private void initComponents(){

        this.controlRegistry = (ControlRegistry)findViewById(R.id.controlRegistry);
        controlRegistry.setOnReturnListener(new OnReturnListener() {
            @Override
            public void onReturn() {
                finish();
            }
        });

    }



    /**
     * Metodo que permite guardar el estado de la actividad
     * @param state
     */
    @Override
    protected void onSaveInstanceState(Bundle state) {
        super.onSaveInstanceState(state);
    }

    @Override
    protected void onRestoreInstanceState(Bundle state) {
        super.onRestoreInstanceState(state);

    }

}
