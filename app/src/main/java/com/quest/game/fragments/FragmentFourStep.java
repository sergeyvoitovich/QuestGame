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

/**
 * Created by VoitovichSergei on 07.03.2015.
 */
public class FragmentFourStep extends Fragment{
    private View view;

    private IFragment iFragment;
    private boolean comboButtonOne = false;
    private boolean comboButtonTwo = false;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        iFragment = (IFragment) activity;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.activity_main_4, null);
        initButtons();
        iFragment.changeTimer((TextView)view.findViewById(R.id.timer),view);
        iFragment.getSendUserInfo("http://beappy.ru/igra/rec.php?ekran=S4");
        iFragment.getStatus(new FragmentFiveStep(), "S5");
        return view;
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
