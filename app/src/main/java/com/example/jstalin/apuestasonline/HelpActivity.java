package com.example.jstalin.apuestasonline;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class HelpActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help);
    }

    public void actionReturn(View v){
        closeActivity();
    }

    public void closeActivity(){
        this.finish();
    }
}