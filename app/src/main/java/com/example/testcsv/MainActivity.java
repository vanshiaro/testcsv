package com.example.testcsv;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

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
import android.os.Environment;
import android.os.ParcelFileDescriptor;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.FileDescriptor;
import java.io.IOException;

public class MainActivity extends AppCompatActivity {
    private ImageView upload;
    Bitmap bmp = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);
        final Context context = this;
        Log.d("EXTER", Environment.getExternalStorageDirectory().getPath());
        upload = (ImageView) findViewById(R.id.photo);
        TextView textView = (TextView) findViewById(R.id.signin);
        upload = (ImageView)findViewById(R.id.photo);
        upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectphoto(context);
            }
        });
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sign_in(v);
            }
        });

    }
        public void sign_in (View v){
            Intent i = new Intent(this, signin.class);
            startActivity(i);
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
                        bmp = (Bitmap) data.getExtras().get("data");
                        upload.setImageBitmap(bmp);
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
                                    bmp = getBitmapFromUri(selectedImage);
                                } catch (IOException e) {
                                    // TODO Auto-generated catch block
                                    e.printStackTrace();
                                }
                                upload.setImageBitmap(bmp);
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

    public void main_sign_up(View view) {
        Context context = this;
        myDbHelper db = new myDbHelper(context);
        EditText name = (EditText)findViewById(R.id.name);
        EditText email = (EditText)findViewById(R.id.email);
        EditText phone = (EditText)findViewById(R.id.phone);
        EditText password =(EditText) findViewById(R.id.password);
        bmp = DbBitmapUtility.getResizedBitmap(bmp,500);
        User user = new User(name.getText().toString(),email.getText().toString(),phone.getText().toString(),password.getText().toString(),DbBitmapUtility.getBytes(bmp));
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
            Toast.makeText(context, "Sign Up Failed", Toast.LENGTH_SHORT).show();
        }else{
            Log.d("DBWrite", "Success");
            Toast.makeText(context, "Sign UP Success", Toast.LENGTH_SHORT).show();
            Intent i = new Intent(this,signin.class);
            startActivity(i);
        }

    }
}

















