package com.dk.animation.effect.in;

import android.animation.Animator;
import android.animation.Animator.AnimatorListener;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.RecyclerView.ViewHolder;

import com.dk.animation.effect.BaseItemAnimator;
import com.dk.animation.effect.SegmentAnimator;

public class FlipHorizonIn extends SegmentAnimator {

    @Override
    public void animationPrepare(ViewHolder holder) {
        ViewCompat.setRotationY(holder.itemView, 90);
        ViewCompat.setAlpha(holder.itemView, 90);
    }

    @Override
    public void startAnimation(final ViewHolder holder, long duration, final BaseItemAnimator animator) {

        ViewCompat.animate(holder.itemView).cancel();

        AnimatorSet set = new AnimatorSet();
        set.playTogether(ObjectAnimator.ofFloat(holder.itemView, "rotationY", 90, -15, 15, 0), ObjectAnimator.ofFloat(holder.itemView, "alpha", 0.25f, 0.5f, 0.75f, 1));
        set.addListener(new AnimatorListener() {

            @Override
            public void onAnimationStart(Animator animation) {
                // TODO Auto-generated method stub

            }

            @Override
            public void onAnimationRepeat(Animator animation) {
                // TODO Auto-generated method stub

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                animator.dispatchAddFinished(holder);
                animator.mAddAnimations.remove(holder);
                animator.dispatchFinishedWhenDone();
            }

            @Override
            public void onAnimationCancel(Animator animation) {
                // TODO Auto-generated method stub

            }
        });
        set.setStartDelay(mDelay * mDelayCount);
        set.setDuration(animator.getAddDuration());
        set.start();

        animator.mAddAnimations.add(holder);
    }
}
