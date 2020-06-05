package com.example.testcsv;

import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.ParcelFileDescriptor;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.io.FileDescriptor;
import java.io.IOException;

public class registration extends AppCompatActivity {
    private ImageView rmemupload;
    Bitmap rmembmp = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        rmemupload = (ImageView)findViewById(R.id.rmemphoto);
        final Context context = this;
        // helper = new MyAdapater(this);
        rmemupload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectphoto(context);
            }
        });
    }
    public void nullCheck(View view){
        Context context = this;
        EditText name = (EditText)findViewById(R.id.rmemname);
        EditText email = (EditText)findViewById(R.id.rmememail);
        EditText phone = (EditText)findViewById(R.id.rmemphone);
        EditText password =(EditText) findViewById(R.id.rmempassword);
        if(!name.getText().toString().isEmpty() ||!email.getText().toString().isEmpty()||!phone.getText().toString().isEmpty()||!password.getText().toString().isEmpty()){
            if(rmembmp!=null){
                RegisterMember(name,email,phone,password,rmembmp);
            }else{
                Toast.makeText(context, "Please select an image ", Toast.LENGTH_SHORT).show();
            }
        }else{

            Toast.makeText(context, "Please Fill all fields", Toast.LENGTH_SHORT).show();
        }

    }
    public void RegisterMember(EditText name,EditText email,EditText phone,EditText password, Bitmap image) {
        Context context = this;
        myDbHelper db = new myDbHelper(context);
        rmembmp = DbBitmapUtility.getResizedBitmap(image,500);
        User user = new User(name.getText().toString(),email.getText().toString(),phone.getText().toString(),password.getText().toString(),DbBitmapUtility.getBytes(rmembmp));
        SQLiteDatabase database = db.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(myDbHelper.NAME,    user.getName());
        cv.put(myDbHelper.EMAIL,   user.getEmail());
        cv.put(myDbHelper.PHONE,user.getPhone());
        cv.put(myDbHelper.IMAGE,user.getImage());
        cv.put(myDbHelper.PASSWORD,user.getPassword());
        long rowid =   database.insert(myDbHelper.TABLE_NAME, null, cv );
        database.close();
        if(rowid==-1){
            Log.d("DBWrite", "Failure");
            Toast.makeText(context, "Registration", Toast.LENGTH_SHORT).show();
        }else{
            Log.d("DBWrite", "Success");
            Toast.makeText(context, "Registration Success", Toast.LENGTH_SHORT).show();
            finish();
        }

    }





    private void selectphoto(Context context){
        final CharSequence[] options = { "Take Photo", "Choose from Gallery","Cancel" };

        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Choose your profile picture");

        builder.setItems(options, new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int item) {

                if (options[item].equals("Take Photo")) {
                    Intent takePicture = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(takePicture, 0);

                } else if (options[item].equals("Choose from Gallery")) {
                    Intent pickPhoto = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    startActivityForResult(pickPhoto , 1);

                } else if (options[item].equals("Cancel")) {
                    dialog.dismiss();
                }
            }
        });
        builder.show();
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != RESULT_CANCELED) {
            switch (requestCode) {
                case 0:
                    if (resultCode == RESULT_OK && data != null) {
                        Bitmap selectedImage = (Bitmap) data.getExtras().get("data");
                        rmemupload.setImageBitmap(selectedImage);
                    }
                    break;
                case 1:
                    if (resultCode == RESULT_OK && data != null) {
                        Uri selectedImage = data.getData();

                        String[] filePathColumn = {MediaStore.Images.Media.DATA};
                        if (selectedImage != null) {
                            Cursor cursor = getContentResolver().query(selectedImage,
                                    filePathColumn, null, null, null);
                            if (cursor != null) {
                                cursor.moveToFirst();

                                int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                                String picturePath = cursor.getString(columnIndex);

                                try {
                                    rmembmp = getBitmapFromUri(selectedImage);
                                } catch (IOException e) {
                                    // TODO Auto-generated catch block
                                    e.printStackTrace();
                                }
                                rmemupload.setImageBitmap(rmembmp);
//                                upload.setImageBitmap(BitmapFactory.decodeFile(picturePath));
                                cursor.close();
                            }
                        }

                    }
                    break;
            }
        }
    }
    private Bitmap getBitmapFromUri(Uri uri) throws IOException {
        ParcelFileDescriptor parcelFileDescriptor =
                getContentResolver().openFileDescriptor(uri, "r");
        FileDescriptor fileDescriptor = parcelFileDescriptor.getFileDescriptor();
        Bitmap image = BitmapFactory.decodeFileDescriptor(fileDescriptor);
        parcelFileDescriptor.close();
        return image;
    }
}

