package com.yj510.studyplanner;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class EditActivity extends AppCompatActivity {
    private ArrayList<TodoItemInfo> mitemInfos;
    private String mid;
    private  SqliteHelper m_DBHelper;

    private TextView title, content, date;
    private Button btn_update;
    private RadioGroup state;
    private Spinner _class;
    ArrayList<String> classList;
    ArrayAdapter<String> arrayAdapter;
    int state_check;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

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

        classList = new ArrayList<>();
        classList.add("기본"); // 메뉴 데이터 삽입
        arrayAdapter = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_spinner_dropdown_item, classList);
        _class.setAdapter(arrayAdapter);

        setItemInfos();

        btn_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(state.getCheckedRadioButtonId()==R.id.edit_radio_state_complete){
                    state_check =1;
                } else { state_check = 0; }

                //Toast.makeText(getApplicationContext(),Integer.toString(state_check),Toast.LENGTH_SHORT).show();

                m_DBHelper.UpdateData(title.getText().toString(), _class.getSelectedItem().toString(), date.getText().toString(),
                        content.getText().toString(), state_check,Integer.parseInt(mid));

                Intent intent = new Intent(getApplicationContext(), ContentActivity.class);
                intent.putExtra("id",mid);
                startActivity(intent);

            }

        });
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