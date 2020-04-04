package com.unogame.app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

public class StartActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
    }

    public void launchMainActivity(View view) {
        Log.i("Start Activity","Button Clicked!");
        Intent intent =new Intent(this,MainActivity.class);
        startActivity(intent);
    }
}
