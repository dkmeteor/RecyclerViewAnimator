package com.dk.animation.demo;

import java.util.ArrayList;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;

import com.dk.animation.R;
import com.dk.animation.demo.ListAdapterHolder.OnItemClickListener;
import com.dk.animation.effect.BaseItemAnimator;
import com.dk.animation.effect.PackageAnimator;
import com.dk.animation.effect.ViewUtils;
import com.dk.animation.effect.in.AnchorDropIn;
import com.dk.animation.effect.out.AnchorDropOut;
import com.dk.animation.effect.out.DropOut;

public class ProgressActivity extends Activity {

	private RecyclerView mRecyclerView;
	private ListAdapterHolder adapter;
	private ArrayList<UserData> mDatas = new ArrayList<UserData>();
	private static int[] mResId = new int[] { R.drawable.p1, R.drawable.p2,
			R.drawable.p3 };
	private BaseItemAnimator mAnimator;
	private View mRefreshIcon, mProgress;
	private Badge mBadge;
	private int count = 0;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_progress);
		getActionBar().hide();
		ViewUtils.init(this);
		mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);
		mBadge = (Badge) findViewById(R.id.badge);
		createTestData();
		adapter = new ListAdapterHolder(mDatas);
		mRecyclerView.setAdapter(adapter);
		mRecyclerView.setHasFixedSize(true);
		mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
		mAnimator = new PackageAnimator(new AnchorDropIn(
				findViewById(R.id.refresh)), new AnchorDropOut(
				findViewById(R.id.trash)));
		mAnimator.setAddDuration(800);
		mRecyclerView.setItemAnimator(mAnimator);
		adapter.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(View view, int position) {
				mAnimator = new PackageAnimator(null, new AnchorDropOut(
						findViewById(R.id.trash)));
				mAnimator.setAddDuration(800);
				mRecyclerView.setItemAnimator(mAnimator);

				removeItem(position);
				count++;
				mBadge.setText(String.valueOf(count));
			}
		});
		mRefreshIcon = findViewById(R.id.refresh);
		mProgress = findViewById(R.id.progressBar);
		mRefreshIcon.setOnClickListener(mOnClickListener);
		findViewById(R.id.trash).setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {

				count = 0;
				mBadge.setText("");

				mAnimator = new PackageAnimator(new AnchorDropIn(
						findViewById(R.id.trash)), new AnchorDropOut(
						findViewById(R.id.trash)));
				mAnimator.setAddDuration(400);
				mRecyclerView.setItemAnimator(mAnimator);

				for (int i = 0; i < 3; i++) {
					addItem(0);
				}
				mRecyclerView.postDelayed(new Runnable() {

					@Override
					public void run() {

						adapter.notifyItemRangeInserted(0, 3);
						mRecyclerView.scrollToPosition(0);

					}
				}, 1);
			}
		});
	}

	private View.OnClickListener mOnClickListener = new OnClickListener() {

		@Override
		public void onClick(View v) {
			mRefreshIcon.setVisibility(View.GONE);
			mProgress.setVisibility(View.VISIBLE);
			TestTask task = new TestTask();
			task.execute("test");
		}
	};

	private void addItems() {
		mAnimator = new PackageAnimator(new AnchorDropIn(
				findViewById(R.id.refresh)), new AnchorDropOut(
				findViewById(R.id.trash)));
		mAnimator.setAddDuration(800);
		mRecyclerView.setItemAnimator(mAnimator);

		for (int i = 0; i < 3; i++) {
			addItem(0);
		}
		mRecyclerView.postDelayed(new Runnable() {

			@Override
			public void run() {

				adapter.notifyItemRangeInserted(0, 3);
				mRecyclerView.scrollToPosition(0);

			}
		}, 1);

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

	private class TestTask extends AsyncTask<String, Void, String> {

		@Override
		protected String doInBackground(String... params) {
			// do sth
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			return null;
		}

		@Override
		protected void onPostExecute(String result) {
			addItems();

			mRefreshIcon.setVisibility(View.VISIBLE);
			mProgress.setVisibility(View.GONE);
		}
	};
}