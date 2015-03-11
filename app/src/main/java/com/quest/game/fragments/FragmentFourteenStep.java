package com.quest.game.fragments;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.quest.game.R;
import com.quest.game.interfaces.IFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by VoitovichSergei on 07.03.2015.
 */
public class FragmentFourteenStep extends Fragment{
    private View view;
    List<ImageView> images = new ArrayList<ImageView>();
    boolean kkk2 = false;
    int tick_count;
    private boolean comboButtonOne = false;
    private boolean comboButtonTwo = false;

    private IFragment iFragment;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        iFragment = (IFragment) activity;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.activity_main_14, null);
        iFragment.changeTimer((TextView)view.findViewById(R.id.timer),view);
        initButtons();
        initImages();

        new CountDownTimer(1000, 350)
        {
            public void onFinish()
            {
                if (!kkk2) {}
                for (int i = 0;; i++)
                {
                    if (i > 1)
                    {
                        tick_count = 0;
                        start();
                        return;
                    }
                    images.get(i).setImageResource(R.drawable.red_on);
                }
            }

            public void onTick(long paramAnonymousLong)
            {
                if (tick_count == 1)
                {
                    images.get(0).setImageResource(R.drawable.red_off);
                    images.get(1).setImageResource(R.drawable.red_off);
                }

                tick_count++;
            }
        }.start();

        iFragment.getStatus(new FragmentFifteenStep(), "S15");
        return view;
    }

    private void initImages() {
        images.add((ImageView) view.findViewById(R.id.charge_13));
        images.add((ImageView) view.findViewById(R.id.charge_14));
    }

    private void initButtons() {

        view.findViewById(R.id.ImageButton05).setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (MotionEvent.ACTION_DOWN == event.getAction()) {
                    comboButtonTwo = true;
                } else if (MotionEvent.ACTION_UP == event.getAction()) {
                    comboButtonTwo = false;
                    iFragment.setZeroCountCombo();
                }
                return false;
            }
        });


        view.findViewById(R.id.ImageButton10).setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (MotionEvent.ACTION_DOWN == event.getAction()) {
                    comboButtonOne = true;
                } else if (MotionEvent.ACTION_UP == event.getAction()) {
                    comboButtonOne = false;
                    iFragment.setZeroCountCombo();
                }
                return false;
            }
        });

        view.findViewById(R.id.ImageButtonSelector).setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (MotionEvent.ACTION_DOWN == event.getAction()) {
                    if (comboButtonOne && comboButtonTwo) {
                        iFragment.setCountCombo();
                        if (iFragment.getCountCombo() == 4) {
                            iFragment.finishActivity();
                        }

                    }
                }
                return false;
            }
        });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        iFragment = null;
    }
}
