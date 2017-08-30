package jp.local.yukichan.mmsp.fragments;


import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import java.util.List;

import jp.local.yukichan.mmsp.R;
import jp.local.yukichan.mmsp.activities.MainActivity;
import jp.local.yukichan.mmsp.application.MMSPApplication;
import jp.local.yukichan.mmsp.codes.Code;
import jp.local.yukichan.mmsp.codes.CodeConstituent;
import jp.local.yukichan.mmsp.history.SelectedCode;
import jp.local.yukichan.mmsp.history.SelectedCodeManager;
import jp.local.yukichan.mmsp.notes.BaseNote;
import timber.log.Timber;

/**
 * A simple {@link Fragment} subclass.
 */
public class CodeDetailFragment extends Fragment {

    private final View.OnClickListener mOnClickLisnter = new OnClickListenerImpl();

    private MainActivity mActivity = null;
    private MMSPApplication mApplication;
    private SelectedCodeManager mSelectedCodeManager;

    private ImageView mIvBackToDetection;
    private RadioGroup mRbTension9th;
    private RadioGroup mRbTension11th;
    private RadioGroup mRbTension13th;
    private LinearLayout mVgBaseNotes;
    private LayoutInflater mInflator;

    public CodeDetailFragment() {
        // Required empty public constructor
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
        mInflator = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_code_detail, container, false);
        view.setVisibility(View.GONE);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initializeViews(view);
    }

    private void initializeViews(View view) {
        mIvBackToDetection = (ImageView) view.findViewById(R.id.iv_back_to_detection);
        mRbTension9th = (RadioGroup) view.findViewById(R.id.rg_tension_9th);
        mRbTension11th = (RadioGroup) view.findViewById(R.id.rg_tension_11th);
        mRbTension13th = (RadioGroup) view.findViewById(R.id.rg_tension_13th);
        mVgBaseNotes = (LinearLayout) view.findViewById(R.id.vg_base_notes);

        SelectedCode selectedCode =
                mSelectedCodeManager.getSelectedCode(mSelectedCodeManager.getCurrentIndex());
        Code code = selectedCode.getCode();

        List<BaseNote> noteList = selectedCode.getNoteList();
        BaseNote baseNote = selectedCode.getBaseNote();

        RadioButton rb1;
        RadioButton rb2;
        RadioButton rb3;
        RadioButton rb4;

        rb1 = (RadioButton) mRbTension9th.getChildAt(0);
        rb2 = (RadioButton) mRbTension9th.getChildAt(1);
        rb3 = (RadioButton) mRbTension9th.getChildAt(2);
        rb4 = (RadioButton) mRbTension9th.getChildAt(3);

        if (code.canSetTension(CodeConstituent.Tension.FLAT_NINE)) {
            rb2.setEnabled(true);
        } else {
            rb2.setEnabled(false);
        }
        if (code.canSetTension(CodeConstituent.Tension.NATURAL_NINE)) {
            rb3.setEnabled(true);
        } else {
            rb3.setEnabled(false);
        }
        if (code.canSetTension(CodeConstituent.Tension.SHAPE_NINE)) {
            rb4.setEnabled(true);
        } else {
            rb4.setEnabled(false);
        }

        rb1 = (RadioButton) mRbTension11th.getChildAt(0);
        rb2 = (RadioButton) mRbTension11th.getChildAt(1);
        rb3 = (RadioButton) mRbTension11th.getChildAt(2);
        rb4 = (RadioButton) mRbTension11th.getChildAt(3);

        if (code.canSetTension(CodeConstituent.Tension.FLAT_ELEVEN)) {
            rb2.setEnabled(true);
        } else {
            rb2.setEnabled(false);
        }
        if (code.canSetTension(CodeConstituent.Tension.NATURAL_ELEVEN)) {
            rb3.setEnabled(true);
        } else {
            rb3.setEnabled(false);
        }
        if (code.canSetTension(CodeConstituent.Tension.SHAPE_ELEVEN)) {
            rb4.setEnabled(true);
        } else {
            rb4.setEnabled(false);
        }
        rb1 = (RadioButton) mRbTension13th.getChildAt(0);
        rb2 = (RadioButton) mRbTension13th.getChildAt(1);
        rb3 = (RadioButton) mRbTension13th.getChildAt(2);
        rb4 = (RadioButton) mRbTension13th.getChildAt(3);

        if (code.canSetTension(CodeConstituent.Tension.FLAT_THIRTEEN)) {
            rb2.setEnabled(true);
        } else {
            rb2.setEnabled(false);
        }
        if (code.canSetTension(CodeConstituent.Tension.NATURAL_THIRTEEN)) {
            rb3.setEnabled(true);
        } else {
            rb3.setEnabled(false);
        }
        if (code.canSetTension(CodeConstituent.Tension.SHAPE_THIRTEEN)) {
            rb4.setEnabled(true);
        } else {
            rb4.setEnabled(false);
        }
        rb1.setChecked(true);

        mVgBaseNotes.removeAllViews();
        for (BaseNote n : noteList) {
            TextView v = (TextView) mInflator.inflate(R.layout.item_base_note, mVgBaseNotes, false);
            v.setText(n.displayName);
            if (n.equals(baseNote)) {
                v.setBackgroundColor(Color.YELLOW);
            } else {
                v.setBackgroundColor(Color.WHITE);
            }
            mVgBaseNotes.addView(v);
        }

        mIvBackToDetection.setOnClickListener(mOnClickLisnter);
    }

    /* inner classes ---------------------------------------------------------------------------- */
    private class OnClickListenerImpl implements View.OnClickListener {

        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.iv_back_to_detection:
                    mActivity.changeDetectionMode();
                    break;
                default:
                    break;
            }
        }
    }
}
