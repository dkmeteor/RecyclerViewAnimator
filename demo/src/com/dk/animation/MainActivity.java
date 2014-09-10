package com.dk.animation;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.dk.animation.ListAdapterHolder.OnItemClickListener;
import com.dk.animation.effect.BaseItemAnimator;
import com.dk.animation.effect.PackageAnimator;
import com.dk.animation.effect.in.AlphaIn;
import com.dk.animation.effect.in.ScaleIn;
import com.dk.animation.effect.in.ShakeIn;
import com.dk.animation.effect.in.TransitionLeftIn;
import com.dk.animation.effect.in.TransitionRightIn;
import com.dk.animation.effect.out.AlphaOut;
import com.dk.animation.effect.out.DropOut;
import com.dk.animation.effect.out.ScaleOut;
import com.dk.animation.effect.out.TransitionLeftOut;
import com.dk.animation.effect.out.TransitionRightOut;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.Mode;
import com.handmark.pulltorefresh.library.PullToRefreshRecyclerView;

public class MainActivity extends Activity {
    private PullToRefreshRecyclerView mRecyclerView;
    private ListAdapterHolder adapter;
    private ArrayList<UserData> mDatas = new ArrayList<UserData>();
    private static int[] mResId = new int[] { R.drawable.p1, R.drawable.p2, R.drawable.p3 };
    private BaseItemAnimator mAnimator;

    @SuppressWarnings({ "unchecked", "rawtypes" })
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ViewUtils.init(this);
        mRecyclerView = (PullToRefreshRecyclerView) findViewById(R.id.recycler_view);
        createTestData();
        adapter = new ListAdapterHolder(mDatas);
        mRecyclerView.getRefreshableView().setAdapter(adapter);
        mRecyclerView.getRefreshableView().setHasFixedSize(true);
        mRecyclerView.getRefreshableView().setLayoutManager(new LinearLayoutManager(this));
        mAnimator = new PackageAnimator(new TransitionLeftIn(), new DropOut());
        mRecyclerView.getRefreshableView().setItemAnimator(mAnimator);
        adapter.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(View view, int position) {
                removeItem(position);
            }
        });
        mRecyclerView.setScrollingWhileRefreshingEnabled(true);
        mRecyclerView.setMode(Mode.BOTH);
        mRecyclerView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2() {

            @Override
            public void onPullDownToRefresh(PullToRefreshBase refreshView) {

                mRecyclerView.onRefreshComplete();
                for (int i = 0; i < 3; i++) {
                    addItem(0);
                }
                mRecyclerView.postDelayed(new Runnable() {

                    @Override
                    public void run() {

                        adapter.notifyItemRangeInserted(0, 3);
                        mRecyclerView.getRefreshableView().scrollToPosition(0);

                    }
                }, 200);

            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase refreshView) {
                mRecyclerView.onRefreshComplete();
                for (int i = 0; i < 5; i++) {
                    addItem(adapter.getItemCount() - 1);
                }

                mRecyclerView.postDelayed(new Runnable() {

                    @Override
                    public void run() {
                        adapter.notifyItemRangeInserted(adapter.getItemCount() - 5, 5);
                        mRecyclerView.getRefreshableView().scrollToPosition(adapter.getItemCount() - 1);

                    }
                }, 200);

            }

        });
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
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
        case R.id.action_alpha:
            mAnimator = new PackageAnimator(new AlphaIn(), new AlphaOut());
            mRecyclerView.getRefreshableView().setItemAnimator(mAnimator);

            break;
        case R.id.action_horizon_left:
            mAnimator = new PackageAnimator(new TransitionLeftIn(), new TransitionLeftOut());
            mRecyclerView.getRefreshableView().setItemAnimator(mAnimator);
            break;
        case R.id.action_horizon_right:
            mAnimator = new PackageAnimator(new TransitionRightIn(), new TransitionRightOut());
            mRecyclerView.getRefreshableView().setItemAnimator(mAnimator);
            break;
        case R.id.action_scale:
            mAnimator = new PackageAnimator(new ScaleIn(), new ScaleOut());
            mRecyclerView.getRefreshableView().setItemAnimator(mAnimator);
            break;
        case R.id.action_drop:
            mAnimator = new PackageAnimator(new TransitionLeftIn(), new DropOut());
            mRecyclerView.getRefreshableView().setItemAnimator(mAnimator);
            break;
        case R.id.action_shake:
            mAnimator = new PackageAnimator(new ShakeIn(), new TransitionLeftOut());
            mRecyclerView.getRefreshableView().setItemAnimator(mAnimator);
            break;
        case R.id.action_next:
            Intent intent = new Intent(MainActivity.this, ProgressActivity.class);
            startActivity(intent);
            break;
        }
        return super.onOptionsItemSelected(item);
    }
}
