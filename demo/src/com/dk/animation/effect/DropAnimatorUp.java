package com.dk.animation.effect;

import android.support.v4.view.ViewCompat;
import android.support.v7.widget.RecyclerView;
import android.view.View;

public class DropAnimatorUp extends BaseItemAnimator {

	private float mOriginalY;
	private float mDeltaY;
	private View mAnchor;
	private int[] mAnchorLocation = new int[2];

	private int mDelayCount = 0;

	public DropAnimatorUp(RecyclerView recyclerView, View anchor) {
		super(recyclerView);
		mAnchor = anchor;
		setMoveDuration(300);
		setAddDuration(300);
		setRemoveDuration(300);
		if (anchor != null)
			mAnchor.getLocationOnScreen(mAnchorLocation);
	}

	public DropAnimatorUp(RecyclerView recyclerView) {
		super(recyclerView);
		setMoveDuration(300);
		setAddDuration(300);
		setRemoveDuration(300);
	}

	protected void animateRemoveImpl(final RecyclerView.ViewHolder holder) {
		// final View view = holder.itemView;
		//
		// ViewCompat.animate(view).cancel();
		// ViewCompat.animate(view).setDuration(getRemoveDuration())
		// .translationY(+mDeltaY).setListener(new VpaListenerAdapter() {
		// @Override
		// public void onAnimationEnd(View view) {
		// // ViewCompat.setTranslationY(view, +mDeltaY);
		// dispatchRemoveFinished(holder);
		// mRemoveAnimations.remove(holder);
		// dispatchFinishedWhenDone();
		// }
		// }).start();
		// mRemoveAnimations.add(holder);
	}

	@Override
	protected void prepareAnimateAdd(RecyclerView.ViewHolder holder) {
		retrieveItemPosition(holder);
		ViewCompat.setTranslationY(holder.itemView, -mDeltaY);
		mDelayCount = 0;
	}

	protected void animateAddImpl(final RecyclerView.ViewHolder holder) {
		final View view = holder.itemView;

		ViewCompat.animate(view).cancel();
		ViewCompat.animate(view).translationX(0).translationY(0)
				.setDuration(getAddDuration()).setStartDelay(0 * 100)
				.setListener(new VpaListenerAdapter() {
					@Override
					public void onAnimationCancel(View view) {
						ViewCompat.setTranslationY(view, 0);
					}

					@Override
					public void onAnimationEnd(View view) {
						dispatchAddFinished(holder);
						mAddAnimations.remove(holder);
						dispatchFinishedWhenDone();
					}
				}).start();

		mDelayCount++;

	}

	private void retrieveItemPosition(final RecyclerView.ViewHolder holder) {
		mOriginalY = mRecyclerView.getLayoutManager().getDecoratedTop(
				holder.itemView);
		mDeltaY = mRecyclerView.getHeight() - mOriginalY;
	}
}
