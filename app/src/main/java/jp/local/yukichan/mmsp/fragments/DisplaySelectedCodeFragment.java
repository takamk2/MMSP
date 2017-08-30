package jp.local.yukichan.mmsp.fragments;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import jp.local.yukichan.mmsp.R;
import jp.local.yukichan.mmsp.activities.MainActivity;
import jp.local.yukichan.mmsp.activities.MainActivity.OnModeChangedListener;
import jp.local.yukichan.mmsp.application.MMSPApplication;
import jp.local.yukichan.mmsp.codes.Code;
import jp.local.yukichan.mmsp.codes.CodeConstituent;
import jp.local.yukichan.mmsp.codes.CodeOptions;
import jp.local.yukichan.mmsp.history.SelectedCode;
import jp.local.yukichan.mmsp.history.SelectedCodeManager;
import jp.local.yukichan.mmsp.history.SelectedCodeManager.OnParameterChangedListener;
import jp.local.yukichan.mmsp.notes.BaseNote;
import jp.local.yukichan.mmsp.scales.Scale;
import jp.local.yukichan.mmsp.scales.ScaleConstituent;
import timber.log.Timber;

public class DisplaySelectedCodeFragment extends Fragment {

    private RelativeLayout mRlSelectedCode;
    private TextView mTvCodeName;
    private TextView mTvOnText;
    private TextView mTvOnCode;
    private TextView mTvTensions;
    private ImageView mBtAddPrev;
    private ImageView mBtAddNext;

    private OnLongClickListener mOnLongClickListener = new OnLongClickListenerImpl();

    private MainActivity mActivity = null;

    private final OnModeChangedListener mOnModeChangedListener = new OnModeChangedListenerImpl();
    private MMSPApplication mApplication;
    private SelectedCodeManager mSelectedCodeManager;
    private SelectedCode mSelectedCode;
    private OnParameterChangedListener mOnParameterChangedListener = new OnParameterChangedListenerImpl();
    private OnClickListener mOnClickListener = new OnClickListenerImpl();

    public static DisplaySelectedCodeFragment newInstance(String param1, String param2) {
        DisplaySelectedCodeFragment fragment = new DisplaySelectedCodeFragment();
        return fragment;
    }

    /* public methods --------------------------------------------------------------------------- */
    public DisplaySelectedCodeFragment() {
        // NOP
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        Timber.i("onAttach called");
        if (context instanceof MainActivity) {
            mActivity = (MainActivity) context;
        }
        if (mActivity != null) {
            mApplication = (MMSPApplication) mActivity.getApplication();
        }
        mSelectedCodeManager = mApplication.getSelectedCodeManager();
        mSelectedCode = mSelectedCodeManager.getSelectedCode(mSelectedCodeManager.getCurrentIndex());
        mSelectedCodeManager.setOnParameterChangedListener(mOnParameterChangedListener);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_display_selected_code, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initializeViews(view);

        if (mActivity.getMode() == MainActivity.Mode.DETAIL) {
        } else {
        }
        mActivity.addOnModeChangedListener(mOnModeChangedListener);

        debugDisplayCode(); // TODO: Remove this temporary method
    }

    @Override
    public void onDetach() {
        mSelectedCodeManager.removeOnParameterChangedListener(mOnParameterChangedListener);
        super.onDetach();
    }

    /* private methods -------------------------------------------------------------------------- */
    private void initializeViews(View view) {
        mRlSelectedCode = (RelativeLayout) view.findViewById(R.id.rl_selected_code);
        mTvCodeName = (TextView) view.findViewById(R.id.tv_code_name);
        mTvOnText = (TextView) view.findViewById(R.id.tv_on_text);
        mTvOnCode = (TextView) view.findViewById(R.id.tv_on_code);
        mTvTensions = (TextView) view.findViewById(R.id.tv_tensions);
        mBtAddPrev = (ImageView) view.findViewById(R.id.bt_add_prev);
        mBtAddNext = (ImageView) view.findViewById(R.id.bt_add_next);

        mRlSelectedCode.setOnLongClickListener(mOnLongClickListener);

        mBtAddPrev.setOnClickListener(mOnClickListener);
        mBtAddNext.setOnClickListener(mOnClickListener);

        if (mActivity.getMode() == MainActivity.Mode.DETAIL) {
            mBtAddPrev.setVisibility(View.INVISIBLE);
            mBtAddNext.setVisibility(View.INVISIBLE);
        } else {
            mBtAddPrev.setVisibility(View.VISIBLE);
            mBtAddNext.setVisibility(View.VISIBLE);
        }
    }

    private void updateDisplayCode(Code code) {
        if (code == null) {
            mTvCodeName.setText(null);
            mTvOnCode.setText(null);
            mTvTensions.setText(null);

            mTvOnText.setVisibility(View.GONE);
            mTvOnCode.setVisibility(View.GONE);
            mTvTensions.setVisibility(View.GONE);
            return;
        }

        mTvCodeName.setText(code.getCodeName());

//        if (code.getTensions() == null && code.getOnCode() == null) {
//            mTvOnCode.setText(null);
//            mTvTensions.setText(null);
//
//            mTvOnText.setVisibility(View.GONE);
//            mTvOnCode.setVisibility(View.GONE);
//            mTvTensions.setVisibility(View.GONE);
//            return;
//        }

        mTvTensions.setText(code.getTensions());
        mTvOnCode.setText(code.getOnCode());

        mTvOnText.setVisibility(View.VISIBLE);
        mTvOnText.setVisibility(View.VISIBLE);
        mTvOnCode.setVisibility(View.VISIBLE);
    }

    private void debugDisplayCode() {
        Code code = mSelectedCode.getCode();
        CodeOptions codeOptions = mSelectedCode.getCodeOptions();

        mTvCodeName.setText(code.getCodeName());
        mTvTensions.setText(codeOptions.getDisplayTension());
        mTvOnCode.setText(codeOptions.getDisplayBaseNote());
    }

    private class OnLongClickListenerImpl implements OnLongClickListener {

        @Override
        public boolean onLongClick(View view) {
            mActivity.changeDetailMode();
            return false;
        }
    }

    /* inner classes ---------------------------------------------------------------------------- */
    private class OnModeChangedListenerImpl implements OnModeChangedListener {

        @Override
        public void onModeChanged(MainActivity.Mode mode) {
            if (mode == MainActivity.Mode.DETAIL) {
                mBtAddPrev.setVisibility(View.INVISIBLE);
                mBtAddNext.setVisibility(View.INVISIBLE);
            } else {
                mBtAddPrev.setVisibility(View.VISIBLE);
                mBtAddNext.setVisibility(View.VISIBLE);
            }
        }
    }

    private class OnParameterChangedListenerImpl implements OnParameterChangedListener {

        @Override
        public void onParameterChanged() {
            mSelectedCode =
                    mSelectedCodeManager.getSelectedCode(mSelectedCodeManager.getCurrentIndex());
            debugDisplayCode();
        }
    }

    private class OnClickListenerImpl implements OnClickListener {

        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.bt_add_prev: {
                    break;
                }
                case R.id.bt_add_next: {
                    break;
                }
                default:
                    break;
            }
        }
    }
}
