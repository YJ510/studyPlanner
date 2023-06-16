package com.yj510.studyplanner;

import static android.service.controls.ControlsProviderService.TAG;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;

public class MainActivity extends AppCompatActivity implements onItemSelectedInterface {

    private ArrayList<Date> StorageCalendar = new ArrayList<>();
    private ArrayList<TodoItemInfo> StorageList = new ArrayList<>();
    GregorianCalendar cal; // 현재날짜
    GregorianCalendar today;
    String select_date;
    TextView Tv_date;


    private static final String DATABASE_NAME = "study_record.db";
    private static final int DATABASE_VERSION = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();

    }

    protected void init() {

        today = new GregorianCalendar();
        select_date = Integer.toString(today.get(Calendar.YEAR)) + "/"
                + Integer.toString(today.get(Calendar.MONTH)+1) + "/"
                + Integer.toString(today.get(Calendar.DATE));

        // ImageButton 등록
        ImageButton Btnprev, Btnnext;
        Button BtnWrite;

        Btnprev = findViewById(R.id.btn_prev);
        Btnnext = findViewById(R.id.btn_next);
        BtnWrite = findViewById(R.id.btn_write);
        Tv_date = findViewById(R.id.tv_date);
        Tv_date.setText(select_date);

        Btnprev.setOnClickListener(BtnClickEvent);
        Btnnext.setOnClickListener(BtnClickEvent);
        BtnWrite.setOnClickListener(BtnClickEvent);


        // Calendar 데이터 세팅
        cal = new GregorianCalendar();
        CalendarSetting(cal);
        RecyclerViewCreate();
        setListview(select_date);

    }

    // 버튼 이벤트 등록
    View.OnClickListener BtnClickEvent = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.btn_prev:
                    cal.set(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH) , cal.get(Calendar.DATE)-7);
                    Log.e(TAG, "이전주");
                    break;
                case R.id.btn_next:
                    cal.set(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH) , cal.get(Calendar.DATE)+7);
                    Log.e(TAG, "다음달");
                    break;

                case R.id.btn_write:
                   //Toast.makeText(getApplicationContext(), select_date, Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(getApplicationContext(), WriteActivity.class);
                    intent.putExtra("date", select_date);
                    startActivity(intent);
            }

            CalendarSetting(cal);


            TextView timeTextView = findViewById(R.id.tv_calDate);
            timeTextView.setText(cal.get(Calendar.YEAR) + "년 " + (cal.get(Calendar.MONTH) + 1) + "월");
        }
    };


    // RecyclerView 생성
    protected void RecyclerViewCreate() {
        // Recycler Calendar 생성
        androidx.recyclerview.widget.RecyclerView calendarView = findViewById(R.id.rv_calendar);
        CalendarAdapter calendarAdapter = new CalendarAdapter(this, StorageCalendar,this);

        GridLayoutManager layoutManager = new GridLayoutManager(getApplicationContext(), 7);
        calendarView.setLayoutManager(layoutManager);
        calendarView.setAdapter(calendarAdapter);

    }

    protected void CalendarSetting(GregorianCalendar cal) {
        StorageCalendar.clear();

        GregorianCalendar calendar = new GregorianCalendar(
                cal.get(Calendar.YEAR),
                cal.get(Calendar.MONTH),
                cal.get(Calendar.DATE),0,0,0);

        GregorianCalendar prev_calendar = new GregorianCalendar(
                cal.get(Calendar.YEAR),
                cal.get(Calendar.MONTH)-1,
                cal.get(Calendar.DATE),0,0,0);

        int date = calendar.get(Calendar.DATE);
        int w = calendar.get(Calendar.DAY_OF_WEEK);
        int min = calendar.getActualMinimum(Calendar.DAY_OF_MONTH);
        int max =  calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
        int prev_max = prev_calendar.getActualMaximum(Calendar.DAY_OF_MONTH);

        Log.d("TAG", Integer.toString(
                cal.get(Calendar.YEAR))+ "/" +Integer.toString(cal.get(Calendar.MONTH)+1) + "/"+ cal.get(Calendar.DATE));

        int sunday;
        sunday = date - w+1;

        int count1=1;

        for (int i = 0; i < 7; i++) {
            if(sunday+i > max){ //월말에서 월초로 넘어갈 때 처리(당일이 월말)
                StorageCalendar.add( new Date(Integer.toString(cal.get(Calendar.MONTH)+2)  + "/"
                        + Integer.toString(count1),

                        Integer.toString(cal.get(Calendar.YEAR))+ "/"+
                                Integer.toString((cal.get(Calendar.MONTH)+2))+ "/" +
                                Integer.toString(count1)));
                count1++;
            } else if ( sunday+i > 0 && sunday+i <= max){  // 매주 일요일의 날짜를 구
                StorageCalendar.add( new Date(Integer.toString(sunday+i),

                        Integer.toString(cal.get(Calendar.YEAR))+ "/"+
                                Integer.toString((cal.get(Calendar.MONTH)+1))+ "/"+
                                Integer.toString(sunday+i))
                        );
            }
            else {
                // 월말에서 월초로 넘어갈 때 처리 (당일이 월초)
                StorageCalendar.add( new Date(Integer.toString(cal.get(Calendar.MONTH))
                            + "/"+Integer.toString(prev_max+date-w+1+i),

                                Integer.toString(cal.get(Calendar.YEAR))+ "/"+
                                        Integer.toString((cal.get(Calendar.MONTH)))+ "/"+
                                        Integer.toString(prev_max+date-w+1+i)));

            }
        }


        RecyclerViewCreate();

    }


    @Override
    public void onItemSelected(View view, ArrayList<Date> date, int position) {

        select_date = date.get(position).date_format;
        Toast.makeText(getApplicationContext(), select_date, Toast.LENGTH_LONG).show();
        Tv_date.setText(select_date);
        setListview(select_date);
        Log.d("TAG",select_date);
    }


    void setListview(String select_date){

        ListView todolist = findViewById(R.id.lv_task_plan);
        TodoListAdapter todo_adapter = new TodoListAdapter();
        todolist.setAdapter(todo_adapter);
        ArrayList<TodoItemInfo> temp = new ArrayList<>();


        SqliteHelper m_DBHelper = new SqliteHelper(getApplicationContext(), DATABASE_NAME,null,DATABASE_VERSION);
        SQLiteDatabase db = m_DBHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM TodoList WHERE date = '"+select_date+"';",null);
        //cursor.moveToFirst();

        if (cursor.getCount() > 0){
            while( cursor.moveToNext()){
                int idx;
                idx = cursor.getColumnIndex("id");
                int id = cursor.getInt(idx);
                idx = cursor.getColumnIndex("title");
                String title = cursor.getString(idx);
                idx = cursor.getColumnIndex("_class");
                String _class = cursor.getString(idx);
                idx = cursor.getColumnIndex("date");
                String date = cursor.getString(idx);
                idx = cursor.getColumnIndex("content");
                String content = cursor.getString(idx);
                idx = cursor.getColumnIndex("complete");
                int complete = cursor.getInt(idx);
                temp.add(new TodoItemInfo(id, title, date, _class, content, complete));

               todo_adapter.addItem(id, title, date, _class, content, complete);  }

        }
        cursor.close();
        todo_adapter.notifyDataSetChanged();



    }

}