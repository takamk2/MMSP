package jp.local.yukichan.mmsp.views;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import jp.local.yukichan.mmsp.R;
import timber.log.Timber;

/**
 * Keyboardを表示するView
 */
public class KeyboardView extends LinearLayout {

    /** オクターブの数のデフォルト値 */
    private static final int DEFAULT_OCTAVE_NUM = 1;

    /** オクターブの数 */
    private int mOctaveNum = DEFAULT_OCTAVE_NUM;

    private OctaveKeyboardView.OnKeyListener mOnKeyListener = new OnKeyListenerImpl();

    private List<OnKeyListener> mOnKeyListeners = new CopyOnWriteArrayList<>();

    private List<OctaveKeyboardView> mViewList = null;

    /** Key入力の通知を受け取るListener */
    public interface OnKeyListener {
        void onKeyPressed(int octave, int keyNo);
        void onKeyLongPressed(int octave, int keyNo);
        void onKeyReleased(int octave, int keyNo);
    }

    public KeyboardView(Context context) {
        super(context);
        initialize(context, null);
    }

    public KeyboardView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initialize(context, attrs);
    }

    public KeyboardView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initialize(context, attrs);
    }

    public KeyboardView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        initialize(context, attrs);
    }

    public void setOnKeyListener(OnKeyListener listener) {
        mOnKeyListeners.add(listener);
    }

    public boolean addSelectedKeyNo(int octave, int keyNo) {
        Timber.i("addSelectedKeyNo: octave=%d keyNo=%d", octave, keyNo);

        if (mViewList == null) {
            throw new IllegalStateException("mViewListが存在しない");
        }

        if (octave < 0 || octave > mViewList.size()) {
            throw new IllegalArgumentException("octaveが存在しない");
        }

        OctaveKeyboardView view = mViewList.get(octave);
        view.addSelectedKeyNo(keyNo);
        view.invalidate();

        return true;
    }

    public boolean removeSelectedKeyNo(int octave, int keyNo) {
        Timber.i("removeSelectedKeyNo: octave=%d keyNo=%d", octave, keyNo);

        if (mViewList == null) {
            throw new IllegalStateException("mViewListが存在しない");
        }

        if (octave < 0 || octave > mViewList.size()) {
            throw new IllegalArgumentException("octaveが存在しない");
        }

        OctaveKeyboardView view = mViewList.get(octave);
        view.removeSelectedKeyNo(keyNo);
        view.invalidate();

        return true;
    }

    public boolean isSelectedKeyNo(int octave, int keyNo) {
        Timber.i("isSelectedKeyNo: octave=%d keyNo=%d", octave, keyNo);

        if (mViewList == null) {
            Timber.i("isSelectedKeyNo: mViewListがnull");
            return false;
        }

        if (octave < 0 || octave > mViewList.size()) {
            Timber.i("isSelectedKeyNo: octave範囲外");
            return false;
        }

        OctaveKeyboardView view = mViewList.get(octave);
        return view.isSelectedKeyNo(keyNo);
    }

    private void initialize(Context context, AttributeSet attrs) {
        Timber.i("initialize");

        TypedArray typedArray =
                context.getTheme().obtainStyledAttributes(attrs, R.styleable.KeyboardView, 0, 0);
        try {
            mOctaveNum =
                    typedArray.getInteger(R.styleable.KeyboardView_octaveNum, DEFAULT_OCTAVE_NUM);
        } finally {
            typedArray.recycle();
        }

        ViewGroup viewGroup =
                (ViewGroup) LayoutInflater.from(context).inflate(R.layout.layout_keyboard, this);
        mViewList = new ArrayList<>();
        for (int i = 0; i < mOctaveNum; i++) {
            OctaveKeyboardView octaveKeyboardView = new OctaveKeyboardView(context);
            octaveKeyboardView.setOctave(i);
            octaveKeyboardView.setOnKeyListener(mOnKeyListener);
            viewGroup.addView(octaveKeyboardView);
            mViewList.add(octaveKeyboardView);
        }
    }

    private class OnKeyListenerImpl implements OctaveKeyboardView.OnKeyListener {

        @Override
        public void onKeyPressed(int octave, int keyNo) {
            Timber.i("onKeyPressed(octave=%d, keyNo=%d)", octave, keyNo);
            for (OnKeyListener listener : mOnKeyListeners) {
                listener.onKeyPressed(octave, keyNo);
            }
        }

        @Override
        public void onKeyLongPressed(int octave, int keyNo) {
            Timber.i("onKeyPressed(octave=%d, keyNo=%d)", octave, keyNo);
            for (OnKeyListener listener : mOnKeyListeners) {
                listener.onKeyLongPressed(octave, keyNo);
            }
        }

        @Override
        public void onKeyReleased(int octave, int keyNo) {
            Timber.i("onKeyPressed(octave=%d, keyNo=%d)", octave, keyNo);
            for (OnKeyListener listener : mOnKeyListeners) {
                listener.onKeyReleased(octave, keyNo);
            }
        }
    }
}
