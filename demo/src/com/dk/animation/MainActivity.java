package com.dk.animation;

import java.util.ArrayList;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.dk.animation.ListAdapterHolder.OnItemClickListener;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.Mode;
import com.handmark.pulltorefresh.library.PullToRefreshRecyclerView;

public class MainActivity extends Activity {
    private PullToRefreshRecyclerView mRecyclerView;
    private ListAdapterHolder adapter;
    private ArrayList<UserData> mDatas = new ArrayList<UserData>();
    private static int[] mResId = new int[] { R.drawable.p1, R.drawable.p2, R.drawable.p3 };

    @SuppressWarnings({ "rawtypes", "unchecked" })
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mRecyclerView = (PullToRefreshRecyclerView) findViewById(R.id.recycler_view);
        createTestData();
        adapter = new ListAdapterHolder(mDatas);
        mRecyclerView.getRefreshableView().setAdapter(adapter);
        mRecyclerView.getRefreshableView().setHasFixedSize(true);
        mRecyclerView.getRefreshableView().setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.getRefreshableView().setItemAnimator(new DefaultItemAnimator());
        adapter.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(View view, int position) {
                removeItem(position);
            }
        });

        mRecyclerView.setMode(Mode.BOTH);

        mRecyclerView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2() {

            @Override
            public void onPullDownToRefresh(PullToRefreshBase refreshView) {
                for (int i = 0; i < 5; i++) {
                    addItem(0);
                }
                mRecyclerView.onRefreshComplete();
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase refreshView) {
                for (int i = 0; i < 5; i++) {
                    addItem(0);
                }
                mRecyclerView.onRefreshComplete();

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
