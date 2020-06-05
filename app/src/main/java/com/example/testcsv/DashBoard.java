package com.example.testcsv;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;

public class DashBoard extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dash_board);
        Intent i = getIntent();
        User user = (User)i.getSerializableExtra("User");
        TextView dbid = (TextView)findViewById(R.id.dbid);
        TextView dbname = (TextView)findViewById(R.id.dbname);
        TextView dbemail = (TextView)findViewById(R.id.dbemail);
        TextView dbphone = (TextView)findViewById(R.id.dbphone);
        ImageView imageView = (ImageView)findViewById(R.id.dbimage);
        if(user!=null){
            Log.d("NAME",user.getName());
            dbid.setText(String.valueOf(user.getId()));
            dbname.setText(user.getName());
            dbemail.setText(user.getEmail());
            dbphone.setText(user.getPhone());
            imageView.setImageBitmap(DbBitmapUtility.getImage(user.getImage()));
        }


    }
    public void exportDB(View v){
        File sd = Environment.getExternalStorageDirectory();
        File data = Environment.getDataDirectory();
        FileChannel source=null;
        FileChannel destination=null;
        String currentDBPath = "/user/0/com.example.testcsv/databases/SmartHome.db";
        String backupDBPath = "SmartHome";
        File currentDB = new File(data, currentDBPath);
        File backupDB = new File(sd, backupDBPath);
        try {
            source = new FileInputStream(currentDB).getChannel();
            destination = new FileOutputStream(backupDB).getChannel();
            destination.transferFrom(source, 0, source.size());
            source.close();
            destination.close();
            Toast.makeText(this, "DB Exported!", Toast.LENGTH_LONG).show();
        } catch(IOException e) {
            e.printStackTrace();
        }
    }
}
