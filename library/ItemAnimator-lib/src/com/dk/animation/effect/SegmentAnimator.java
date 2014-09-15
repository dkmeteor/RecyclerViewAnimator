package com.dk.animation.effect;

import android.support.v7.widget.RecyclerView;

public abstract class SegmentAnimator {
    protected long mDelayCount = 0;
    protected long mDelay = 200;

    public abstract void animationPrepare(final RecyclerView.ViewHolder holder);

    public abstract void startAnimation(final RecyclerView.ViewHolder holder, long duration, final BaseItemAnimator animator);

    /**
     * When notify add / remove more than 1 item , and you want them to animated
     * one by one , set the divider time. Otherwise , set it to 0.
     * 
     * @param divider
     *            unit millisecond
     */
    public SegmentAnimator setAnimationDivider(long divider) {
        mDelay = divider;
        return this;
    }

    public SegmentAnimator resetDelayCount() {
        mDelayCount = 0;
        return this;
    }

    public SegmentAnimator nextDelay() {
        mDelayCount++;
        return this;
    }
}
