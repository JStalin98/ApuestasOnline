package com.example.jstalin.apuestasonline;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.res.Resources;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;

public class SettingsActivity extends AppCompatActivity {

    private Spinner moneyBet;
    private TextView teams;
    private EditText number1;
    private EditText number2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        initComponents();
        addTabHost();

    }

    private void addTabHost() {

        Resources res = getResources();

        String textTab1 = getString(R.string.text_tab1);
        String textTab2 = getString(R.string.text_tab2);

        TabHost tabs = (TabHost) findViewById(R.id.tabhost);
        tabs.setup();

        TabHost.TabSpec spec = tabs.newTabSpec("mitab1");
        spec.setContent(R.id.tab1);
        spec.setIndicator(textTab1);
        tabs.addTab(spec);

        spec = tabs.newTabSpec("mitab2");
        spec.setContent(R.id.tab2);
        spec.setIndicator(textTab2);
        tabs.addTab(spec);

        tabs.setCurrentTab(0);


        ArrayAdapter adapterMoneyBet = ArrayAdapter.createFromResource(this, R.array.MoneyBet, android.R.layout.simple_spinner_item);
        moneyBet.setAdapter(adapterMoneyBet);

    }

    private void initComponents() {

        moneyBet = (Spinner) findViewById(R.id.spinner_moneybet);
        teams = (TextView) findViewById(R.id.textView_teams);
        number1 = (EditText) findViewById(R.id.editText_number1);
        number2 = (EditText) findViewById(R.id.editText_number2);


    }

    private boolean validateMoneyBet() {

        boolean valid = true;

        int indexSpinetBets = this.moneyBet.getSelectedItemPosition();

        if (indexSpinetBets == 0)
            valid = false;

        return valid;
    }

    private boolean validateNumbers() {

        boolean valid = true;

        String number1 = this.number1.getText().toString();
        String number2 = this.number2.getText().toString();

        if (number1.length() == 0)
            return false;

        if (number2.length() == 0)
            return false;

        return valid;

    }

    private boolean validateUnderstoodNumbers() {

        boolean valid = true;

        int value1 = Integer.parseInt(this.number1.getText().toString());
        int value2 = Integer.parseInt(this.number2.getText().toString());

        if (value1 < 0 || value1 > 300)
            return false;

        if (value2 < 0 || value2 > 300)
            return false;

        return valid;

    }

    private boolean validateData() {


        boolean valid = true;
        String message = "";

        if (!validateMoneyBet()) {

            message = getString(R.string.error_no_bets);
            Toast.makeText(SettingsActivity.this, message, Toast.LENGTH_SHORT).show();
            return false;
        }
        if (!validateNumbers()) {

            message = getString(R.string.error_no_numbers);
            Toast.makeText(SettingsActivity.this, message, Toast.LENGTH_SHORT).show();
            return false;
        }
        if (!validateUnderstoodNumbers()) {

            message = getString(R.string.error_no_between);
            Toast.makeText(SettingsActivity.this, message, Toast.LENGTH_SHORT).show();
            return false;
        }


        return valid;

    }

    public void actionSave(View v) {

        String message = "";

        if (validateData()) {
            senData();
            message = getString(R.string.text_acceptRegistry);
            Toast.makeText(SettingsActivity.this, message, Toast.LENGTH_SHORT).show();
        }


    }

    public void actionReturn(View v){
        closeActivity();
    }

    public void closeActivity(){
        this.finish();
    }

    private void senData() {

        Intent intent = new Intent();

        String moneyBet = (String) this.moneyBet.getSelectedItem();
        String valueNumber1 = this.number1.getText().toString();
        String valueNumber2 = this.number2.getText().toString();

        intent.putExtra("monetbet", moneyBet);
        intent.putExtra("number1", valueNumber1);
        intent.putExtra("number2", valueNumber2);

        setResult(RESULT_OK, intent);

    }


}
