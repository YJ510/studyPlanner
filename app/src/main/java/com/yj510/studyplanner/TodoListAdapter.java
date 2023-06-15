package com.yj510.studyplanner;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class TodoListAdapter extends BaseAdapter {

    private ArrayList<TodoItemInfo> m_itemInfos = new ArrayList<TodoItemInfo>();
    private TextView title, content;
    private CheckBox checkBox;
    private  SqliteHelper m_DBHelper;

    public TodoListAdapter() {
    }


    @Override
    public int getCount() {
        return m_itemInfos.size();
    }

    @Override
    public Object getItem(int position) {
        return m_itemInfos.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final int pos = position;
        final Context context = parent.getContext();

        if(convertView == null){
            LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.todolist_item,parent,false);
        }

        title = convertView.findViewById(R.id.todolist_title);
        content =  convertView.findViewById(R.id.todolist_content);
        checkBox = convertView.findViewById(R.id.checkbox);

        title.setText(m_itemInfos.get(pos).title);
        content.setText(m_itemInfos.get(pos).content);

        if (m_itemInfos.get(pos).state==1){
            checkBox.setChecked(true);
        }else {
            checkBox.setChecked(false);
        }

        LinearLayout selArea = (LinearLayout)convertView.findViewById(R.id.select_area);
        selArea.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(v.getContext(), Integer.toString(m_itemInfos.get(pos).getId()), Toast.LENGTH_LONG).show();
                Intent intent = new Intent(v.getContext(), ContentActivity.class);
                intent.putExtra("id", Integer.toString(m_itemInfos.get(pos).getId()));
                v.getContext().startActivity(intent);
            }
        });

        checkBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(v.getContext(), Integer.toString(m_itemInfos.get(pos).getId()), Toast.LENGTH_LONG).show();
                String DATABASE_NAME = "study_record.db";
                int DATABASE_VERSION = 2;
                m_DBHelper= new SqliteHelper(context, DATABASE_NAME,null,DATABASE_VERSION);

                if(checkBox.isChecked()){
                    Toast.makeText(v.getContext(), "선택", Toast.LENGTH_SHORT).show();
                    m_DBHelper.change_completed(1,m_itemInfos.get(pos).getId());
                }else {
                    Toast.makeText(v.getContext(), "비선택", Toast.LENGTH_SHORT).show();
                    m_DBHelper.change_completed(0,m_itemInfos.get(pos).getId());
                }

            }
        });

        return convertView;
    }

    void addItem(int _id, String _title, String _date, String _class, String _content, int _state ){
        TodoItemInfo stroge = new TodoItemInfo();

        stroge.setId(_id);
        stroge.setTitle(_title);
        stroge.setDate(_date);
        stroge.set_class(_class);
        stroge.setContent(_content);
        stroge.setState(_state);

        m_itemInfos.add(stroge);

    }

}
