package jp.local.yukichan.mmsp.activities;

import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.BaseAdapter;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import jp.local.yukichan.mmsp.R;
import jp.local.yukichan.mmsp.application.MMSPApplication;
import jp.local.yukichan.mmsp.history.SelectedCode;
import jp.local.yukichan.mmsp.history.SelectedCodeManager;
import timber.log.Timber;

public class MainActivity extends AppCompatActivity {

    private static final String FIND_SCALE = "Scaleを探す →";

    private final List<OnModeChangedListener> mOnModeChangedListeners
            = new CopyOnWriteArrayList<>();

    private Spinner mSpScales;
    private SpinnerAdapter mScalesAdapter;

    private FragmentManager mFragmentManager;

    private Mode mMode;
    private OnItemSelectedListener mOnItemSelectedListener = new OnItemSelectedListenerImpl();

    private MMSPApplication mApplication;

    public interface OnModeChangedListener {
        void onModeChanged(Mode mode);
    }

    public enum Mode {
        DETECTION,
        DETAIL
    }

    /* public methods --------------------------------------------------------------------------- */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mApplication = (MMSPApplication) getApplication();

        Toolbar toolbar = (Toolbar) findViewById(R.id.tool_bar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(null);

        mFragmentManager = getSupportFragmentManager();

        initializeViews();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void addOnModeChangedListener(OnModeChangedListener listener) {
        mOnModeChangedListeners.add(listener);
    }

    public void removeOnModeChangedListener(OnModeChangedListener listener) {
        mOnModeChangedListeners.remove(listener);
    }

    public Mode getMode() {
        return mMode;
    }

    /* private methods -------------------------------------------------------------------------- */
    private void initializeViews() {
        mSpScales = (Spinner) findViewById(R.id.sp_scales);
        mScalesAdapter = new ScalesAdapter();
        mSpScales.setAdapter(mScalesAdapter);
        mSpScales.setOnItemSelectedListener(mOnItemSelectedListener);
    }

    public void changeDetectionMode() {
        Timber.i("changeDetectionMode called");
        mFragmentManager.findFragmentById(R.id.fragment_histories)
                .getView().setVisibility(View.VISIBLE);
        mFragmentManager.findFragmentById(R.id.fragment_detected_code)
                .getView().setVisibility(View.VISIBLE);
        mFragmentManager.findFragmentById(R.id.fragment_code_detail)
                .getView().setVisibility(View.GONE);
        mMode = Mode.DETECTION;
        for (OnModeChangedListener listener : mOnModeChangedListeners) {
            listener.onModeChanged(mMode);
        }
    }

    public void changeDetailMode() {
        Timber.i("changeDetailMode called");
        mFragmentManager.findFragmentById(R.id.fragment_histories)
                .getView().setVisibility(View.GONE);
        mFragmentManager.findFragmentById(R.id.fragment_detected_code)
                .getView().setVisibility(View.GONE);
        mFragmentManager.findFragmentById(R.id.fragment_code_detail)
                .getView().setVisibility(View.VISIBLE);
        mMode = Mode.DETAIL;
        for (OnModeChangedListener listener : mOnModeChangedListeners) {
            listener.onModeChanged(mMode);
        }
    }

    private class ScalesAdapter extends BaseAdapter {

        private final List<String> mmScales = new ArrayList<>();

        ScalesAdapter() {
            mmScales.add("未設定");
            mmScales.add(FIND_SCALE);
            mmScales.add("C♭ major scale");
            mmScales.add("C major scale");
            mmScales.add("C♯ major scale"); // == D♭
            mmScales.add("D♭ major scale");
            mmScales.add("D major scale");
            mmScales.add("E♭ major scale");
            mmScales.add("E major scale");
            mmScales.add("F major scale");
            mmScales.add("F♯ major scale"); // == G♭
            mmScales.add("G♭ major scale");
            mmScales.add("G major scale");
            mmScales.add("A♭ major scale");
            mmScales.add("A major scale");
            mmScales.add("B♭ major scale");
            mmScales.add("B major scale");
        }

        @Override
        public int getCount() {
            return mmScales.size();
        }

        @Override
        public Object getItem(int position) {
            return mmScales.get(position);
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View view = convertView;
            if (view == null) {
                view = getLayoutInflater()
                        .inflate(R.layout.item_spinner_scales, parent, false);
            }

            // TODO: "Scaleを探す"を選択したらScaleDetectorActivityを起動する
            // TODO: Scaleを選択したら、変更してよいかのConfirmを表示する
            // TODO: デフォルト値は"-"とする
            // TODO: "-"が選択されている時は何もできない
            if (position == 1) {
                Timber.d("getView: Launch ScaleDetector");
            } else {
                Timber.d("getView: select scale");
            }

            TextView tvTitle = (TextView) view.findViewById(R.id.title);
            tvTitle.setText(mmScales.get(position));

            return view;
        }

        @Override
        public View getDropDownView(int position, View convertView, ViewGroup parent) {
            View view = super.getDropDownView(position, convertView, parent);
            ((TextView) view).setGravity(Gravity.CENTER);
            return view;
        }
    }

    /* inner classses --------------------------------------------------------------------------- */
    private class OnItemSelectedListenerImpl implements OnItemSelectedListener {

        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            String selected = (String) parent.getAdapter().getItem(position);
            if (selected.equals(FIND_SCALE)) {
                Intent intent = new Intent(MainActivity.this, ScaleActivity.class);
                startActivity(intent);
            }
        }

        @Override
        public void onNothingSelected(AdapterView<?> adapterView) {

        }
    }
}
