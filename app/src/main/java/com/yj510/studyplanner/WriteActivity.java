package com.yj510.studyplanner;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;

import yuku.ambilwarna.AmbilWarnaDialog;

public class WriteActivity extends AppCompatActivity {
    String date;
    int state;
    SqliteHelper TodoDBHelper;
    ClassDBHelper classDBHelper;

    EditText et_title, et_memo ;
    Button Btn_new_Date, Btn_insert, Btn_new_class;
    TextView tv_write_date;
    RadioGroup rd_state;
    Spinner spinner;
    DatePickerDialog.OnDateSetListener datePickerDialog;
    //RadioButton Btn_ready, Btn_complete;



    private static final String DATABASE_NAME = "study_record.db";
    private static final int DATABASE_VERSION = 2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_write);

        Intent intent = getIntent();
        date = intent.getStringExtra("date");
        TodoDBHelper = new SqliteHelper(this,DATABASE_NAME,null,DATABASE_VERSION);
        classDBHelper = new ClassDBHelper(this,"class_record.db",null,DATABASE_VERSION);

        onInit();


    }

    private void onInit() {

        et_title = findViewById(R.id.et_title);
        et_memo = findViewById(R.id.et_content);
        Btn_new_Date = findViewById(R.id.btn_newDate);
        Btn_insert = findViewById(R.id.btn_insert);
        tv_write_date = findViewById(R.id.tv_writeDate);
        rd_state = findViewById(R.id.radio_state_group);
        spinner = findViewById(R.id.spin_class);
        Btn_new_class=findViewById(R.id.btn_newClass);
        //Btn_ready = findViewById(R.id.radio_state_ready);
        //Btn_complete = findViewById(R.id.radio_state_complete);

        tv_write_date.setText(date);
        Btn_new_Date.setOnClickListener(BtnClickEvent);
        Btn_insert.setOnClickListener(BtnClickEvent);
        Btn_new_class.setOnClickListener(BtnClickEvent);



        ArrayList<String> classList;
        ArrayAdapter<String> arrayAdapter;

        //카테고리 임시 데이터
        classList = new ArrayList<>();
        ArrayList<String> temp = new ArrayList<>();
        temp = classDBHelper.getAllClassNameData();
        classList.add("기본"); // 메뉴 데이터 삽입
        for(int i=0; i<temp.size();i++) {
            classList.add(temp.get(i));
        }

        arrayAdapter = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_spinner_dropdown_item, classList);
        spinner.setAdapter(arrayAdapter);
        spinner.setSelection(0);
        arrayAdapter.notifyDataSetChanged();

        //rd_state.check(Btn_ready.getId());
        rd_state.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.radio_state_ready:
                        state = 0 ; break;

                    case R.id.radio_state_complete:
                        state=1;
                        //Toast.makeText(getApplicationContext(),Integer.toString(state), Toast.LENGTH_LONG).show();break;
                }

            }
        });

    }



    View.OnClickListener BtnClickEvent = new View.OnClickListener(){

        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.btn_newDate:  selectNewDate(); break;

                case R.id.btn_insert:
                    insertData();
                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(intent);
                    break;

                case R.id.btn_newClass:
                    Intent intent_nc = new Intent(getApplicationContext(),MakeClassActivity.class);
                    intent_nc.putExtra("mode","insert");
                    startActivity(intent_nc);

                    break;

            }
        }
    };




    private void selectNewDate(){
        Calendar myCalendar = Calendar.getInstance();
        int year = myCalendar.get(Calendar.YEAR);
        int month = myCalendar.get(Calendar.MONTH);
        int day = myCalendar.get(Calendar.DATE);

        DatePickerDialog datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener(){

            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                tv_write_date.setText(year +"/" + (month+1) + "/"+dayOfMonth);
            }
        },year, month, day);
        datePickerDialog.show();
    }

    private void insertData(){

        String title = et_title.getText().toString();
        String date = tv_write_date.getText().toString();
        String _class = spinner.getSelectedItem().toString();
        String memo = et_memo.getText().toString();

        if(title != null ){
            TodoDBHelper.InsertData(title, _class, date, memo, state);
        }

    }
}