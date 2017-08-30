package jp.local.yukichan.mmsp.application;

import android.app.Application;

import jp.local.yukichan.mmsp.history.SelectedCodeManager;
import jp.local.yukichan.mmsp.sound.SoundManager;
import timber.log.Timber;

/**
 * Created by takamk2 on 17/08/10.
 * <p>
 * The Edit Fragment of Base Class.
 */

public class MMSPApplication extends Application {

    private SelectedCodeManager mSelectedCodeManager;
    private SoundManager mSoundManager;

    @Override
    public void onCreate() {
        super.onCreate();
        Timber.plant(new Timber.DebugTree());

        mSelectedCodeManager = new SelectedCodeManager();
        mSelectedCodeManager.restoreSelectedCodes();

        mSoundManager = new SoundManager(getApplicationContext());
    }

    public SoundManager getSoundManager() {
        return mSoundManager;
    }

    public SelectedCodeManager getSelectedCodeManager() {
        return mSelectedCodeManager;
    }
}
