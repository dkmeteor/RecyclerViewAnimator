package com.dk.animation;

import java.util.ArrayList;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import com.dk.animation.ListAdapterHolder.OnItemClickListener;
import com.dk.animation.effect.DropAnimatorDown;
import com.dk.animation.effect.DropAnimatorUp;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.Mode;
import com.handmark.pulltorefresh.library.PullToRefreshRecyclerView;

public class MainActivity extends Activity {
	private PullToRefreshRecyclerView mRecyclerView;
	private ListAdapterHolder adapter;
	private ArrayList<UserData> mDatas = new ArrayList<UserData>();
	private static int[] mResId = new int[] { R.drawable.p1, R.drawable.p2,
			R.drawable.p3 };

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
		mRecyclerView.getRefreshableView().setLayoutManager(
				new LinearLayoutManager(this));
		mRecyclerView.getRefreshableView().setItemAnimator(
				new DropAnimatorDown(mRecyclerView.getRefreshableView(), null));
		adapter.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(View view, int position) {
				removeItem(position);
			}
		});
		mRecyclerView.setScrollingWhileRefreshingEnabled(true);
		mRecyclerView.setMode(Mode.BOTH);
		mRecyclerView
				.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2() {

					@Override
					public void onPullDownToRefresh(
							PullToRefreshBase refreshView) {

						mRecyclerView.onRefreshComplete();
						mRecyclerView.getRefreshableView().setItemAnimator(
								new DropAnimatorUp(mRecyclerView
										.getRefreshableView()));
						for (int i = 0; i < 5; i++) {
							addItem(0);
						}
						mRecyclerView.postDelayed(new Runnable() {

							@Override
							public void run() {

								adapter.notifyItemRangeInserted(0, 5);
								mRecyclerView.getRefreshableView()
										.scrollToPosition(0);

							}
						}, 200);

					}

					@Override
					public void onPullUpToRefresh(PullToRefreshBase refreshView) {
						mRecyclerView.onRefreshComplete();
						mRecyclerView.getRefreshableView().setItemAnimator(
								new DropAnimatorDown(mRecyclerView
										.getRefreshableView()));
						for (int i = 0; i < 5; i++) {
							addItem(adapter.getItemCount() - 1);
						}

						mRecyclerView.postDelayed(new Runnable() {

							@Override
							public void run() {
								adapter.notifyItemRangeInserted(
										adapter.getItemCount() - 5, 5);
								mRecyclerView.getRefreshableView()
										.scrollToPosition(
												adapter.getItemCount() - 1);

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
}
