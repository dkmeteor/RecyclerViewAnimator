package com.dk.animation.effect.in;

import android.animation.Animator;
import android.animation.Animator.AnimatorListener;
import android.animation.ObjectAnimator;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.ViewHolder;

import com.dk.animation.effect.BaseItemAnimator;
import com.dk.animation.effect.SegmentAnimator;
import com.dk.animation.effect.ViewUtils;

public class ShakeIn extends SegmentAnimator {
    @Override
    public void animationPrepare(final RecyclerView.ViewHolder holder) {
        ViewCompat.setTranslationX(holder.itemView, -ViewUtils.getScreenWidth());
    }

    @Override
    public void startAnimation(final ViewHolder holder, long duration, final BaseItemAnimator animator) {
        ViewCompat.animate(holder.itemView).cancel();
        ObjectAnimator objectAnimatorAnimator = ObjectAnimator.ofFloat(holder.itemView, "translationX", -ViewUtils.getScreenWidth(), -ViewUtils.getScreenWidth() * 3f / 4f,
                -ViewUtils.getScreenWidth() / 2f, -ViewUtils.getScreenWidth() / 4f, 0, 25, -25, 25, -25, 15, -15, 6, -6, 0);
        objectAnimatorAnimator.addListener(new AnimatorListener() {

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
        objectAnimatorAnimator.setStartDelay(mDelay * mDelayCount);
        objectAnimatorAnimator.setDuration(animator.getAddDuration());
        objectAnimatorAnimator.start();
        animator.mAddAnimations.add(holder);
    }
}
