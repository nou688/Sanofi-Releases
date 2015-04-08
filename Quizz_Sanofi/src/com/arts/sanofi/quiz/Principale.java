package com.arts.sanofi.quiz;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.arts.sanofi.quiz.ui.PasswordDialogFragment;
import com.arts.sanofi.quiz.ui.PasswordDialogFragment.PasswordDialogListener;

public class Principale extends SanofiActivity implements OnClickListener,
        PasswordDialogListener {

    private static final int NUMBER_OF_CLICK_FOR_HIDDEN_MENU = 3;

    private RelativeLayout mMainLayout;
    private ImageView mSanofiImage;

    private Handler mHandler;
    private int mClickCounter = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.first_page);
        mainLayoutInit();

        mSanofiImage = (ImageView) findViewById(R.id.sanofi_image);

        mSanofiImage.setOnClickListener(this);
        mHandler = new Handler();
    }

    @Override
    public void onBackPressed() {
        // do nothing
    }

    public void mainLayoutInit() {
        mMainLayout = (RelativeLayout) findViewById(R.id.main_layout);
        mMainLayout.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
        case R.id.sanofi_image:
            sanofiImageClickPerform();
            break;
        case R.id.main_layout:
            Intent intent = new Intent(Principale.this, Questions.class); //
            startActivity(intent);
            break;
        default:
            break;
        }

    }

    private void sanofiImageClickPerform() {
        mHandler.removeCallbacksAndMessages(null);
        mClickCounter++;
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