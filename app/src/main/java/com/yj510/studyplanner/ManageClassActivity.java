package com.yj510.studyplanner;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import yuku.ambilwarna.AmbilWarnaDialog;

public class ManageClassActivity extends AppCompatActivity {
    ClassDBHelper mDBHelper;
    private static final String DATABASE_NAME = "class_record.db";
    private static final int DATABASE_VERSION = 2;

    private String m_classname;
    private String hexColor;
    int tColor;

    Button btn_color, btn_edit, btn_remove;
    TextView name, cycle;
    ArrayList<ClassInfo> m_classItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_class);

        Intent intent = getIntent();
        m_classname =  intent.getStringExtra("className");
        mDBHelper = new ClassDBHelper(getApplicationContext(), DATABASE_NAME,null,DATABASE_VERSION);


        onInit();
        setData();
    }

    private void onInit() {
        m_classItem=new ArrayList<>();
        btn_color = findViewById(R.id.btn_eidt_color);
        btn_edit= findViewById(R.id.btn_class_edit);
        btn_remove= findViewById(R.id.btn_class_remove);

        btn_color.setOnClickListener(mBtnClickLister);
        btn_edit.setOnClickListener(mBtnClickLister);
        btn_remove.setOnClickListener(mBtnClickLister);

        name = findViewById(R.id.dlg_classTitle);
        cycle = findViewById(R.id.dlg_rwcycle);
        m_classItem = mDBHelper.getClassData(m_classname);
        Toast.makeText(getApplicationContext(), m_classname, Toast.LENGTH_SHORT).show();

    }

    void setData(){

        name.setText(m_classItem.get(0).name);
        cycle.setText(m_classItem.get(0).cycle);
        cycle.requestFocus();
        btn_color.setBackgroundColor(Color.parseColor(m_classItem.get(0).hex_color));
    }

    View.OnClickListener mBtnClickLister = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent back = new Intent(getApplicationContext(), MainActivity.class);;
            switch (v.getId()){
                case R.id.btn_class_edit:
                    if(m_classItem.get(0).name.equals("기본")){
                        Toast.makeText(getApplicationContext(), "기본 카테고리는" +
                                "설정을 변경할 수 없습니다.", Toast.LENGTH_LONG).show();
                    }
                    else{
                    mDBHelper.UpdateClass(m_classname, cycle.getText().toString(), hexColor);
                    }
                    startActivity(back);
                    break;

                case R.id.btn_class_remove:
                    mDBHelper.DeleteClass(m_classname);
                    back = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(back);
                    break;

                case R.id.btn_eidt_color :
                    openColorPicker();
                    break;
            }
        }
    };

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