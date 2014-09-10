package com.dk.animation;

import java.util.ArrayList;

import android.app.ActionBar;
import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.dk.animation.ListAdapterHolder.OnItemClickListener;
import com.dk.animation.effect.BaseItemAnimator;
import com.dk.animation.effect.PackageAnimator;
import com.dk.animation.effect.in.TransitionLeftIn;
import com.dk.animation.effect.out.DropOut;

public class ProgressActivity extends Activity {

    private MenuItem menuItem;
    private RecyclerView mRecyclerView;
    private ListAdapterHolder adapter;
    private ArrayList<UserData> mDatas = new ArrayList<UserData>();
    private static int[] mResId = new int[] { R.drawable.p1, R.drawable.p2, R.drawable.p3 };
    private BaseItemAnimator mAnimator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_progress);
        ActionBar actionBar = getActionBar();
        actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_HOME | ActionBar.DISPLAY_SHOW_TITLE | ActionBar.DISPLAY_SHOW_CUSTOM);
        ViewUtils.init(this);
        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        createTestData();
        adapter = new ListAdapterHolder(mDatas);
        mRecyclerView.setAdapter(adapter);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mAnimator = new PackageAnimator(new TransitionLeftIn(), new DropOut());
        mRecyclerView.setItemAnimator(mAnimator);
        adapter.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(View view, int position) {
                removeItem(position);
            }
        });

    }

    private void addItems() {
        for (int i = 0; i < 5; i++) {
            addItem(0);
        }
        mRecyclerView.postDelayed(new Runnable() {

            @Override
            public void run() {

                adapter.notifyItemRangeInserted(0, 5);
                mRecyclerView.scrollToPosition(0);

            }
        }, 100);

    }

    private void addItem(int position) {
        mDatas.add(position, new UserData(mResId[position % 3], "Hello World"));

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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.progress, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
        case R.id.menu_load:
            menuItem = item;
            menuItem.setActionView(R.layout.progressbar);
            menuItem.expandActionView();
            TestTask task = new TestTask();
            task.execute("test");
            break;
        default:
            break;
        }
        return true;
    }

    private class TestTask extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... params) {
            // Simulate something long running
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(String result) {
            menuItem.collapseActionView();
            menuItem.setActionView(null);
            addItems();
        }
    };
}