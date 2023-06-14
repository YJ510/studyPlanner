package com.yj510.studyplanner;

import static android.service.controls.ControlsProviderService.TAG;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;

public class MainActivity extends AppCompatActivity implements onItemSelectedInterface {

    private ArrayList<Date> StorageCalendar = new ArrayList<>();
    GregorianCalendar cal; // 현재날짜
    String select_date;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();
        Intent intent = getIntent();
        select_date = intent.getStringExtra("select_date");
        //Toast.makeText(getApplicationContext(), select_date, Toast.LENGTH_LONG).show();


    }

    protected void init() {


        // ImageButton 등록
        ImageButton Btnprev, Btnnext;
        Button BtnWrite;

        Btnprev = findViewById(R.id.btn_prev);
        Btnnext = findViewById(R.id.btn_next);
        BtnWrite = findViewById(R.id.btn_write);

        Btnprev.setOnClickListener(BtnClickEvent);
        Btnnext.setOnClickListener(BtnClickEvent);
        BtnWrite.setOnClickListener(BtnClickEvent);


        // Calendar 데이터 세팅
        cal = new GregorianCalendar();
        CalendarSetting(cal);
        RecyclerViewCreate();



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
                    //Intent intent = new Intent(getApplicationContext(), WriteActivity.class);


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

        Log.d("date", Integer.toString(
                cal.get(Calendar.YEAR))+ "/" +cal.get(Calendar.MONTH) + "/"+ cal.get(Calendar.DATE));

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
    }

}