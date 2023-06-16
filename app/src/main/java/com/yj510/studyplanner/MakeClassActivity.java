package com.yj510.studyplanner;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import yuku.ambilwarna.AmbilWarnaDialog;

public class MakeClassActivity extends AppCompatActivity {
    private int tColor;
    private String hexColor;
    Button btn_color, btn_ok;
    TextView name, cycle;

    ClassDBHelper mDBHelper;
    private static final String DATABASE_NAME = "class_record.db";
    private static final int DATABASE_VERSION = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_make_class);

        onInit();
    }

    private void onInit() {
        mDBHelper = new ClassDBHelper(getApplicationContext(), DATABASE_NAME,null,DATABASE_VERSION);
        btn_color = findViewById(R.id.btn_dlg_color);
        btn_ok= findViewById(R.id.btn_dlg_ok);
        name = findViewById(R.id.dlg_classTitle);
        cycle = findViewById(R.id.dlg_rwcycle);
        //mDBHelper.InsertClass("기본", "0", "#000000");


        btn_color.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openColorPicker();
                //Toast.makeText(getApplicationContext(),Integer.toString(tColor),Toast.LENGTH_SHORT).show();

            }
        });

        btn_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = getIntent();

                    mDBHelper.InsertClass(name.getText().toString(), cycle.getText().toString(), hexColor);
                }
        });
    }

    private void openColorPicker() {
        AmbilWarnaDialog colorPicker = new AmbilWarnaDialog(this, tColor, new AmbilWarnaDialog.OnAmbilWarnaListener() {
            @Override
            public void onCancel(AmbilWarnaDialog dialog) {
            }

            @Override
            public void onOk(AmbilWarnaDialog dialog, int color) {

                tColor = color; // 직전 선택한 색상

                // int to String
                hexColor = Integer.toHexString(color).substring(2);

                hexColor = "#"+hexColor;

                // 원하는 곳 색상 변경
                btn_color.setBackgroundColor(Color.parseColor(hexColor));

            }
        });
        colorPicker.show();
    }
}