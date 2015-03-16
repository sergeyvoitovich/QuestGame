package com.quest.game.utils;

import android.os.Handler;
import android.os.SystemClock;
import android.widget.TextView;

import com.quest.game.interfaces.IFragment;
import com.quest.game.interfaces.TimerListener;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

public class CustomTimer{

    private final static String m24 = "mm:ss";
    private TextView textView;
    private long seconds = -1;

    private Runnable mTicker;
    private Handler mHandler;
    private boolean firstStart = false;

    private boolean mTickerStopped = true;

    private TimerListener timerListener;

    public CustomTimer() {
    }

    public CustomTimer(TimerListener timerListener) {
        this.timerListener = timerListener;
    }

    public void setTextView(TextView textView){
        this.textView = textView;
        initTimer();
    }

    public void setTimerListener(TimerListener timerListener){
        this.timerListener = timerListener;
    }

    public void startTimer(){
        firstStart = true;
        if (!isRunning()) {
            mTickerStopped = false;
            mTicker.run();
        }
    }

    public boolean isRunning(){
        return !mTickerStopped;
    }

    public void stopTimer(){
        mTickerStopped = true;
    }

    protected void initTimer() {
        mHandler = new Handler();

        /**
         * requests a tick on the next hard-second boundary
         */
        mTicker = new Runnable() {
            public void run() {
                if (mTickerStopped) return;

                SimpleDateFormat sdf = new SimpleDateFormat(m24);
                sdf.setTimeZone(TimeZone.getTimeZone("UTC"));

                textView.setText(sdf.format(new Date(seconds * 1000)));

                if (seconds == 0) {
                    if (timerListener != null) timerListener.onTimerFinish();
                    mTickerStopped = true;
                }

                if (seconds % 10 == 0) {
                    timerListener.onCurrentTime(sdf.format(new Date(seconds * 1000)));
                }

                seconds--;

                textView.invalidate();
                long now = SystemClock.uptimeMillis();
                long next = now + (1000 - now % 1000);
                mHandler.postAtTime(mTicker, next);
            }
        };
    }

    public void setTime(long seconds){
        this.seconds = seconds;
    }

    public boolean getFirstStart() {
        return firstStart;
    }

}
