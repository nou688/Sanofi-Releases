package com.arts.sanofi.app;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;

import com.arts.sanofi.app.ui.PasswordDialogFragment;
import com.arts.sanofi.app.ui.PasswordDialogFragment.PasswordDialogListener;

public class MainActivity extends SanofiActivity implements OnClickListener,
        PasswordDialogListener {

    private static final int NUMBER_OF_CLICK_FOR_HIDDEN_MENU = 3;

    private ImageView b1, b2, b3, b4, b5, sanofi;

    private Handler mHandler;
    private int mClickCounter = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        b1 = (ImageView) findViewById(R.id.ib1);
        b1.setOnClickListener(this);

        b2 = (ImageView) findViewById(R.id.ib2);
        b2.setOnClickListener(this);

        b3 = (ImageView) findViewById(R.id.ib3);
        b3.setOnClickListener(this);

        b4 = (ImageView) findViewById(R.id.ib4);
        b4.setOnClickListener(this);

        b5 = (ImageView) findViewById(R.id.ib5);
        b5.setOnClickListener(this);

        sanofi = (ImageView) findViewById(R.id.sanofi);
        sanofi.setOnClickListener(this);

        mHandler = new Handler();
    }

    @Override
    public void onBackPressed() {
        // do nothing
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
        case R.id.ib1:
            launchApp(AppsActivity.SEQUENCE_HYGIENE_ID);
            break;
        case R.id.ib2:
            launchApp(AppsActivity.SEQUENCE_REGLES_DOULEREUSES_ID);
            break;
        case R.id.ib3:
            Intent intent = new Intent(MainActivity.this,
                    MauxEstomacActivity.class);
            startActivity(intent);
            break;
        case R.id.ib4:
            launchApp(AppsActivity.SEQUENCE_VITAMINE_ID);
            break;
        case R.id.ib5:
            launchApp(AppsActivity.SEQUENCE_DOULEUR_ID);
            break;
        case R.id.sanofi:
            sanofiImageClickPerform();
            break;

        default:
            break;
        }

    }

    private void launchApp(int appId) {
        Intent intent = new Intent(MainActivity.this, AppsActivity.class);
        intent.putExtra(AppsActivity.SEQUENCE_KEY, appId);
        startActivity(intent);
    }

    private void sanofiImageClickPerform() {
        mHandler.removeCallbacksAndMessages(null);
        mClickCounter++;
        Log.v("slim", "counter = " + mClickCounter);
        if (mClickCounter == NUMBER_OF_CLICK_FOR_HIDDEN_MENU) {
            PasswordDialogFragment passwordDialogFragment = PasswordDialogFragment
                    .newInstance(this);
            passwordDialogFragment.show(getFragmentManager(), "");
        }

        mHandler.postDelayed(new Runnable() {

            @Override
            public void run() {
                mClickCounter = 0;
            }
        }, 400);
    }

    @Override
    public void onFinishPassword(String inputText) {
        if (inputText.equals(getResources().getString(R.string.password_text))) {
            try {
                Intent i;
                PackageManager manager = getPackageManager();

                i = manager.getLaunchIntentForPackage("com.softwinner.explore");
                if (i == null) {
                    throw new PackageManager.NameNotFoundException();
                }
                i.addCategory(Intent.CATEGORY_LAUNCHER);
                startActivity(i);
            } catch (PackageManager.NameNotFoundException e) {

            }
        }

    }
}
