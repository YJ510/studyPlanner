package com.yj510.studyplanner;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.Calendar;

public class CalendarAdapter extends RecyclerView.Adapter<CalendarAdapter.viewHolder> {

    Context context;
    String[] data;

    CalendarAdapter(Context context, String[] data){
        this.context=context;
        this.data = data;
    }

    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_calender, parent, false);
        return new viewHolder(view);
    }

    @Override
    public int getItemCount() {
        return data.length;
    }

    public class viewHolder extends RecyclerView.ViewHolder {
        TextView tv_date;
        public viewHolder(@NonNull View itemView) {
            super(itemView);
            tv_date = itemView.findViewById(R.id.day_calendar);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull viewHolder holder, int position) {
        holder.tv_date.setText(data[position]);
        //달력 요일 색칠
        if ( position % 7 == 0) {
            holder.tv_date.setTextColor(Color.parseColor("#dc143c"));
        }
        if ( position % 7 == 6) {
            holder.tv_date.setTextColor(Color.parseColor("#0070ce"));
        }

    }
}
