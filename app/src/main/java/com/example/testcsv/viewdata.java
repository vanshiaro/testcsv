package com.example.testcsv;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class viewdata extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_viewdata);
        Button b = (Button)findViewById(R.id.register);

    }
    public void register(View view){
        Intent i = new Intent(this,registration.class);
        startActivity(i);
    }
    public void registerLIST(View view){
        Intent i = new Intent(this,AllUsers.class);
        startActivity(i);
    }

}
