package com.arts.sanofi.app;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

public class MauxEstomacActivity extends CounterActivity implements
        OnClickListener {

    private RelativeLayout mMainLayout;
    private LinearLayout mButtonsLayout;
    private ImageView mExtincteur;
    private ImageView mSanofiLogo;

    private ImageView mBt1, mBt2, mBt3, mBt4, mBt5, mBt6;
    private ImageView mImg1, mImg2, mImg3, mImg4, mImg5, mImg6;

    private int mScreenIndex = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        mAppId = AppsActivity.SEQUENCE_MAUX_ESTOMAC_ID;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.maux_estomac_layout);
        initViews();
    }

    private void initViews() {
        mMainLayout = (RelativeLayout) findViewById(R.id.me_main_layout);
        mMainLayout.setOnClickListener(this);

        mButtonsLayout = (LinearLayout) findViewById(R.id.me_content_layout);

        mExtincteur = (ImageView) findViewById(R.id.ext_button);
        mExtincteur.setOnClickListener(this);

        mSanofiLogo = (ImageView) findViewById(R.id.sanofi_logo);

        mBt1 = (ImageView) findViewById(R.id.bt_ic1);
        mBt1.setOnClickListener(this);
        mBt2 = (ImageView) findViewById(R.id.bt_ic2);
        mBt2.setOnClickListener(this);
        mBt3 = (ImageView) findViewById(R.id.bt_ic3);
        mBt3.setOnClickListener(this);
        mBt4 = (ImageView) findViewById(R.id.bt_ic4);
        mBt4.setOnClickListener(this);
        mBt5 = (ImageView) findViewById(R.id.bt_ic5);
        mBt5.setOnClickListener(this);
        mBt6 = (ImageView) findViewById(R.id.bt_ic6);
        mBt6.setOnClickListener(this);

        mImg1 = (ImageView) findViewById(R.id.img_1);
        mImg2 = (ImageView) findViewById(R.id.img_2);
        mImg3 = (ImageView) findViewById(R.id.img_3);
        mImg4 = (ImageView) findViewById(R.id.img_4);
        mImg5 = (ImageView) findViewById(R.id.img_5);
        mImg6 = (ImageView) findViewById(R.id.img_6);

    }

    @Override
    public void onBackPressed() {
        switch (mScreenIndex) {
        case 0:
            finish();
            break;
        case 1:
            configureMainScreen();
            break;
        case 2:
            configureScreen1();
            break;

        default:
            break;
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
        case R.id.me_main_layout:
            mainLayoutClickPerform();
            break;
        case R.id.ext_button:
            extincteurClickPerform();
            break;
        case R.id.bt_ic1:
            bt1ClickPerform();
            break;
        case R.id.bt_ic2:
            bt2ClickPerform();
            break;
        case R.id.bt_ic3:
            bt3ClickPerform();
            break;
        case R.id.bt_ic4:
            bt4ClickPerform();
            break;
        case R.id.bt_ic5:
            bt5ClickPerform();
            break;
        case R.id.bt_ic6:
            bt6ClickPerform();
            break;
        default:
            break;
        }

    }

    private void mainLayoutClickPerform() {
        configureScreen1();
    }

    private void extincteurClickPerform() {
        if (mScreenIndex == 1) {
            configureScreen2();
        } else {
            finish();
        }
    }

    private void bt1ClickPerform() {
        mImg1.setVisibility(View.VISIBLE);
    }

    private void bt2ClickPerform() {
        mImg2.setVisibility(View.VISIBLE);
    }

    private void bt3ClickPerform() {
        mImg3.setVisibility(View.VISIBLE);
    }

    private void bt4ClickPerform() {
        mImg4.setVisibility(View.VISIBLE);
    }

    private void bt5ClickPerform() {
        mImg5.setVisibility(View.VISIBLE);
    }

    private void bt6ClickPerform() {
        mImg6.setVisibility(View.VISIBLE);
    }

    private void configureMainScreen() {
        mScreenIndex = 0;
        mMainLayout
                .setBackgroundResource(R.drawable.maux_estomact_background_1);
        mButtonsLayout.setVisibility(View.GONE);
        mExtincteur.setVisibility(View.GONE);
        mSanofiLogo.setVisibility(View.INVISIBLE);
    }

    private void configureScreen1() {
        mScreenIndex = 1;
        mMainLayout
                .setBackgroundResource(R.drawable.maux_estomact_background_2);
        mButtonsLayout.setVisibility(View.VISIBLE);
        mExtincteur.setVisibility(View.VISIBLE);
        mSanofiLogo.setVisibility(View.VISIBLE);
        mBt1.setImageResource(R.drawable.bt_s1);
        mBt2.setImageResource(R.drawable.bt_s2);
        mBt3.setImageResource(R.drawable.bt_s3);
        mBt4.setImageResource(R.drawable.bt_s4);
        mBt5.setImageResource(R.drawable.bt_s5);
        mBt6.setImageResource(R.drawable.bt_s6);

        mImg1.setImageResource(R.drawable.bt1);
        mImg1.setVisibility(View.INVISIBLE);
        mImg2.setImageResource(R.drawable.bt2);
        mImg2.setVisibility(View.INVISIBLE);
        mImg3.setImageResource(R.drawable.bt3);
        mImg3.setVisibility(View.INVISIBLE);
        mImg4.setImageResource(R.drawable.bt4);
        mImg4.setVisibility(View.INVISIBLE);
        mImg5.setImageResource(R.drawable.bt5);
        mImg5.setVisibility(View.INVISIBLE);
        mImg6.setImageResource(R.drawable.bt6);
        mImg6.setVisibility(View.INVISIBLE);
    }

    private void configureScreen2() {
        mScreenIndex = 2;
        mBt1.setImageResource(R.drawable.bt_s21);
        mBt2.setImageResource(R.drawable.bt_s22);
        mBt3.setImageResource(R.drawable.bt_s23);
        mBt4.setImageResource(R.drawable.bt_s24);
        mBt5.setImageResource(R.drawable.bt_s25);
        mBt6.setImageResource(R.drawable.bt_s26);

        mImg1.setImageResource(R.drawable.bt21);
        mImg1.setVisibility(View.INVISIBLE);
        mImg2.setImageResource(R.drawable.bt22);
        mImg2.setVisibility(View.INVISIBLE);
        mImg3.setImageResource(R.drawable.bt23);
        mImg3.setVisibility(View.INVISIBLE);
        mImg4.setImageResource(R.drawable.bt24);
        mImg4.setVisibility(View.INVISIBLE);
        mImg5.setImageResource(R.drawable.bt25);
        mImg5.setVisibility(View.INVISIBLE);
        mImg6.setImageResource(R.drawable.bt26);
        mImg6.setVisibility(View.INVISIBLE);
    }

    @Override
    protected void onDestroy() {
        // we have to call this for use reports
        super.onDestroy();
    }
}
