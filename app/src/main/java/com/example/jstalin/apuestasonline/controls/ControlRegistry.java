package com.example.jstalin.apuestasonline.controls;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.jstalin.apuestasonline.lessons.DataRegistry;
import com.example.jstalin.apuestasonline.Interfaces.OnReturnListener;
import com.example.jstalin.apuestasonline.Interfaces.OnValidateListener;
import com.example.jstalin.apuestasonline.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Clase que permite registrar a un suaurio
 */
public class ControlRegistry extends LinearLayout {


    // VARIABLES PARA RELLENAR LOS DIGITOS
    private static final int TWO_DIGITS = 2;
    private static final int FOUR_DIGITS = 4;

    // VAriables que hacen referencia a los componentes
    private TextView title;
    private EditText name;
    private EditText email;
    private EditText birthdate;
    private Button validate;
    private Button comeBack;

    public ControlRegistry(Context context) {
        super(context);

        initialize();
    }

    public ControlRegistry(Context context, AttributeSet attrs) {
        super(context, attrs);
        initialize();
    }

    /**
     * Iniciamos los componentes
     */
    private void initialize() {

        inflaterLayout();

        initControls();

        assignEvents();

    }


    private void inflaterLayout() {
        String infService = Context.LAYOUT_INFLATER_SERVICE;
        LayoutInflater li =
                (LayoutInflater) getContext().getSystemService(infService);
        li.inflate(R.layout.control_registry, this, true);
    }

    private void initControls() {

        this.title = (TextView) findViewById(R.id.textView_title);
        this.name = (EditText) findViewById(R.id.editText_name);
        this.email = (EditText) findViewById(R.id.editText_email);
        this.birthdate = (EditText) findViewById(R.id.editText_birthdate);
        this.comeBack = (Button) findViewById(R.id.button_return);
        this.validate = (Button) findViewById(R.id.button_validate);



    }

    private void assignEvents() {

        assignEventBirthdate();

        assignEventReturn();

        assignEventValidate();

    }

    private void assignEventBirthdate(){
        this.birthdate.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialogDatePicker();
            }
        });
    }

    private void assignEventValidate(){
        this.validate.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                DataRegistry dataRegistry = getInformationRegistry();
                listenerValidate.onValidate(dataRegistry);
            }
        });
    }

    private DataRegistry getInformationRegistry(){

        String stName = this.name.getText().toString();
        String stEmail = this.email.getText().toString();
        String stBirthdate = this.birthdate.getText().toString();

        return new DataRegistry(stName,stEmail,stBirthdate);

    }

    private void assignEventReturn(){
        this.comeBack.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                listenerReturn.onReturn();
            }
        });
    }


    /**
     * Metodo mmuestra la fecha seleccionado en el campo de texto
     *
     * @param year
     * @param month
     * @param day
     */
    public void setDate(int year, int month, int day) {

        String selectedDate = formatedDate(year, month, day);

        this.birthdate.setText(selectedDate);

    }

    /**
     * Metodo que formate la fecha para que quede en formato dd/mm/yyyy
     *
     * @param year
     * @param month
     * @param day
     * @return
     */
    private String formatedDate(int year, int month, int day) {

        String formatDate = "";

        String stYear = addDigits(year, FOUR_DIGITS);
        String stMonth = addDigits(month + 1, TWO_DIGITS);// +1 Debido a que los meses empiezan en 0
        String stDay = addDigits(day, TWO_DIGITS);

        formatDate = stDay + "/" + stMonth + "/" + stYear;

        return formatDate;
    }


    /**
     * Metodo que añade una cantidad de digitos al entero que se le pase
     * por parametro
     *
     * @param digit
     * @param digits
     * @return
     */
    public String addDigits(int digit, int digits) {

        String number = String.valueOf(digit);
        int quantity = digits - number.length();

        String aux = "";

        for (int i = 0; i < quantity; i++) {
            aux += "0";

        }

        aux += number;
        return aux;

    }

    /**
     * Metodo que permite mostrar un dialogo con el componente
     * DatePicker
     */
    private void showDialogDatePicker() {

        // Creacion de un dialogo con el DatePicker
        DatePickerDialog dialogBirthdate = newDatePickerDialog();
        // Lo mostramos
        dialogBirthdate.show();
    }

    /**
     * Metodo que crear un objeto DatePickerDialog con la fecha actual del sistema
     *
     * @return D
     */
    private DatePickerDialog newDatePickerDialog() {
        final Calendar c = Calendar.getInstance(); // Obtenemos una intancia de la fecha actual

        // Separamos la fecha en años, meses y dias
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);

        return new DatePickerDialog(getContext(), new SelectorOfDate(), year, month, day);
    }


    /**
     * Clase que permite gestionar el evento que se produce cuando
     * el usuario selecciona una fecha en el componente DatePicker
     */
    class SelectorOfDate implements DatePickerDialog.OnDateSetListener {

        @Override
        public void onDateSet(DatePicker datePicker, int year, int month, int day) {

            setDate(year, month, day); // LLamamos al metodo setDate

        }
    }





    private OnValidateListener listenerValidate;
    private OnReturnListener listenerReturn;

    public void setOnValidateListener(OnValidateListener listener){
        this.listenerValidate = listener;
    }

    public void setOnReturnListener(OnReturnListener listener){
        this.listenerReturn = listener;
    }


}
