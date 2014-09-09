package com.dk.animation.effect;

import android.support.v4.view.ViewCompat;
import android.support.v7.widget.RecyclerView;
import android.view.View;

public class ShakeAnimator extends BaseItemAnimator {
    private float mDeltaY = 0;

    public ShakeAnimator(RecyclerView recyclerView) {
        super(recyclerView);
    }

    protected void animateRemoveImpl(final RecyclerView.ViewHolder holder) {
        final View view = holder.itemView;

        ViewCompat.animate(view).cancel();
        ViewCompat.animate(view).setDuration(getRemoveDuration()).translationY(+mDeltaY).setListener(new VpaListenerAdapter() {
            @Override
            public void onAnimationEnd(View view) {
                // ViewCompat.setTranslationY(view, +mDeltaY);
                dispatchRemoveFinished(holder);
                mRemoveAnimations.remove(holder);
                dispatchFinishedWhenDone();
            }
        }).start();
        mRemoveAnimations.add(holder);
    }

    @Override
    protected void prepareAnimateAdd(RecyclerView.ViewHolder holder) {
        ViewCompat.setTranslationX(holder.itemView, -480);
        count = 0;
    }

    int count = 0;

    protected void animateAddImpl(final RecyclerView.ViewHolder holder) {

        final View view = holder.itemView;
        ViewCompat.animate(view).cancel();
        ViewCompat.animate(view).translationX(0).translationY(0).setDuration(getAddDuration()).setStartDelay(count * 100).setListener(new VpaListenerAdapter() {
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

        count++;
        // ObjectAnimator animator = ObjectAnimator.ofFloat(view,
        // "translationX",
        // 0, 25, -25, 25, -25, 15, -15, 6, -6, 0);
        // animator.addListener(new AnimatorListener() {
        //
        // @Override
        // public void onAnimationStart(Animator animation) {
        // // TODO Auto-generated method stub
        //
        // }
        //
        // @Override
        // public void onAnimationRepeat(Animator animation) {
        // // TODO Auto-generated method stub
        //
        // }
        //
        // @Override
        // public void onAnimationEnd(Animator animation) {
        // dispatchAddFinished(holder);
        // mAddAnimations.remove(holder);
        // dispatchFinishedWhenDone();
        // }
        //
        // @Override
        // public void onAnimationCancel(Animator animation) {
        // // TODO Auto-generated method stub
        //
        // }
        // });
        // animator.setStartDelay(1000);
        // animator.start();
        // mAddAnimations.add(holder);
    }

}
