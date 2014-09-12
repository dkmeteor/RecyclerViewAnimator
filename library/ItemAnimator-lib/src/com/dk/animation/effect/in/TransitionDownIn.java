package com.dk.animation.effect.in;

import android.support.v4.view.ViewCompat;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.View;

import com.dk.animation.effect.BaseItemAnimator;
import com.dk.animation.effect.BaseItemAnimator.VpaListenerAdapter;
import com.dk.animation.effect.SegmentAnimator;
import com.dk.animation.effect.ViewUtils;

public class TransitionDownIn extends SegmentAnimator {

    @Override
    public void animationPrepare(ViewHolder holder) {
        ViewCompat.setTranslationY(holder.itemView, -ViewUtils.getScreenHeight());
    }

    @Override
    public void startAnimation(final ViewHolder holder, long duration, final BaseItemAnimator animator) {
        final View view = holder.itemView;
        ViewCompat.animate(view).cancel();
        ViewCompat.animate(view).translationX(0).translationY(0).setDuration(duration).setStartDelay(mDelayCount * mDelay)
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
