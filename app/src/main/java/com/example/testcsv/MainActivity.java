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
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.io.FileDescriptor;
import java.io.IOException;

/**
 * import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
 * import org.apache.poi.ss.usermodel.Cell;
 * import org.apache.poi.ss.usermodel.CellStyle;
 * import org.apache.poi.ss.usermodel.Font;
 * import org.apache.poi.ss.usermodel.IndexedColors;
 * import org.apache.poi.ss.usermodel.Row;
 * import org.apache.poi.ss.usermodel.Sheet;
 * import org.apache.poi.ss.usermodel.Workbook;
 * import org.apache.poi.xssf.usermodel.XSSFWorkbook;
 **/

public class MainActivity extends AppCompatActivity{

    //MyAdapater helper;
    private ImageView upload;
    //EditText updateold,updatenew,delete;
    Bitmap bmp = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);
        final Context context = this;
    /**    String t1 = name.getText().toString();
        String t2 = password.getText().toString();
        String t3 = email.getText().toString();
        String t4 = phone.getText().toString();

        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Users"); //Creating a sheet

        for(int  i=0; i<5; i++){

            Row row = sheet.createRow(i);
            row.createCell(0).setCellValue(t1);
            row.createCell(1).setCellValue(t2);
            row.createCell(2).setCellValue(t3);
            row.createCell(3).setCellValue(t4);

        }

        String fileName = "FileName.xlsx"; //Name of the file

        String extStorageDirectory = Environment.getExternalStorageDirectory()
                .toString();
        File folder = new File(extStorageDirectory, "FolderName");// Name of the folder you want to keep your file in the local storage.
        folder.mkdir(); //creating the folder
        File file = new File(folder, fileName);
        try {
            file.createNewFile(); // creating the file inside the folder
        } catch (IOException e1) {
            e1.printStackTrace();
        }

        try {
            FileOutputStream fileOut = new FileOutputStream(file); //Opening the file
            workbook.write(fileOut); //Writing all your row column inside the file
            fileOut.close(); //closing the file and done

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
**/

        TextView textView = (TextView) findViewById(R.id.signin);
        upload = (ImageView)findViewById(R.id.photo);

       // helper = new MyAdapater(this);
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


    /**public void addUser(View view) {
     String t1 = name.getText().toString();
     String t2 = password.getText().toString();
     String t3 = email.getText().toString();
     String t4 = phone.getText().toString();
     if (t1.isEmpty() || t2.isEmpty()||t3.isEmpty()||t4.isEmpty()) {
     Toast.makeText(getApplicationContext(), "You have missed a value!", Toast.LENGTH_LONG).show();
     } else {
     long id = helper.insertData(t1,t2,t3,t4);
     if (id <= 0) {
     Toast.makeText(getApplicationContext(), "Insertion Unsuccessful", Toast.LENGTH_LONG).show();
     name.setText("");
     email.setText("");
     phone.setText("");
     password.setText("");
     } else {
     Toast.makeText(getApplicationContext(), "Insertion  successful", Toast.LENGTH_LONG).show();
     name.setText("");
     email.setText("");
     phone.setText("");
     password.setText("");
     }
     }
     }

     public void viewdata(View view) {
     String data = helper.getData();
     Toast.makeText(this, "DATA IS " + data, Toast.LENGTH_SHORT).show();
     }

     public void update(View view) {
     String u1 = updateold.getText().toString();
     String u2 = updatenew.getText().toString();
     if (u1.isEmpty() || u2.isEmpty()) {
     Toast.makeText(this, "ENTER DATA", Toast.LENGTH_SHORT).show();
     } else {
     int a = helper.updateName(u1, u2);
     if (a <= 0) {
     Toast.makeText(this, "UN SUUCC", Toast.LENGTH_SHORT).show();
     updateold.setText("");
     updatenew.setText("");
     } else {
     Toast.makeText(this, "UPDATED", Toast.LENGTH_SHORT).show();
     updateold.setText("");
     updatenew.setText("");
     }
     }
     }

     public void delete(View view) {
     String uname = delete.getText().toString();
     if (uname.isEmpty()) {
     Toast.makeText(this, "Enter Data", Toast.LENGTH_SHORT).show();
     } else {
     int a = helper.delete(uname);
     if (a <= 0) {
     Toast.makeText(this, "Unsuccessful", Toast.LENGTH_SHORT).show();
     delete.setText("");
     } else {
     Toast.makeText(this, "DELETED", Toast.LENGTH_SHORT).show();
     delete.setText("");
     }
     }
     }
**/
    public void nullCheck(View view){
        Context context = this;
        EditText name = (EditText)findViewById(R.id.name);
        EditText email = (EditText)findViewById(R.id.email);
        EditText phone = (EditText)findViewById(R.id.phone);
        EditText password =(EditText) findViewById(R.id.password);
        if(!name.getText().toString().isEmpty() ||!email.getText().toString().isEmpty()||!phone.getText().toString().isEmpty()||!password.getText().toString().isEmpty()){
            if(bmp!=null){
                main_sign_up(name,email,phone,password,bmp);
            }else{
                Toast.makeText(context, "Please select an image ", Toast.LENGTH_SHORT).show();
            }
        }else{

            Toast.makeText(context, "Please Fill all fields", Toast.LENGTH_SHORT).show();
        }

    }
    public void main_sign_up(EditText name,EditText email,EditText phone,EditText password, Bitmap image) {
        Context context = this;
        myDbHelper db = new myDbHelper(context);
        bmp = DbBitmapUtility.getResizedBitmap(image,500);
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
                        Bitmap selectedImage = (Bitmap) data.getExtras().get("data");
                        upload.setImageBitmap(selectedImage);
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
}

