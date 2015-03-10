package com.quest.game.activities;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.widget.FrameLayout;

import com.quest.game.R;
import com.quest.game.fragments.FragmentFirstStep;
import com.quest.game.interfaces.IFragment;

import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import java.io.IOException;
import java.net.URL;

import javax.xml.parsers.DocumentBuilderFactory;


public class MainActivity extends Activity implements IFragment {
    private Fragment nextFragment;
    private FragmentManager myFragmentManager;
    private Fragment fragmentFirstStep;
    private String url, forthcomingStatus;
    private static int comboCount = 0;

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

        initFragments();

        if (savedInstanceState == null) {
            FragmentTransaction fragmentTransaction = myFragmentManager
                    .beginTransaction();
            fragmentTransaction.add(R.id.container, fragmentFirstStep, "FragmentFirstStep");
            fragmentTransaction.commit();
        }
    }



    private void initFragments() {
        myFragmentManager = getFragmentManager();
        fragmentFirstStep = new FragmentFirstStep();


    }

    @Override
    public void getStatus(Fragment fragment, String forthcomingStatus) {
        this.nextFragment = fragment;
        this.forthcomingStatus = forthcomingStatus;
        new getstatus().execute(new Void[0]);
    }

    @Override
    public void getSendUserInfo(String url) {
        this.url = url;
        new SendUserInfo().execute(new Void[0]);
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

    public class getstatus
            extends AsyncTask<Void, Void, Void>
    {
        String status = "";

        protected Void doInBackground(Void... paramVarArgs)
        {
            try
            {
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
            catch (Exception localException)
            {
                System.out.println("XML Pasing Exception = " + localException);
            }
            return null;
        }

        protected void onPostExecute(Void paramVoid)
        {
            super.onPostExecute(paramVoid);
            if (this.status.equals(forthcomingStatus))
            {
                nextFragment(nextFragment);
                return;
            }
            MainActivity.this.getStatus(nextFragment, forthcomingStatus);
        }
    }

    public class SendUserInfo extends AsyncTask<Void, Void, Void>
    {
        public SendUserInfo() {}

        protected Void doInBackground(Void... paramVarArgs)
        {
            DefaultHttpClient localDefaultHttpClient = new DefaultHttpClient();
            HttpGet localHttpGet = new HttpGet(url);
            try
            {
                localDefaultHttpClient.execute(localHttpGet);
                return null;
            }
            catch (ClientProtocolException localClientProtocolException)
            {
                localClientProtocolException.printStackTrace();
            }
            catch (IOException localIOException)
            {
               localIOException.printStackTrace();
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
}
