package com.example.jstalin.apuestasonline;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class RegistryActivity extends AppCompatActivity {


    private static final int TWO_DIGITS = 2;
    private static final int FOUR_DIGITS = 4;

    private EditText name;
    private EditText email;
    private EditText birthdate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registry);

        initComponents();
    }

    public void openCalendar(View view) {
        showDatePickerDialog();
    }

    public void showDatePickerDialog() {
        DatePickerFragment newFragment = DatePickerFragment.newInstance(new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int day) {
                setDate(year, month, day);
            }
        });


        newFragment.show(getFragmentManager(), "datePicker");


    }

    public void setDate(int year, int month, int day) {

        String selectedDate = "";

        String stYear = addDigits(year, FOUR_DIGITS);
        String stMonth = addDigits(month + 1, TWO_DIGITS);
        String stDay = addDigits(day, TWO_DIGITS);

        selectedDate = stDay + "/" + stMonth + "/" + stYear;

        this.birthdate.setText(selectedDate);

    }


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


    private void initComponents() {
        this.name = (EditText) findViewById(R.id.editText_name);
        this.email = (EditText) findViewById(R.id.editText_email);
        this.birthdate = (EditText) findViewById(R.id.editText_birthdate);
    }

    private boolean validateAge() {

        String birthdate = this.birthdate.getText().toString();

        int edad = calculateAge(birthdate);
        boolean valid = true;

        if (edad < 18)
            return false;


        return valid;

    }

    private int calculateAge(String stBirthdate) {

        Date birthdate =null;
        try {
            /**Se puede cambiar la mascara por el formato de la fecha
             que se quiera recibir, por ejemplo año mes día "yyyy-MM-dd"
             en este caso es día mes año*/
            birthdate = new SimpleDateFormat("dd/MM/yyyy").parse(stBirthdate);
        } catch (Exception ex) {
            System.out.println("Error:"+ex);
        }

        Calendar cBirthdate = Calendar.getInstance();
        //Se crea un objeto con la fecha actual
        Calendar rightnow = Calendar.getInstance();
        //Se asigna la fecha recibida a la fecha de nacimiento.
        cBirthdate.setTime(birthdate);
        //Se restan la fecha actual y la fecha de nacimiento
        int year = rightnow.get(Calendar.YEAR)- cBirthdate.get(Calendar.YEAR);
        int month = rightnow.get(Calendar.MONTH)- cBirthdate.get(Calendar.MONTH);
        int day = rightnow.get(Calendar.DATE)- cBirthdate.get(Calendar.DATE);
        //Se ajusta el año dependiendo el mes y el día
        if(month<0 || (month==0 && day<0)){
            year--;
        }
        //Regresa la edad en base a la fecha de nacimiento
        return year;

    }


    private boolean validateMissingData() {
        boolean valid = true;

        String name = this.name.getText().toString();
        String email = this.email.getText().toString();
        String birthdate = this.birthdate.getText().toString();

        if (name.length() == 0)
            return false;
        if (email.length() == 0)
            return false;
        if (birthdate.length() == 0)
            return false;


        return valid;
    }

    private boolean validateData() {

        boolean valid = true;

        String mesaggeError = "";


        if (!validateMissingData()) {
            mesaggeError = getString(R.string.error_missignDate);
            Toast.makeText(RegistryActivity.this, mesaggeError, Toast.LENGTH_SHORT).show();
            return false;

        } else {
            if (!validateAge()) {
                mesaggeError = getString(R.string.error_younger);
                Toast.makeText(RegistryActivity.this, mesaggeError, Toast.LENGTH_SHORT).show();
                return false;
            }
        }

        return valid;
    }

    public void sendData() {

        Intent intent = new Intent();

        String name = this.name.getText().toString();
        String email = this.email.getText().toString();
        String birthdate =  this.birthdate.getText().toString();

        intent.putExtra("name", name);
        intent.putExtra("email", email);
        intent.putExtra("birthdate", birthdate);

        setResult(RESULT_OK, intent);

    }

    public void actionValidate(View v) {

        String menssageOk = "";

        if (validateData()) {
            menssageOk = getString(R.string.text_acceptRegistry);
            sendData();
            Toast.makeText(RegistryActivity.this, menssageOk, Toast.LENGTH_SHORT).show();
            closeActivity();
        }

    }

    public void actionReturn(View v) {
        closeActivity();
    }

    private void closeActivity() {
        this.finish();
    }


}
