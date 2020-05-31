package com.example.testcsv;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Environment;
import android.util.Log;
import android.widget.Toast;

public class myDbHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "SmartHouse";
    public static final String TABLE_NAME = "User";
    private static final int DATABASE_Version = 1;
    public static final String UID = "_id";
    public static final String NAME = "Name";
    public static final String EMAIL = "Email";
    public static final String PHONE = "Phone";
    public static final String IMAGE = "Image";
    public static final String PASSWORD = "Password";
    private static final String CREATE_TABLE =
            "CREATE TABLE " + TABLE_NAME + " (" + UID + " INTEGER PRIMARY KEY" +
                    " AUTOINCREMENT, " + NAME + " VARCHAR(255) ," + EMAIL + " VARCHAR(255) ," + PHONE + " VARCHAR(255) ," + IMAGE + " BLOB ," + PASSWORD
                    + " VARCHAR(225));";
    private static final String DROP_TABLE = "DROP TABLE IF EXISTS " + TABLE_NAME;
    private Context context;

    public myDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_Version);
        this.context = context;
    }

    public void onCreate(SQLiteDatabase db) {
        Log.d("DB LOC",context.getDatabasePath(DATABASE_NAME).toString());
        try {
            db.execSQL(CREATE_TABLE);
        } catch (Exception e) {
            Toast.makeText(context, "" + e, Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        try {
            Toast.makeText(context, "OnUpgrade", Toast.LENGTH_SHORT).show();
            db.execSQL(DROP_TABLE);
            onCreate(db);
        } catch (Exception e) {
            Toast.makeText(context, "" + e, Toast.LENGTH_SHORT).show();
        }
    }
}