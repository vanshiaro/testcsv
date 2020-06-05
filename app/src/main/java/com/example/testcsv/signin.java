package com.example.testcsv;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class signin extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin);
        TextView textView = (TextView) findViewById(R.id.signup);
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signup(v);
            }
        });
    }

    public void signup(View v) {
        Intent i = new Intent(this, MainActivity.class);
        startActivity(i);
    }

    public void signin(View v) {
        EditText email = (EditText) findViewById(R.id.signin_email);
        EditText password = (EditText) findViewById(R.id.signin_password);
        myDbHelper db = new myDbHelper(this);
        SQLiteDatabase database = db.getReadableDatabase();

        String selectQuery = "SELECT  * FROM " + myDbHelper.TABLE_NAME + " WHERE EMAIL = ? AND PASSWORD = ? LIMIT 1";
//        + myDbHelper.EMAIL + " = " + email.getText().toString() + " AND " + myDbHelper.PASSWORD + " = "+password.getText().toString()
        Cursor cursor = database.rawQuery(selectQuery, new String[]{email.getText().toString(), password.getText().toString()});

        if (cursor != null){
            cursor.moveToFirst();
            User user = new User(cursor.getInt(0),cursor.getString(1),cursor.getString(2),cursor.getString(3),cursor.getString(5),cursor.getBlob(4));
            cursor.close();
            //Intent i = new Intent(this,view.class);
            Intent i = new Intent(this, DashBoard.class);
            i.putExtra("User",user);
            startActivity(i);

        }



    }


}
