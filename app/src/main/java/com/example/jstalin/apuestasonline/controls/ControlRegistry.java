package com.example.jstalin.apuestasonline.controls;

import android.app.DatePickerDialog;
import android.content.Context;
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

import java.util.Calendar;

/**
 * Clase que crea un control
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

    // Eventos que gestiona el control
    private OnValidateListener listenerValidate;
    private OnReturnListener listenerReturn;

    /**
     * Constructor con contexto
     *
     * @param context
     */
    public ControlRegistry(Context context) {
        super(context);

        initialize(); // Inicializamos el estado del control
    }

    /**
     * Constructor con contexto y atributos
     *
     * @param context
     * @param attrs
     */
    public ControlRegistry(Context context, AttributeSet attrs) {
        super(context, attrs);
        initialize();
    }

    /**
     * Iniciamos el estado del control
     */
    private void initialize() {

        // Inflamos el control con el xml
        inflaterLayout();

        // Ininiciamos los componentes
        initComponents();

        // ASignacion de evnetos
        assignEvents();

    }


    /**
     * Metodo asigna la interfaz al control desde el xml
     */
    private void inflaterLayout() {
        // Obtenemos el contexto
        String infService = Context.LAYOUT_INFLATER_SERVICE;
        // Obtenmos el layout que se utilizara y asignamos al control
        LayoutInflater li =
                (LayoutInflater) getContext().getSystemService(infService);
        li.inflate(R.layout.control_registry, this, true);
    }

    /**
     * Metodo que inicia los componentes del control
     */
    private void initComponents() {

        this.title = (TextView) findViewById(R.id.textView_title);
        this.name = (EditText) findViewById(R.id.editText_name);
        this.email = (EditText) findViewById(R.id.editText_email);
        this.birthdate = (EditText) findViewById(R.id.editText_birthdate);
        this.comeBack = (Button) findViewById(R.id.button_return);
        this.validate = (Button) findViewById(R.id.button_validate);

    }

    /**
     * Metodo que asigna los eventos que gestiona el control
     */
    private void assignEvents() {

        // Asignamos el evento de la fecha de nacimiento
        assignEventBirthdate();

        // Asignamos el evento del boton de volver
        assignEventReturn();

        // Asignamos el evento del boton de validar
        assignEventValidate();

    }

    /**
     * Metodo que asigna al edit text de la fecha de nacimiento un evento
     */
    private void assignEventBirthdate() {
        this.birthdate.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialogDatePicker();
            }
        });
    }

    /**
     * Metodo que muestra un dialogo con el datepicker dentroo
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
     * Metodo que asigna un evento al boton de validar
     */
    private void assignEventValidate() {
        this.validate.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                DataRegistry dataRegistry = getInformationRegistry(); // Obtenemos los datos del control
                listenerValidate.onValidate(dataRegistry);// Enviamos evento con los datos
            }
        });
    }


    /**
     * Metodo que obtiene los datos de los componentes del control
     * @return
     */
    private DataRegistry getInformationRegistry() {

        // Obtenemos los datos
        String stName = this.name.getText().toString();
        String stEmail = this.email.getText().toString();
        String stBirthdate = this.birthdate.getText().toString();

        // Devolvemos un objeto que contiene los datos
        return new DataRegistry(stName, stEmail, stBirthdate);

    }

    /**
     * Metodo que asigna un evento al boton return
     */
    private void assignEventReturn() {
        this.comeBack.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                // Devolvemos un evento
                listenerReturn.onReturn();
            }
        });
    }


    /**
     * Metodo que permite asinga un listener al evento
     * @param listener
     */
    public void setOnValidateListener(OnValidateListener listener) {
        this.listenerValidate = listener;
    }


    /**
     * Metodo que permite asinga un listener al evento
     * @param listener
     */
    public void setOnReturnListener(OnReturnListener listener) {
        this.listenerReturn = listener;
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


}
