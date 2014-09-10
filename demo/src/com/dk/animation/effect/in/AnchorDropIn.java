package com.dk.animation.effect.in;

import android.support.v4.view.ViewCompat;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.View;

import com.dk.animation.effect.BaseItemAnimator;
import com.dk.animation.effect.SegmentAnimator;
import com.dk.animation.effect.BaseItemAnimator.VpaListenerAdapter;

public class AnchorDropIn extends SegmentAnimator {

	private View mAnchor;
	private int[] mLocations = new int[2];

	public AnchorDropIn(View anchor) {
		super();
		mDelay = 200;
		mAnchor = anchor;
		if (anchor.getParent() != null)
			mAnchor.getLocationInWindow(mLocations);
	}

	@Override
	public void animationPrepare(ViewHolder holder) {
		mDelayCount = 0;
		int[] holderLocations = new int[2];

		holder.itemView.getLocationOnScreen(holderLocations);

		ViewCompat.setTranslationX(holder.itemView, mLocations[0]
				- holderLocations[0]);

		ViewCompat.setTranslationY(holder.itemView, mLocations[1]
				- holderLocations[1]);

		holder.itemView.setPivotX(0);
		holder.itemView.setPivotY(0);
		ViewCompat.setScaleY(holder.itemView, 0.0f);
		ViewCompat.setScaleX(holder.itemView, 0.0f);

	}

	@Override
	public void startAnimation(final ViewHolder holder, long duration,
			final BaseItemAnimator animator) {
		ViewCompat.animate(holder.itemView).cancel();
		ViewCompat.animate(holder.itemView).translationX(0).translationY(0)
				.scaleX(1f).scaleY(1f).setDuration(duration)
				.setStartDelay(mDelayCount * mDelay)
				.setListener(new VpaListenerAdapter() {
					@Override
					public void onAnimationCancel(View view) {
						ViewCompat.setTranslationY(view, 0);
					}

					@Override
					public void onAnimationEnd(View view) {
						animator.dispatchAddFinished(holder);
						animator.mAddAnimations.remove(holder);
						animator.dispatchFinishedWhenDone();
					}
				}).start();
		mDelayCount++;
	}

}
