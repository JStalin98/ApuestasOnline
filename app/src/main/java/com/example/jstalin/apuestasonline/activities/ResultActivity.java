package com.example.jstalin.apuestasonline.activities;


import android.app.AlertDialog;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;

import android.util.Log;

import android.view.View;
import android.view.ViewGroup;

import android.widget.Button;
import android.widget.ScrollView;
import android.widget.TableLayout;
import android.widget.TextView;


import com.example.jstalin.apuestasonline.R;
import com.example.jstalin.apuestasonline.databases.OnlineBetsDatabase;

import com.example.jstalin.apuestasonline.lessons.Sport;
import com.example.jstalin.apuestasonline.lessons.TableResult;

import java.util.ArrayList;

/**
 * Clase sin funcionalidad
 */
public class ResultActivity extends AppCompatActivity {


    private TextView textViewSport;

    private Button buttonAddResult;

    private TableResult tableResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        initComponent();

        getBetsInBD();


    }

    private void initComponent() {

        tableResult = new TableResult(this, (TableLayout) findViewById(R.id.tableResult));
        textViewSport = (TextView) findViewById(R.id.textView_nameSport);
        buttonAddResult = (Button) findViewById(R.id.button_addResult);


        if (!isSelectedSport())
            buttonAddResult.setEnabled(false);


    }


    private void getBetsInBD() {

        OnlineBetsDatabase onlineBetsDatabase = new OnlineBetsDatabase(ResultActivity.this, OnlineBetsDatabase.NAME_BD, null, OnlineBetsDatabase.VERSION);
        SQLiteDatabase db = onlineBetsDatabase.getWritableDatabase();

        String email = PreferenceManager.getDefaultSharedPreferences(this).getString("preferenceEmail", "");
        int sportCodeSelected = PreferenceManager.getDefaultSharedPreferences(this).getInt("preferenceSport", -1);
        int idUser = -1;

        String[] argsUser = new String[]{email};
        String[] columns = new String[]{"id"};


        Cursor cursorUser = db.query(OnlineBetsDatabase.Tables.USER, columns, "email=?", argsUser, null, null, null);
        boolean b = cursorUser.moveToFirst();
        Log.d("primer registro", b + "-------");
        idUser = cursorUser.getInt(0);


        String[] argsBet = new String[]{String.valueOf(idUser), String.valueOf(sportCodeSelected)};
        columns = new String[]{"id", "team1", "team2", "result1", "result2"};

        Cursor cursor = db.query(OnlineBetsDatabase.Tables.BET, columns, "user=? and codeSport=?  ", argsBet, null, null, columns[0]);

        processQueryResult(cursor);

        db.close();

    }

    private void processQueryResult(Cursor c) {


        if (c.moveToFirst()) {

            Log.d("ENTRO PRIMER REGISTRO", "ENTRO MOVE TO FIRST");

            setSporText();

            Log.d("ENTRO PRIMER REGISTRO", "SALGO MOVE TO FIRST");

            tableResult.addHead(R.array.head_table);

            ArrayList<String> newRow = new ArrayList<String>();
            do {
                newRow.removeAll(newRow);
                int idBD = c.getInt(0);
                String team1DB = c.getString(1);
                String team2DB = c.getString(2);
                int result1DB = c.getInt(3);
                int result2DB = c.getInt(4);


                newRow.add(String.valueOf(idBD));
                newRow.add(team1DB);
                newRow.add(team2DB);
                newRow.add(String.valueOf(result1DB));
                newRow.add(String.valueOf(result2DB));

                tableResult.addRowTable(newRow);

            } while (c.moveToNext());

        } else {

            showDialogSimple("No hay resultados");

        }


    }

    private void showDialogSimple(String message) {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setMessage(message);

        AlertDialog dialog = builder.create();

        dialog.show();

    }

    private void setSporText() {


        int codeSportPrefered = PreferenceManager.getDefaultSharedPreferences(this).getInt("preferenceSport", -1);

        Log.d("TEXT SPORT ", "ENTRO CODE SPOR PREFERENCE  " + codeSportPrefered);

        String textSport = getSelectedBet(codeSportPrefered);


        textViewSport.setText(textSport);

    }

    private String getSelectedBet(int codeBet) {

        String stSelectedBet = "";

        switch (codeBet) { // Comprobamos con los distinto codigos
            case Sport.CODE_FOOTBALL:
                return getString(R.string.text_football);

            case Sport.CODE_TENNIS:
                return getString(R.string.text_tennis);

            case Sport.CODE_BASKETBALL:
                return getString(R.string.text_basketball);

            case Sport.CODE_HANDBALL:
                return getString(R.string.text_handball);

        }

        return stSelectedBet;
    }

    private boolean isSelectedSport() {

        int code = PreferenceManager.getDefaultSharedPreferences(this).getInt("preferenceSport", -1);
        if (code == -1)
            return false;

        return true;

    }

    public void openAddResult(View v) {

        Intent i = new Intent(this, AddResultActivity.class);
        startActivity(i);

    }


}
