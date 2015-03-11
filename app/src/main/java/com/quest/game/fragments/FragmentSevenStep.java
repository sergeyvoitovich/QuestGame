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

import com.quest.game.Constants;
import com.quest.game.R;
import com.quest.game.interfaces.IFragment;

/**
 * Created by VoitovichSergei on 07.03.2015.
 */
public class FragmentSevenStep extends Fragment{
    private View view;
    private TextView letterOne;
    private TextView letterTwo;
    private TextView letterThree;
    private TextView letterFour;
    private TextView letterFive;

    private int positionLetterOne = 0;
    private int positionLetterTwo = 0;
    private int positionLetterThree = 0;
    private int positionLetterFour = 0;
    private int positionLetterFive = 0;
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
        view = inflater.inflate(R.layout.activity_main_3, null);
        iFragment.changeTimer((TextView)view.findViewById(R.id.timer),view);
        initButtons();
        initTexts();
        iFragment.getSendUserInfo("http://beappy.ru/igra/rec.php?ekran=S7");
        iFragment.getStatus(new FragmentEightStep(), "S8");
        return view;
    }

    private void initButtons() {
        view.findViewById(R.id.ImageButton01).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                positionLetterFive = changeLettersUp(letterFive, positionLetterFive);
            }
        });
        view.findViewById(R.id.ImageButton02).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                positionLetterFour = changeLettersUp(letterFour, positionLetterFour);
            }
        });
        view.findViewById(R.id.ImageButton03).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                positionLetterThree = changeLettersUp(letterThree, positionLetterThree);
            }
        });
        view.findViewById(R.id.ImageButton04).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                positionLetterTwo = changeLettersUp(letterTwo, positionLetterTwo);
            }
        });
        view.findViewById(R.id.ImageButton05).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                positionLetterFive = changeLettersDown(letterFive, positionLetterFive);
            }
        });
        view.findViewById(R.id.ImageButton06).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                positionLetterFour =changeLettersDown(letterFour, positionLetterFour);
            }
        });
        view.findViewById(R.id.ImageButton07).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                positionLetterThree =changeLettersDown(letterThree, positionLetterThree);
            }
        });
        view.findViewById(R.id.ImageButton08).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                positionLetterTwo =changeLettersDown(letterTwo, positionLetterTwo);
            }
        });
        view.findViewById(R.id.ImageButton09).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                positionLetterOne =changeLettersDown(letterOne, positionLetterOne);
            }
        });
        view.findViewById(R.id.ImageButton10).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                positionLetterOne = changeLettersUp(letterOne, positionLetterOne);
            }
        });
        view.findViewById(R.id.ImageButtonSelector).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });

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

    private void initTexts() {
        letterOne = (TextView) view.findViewById(R.id.textView1);
        letterTwo = (TextView) view.findViewById(R.id.textView2);
        letterThree = (TextView) view.findViewById(R.id.textView3);
        letterFour = (TextView) view.findViewById(R.id.textView4);
        letterFive = (TextView) view.findViewById(R.id.textView5);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        iFragment = null;
    }

    private int changeLettersUp(TextView textView, int position) {
        position++;
        if (position < Constants.ALPHABET.length) {
            textView.setText(Constants.ALPHABET[position]);
        } else {
            position = 0;
            textView.setText(Constants.ALPHABET[position]);
        }
        return position;
    }

    private int changeLettersDown(TextView textView, int position) {
        position--;
        if (position < 0) {
            position = Constants.ALPHABET.length - 1;
            textView.setText(Constants.ALPHABET[position]);
        } else {
            textView.setText(Constants.ALPHABET[position]);
        }
        return position;
    }


}
