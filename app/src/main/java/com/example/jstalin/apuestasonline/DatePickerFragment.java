package com.example.jstalin.apuestasonline;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.widget.CalendarView;
import android.widget.DatePicker;

import java.util.Calendar;


/**
 * Clase que permite crear un Dialogo con el Widget DatePicker
 */
public class DatePickerFragment extends DialogFragment{

    /**
     * Metodo llamado a la hora de crear el dialogo
     * @param savedInstanceState
     * @return --> Devuevle el dialogo creado
     */
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final Calendar c = Calendar.getInstance(); // Obtenemos una intancia de la fecha actual
        // Separamos la fecha en años, meses y dias
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);

        // Devolvemos el Dialogo
        return new DatePickerDialog(getActivity(), listener, year, month, day);

    }

    /**
     * Metodo que permite instancia el DatePicker
     * @param listener
     * @return --> Devuelve un Fragment con el date picker dentro de el
     */
    public static DatePickerFragment newInstance(DatePickerDialog.OnDateSetListener listener) {
        DatePickerFragment fragment = new DatePickerFragment(); // Creamos un nuevo date picker
        fragment.setListener(listener); // Agregamos evento
        return fragment;
    }

    /**
     * Metodo que permite asignar un evento listener al dialogo
     * @param listener --> Evento que se va a asignar
     */
    public void setListener(DatePickerDialog.OnDateSetListener listener){
        this.listener = listener;
    }

    // Variable que alamcena el evento que utilizara el dialogo con el date picker
    private DatePickerDialog.OnDateSetListener listener;
}
