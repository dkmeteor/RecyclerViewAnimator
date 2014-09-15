package com.dk.animation.effect.out;

import android.animation.Animator;
import android.animation.Animator.AnimatorListener;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.ViewHolder;

import com.dk.animation.effect.BaseItemAnimator;
import com.dk.animation.effect.SegmentAnimator;
import com.dk.animation.effect.ViewUtils;

public class DropOut extends SegmentAnimator {
    @Override
    public void animationPrepare(final RecyclerView.ViewHolder holder) {
    }

    @Override
    public void startAnimation(final ViewHolder holder, long duration, final BaseItemAnimator animator) {
        ViewCompat.animate(holder.itemView).cancel();
        AnimatorSet set = new AnimatorSet();
        int abs = Math.random() > 0.5 ? -1 : 1;
        ObjectAnimator objectAnimatorAnimatorR = ObjectAnimator.ofFloat(holder.itemView, "rotation", abs*30, abs*90);
        ObjectAnimator objectAnimatorAnimatorT = ObjectAnimator.ofFloat(holder.itemView, "translationY",0, ViewUtils.getScreenHeight());

        set.addListener(new AnimatorListener() {

            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                animator.dispatchAddFinished(holder);
                animator.mAddAnimations.remove(holder);
                animator.dispatchFinishedWhenDone();
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }
        });

        set.playTogether(objectAnimatorAnimatorR, objectAnimatorAnimatorT);
        set.setStartDelay(mDelay * mDelayCount);
        set.setDuration(animator.getAddDuration());
        set.start();

        animator.mAddAnimations.add(holder);

    }
}
