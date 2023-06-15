package com.yj510.studyplanner;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class ContentActivity extends AppCompatActivity {
    private ArrayList<TodoItemInfo> mitemInfos;
    private String mid;
    private  SqliteHelper m_DBHelper;
    //SQLiteDatabase db = m_DBHelper.getReadableDatabase();

    private TextView title, _class, content, date, state;
    private Button btn_edit, btn_delete;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_content);

        Intent intent = getIntent();
        mid=intent.getStringExtra("id");
        //Toast.makeText(getApplicationContext(), mid, Toast.LENGTH_LONG).show();

        onInit();
    }

    private void onInit() {

        String DATABASE_NAME = "study_record.db";
        int DATABASE_VERSION = 2;

        mitemInfos = new ArrayList<>();;
        m_DBHelper= new SqliteHelper(getApplicationContext(), DATABASE_NAME,null,DATABASE_VERSION);

        title = findViewById(R.id.content_title);
        date = findViewById(R.id.content_writeDate);
        _class = findViewById(R.id.content_class);
        state = findViewById(R.id.content_state);
        content = findViewById(R.id.content_content);
        btn_edit = findViewById(R.id.btn_edit);
        btn_delete = findViewById(R.id.btn_delete);

        setItemInfos();
        btn_edit.setOnClickListener(mBtnClickListener);
        btn_delete.setOnClickListener(mBtnClickListener);
    }

    View.OnClickListener mBtnClickListener=new View.OnClickListener(){

        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.btn_edit:
                    Intent intent = new Intent(getApplicationContext(), EditActivity.class);
                    intent.putExtra("id", mid);
                    startActivity(intent);
                    break;
                case R.id.btn_delete:
                    m_DBHelper.DeleteData(Integer.parseInt(mid));
                    break;
            }
        }
    };

    private void setItemInfos(){
        mitemInfos = m_DBHelper.getTodoListContentData(Integer.parseInt(mid));
        title.setText(mitemInfos.get(0).getTitle());
        date.setText(mitemInfos.get(0).getDate());
        _class.setText(mitemInfos.get(0).get_class());
        if(mitemInfos.get(0).getState()==1){ state.setText("완료");}
        else {state.setText("준비중");}

        content.setText(mitemInfos.get(0).getContent());

    }
}