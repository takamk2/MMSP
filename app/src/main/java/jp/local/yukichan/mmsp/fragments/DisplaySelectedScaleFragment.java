package jp.local.yukichan.mmsp.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import jp.local.yukichan.mmsp.R;
import jp.local.yukichan.mmsp.notes.BaseNote;
import jp.local.yukichan.mmsp.scales.Scale;
import jp.local.yukichan.mmsp.scales.ScaleConstituent;
import timber.log.Timber;

public class DisplaySelectedScaleFragment extends Fragment {

    private TextView mTvScaleName;
    private ImageView mIvScaleSelector;

    private OnClickListener mOnClickListener = new OnClickListenerImpl();

    public static DisplaySelectedScaleFragment newInstance() {
        DisplaySelectedScaleFragment fragment = new DisplaySelectedScaleFragment();
        return fragment;
    }

    public DisplaySelectedScaleFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        Timber.i("onAttach called");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_display_selected_scale, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mTvScaleName = (TextView) view.findViewById(R.id.tv_scale_name);
        Scale scale = new Scale.Builder().setRootNote(BaseNote.C).setScaleConstituent(ScaleConstituent.Major).build();
        mTvScaleName.setText(scale.getScaleName());
        mIvScaleSelector = (ImageView) view.findViewById(R.id.iv_scale_selector);
        mIvScaleSelector.setOnClickListener(mOnClickListener);
    }

    private void startScaleSelectorDialog() {
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        Fragment prev = getFragmentManager().findFragmentByTag("dialog");
        if (prev != null) {
            ft.remove(prev);
        }
        ft.addToBackStack(null);
        DialogFragment newFragment = ScaleSelectorDialogFragment.newInstance();
        newFragment.show(ft, "dialog");

    }

    private class OnClickListenerImpl implements OnClickListener {

        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.iv_scale_selector:
                    startScaleSelectorDialog();
                    break;
                default:
                    break;
            }
        }
    }
}
