package com.dk.animation;

import java.util.ArrayList;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.dk.animation.ListAdapterHolder.OnItemClickListener;
import com.dk.animation.effect.BaseItemAnimator;
import com.dk.animation.effect.DropAnimator;

public class MainActivity extends Activity {
    private RecyclerView mRecyclerView;
    private ListAdapterHolder adapter;
    private ArrayList<UserData> mDatas = new ArrayList<UserData>();
    private static int[] mResId = new int[] { R.drawable.p1, R.drawable.p2, R.drawable.p3 };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        createTestData();
        adapter = new ListAdapterHolder(mDatas);
        mRecyclerView.setAdapter(adapter);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        adapter.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(View view, int position) {
                removeItem(position);
            }
        });

        findViewById(R.id.btn_add).setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                addItem(5);
            }
        });
    }

    private void addItem(int position) {
        mDatas.add(position, new UserData(mResId[position % 3], "Hello World"));
        adapter.notifyItemInserted(position);
    }

    private void removeItem(int position) {
        mDatas.remove(position);
        adapter.notifyItemRemoved(position);
    }

    private void createTestData() {
        for (int i = 0; i < 20; i++) {
            mDatas.add(new UserData(mResId[i % 3], "Hello World"));
        }
    }
}
