package com.dk.animation.effect.out;

import android.support.v4.view.ViewCompat;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.View;

import com.dk.animation.effect.BaseItemAnimator;
import com.dk.animation.effect.SegmentAnimator;
import com.dk.animation.effect.BaseItemAnimator.VpaListenerAdapter;

public class AnchorDropOut extends SegmentAnimator {

	private View mAnchor;
	private int[] mLocations = new int[2];

	public AnchorDropOut(View anchor) {
		super();
		mDelay = 200;
		mAnchor = anchor;
		if (anchor.getParent() != null)
			mAnchor.getLocationOnScreen(mLocations);
	}

	@Override
	public void animationPrepare(ViewHolder holder) {

	}

	@Override
	public void startAnimation(final ViewHolder holder, long duration,
			final BaseItemAnimator animator) {

		int[] holderLocations = new int[2];

		holder.itemView.getLocationOnScreen(holderLocations);
		
		holder.itemView.setPivotX(0);
		holder.itemView.setPivotY(0);
		

		ViewCompat.animate(holder.itemView).cancel();
		ViewCompat.animate(holder.itemView)
				.translationX(mLocations[0] - holderLocations[0])
				.translationY(mLocations[1] - holderLocations[1]).scaleX(0f)
				.scaleY(0f).setDuration(duration)
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
