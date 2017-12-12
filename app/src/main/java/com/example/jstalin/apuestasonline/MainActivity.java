package com.example.jstalin.apuestasonline;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {


    private static final int REGISTRY_CODE = 1;
    private static final int BETS_CODE = 2;
    private static final int SETTINGS_CODE = 3;
    private static final int DRAW_CODE = 4;

    private boolean isRegistry = true;
    private boolean isBet = true;
    private boolean canDraw = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int selection = item.getItemId();

        switch (selection) {
            case R.id.option_about:
                openAbout();
                return true;
            case R.id.option_help:
                openHelp();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        tryAnswers(requestCode, resultCode, data);
    }

    private void tryAnswers(int requestCode, int resultCode, Intent data) {

        switch (requestCode) {
            case (REGISTRY_CODE):
                if (resultCode == RESULT_OK) {
                    responseActionRegistry(data);
                }
                break;
            case (BETS_CODE):
                if (resultCode == RESULT_OK) {
                    responseActionBets(data);
                }
                break;
            default:
                break;
        }

    }

    private void responseActionRegistry(Intent data) {


        isRegistry = true;
    }

    private void responseActionBets(Intent data) {

        isBet = true;

    }


    public void openRegistry(View v) {

        Intent i = new Intent(this, RegistryActivity.class);
        startActivityForResult(i, REGISTRY_CODE);

    }

    public void openBets(View v) {

        String messageError = "";

        if (isRegistry) {
            Intent i = new Intent(this, BetsActivity.class);
            startActivity(i);
        } else {
            messageError = getString(R.string.error_registry);
            Toast.makeText(MainActivity.this, messageError, Toast.LENGTH_SHORT).show();
        }

    }

    public void openSettings(View v) {

        String messageError = "";

        if(isBet) {
            Intent i = new Intent(this, SettingsActivity.class);
            startActivity(i);
        }else {
            messageError = getString(R.string.error_bet);
            Toast.makeText(MainActivity.this, messageError, Toast.LENGTH_SHORT).show();
        }

    }

    public void openDraw(View v) {

        String messageError = "";

        if(canDraw) {
            Intent i = new Intent(this, DrawActivity.class);
            startActivity(i);
        }else {
            messageError = getString(R.string.error_draw);
            Toast.makeText(MainActivity.this, messageError, Toast.LENGTH_SHORT).show();
        }

    }

    public void openAbout() {

        Intent i = new Intent(this, AboutActivity.class);
        startActivity(i);
    }

    public void openHelp() {
        Intent i = new Intent(this, HelpActivity.class);
        startActivity(i);
    }




}
