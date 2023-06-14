package com.yj510.studyplanner;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Calendar;

public class CalendarAdapter extends RecyclerView.Adapter<CalendarAdapter.viewHolder> {

    Context context;
    ArrayList<Date> mData;

    CalendarAdapter(Context context, ArrayList<Date> data){
        this.context=context;
        this.mData = data;
    }

    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_calender, parent, false);
        return new viewHolder(view);
    }

    //@Override
    public class viewHolder extends RecyclerView.ViewHolder {
        TextView tv_date;
        public viewHolder(@NonNull View itemView) {
            super(itemView);
            tv_date = itemView.findViewById(R.id.day_calendar);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos = getAdapterPosition() ;
                    if (pos != RecyclerView.NO_POSITION) {
                        //Toast.makeText(context, mData.get(pos).date_format,Toast.LENGTH_LONG).show();

                        Intent intent = new Intent(context, MainActivity.class);
                        intent.putExtra("select_date", mData.get(pos).date_format);
                    }
                }
            });
        }
    }

    @Override
    public void onBindViewHolder(@NonNull viewHolder holder, int position) {
        holder.tv_date.setText(mData.get(position).date);
        //달력 요일 색칠
        if ( position % 7 == 0) {
            holder.tv_date.setTextColor(Color.parseColor("#dc143c"));
        }
        if ( position % 7 == 6) {
            holder.tv_date.setTextColor(Color.parseColor("#0070ce"));
        }

    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

}
