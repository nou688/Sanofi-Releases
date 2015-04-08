package com.arts.sanofi.quiz;

import android.app.Activity;
import android.app.KeyguardManager;
import android.content.Context;
import android.os.Bundle;
import android.os.PowerManager;
import android.os.PowerManager.WakeLock;
import android.view.Window;
import android.view.WindowManager;
import android.view.WindowManager.LayoutParams;

public class SanofiActivity extends Activity {

    private PowerManager mPowerManager;
    private WakeLock mWakeLock;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().addFlags(
                WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON
                        | WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD
                        | WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED
                        | WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON);

        // getWindow().getDecorView().setSystemUiVisibility(
        // View.SYSTEM_UI_FLAG_LAYOUT_STABLE
        // | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
        // | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
        // | View.SYSTEM_UI_FLAG_FULLSCREEN // hide status bar
        // | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);

        mPowerManager = (PowerManager) getSystemService(POWER_SERVICE);

        mWakeLock = mPowerManager.newWakeLock(
                PowerManager.SCREEN_BRIGHT_WAKE_LOCK, getClass().getName());

    }

    @Override
    protected void onResume() {
        super.onResume();

        KeyguardManager km = (KeyguardManager) this
                .getSystemService(Context.KEYGUARD_SERVICE);
        final KeyguardManager.KeyguardLock kl = km
                .newKeyguardLock("MyKeyguardLock");
        kl.disableKeyguard();

        mWakeLock.acquire();

        unlockScreen();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mWakeLock.release();
    }

    private void unlockScreen() {

        Window window = this.getWindow();
        window.addFlags(LayoutParams.FLAG_DISMISS_KEYGUARD);
        window.addFlags(LayoutParams.FLAG_SHOW_WHEN_LOCKED);
        window.addFlags(LayoutParams.FLAG_TURN_SCREEN_ON);
    }

}
