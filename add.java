package com.example.mateu.languagemaster;

import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import  android.support.v7.widget.*;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

public class add extends AppCompatActivity {
    public void setColor() {
        if(theme.equals("dark")) {
                recycleView.setBackgroundColor(Color.parseColor("#000000"));
        }
        else if(theme.equals("light")) {
                recycleView.setBackgroundColor(Color.parseColor("#ffffff"));
        }
    }
    public void  init() {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        for (int i = 1; i < (preferences.getInt("objects", 2)); i = i + 2) {
            list.add(preferences.getString((i+""), ""));
            list.add(preferences.getString((i+1+""), ""));
        }
        colorBg = preferences.getString("textcolor", "#61ff00");
        theme = preferences.getString("theme", "light");
        setColor();
    }

    private RecyclerView recycleView;
    private MyAdapter myAdapter;
    private RecyclerView.LayoutManager layout;
    private ArrayList<String> list = new ArrayList<>();
    private String colorBg;
    private String theme;
    private TextView article;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        article = findViewById(R.id.article);
        recycleView = findViewById(R.id.words);
        recycleView.setHasFixedSize(true);

        init();
        recycleView.setItemAnimator(new DefaultItemAnimator());
        layout = new LinearLayoutManager(this);
        recycleView.setLayoutManager(layout);
        myAdapter = new MyAdapter(list, recycleView);
        recycleView.setAdapter(myAdapter);
    }


    public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {
        private ArrayList<String> items = new ArrayList<>();
        private RecyclerView mRecycleView;

        public MyAdapter(ArrayList<String> list, RecyclerView recyclerView) {
            mRecycleView = recyclerView;
            items =list;
        }

        public class MyViewHolder extends RecyclerView.ViewHolder {

            private TextView mTextView;

            public MyViewHolder(View v) {
                super(v);
                mTextView = findViewById(R.id.article);
            }
        }

        @NonNull
        @Override
        public MyAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_item, parent, false);
            v.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = mRecycleView.getChildAdapterPosition(v);
                    if (position==1) {
                        if ((position*2) % 2 == 0) {
                            items.remove((position*2)-1);
                            items.remove((position*2) );
                            notifyItemRemoved(position);
                        } else {
                            items.remove((position*2) + 1);
                            items.remove((position*2));
                            notifyItemRemoved(position);
                        }
                    } else {
                        items.remove(1);
                        items.remove(0);
                        notifyItemRemoved(position);
                    }
                    notifyDataSetChanged();
                }
        });

            MyViewHolder vh = new MyViewHolder(v);
            return vh;
        }

        @Override
        public void onBindViewHolder(MyViewHolder holder, final int position) {
            String a;
                if ((position*2) % 2 == 0) {
                    a = items.get((position*2)) + "-" + items.get((position*2) + 1);
                } else if ((position*2) % 2 == 1)
                    a = items.get((position*2)) + "-" + items.get((position*2) - 1);
                else
                    a="Nie ma obiektu";
                if (holder.mTextView!=null)
                    holder.mTextView.setText(a);
        }
        @Override
        public int getItemCount() {
            return (items.size()/2);
        }
    }
}
