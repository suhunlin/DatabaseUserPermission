package com.suhun.databaseuserpermission;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private String tag = MainActivity.class.getSimpleName();
    private TextView result, birthday;
    private EditText id, name, tel;
    private MySQLiteOpenHelper mySQLiteOpenHelper;
    private SQLiteDatabase db;

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
            String cResult = String.format("%s:%s:%s:%s", cid, cname, ctel, cbirthday);
            stringBuffer.append(cResult);
        }
        result.setText(stringBuffer);
    }

    public void insertFun(View view){
        execQuery();
    }

    public void updateFun(View view){
        execQuery();
    }

    public void deleteFun(View view){
        execQuery();
    }
}