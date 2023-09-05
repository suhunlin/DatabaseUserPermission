package com.suhun.databaseuserpermission;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private String tag = MainActivity.class.getSimpleName();
    private TextView resultLog, birthday;
    private EditText id, name, tel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    private void initView(){
        resultLog = findViewById(R.id.lid_resultLog);
        id = findViewById(R.id.lid_idInput);
        name = findViewById(R.id.lid_nameInput);
        tel = findViewById(R.id.lid_telInput);
        birthday = findViewById(R.id.lid_birthdayInput);
    }

    public void selectDateFun(View view){

    }

    public void insertFun(View view){

    }

    public void updateFun(View view){

    }

    public void deleteFun(View view){

    }
}