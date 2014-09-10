package com.dk.animation.effect;

import android.support.v7.widget.RecyclerView;

public abstract class SegmentAnimator {
    public int mDelayCount = 0;
    public int mDelay = 0;

    public abstract void animationPrepare(final RecyclerView.ViewHolder holder);

    public abstract void startAnimation(final RecyclerView.ViewHolder holder, long duration, final BaseItemAnimator animator);
}
