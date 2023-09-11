package com.suhun.databaseuserpermission;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.DatePickerDialog;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private String tag = MainActivity.class.getSimpleName();
    private TextView result, birthday, fieldName, fieldresult;
    private EditText id, name, tel;
    private MySQLiteOpenHelper mySQLiteOpenHelper;
    private SQLiteDatabase db;
    private ContentResolver contentResolver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        initDatabase();
    }
    private void initView(){
        result = findViewById(R.id.lid_resultLog);
        id = findViewById(R.id.lid_idInput);
        name = findViewById(R.id.lid_nameInput);
        tel = findViewById(R.id.lid_telInput);
        birthday = findViewById(R.id.lid_birthdayInput);
        fieldName = findViewById(R.id.lid_fieldName);
        fieldresult = findViewById(R.id.lid_fieldResult);
    }
    private void initDatabase(){
        mySQLiteOpenHelper = new MySQLiteOpenHelper(this, "suhunDB", null, 1);
        db = mySQLiteOpenHelper.getWritableDatabase();
    }

    public void selectDateFun(View view){
        DatePickerDialog dialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                String dateSelect = String.format("%s/%s/%s", year, month+1, dayOfMonth);
                birthday.setText(dateSelect);
            }
        }, 2023, 0, 01);
        dialog.show();
    }

    private void execQuery(){
        StringBuffer stringBuffer = new StringBuffer();
        Cursor cursor = db.query("cust", null, null, null, null, null, null);
        while(cursor.moveToNext()){
            String cid = cursor.getString(cursor.getColumnIndexOrThrow("cid"));
            String cname = cursor.getString(cursor.getColumnIndexOrThrow("cname"));
            String ctel = cursor.getString(cursor.getColumnIndexOrThrow("ctel"));
            String cbirthday = cursor.getString(cursor.getColumnIndexOrThrow("cbirthday"));
            String cResult = String.format("%s:%s:%s:%s\n", cid, cname, ctel, cbirthday);
            stringBuffer.append(cResult);
        }
        result.setText(stringBuffer);
        cursor.close();
    }

    public void queryFun(View view){
        execQuery();
    }

    public void insertFun(View view){
        ContentValues values = new ContentValues();
        values.put("cname", name.getText().toString());
        values.put("ctel", tel.getText().toString());
        values.put("cbirthday", birthday.getText().toString());
        db.insert("cust", null, values);
        execQuery();
    }

    public void updateFun(View view){
        //uid->update id
        String uId = id.getText().toString();
        String uname = name.getText().toString();
        String utel = tel.getText().toString();
        String ubirthday = birthday.getText().toString();
        if(!uId.equals("") || !uId.equals("Enter id number")){
            ContentValues values = new ContentValues();
            //update name
            if(!uname.equals("") && !uname.equals("Enter name")){
                values.put("cname", uname);
            }
            //update tel
            if(!utel.equals("") && !utel.equals("Enter tel")){
                values.put("ctel", utel);
            }
            //update birthday
            if(!ubirthday.equals("") && !ubirthday.equals("Touch to select date")){
                values.put("cbirthday", ubirthday);
            }
            db.update("cust", values, "cid = ?", new String[]{uId});
        }
        execQuery();
    }

    public void deleteFun(View view){
        String dId = id.getText().toString();
        if(!dId.equals("") && !dId.equals("Enter id number")){
            db.delete("cust", "cid = ?", new String[]{dId});
            execQuery();
        }
    }

    public void checkUserPermissionFun(View view){
        if(userAgreePermission()){
            initContentResolver();
        }else{
            requestPermissions(new String[]{Manifest.permission.READ_CALL_LOG, Manifest.permission.READ_CONTACTS,
            Manifest.permission.READ_EXTERNAL_STORAGE}, 123);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode == 123){
            if(grantResults[0]==PackageManager.PERMISSION_GRANTED && grantResults[1]==PackageManager.PERMISSION_GRANTED
            /*&& grantResults[2]==PackageManager.PERMISSION_GRANTED*/){
                initContentResolver();
            }else{
                finish();
            }
        }
    }

    public void getFieldNameFun(View view){
        if(contentResolver!=null) {
            Uri uri = Settings.System.CONTENT_URI;
            StringBuffer stringBuffer = new StringBuffer();
            Cursor cursor = contentResolver.query(uri, null, null, null);

            for (int i = 0; i < cursor.getColumnCount(); i++) {
                stringBuffer.append(cursor.getColumnName(i) + "\n");
                if(i>0 && i<cursor.getColumnCount()-1){
                }
            }
            fieldName.setText(stringBuffer);
            cursor.close();
        }
    }

    private boolean userAgreePermission(){
        boolean result = false;
        if(ContextCompat.checkSelfPermission(this, Manifest.permission.READ_CALL_LOG) == PackageManager.PERMISSION_GRANTED ||
            ContextCompat.checkSelfPermission(this, Manifest.permission.READ_CONTACTS) == PackageManager.PERMISSION_GRANTED ||
            ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED){
            result = true;
        }
        return result;
    }

    private void initContentResolver(){
        contentResolver = getContentResolver();
    }

    public void getFieldResultFun(View view) {
        StringBuffer stringBuffer = new StringBuffer();
        Uri uri = Settings.System.CONTENT_URI;
        Cursor cursor = contentResolver.query(uri, null, null, null, null);

        while (cursor.moveToNext()){
            String name = cursor.getString(cursor.getColumnIndexOrThrow("name"));
            String value = cursor.getString(cursor.getColumnIndexOrThrow("value"));
            stringBuffer.append(String.format("%s:%s\n", name, value));
        }
        fieldresult.setText(stringBuffer);
    }
}