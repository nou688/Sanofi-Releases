package com.arts.sanofi.app;

import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.MediaController;
import android.widget.RelativeLayout;
import android.widget.VideoView;

public class AppsActivity extends CounterActivity implements OnClickListener {

    public static final String SEQUENCE_KEY = "sequence_key";

    public static final int SEQUENCE_HYGIENE_ID = 1;
    private static final int SEQUENCE_HYGIENE_MAX = 5;

    public static final int SEQUENCE_REGLES_DOULEREUSES_ID = 2;
    private static final int SEQUENCE_REGLES_DOULEREUSES_MAX = 5;

    public static final int SEQUENCE_MAUX_ESTOMAC_ID = 3;

    public static final int SEQUENCE_VITAMINE_ID = 4;
    private static final int SEQUENCE_VITAMINE_MAX = 6;

    public static final int SEQUENCE_DOULEUR_ID = 5;
    private static final int SEQUENCE_DOULEUR_MAX = 12;

    private RelativeLayout mMainLayout;

    private LinearLayout mBottomLayout;
    private ImageButton mBottomButton1;
    private ImageButton mBottomButton2;
    private ImageButton mBottomButton3;
    private ImageButton mBottomButton4;
    private ImageButton mBottomButton5;
    private ImageButton mBottomButton6;
    private ImageButton mBottomButton7;

    private int[] mSequenceResoucresArray;
    private int mSequenceId = 0;
    private int mSequenceIndex = 0;
    private int mSequenceMax = 0;

    private ImageView mLotusImage;
    private Animation mLotusAnimation;

    private VideoView mVideoView;

    @Override
    public void onCreate(Bundle savedInstanceState) {

        mAppId = getIntent().getIntExtra(SEQUENCE_KEY, 0);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.apps_activity_layout);

        initViews();

        configureSequence(mAppId);

        mMainLayout
                .setBackgroundResource(mSequenceResoucresArray[mSequenceIndex]);

        triggerLotusAnimation();

    }

    private void initViews() {
        mMainLayout = (RelativeLayout) findViewById(R.id.simple_image_activy_main_layout);
        mMainLayout.setOnClickListener(this);
        mBottomLayout = (LinearLayout) findViewById(R.id.bottom_buttons_layout);

        mBottomButton1 = (ImageButton) findViewById(R.id.rd_button1);
        mBottomButton1.setOnClickListener(this);
        mBottomButton2 = (ImageButton) findViewById(R.id.rd_button2);
        mBottomButton2.setOnClickListener(this);
        mBottomButton3 = (ImageButton) findViewById(R.id.rd_button3);
        mBottomButton3.setOnClickListener(this);
        mBottomButton4 = (ImageButton) findViewById(R.id.rd_button4);
        mBottomButton4.setOnClickListener(this);
        mBottomButton5 = (ImageButton) findViewById(R.id.rd_button5);
        mBottomButton5.setOnClickListener(this);
        mBottomButton6 = (ImageButton) findViewById(R.id.rd_button6);
        mBottomButton6.setOnClickListener(this);
        mBottomButton7 = (ImageButton) findViewById(R.id.rd_button7);
        mBottomButton7.setOnClickListener(this);

        mVideoView = (VideoView) findViewById(R.id.video_view);
        MediaController mediaController = new MediaController(this);
        mediaController.setAnchorView(mVideoView);
        mVideoView.setMediaController(mediaController);
        mVideoView.setVideoURI(Uri.parse("android.resource://"
                + getPackageName() + "/" + R.raw.doliprane));

    }

    private void triggerLotusAnimation() {
        if (mSequenceId == SEQUENCE_HYGIENE_ID) {
            if (mSequenceIndex == 0) {
                addAnimImageView();
            } else {
                mMainLayout.removeView(mLotusImage);
            }
        }
    }

    private void updateBottomButtons() {
        if (mSequenceId == SEQUENCE_REGLES_DOULEREUSES_ID) {
            resetBottomButtons();
            switch (mSequenceIndex) {
            case 0:
                mBottomButton1.setImageResource(R.drawable.rd_b1_r);
                break;
            case 1:
                mBottomButton2.setImageResource(R.drawable.rd_b2_r);
                break;
            case 2:
                mBottomButton3.setImageResource(R.drawable.rd_b3_r);
                break;
            case 3:
                mBottomButton4.setImageResource(R.drawable.rd_b4_r);
                break;
            case 4:
                mBottomButton5.setImageResource(R.drawable.rd_b5_r);
                break;
            case 5:
                mBottomButton6.setImageResource(R.drawable.rd_b6_r);
                break;
            default:
                break;
            }
        }
    }

    private void playVideo() {
        if (mSequenceId == SEQUENCE_DOULEUR_ID) {
            if (mSequenceIndex == 1) {
                mVideoView.setVisibility(View.VISIBLE);
                mVideoView.start();
            } else {
                mVideoView.setVisibility(View.GONE);
                mVideoView.stopPlayback();
            }
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
        case R.id.simple_image_activy_main_layout:
            mainLayoutClickPerform();
            break;
        case R.id.rd_button1:
            rDButton1ClickPerform();
            break;
        case R.id.rd_button2:
            rDButton2ClickPerform();
            break;
        case R.id.rd_button3:
            rDButton3ClickPerform();
            break;
        case R.id.rd_button4:
            rDButton4ClickPerform();
            break;
        case R.id.rd_button5:
            rDButton5ClickPerform();
            break;
        case R.id.rd_button6:
            rDButton6ClickPerform();
            break;
        case R.id.rd_button7:
            rDButton7ClickPerform();
            break;
        default:
            break;
        }

    }

    @Override
    public void onBackPressed() {
        if (mSequenceIndex == 0) {
            finish();
        } else {
            mSequenceIndex--;
            mMainLayout
                    .setBackgroundResource(mSequenceResoucresArray[mSequenceIndex]);
            triggerLotusAnimation();
            updateBottomButtons();
            playVideo();
        }
    }

    private void mainLayoutClickPerform() {
        if (mSequenceIndex < mSequenceMax) {
            mSequenceIndex++;
            mMainLayout
                    .setBackgroundResource(mSequenceResoucresArray[mSequenceIndex]);
        } else {
            switch (mSequenceId) {

            case SEQUENCE_HYGIENE_ID:
                finish();
                break;
            case SEQUENCE_REGLES_DOULEREUSES_ID:
                finish();
                break;
            case SEQUENCE_VITAMINE_ID:
                finish();
                break;
            case SEQUENCE_DOULEUR_ID:
                finish();
                break;
            default:
                break;
            }
        }
        triggerLotusAnimation();
        updateBottomButtons();
        playVideo();
    }

    private void rDButton1ClickPerform() {
        resetBottomButtons();
        mBottomButton1.setImageResource(R.drawable.rd_b1_r);
        mSequenceIndex = 0;
        mMainLayout
                .setBackgroundResource(mSequenceResoucresArray[mSequenceIndex]);
    }

    private void rDButton2ClickPerform() {
        resetBottomButtons();
        mBottomButton2.setImageResource(R.drawable.rd_b2_r);
        mSequenceIndex = 1;
        mMainLayout
                .setBackgroundResource(mSequenceResoucresArray[mSequenceIndex]);
    }

    private void rDButton3ClickPerform() {
        resetBottomButtons();
        mBottomButton3.setImageResource(R.drawable.rd_b3_r);
        mSequenceIndex = 2;
        mMainLayout
                .setBackgroundResource(mSequenceResoucresArray[mSequenceIndex]);
    }

    private void rDButton4ClickPerform() {
        resetBottomButtons();
        mBottomButton4.setImageResource(R.drawable.rd_b4_r);
        mSequenceIndex = 3;
        mMainLayout
                .setBackgroundResource(mSequenceResoucresArray[mSequenceIndex]);
    }

    private void rDButton5ClickPerform() {
        resetBottomButtons();
        mBottomButton5.setImageResource(R.drawable.rd_b5_r);
        mSequenceIndex = 4;
        mMainLayout
                .setBackgroundResource(mSequenceResoucresArray[mSequenceIndex]);
    }

    private void rDButton6ClickPerform() {
        resetBottomButtons();
        mBottomButton6.setImageResource(R.drawable.rd_b6_r);
        mSequenceIndex = 5;
        mMainLayout
                .setBackgroundResource(mSequenceResoucresArray[mSequenceIndex]);
    }

    private void rDButton7ClickPerform() {
        finish();
    }

    private void resetBottomButtons() {
        mBottomButton1.setImageResource(R.drawable.rd_b1_b);
        mBottomButton2.setImageResource(R.drawable.rd_b2_b);
        mBottomButton3.setImageResource(R.drawable.rd_b3_b);
        mBottomButton4.setImageResource(R.drawable.rd_b4_b);
        mBottomButton5.setImageResource(R.drawable.rd_b5_b);
        mBottomButton6.setImageResource(R.drawable.rd_b6_b);
    }

    private void configureSequence(int sequenceId) {

        mSequenceId = sequenceId;
        switch (sequenceId) {
        case SEQUENCE_HYGIENE_ID:
            configureSequenceForHygiene();
            break;
        case SEQUENCE_REGLES_DOULEREUSES_ID:
            configureSequenceForRegle();
            break;
        case SEQUENCE_VITAMINE_ID:
            configureSequenceForVitamines();
            break;
        case SEQUENCE_DOULEUR_ID:
            configureSequenceForDouleurs();

            break;

        default:
            break;
        }

    }

    private void addAnimImageView() {
        mLotusImage = new ImageView(this);
        mLotusImage.setImageResource(R.drawable.lotus);

        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
                350, 360);
        params.leftMargin = 335;
        params.topMargin = 130;
        mMainLayout.addView(mLotusImage, params);

        mLotusAnimation = AnimationUtils.loadAnimation(this, R.anim.translate);
        mLotusImage.startAnimation(mLotusAnimation);
        mLotusImage.setVisibility(View.INVISIBLE);
    }

    private void configureSequenceForHygiene() {
        mSequenceResoucresArray = new int[] { R.drawable.l1, R.drawable.l2,
                R.drawable.l3, R.drawable.l4, R.drawable.l5, R.drawable.l6 };

        mSequenceMax = SEQUENCE_HYGIENE_MAX;
    }

    private void configureSequenceForRegle() {
        mSequenceResoucresArray = new int[] { R.drawable.slide1,
                R.drawable.slide2, R.drawable.slide3, R.drawable.slide4,
                R.drawable.slide5, R.drawable.slide6 };
        mSequenceMax = SEQUENCE_REGLES_DOULEREUSES_MAX;

        mBottomLayout.setVisibility(View.VISIBLE);

    }

    private void configureSequenceForVitamines() {
        mSequenceResoucresArray = new int[] { R.drawable.o1, R.drawable.o2,
                R.drawable.o3, R.drawable.o4, R.drawable.o5, R.drawable.o6,
                R.drawable.o7 };
        mSequenceMax = SEQUENCE_VITAMINE_MAX;
    }

    private void configureSequenceForDouleurs() {
        mSequenceResoucresArray = new int[] { R.drawable.i1, R.drawable.i2,
                R.drawable.i3, R.drawable.i4, R.drawable.i5, R.drawable.i6,
                R.drawable.i7, R.drawable.i8, R.drawable.i9, R.drawable.i10,
                R.drawable.i11, R.drawable.i12, R.drawable.i13 };
        mSequenceMax = SEQUENCE_DOULEUR_MAX;
    }

    @Override
    protected void onDestroy() {
        // we have to call this for use reports
        super.onDestroy();
    }

}