package jp.local.yukichan.mmsp.views;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import jp.local.yukichan.mmsp.R;
import timber.log.Timber;

/**
 * Keyboard View
 */
public class OctaveKeyboardView extends FrameLayout {

    private Context mContext;
    private int mOctave = 0;

    private ImageView mKey1; // C
    private ImageView mKey2; // CS
    private ImageView mKey3; // D
    private ImageView mKey4; // DS
    private ImageView mKey5; // E
    private ImageView mKey6; // F
    private ImageView mKey7; // FS
    private ImageView mKey8; // G
    private ImageView mKey9; // GS
    private ImageView mKey10; // A
    private ImageView mKey11; // AS
    private ImageView mKey12; // B

    private List<ImageView> mWhiteKeyList = new ArrayList<>();

    private List<OnKeyListener> mOnKeyListeners = new CopyOnWriteArrayList<>();

    private OnTouchListener mOnTouchListener = new OnTouchListenerImpl();

    private OnLongClickListener mOnLongClickListener = new OnLongClickListenerImpl();

    private List<Integer> mSelectedKeyNo = new CopyOnWriteArrayList<>();

    public interface OnKeyListener {
        void onKeyPressed(int octave, int keyNo);

        void onKeyLongPressed(int octave, int keyNo);

        void onKeyReleased(int octave, int keyNo);
    }

    public OctaveKeyboardView(Context context) {
        super(context);
        initialize(context);
    }

    public OctaveKeyboardView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initialize(context);
    }

    public OctaveKeyboardView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initialize(context);
    }

    public void setOctave(int octave) {
        Timber.i("setOctave(octave=%d)", octave);
        mOctave = octave;
    }

    public void addSelectedKeyNo(Integer keyNo) {
        Timber.i("addSelectedKeyNo(keyNo=%d)", keyNo);
        mSelectedKeyNo.add(keyNo);
    }

    public void removeSelectedKeyNo(Integer keyNo) {
        Timber.i("removeSelectedKeyNo(keyNo=%d)", keyNo);
        if (mSelectedKeyNo.contains(keyNo)) {
            mSelectedKeyNo.remove(keyNo);
        }
    }

    public boolean isSelectedKeyNo(Integer keyNo) {
        Timber.i("isSelectedKeyNo(keyNo=%d)", keyNo);
        return mSelectedKeyNo.contains(keyNo);
    }

    public void setOnKeyListener(OnKeyListener listener) {
        mOnKeyListeners.add(listener);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        Timber.i("onDraw");
        Timber.i("onDraw: childCound=%d", getChildCount());
        ViewGroup rootView = (ViewGroup) getChildAt(0);
        for (int i = 0; i < rootView.getChildCount(); i++) {
            View childView = rootView.getChildAt(i);
            if (!(childView instanceof ImageView)) {
                Timber.i("onDraw: childView is not ImageView");
                continue;
            }
            ImageView view = (ImageView) childView;

            int keyNo = getKeyNoFromViewId(view.getId());
            if (keyNo == -1) {
                Timber.i("onDraw: keyNo == -1");
                continue;
            }

            Timber.i("onDraw: keyNo=%d", keyNo);
            if (isSelectedKeyNo(keyNo)) {
                int color;
                if (mWhiteKeyList.contains(view)) {
                    color = Color.parseColor("#550000ff");
                } else {
                    color = Color.parseColor("#990000ff");
                }
                view.setColorFilter(color);
//                view.setAlpha(0.5f);
//                view.setBackgroundColor(Color.RED);
            } else {
                view.clearColorFilter();
//                view.setAlpha(1f);
            }
        }
    }

    private void initialize(Context context) {
        Timber.i("initialize");
        mContext = context;
        LayoutInflater.from(context).inflate(R.layout.layout_octave_keyboard, this);
        initializeViews();
    }

    private void initializeViews() {
        Timber.i("initializeViews");
        mKey1 = (ImageView) findViewById(R.id.key1);
        mKey2 = (ImageView) findViewById(R.id.key2);
        mKey3 = (ImageView) findViewById(R.id.key3);
        mKey4 = (ImageView) findViewById(R.id.key4);
        mKey5 = (ImageView) findViewById(R.id.key5);
        mKey6 = (ImageView) findViewById(R.id.key6);
        mKey7 = (ImageView) findViewById(R.id.key7);
        mKey8 = (ImageView) findViewById(R.id.key8);
        mKey9 = (ImageView) findViewById(R.id.key9);
        mKey10 = (ImageView) findViewById(R.id.key10);
        mKey11 = (ImageView) findViewById(R.id.key11);
        mKey12 = (ImageView) findViewById(R.id.key12);

        mWhiteKeyList.addAll(Arrays.asList(mKey1, mKey3, mKey5, mKey6, mKey8, mKey10, mKey12));

        mKey1.setOnTouchListener(mOnTouchListener);
        mKey2.setOnTouchListener(mOnTouchListener);
        mKey3.setOnTouchListener(mOnTouchListener);
        mKey4.setOnTouchListener(mOnTouchListener);
        mKey5.setOnTouchListener(mOnTouchListener);
        mKey6.setOnTouchListener(mOnTouchListener);
        mKey7.setOnTouchListener(mOnTouchListener);
        mKey8.setOnTouchListener(mOnTouchListener);
        mKey9.setOnTouchListener(mOnTouchListener);
        mKey10.setOnTouchListener(mOnTouchListener);
        mKey11.setOnTouchListener(mOnTouchListener);
        mKey12.setOnTouchListener(mOnTouchListener);

        mKey1.setOnLongClickListener(mOnLongClickListener);
        mKey2.setOnLongClickListener(mOnLongClickListener);
        mKey3.setOnLongClickListener(mOnLongClickListener);
        mKey4.setOnLongClickListener(mOnLongClickListener);
        mKey5.setOnLongClickListener(mOnLongClickListener);
        mKey6.setOnLongClickListener(mOnLongClickListener);
        mKey7.setOnLongClickListener(mOnLongClickListener);
        mKey8.setOnLongClickListener(mOnLongClickListener);
        mKey9.setOnLongClickListener(mOnLongClickListener);
        mKey10.setOnLongClickListener(mOnLongClickListener);
        mKey11.setOnLongClickListener(mOnLongClickListener);
        mKey12.setOnLongClickListener(mOnLongClickListener);

        setWillNotDraw(false);
    }

    private void notifyKeyPress(int octave, int keyNo) {
        Timber.i("notifyKeyPress: octave=%d, keyNo=%d", octave, keyNo);
        for (OnKeyListener listener : mOnKeyListeners) {
            listener.onKeyPressed(octave, keyNo);
        }
    }

    private void notifyKeyLongPress(int octave, int keyNo) {
        Timber.i("notifyKeyLongPress: octave=%d, keyNo=%d", octave, keyNo);
        for (OnKeyListener listener : mOnKeyListeners) {
            listener.onKeyLongPressed(octave, keyNo);
        }
    }

    private void notifyKeyRelease(int octave, int keyNo) {
        Timber.i("notifyKeyRelease: octave=%d, keyNo=%d", octave, keyNo);
        for (OnKeyListener listener : mOnKeyListeners) {
            listener.onKeyReleased(octave, keyNo);
        }
    }

    private int getKeyNoFromViewId(int keyId) {
        Timber.i("getKeyNoFromViewId(keyId=%d)", keyId);

        switch (keyId) {
            case R.id.key1:
                return 1;
            case R.id.key2:
                return 2;
            case R.id.key3:
                return 3;
            case R.id.key4:
                return 4;
            case R.id.key5:
                return 5;
            case R.id.key6:
                return 6;
            case R.id.key7:
                return 7;
            case R.id.key8:
                return 8;
            case R.id.key9:
                return 9;
            case R.id.key10:
                return 10;
            case R.id.key11:
                return 11;
            case R.id.key12:
                return 12;
            default:
                return -1;
        }
    }

    private class OnTouchListenerImpl implements OnTouchListener {

        @Override
        public boolean onTouch(View view, MotionEvent motionEvent) {
            Timber.i("onTouch called");

            int action = motionEvent.getActionMasked();
            switch (action) {
                case MotionEvent.ACTION_DOWN:
                    actionDown((ImageView) view);
                    break;
                case MotionEvent.ACTION_UP:
                case MotionEvent.ACTION_CANCEL:
                    actionUp((ImageView) view);
                    break;
                default:
                    break;
            }

            return false;
        }

        private void actionDown(ImageView view) {
            Timber.i("actionDown");

            if (mWhiteKeyList.contains(view)) {
                view.setImageResource(R.drawable.whitekey_pressed);
            } else {
                view.setImageResource(R.drawable.blackkey_pressed);
            }

            int keyNo = getKeyNoFromViewId(view.getId());
            OctaveKeyboardView.this.notifyKeyPress(mOctave, keyNo);
        }

        private void actionUp(ImageView view) {
            Timber.i("actionUp");

            if (mWhiteKeyList.contains(view)) {
                view.setImageResource(R.drawable.whitekey);
            } else {
                view.setImageResource(R.drawable.blackkey);
            }

            int keyNo = getKeyNoFromViewId(view.getId());
            OctaveKeyboardView.this.notifyKeyRelease(mOctave, keyNo);
        }
    }

    private class OnLongClickListenerImpl implements OnLongClickListener {

        @Override
        public boolean onLongClick(View view) {
            Timber.i("onLongClick");
            int keyNo = getKeyNoFromViewId(view.getId());
            OctaveKeyboardView.this.notifyKeyLongPress(mOctave, keyNo);
            return true;
        }
    }
}
