package com.yj510.studyplanner;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;

public class EditActivity extends AppCompatActivity {
    private ArrayList<TodoItemInfo> mitemInfos;
    private String mid;
    private  SqliteHelper m_DBHelper;
    private  ClassDBHelper classDBHelper;

    private TextView title, content, date;
    private Button btn_update, btn_newDate, btn_newClass;
    private RadioGroup state;
    private Spinner _class;
    ArrayList<String> classList;
    ArrayAdapter<String> arrayAdapter;
    int state_check;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);
        classDBHelper = new ClassDBHelper(this,"class_record.db",null,2);

        Intent intent = getIntent();
        mid=intent.getStringExtra("id");
        onInit();
    }

    private void onInit() {

        String DATABASE_NAME = "study_record.db";
        int DATABASE_VERSION = 2;

        mitemInfos = new ArrayList<>();;
        m_DBHelper= new SqliteHelper(getApplicationContext(), DATABASE_NAME,null,DATABASE_VERSION);

        title = findViewById(R.id.edit_title);
        date = findViewById(R.id.edit_writeDate);
        _class = findViewById(R.id.edit_spin_class);
        state = findViewById(R.id.edit_radio_state_group);
        content = findViewById(R.id.edit_content);
        btn_update = findViewById(R.id.btn_edit_update);
        btn_newDate = findViewById(R.id.btn_edit_newDate);
        btn_newClass = findViewById(R.id.edit_btn_newClass);

        //카테고리 임시 데이터
        classList = new ArrayList<>();
        ArrayList<String> temp = new ArrayList<>();
        temp = classDBHelper.getAllClassNameData();
        classList.add("기본"); // 메뉴 데이터 삽입
        for(int i=0; i<temp.size();i++) {
            classList.add(temp.get(i));
        }

        arrayAdapter = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_spinner_dropdown_item, classList);
        _class.setAdapter(arrayAdapter);
        arrayAdapter.notifyDataSetChanged();

        setItemInfos();

        btn_update.setOnClickListener(mBtnClickLisnter);
        btn_newDate.setOnClickListener(mBtnClickLisnter);
        btn_newClass.setOnClickListener(mBtnClickLisnter);
    }

    View.OnClickListener mBtnClickLisnter = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.edit_btn_newClass:
                    Intent intent_nc = new Intent(getApplicationContext(),MakeClassActivity.class);
                    startActivity(intent_nc);

                case R.id.btn_edit_newDate:
                    selectNewDate(); break;

                case R.id.btn_edit_update:
                    if(state.getCheckedRadioButtonId()==R.id.edit_radio_state_complete){
                        state_check =1;
                    } else { state_check = 0; }

                    //Toast.makeText(getApplicationContext(),Integer.toString(state_check),Toast.LENGTH_SHORT).show();

                    m_DBHelper.UpdateData(title.getText().toString(), _class.getSelectedItem().toString(), date.getText().toString(),
                            content.getText().toString(), state_check,Integer.parseInt(mid));

                    Intent intent = new Intent(getApplicationContext(), ContentActivity.class);
                    intent.putExtra("id",mid);
                    startActivity(intent);

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
                date.setText(year +"/" +(month+1)+ "/"+dayOfMonth);
            }
        },year, month, day);
        datePickerDialog.show();
    }

    private void setItemInfos(){
        mitemInfos = m_DBHelper.getTodoListContentData(Integer.parseInt(mid));
        title.setText(mitemInfos.get(0).getTitle());
        date.setText(mitemInfos.get(0).getDate());

        String str_class = mitemInfos.get(0).get_class();
        int idx = classList.indexOf(str_class);
        _class.setSelection(idx);

        if(mitemInfos.get(0).getState()==1){
            state.check(R.id.edit_radio_state_complete);
}
        else {
            state.check(R.id.edit_radio_state_ready);;
        }

        content.setText(mitemInfos.get(0).getContent());

    }
}