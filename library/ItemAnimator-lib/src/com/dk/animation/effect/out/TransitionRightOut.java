package com.dk.animation.effect.out;

import android.support.v4.view.ViewCompat;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.View;

import com.dk.animation.effect.BaseItemAnimator;
import com.dk.animation.effect.BaseItemAnimator.VpaListenerAdapter;
import com.dk.animation.effect.SegmentAnimator;
import com.dk.animation.effect.ViewUtils;

public class TransitionRightOut extends SegmentAnimator {

    private int mDelayCount = 0;
    private int mDelay = 0;

    @Override
    public void animationPrepare(final RecyclerView.ViewHolder holder) {
        ViewCompat.setTranslationX(holder.itemView, 0);
        ViewCompat.setTranslationY(holder.itemView, 0);
    }

    @Override
    public void startAnimation(final ViewHolder holder, long duration, final BaseItemAnimator animator) {
        ViewCompat.animate(holder.itemView).cancel();
        ViewCompat.animate(holder.itemView).translationX(ViewUtils.getScreenWidth()).translationY(0).setDuration(duration).setStartDelay(mDelayCount * mDelay)
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
    }
}
