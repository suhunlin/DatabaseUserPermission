package com.suhun.databaseuserpermission;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private String tag = MainActivity.class.getSimpleName();
    private TextView result, birthday;
    private EditText id, name, tel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    private void initView(){
        result = findViewById(R.id.lid_resultLog);
        id = findViewById(R.id.lid_idInput);
        name = findViewById(R.id.lid_nameInput);
        tel = findViewById(R.id.lid_telInput);
        birthday = findViewById(R.id.lid_birthdayInput);
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

    public void insertFun(View view){

    }

    public void updateFun(View view){

    }

    public void deleteFun(View view){

    }
}