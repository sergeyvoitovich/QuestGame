package com.quest.game.fragments;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.quest.game.R;
import com.quest.game.interfaces.IFragment;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by VoitovichSergei on 07.03.2015.
 */
public class FragmentBlackStep extends Fragment{
    private View view;

    private IFragment iFragment;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        iFragment = (IFragment) activity;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.activity_black, null);
        TimerTask timerSlipS2 = new TimerTask() {
            @Override
            public void run() {
                iFragment.nextFragment(new FragmentSecondStep());
            }
        };
        Timer timer = new Timer();
        timer.schedule(timerSlipS2, 1000 * 50);

        return view;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        iFragment = null;
    }
}
