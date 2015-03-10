package com.quest.game.interfaces;

import android.app.Fragment;

/**
 * Created by VoitovichSergei on 07.03.2015.
 */
public interface IFragment {
    public void getStatus(Fragment fragment, String forthcomingStatus);
    public void getSendUserInfo(String url);
    public void nextFragment(Fragment fragment);
    public void finishActivity();
    public int getCountCombo();
    public void setCountCombo();
    public void setZeroCountCombo();
}
