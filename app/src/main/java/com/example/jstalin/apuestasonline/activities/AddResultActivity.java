package com.example.jstalin.apuestasonline.activities;

import android.app.AlertDialog;
import android.app.IntentService;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.jstalin.apuestasonline.R;
import com.example.jstalin.apuestasonline.databases.OnlineBetsDatabase;

public class AddResultActivity extends AppCompatActivity {

    private EditText editText_team1;
    private EditText editText_team2;

    private EditText editText_result1;
    private EditText editText_result2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_result);

        inirComponents();
    }

    private void inirComponents(){

        editText_team1 = (EditText)findViewById(R.id.editText_team1);
        editText_team2 = (EditText)findViewById(R.id.editText_team2);

        editText_result1 = (EditText)findViewById(R.id.editText_resul1);
        editText_result2 = (EditText)findViewById(R.id.editText_result2);

    }

    public void actionAddResult(View v){


        if (validateData()) {

            addNewResultInBD();
            reloadResultActivity();
            closeActivity();

        }else {
            Toast.makeText(AddResultActivity.this, "Faltan datos", Toast.LENGTH_SHORT).show();
        }

    }

    private boolean validateData(){

        String team1 = editText_team1.getText().toString();
        String team2 = editText_team2.getText().toString();

        String result1 = editText_result1.getText().toString();
        String result2 = editText_result2.getText().toString();

        if(team1.isEmpty())
            return false;

        if(team2.isEmpty()){
            return false;
        }

        if(result1.isEmpty())
            return false;

        if(result2.isEmpty())
            return false;

        return true;


    }

    private void reloadResultActivity(){

        Intent i = new Intent(this, ResultActivity.class);
        startActivity(i);


    }

    private void addNewResultInBD(){

        OnlineBetsDatabase onlineBetsDatabase = new OnlineBetsDatabase(AddResultActivity.this, OnlineBetsDatabase.NAME_BD, null, OnlineBetsDatabase.VERSION);
        SQLiteDatabase db = onlineBetsDatabase.getWritableDatabase();

        String email = PreferenceManager.getDefaultSharedPreferences(this).getString("preferenceEmail","");
        int sportCode = PreferenceManager.getDefaultSharedPreferences(this).getInt("preferenceSport", -1);

        String[] argsUser = new String[]{email};
        String[] columns = new String[]{"id"};

        Cursor cursorUser = db.query(OnlineBetsDatabase.Tables.USER, columns, "email=?", argsUser, null, null, null);
        cursorUser.moveToFirst();
        int idUser = cursorUser.getInt(0);

        ContentValues newBet = new ContentValues();
        newBet.put("user", idUser);
        newBet.put("codeSport", sportCode);
        newBet.put("money", Double.parseDouble("0.0"));
        newBet.put("team1", editText_team1.getText().toString());
        newBet.put("team2", editText_team2.getText().toString());
        newBet.put("result1", Integer.parseInt(editText_result1.getText().toString()));
        newBet.put("result2", Integer.parseInt(editText_result2.getText().toString()));


        db.insert(OnlineBetsDatabase.Tables.BET, "id", newBet);
        db.close();

    }

    public void actionReturn(View v){

        closeActivity();


    }

    private void closeActivity(){

        finish();
    }

}
