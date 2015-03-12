package com.quest.game.activities;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.media.MediaPlayer;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.TextView;

import com.quest.game.R;
import com.quest.game.fragments.FragmentEightStep;
import com.quest.game.fragments.FragmentElevenStep;
import com.quest.game.fragments.FragmentFifteenStep;
import com.quest.game.fragments.FragmentFirstStep;
import com.quest.game.fragments.FragmentFiveStep;
import com.quest.game.fragments.FragmentFourStep;
import com.quest.game.fragments.FragmentFourteenStep;
import com.quest.game.fragments.FragmentNineStep;
import com.quest.game.fragments.FragmentSecondStep;
import com.quest.game.fragments.FragmentSevenStep;
import com.quest.game.fragments.FragmentSixStep;
import com.quest.game.fragments.FragmentTenStep;
import com.quest.game.fragments.FragmentThirdStep;
import com.quest.game.fragments.FragmentThirteenthStep;
import com.quest.game.fragments.FragmentTwelfthStep;
import com.quest.game.interfaces.IFragment;
import com.quest.game.interfaces.TimerListener;
import com.quest.game.utils.CustomTimer;

import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import java.io.IOException;
import java.net.URL;
import java.util.Timer;
import java.util.TimerTask;

import javax.xml.parsers.DocumentBuilderFactory;


public class MainActivity extends Activity implements IFragment,TimerListener{
    private FragmentManager myFragmentManager;
    private Fragment fragmentFirstStep;
    private String url;
    private static int comboCount = 0;
    private CustomTimer customTimer;
    private View currentView;
    private String currentStatus;
    private boolean startAsynctaskStatus = false;
    private TimerTask timerTask;
    private MediaPlayer mediaPlayer;

    public boolean dispatchTouchEvent(MotionEvent paramMotionEvent)
    {
        if (paramMotionEvent.getAction() == 0) {
            getWindow().getDecorView().setSystemUiVisibility(5894);
        }
        return super.dispatchTouchEvent(paramMotionEvent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.main_layout);
        getWindow().getDecorView().setSystemUiVisibility(5894);
        setZeroCountCombo();
        getSendUserInfo("http://beappy.ru/igra/rec.php?ekran=S1");

        mediaPlayer = new MediaPlayer();
        mediaPlayer.stop();

        startAsynctaskStatus = true;
        currentStatus = "S1";
        //getStatus();

        customTimer = new CustomTimer(this);
        customTimer.setTime(0);
        initFragments();

        if (savedInstanceState == null) {
            FragmentTransaction fragmentTransaction = myFragmentManager
                    .beginTransaction();
            fragmentTransaction.add(R.id.container, fragmentFirstStep, "FragmentFirstStep");
            fragmentTransaction.commit();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (customTimer != null) {
            customTimer.stopTimer();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (customTimer != null && customTimer.getFirstStart()) {
            customTimer.startTimer();
        }
    }

    private void initFragments() {
        myFragmentManager = getFragmentManager();
        fragmentFirstStep = new FragmentFirstStep();
    }

    @Override
    public void getStatus() {
        new GetStatusAsyncTask().execute();
    }

    @Override
    public void getSendUserInfo(String url) {
        this.url = url;
        new SendUserInfo().execute();
    }

    @Override
    public void nextFragment(Fragment fragment) {
        comboCount=0;
        FragmentTransaction fragmentTransaction = myFragmentManager
                .beginTransaction();
        fragmentTransaction.replace(R.id.container, fragment,"");
        fragmentTransaction.commit();
    }

    @Override
    public void finishActivity() {
        finish();
    }

    @Override
    public void onTimerFinish() {
        startAsynctaskStatus = false;
       startAnimation();
    }


    public class GetStatusAsyncTask extends AsyncTask<Void, Void, Void> {
        String status = "";

        protected Void doInBackground(Void... paramVarArgs) {
            try {
                URL localURL = new URL("http://beappy.ru/igra/getstatus2.php");
                Document localDocument = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(new InputSource(localURL.openStream()));
                localDocument.getDocumentElement().normalize();
                NodeList localNodeList1 = localDocument.getElementsByTagName("slide");
                for (int i = 0; i < localNodeList1.getLength(); i++)
                {
                    NodeList localNodeList2 = ((Element)((Element)localNodeList1.item(i)).getElementsByTagName("id").item(0)).getChildNodes();
                    System.out.println("ID = " + localNodeList2.item(0).getNodeValue());
                    this.status = localNodeList2.item(0).getNodeValue();
                }
                return null;
            }
            catch (Exception localException) {
                Log.d("QuestGameException", Log.getStackTraceString(localException));
            }
            return null;
        }

        protected void onPostExecute(Void paramVoid)
        {
            super.onPostExecute(paramVoid);
            if (!this.status.equals(currentStatus)) {
                if (this.status.equals("S1")) {
                    nextFragment(new FragmentFirstStep());
                } else if (this.status.equals("S2")) {
                    nextFragment(new FragmentSecondStep());
                } else if (this.status.equals("S3")) {
                    nextFragment(new FragmentThirdStep());
                } else if (this.status.equals("S4")) {
                    nextFragment(new FragmentFourStep());
                } else if (this.status.equals("S5")) {
                    nextFragment(new FragmentFiveStep());
                } else if (this.status.equals("S6")) {
                    nextFragment(new FragmentSixStep());
                } else if (this.status.equals("S7")) {
                    nextFragment(new FragmentSevenStep());
                } else if (this.status.equals("S8")) {
                    nextFragment(new FragmentEightStep());
                } else if (this.status.equals("S9")) {
                    nextFragment(new FragmentNineStep());
                } else if (this.status.equals("S10")) {
                    nextFragment(new FragmentTenStep());
                } else if (this.status.equals("S11")) {
                    nextFragment(new FragmentElevenStep());
                } else if (this.status.equals("S12")) {
                    nextFragment(new FragmentTwelfthStep());
                } else if (this.status.equals("S13")) {
                    nextFragment(new FragmentThirteenthStep());
                } else if (this.status.equals("S14")) {
                    nextFragment(new FragmentFourteenStep());
                } else if (this.status.equals("S15")) {
                    nextFragment(new FragmentFifteenStep());
                } else if (this.status.equals("FINISH")) {
                } else if (this.status.equals("FAIL")) {
                    startAnimation();
                }
                currentStatus = status;
            }
            if (!startAsynctaskStatus) {
                return;
            }
            MainActivity.this.getStatus();
        }
    }

    public class SendUserInfo extends AsyncTask<Void, Void, Void> {
        public SendUserInfo() {}

        protected Void doInBackground(Void... paramVarArgs) {
            DefaultHttpClient localDefaultHttpClient = new DefaultHttpClient();
            HttpGet localHttpGet = new HttpGet(url);
            try {
                localDefaultHttpClient.execute(localHttpGet);
                return null;
            }
            catch (ClientProtocolException localClientProtocolException) {
                Log.d("QuestGameException", Log.getStackTraceString(localClientProtocolException));
            }
            catch (IOException localIOException) {
                Log.d("QuestGameException", Log.getStackTraceString(localIOException));
            }
            return null;
        }

        protected void onPostExecute(Void paramVoid)
        {
            super.onPostExecute(paramVoid);
        }
    }

    @Override
    public int getCountCombo() {
        return comboCount;
    }

    @Override
    public void setCountCombo() {
        comboCount++;
    }

    @Override
    public void setZeroCountCombo() {
        comboCount = 0;
    }

    @Override
    public void changeTimer(TextView textView, View view) {
        this.currentView = view;

        if (textView!=null) {
            customTimer.setTextView(textView);
            customTimer.startTimer();
        } else {
            customTimer.stopTimer();
        }
    }

    @Override
    public void resetTimer() {
        customTimer.setTime(3*60);
    }

    @Override
    public void stopTimer() {
        customTimer.stopTimer();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mediaPlayer.stop();
        startAsynctaskStatus = false;
    }

    private void startAnimation() {
        startAsynctaskStatus = false;
        Animation animation = new AlphaAnimation(Float.valueOf("0.8"), 0);
        animation.setDuration(1000 * 15);
        animation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                prepareMediaPlayer(MainActivity.this);
                mediaPlayer.start();
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                mediaPlayer.stop();
                currentView.setVisibility(View.GONE);
                timerTask = new TimerTask() {

                    @Override
                    public void run() {
                        finish();
                    }
                };
                Timer timer = new Timer();
                timer.schedule(timerTask, 1000 * 60 * 3);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        currentView.startAnimation(animation);
    }
    public MediaPlayer prepareMediaPlayer(Context context) {
        try {
            mediaPlayer.reset();
            AssetFileDescriptor descriptor = context.getAssets().openFd("profilactics.mp3");
            mediaPlayer.setDataSource(descriptor.getFileDescriptor(),
                    descriptor.getStartOffset(), descriptor.getLength());
            descriptor.close();
            mediaPlayer.prepare();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return mediaPlayer;
    }
}
