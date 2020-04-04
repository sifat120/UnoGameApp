package com.unogame.app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class EndActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_end);

        Intent intent = getIntent();
        String message= intent.getStringExtra(MainActivity.MESSAGE);
        TextView textView = findViewById(R.id.resultText);
        textView.setText(message);
    }

    public void launchStartActivity(View view) {
        Log.i("End Activity","Play Again Button Clicked!");
        Intent intent =new Intent(this,StartActivity.class);
        startActivity(intent);
    }
}
