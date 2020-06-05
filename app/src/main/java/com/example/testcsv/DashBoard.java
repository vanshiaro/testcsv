package com.example.testcsv;

import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

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
        Button button = (Button)findViewById(R.id.viewdata) ;
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewData(v);

            }
        });

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
    public void viewData(View v){
        Intent intent = new Intent(this,viewdata.class);
        startActivity(intent);
    }
    public void exportDB(View v){
        Toast.makeText(this,"Data exported",Toast.LENGTH_SHORT).show();
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
