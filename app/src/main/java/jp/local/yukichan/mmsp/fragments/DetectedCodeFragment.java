package jp.local.yukichan.mmsp.fragments;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import jp.local.yukichan.mmsp.R;
import jp.local.yukichan.mmsp.activities.MainActivity;
import jp.local.yukichan.mmsp.codes.Code;
import jp.local.yukichan.mmsp.codes.CodeConstituent;
import jp.local.yukichan.mmsp.notes.BaseNote;
import timber.log.Timber;

/**
 * A simple {@link Fragment} subclass.
 */
public class DetectedCodeFragment extends Fragment {

    private final MainActivity.OnModeChangedListener mOnModeChangedListener
            = new OnModeChangedListenerImpl();

    private ListView mLvDetectedCodeList;
    private DetectedCodeListAdapter mDetectedCodeListAdapter;

    private MainActivity mActivity;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        Timber.i("onAttach called");
        if (context instanceof MainActivity) {
            mActivity = (MainActivity) context;
        }
    }

    public DetectedCodeFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mDetectedCodeListAdapter = new DetectedCodeListAdapter(getActivity());
        List<Code> codes = new ArrayList<>();
        codes.add(new Code.Builder()
                .setRootNote(BaseNote.C)
                .setCodeConstituent(CodeConstituent.MajorSeventh)
                .build());
        codes.add(new Code.Builder()
                .setRootNote(BaseNote.D)
                .setCodeConstituent(CodeConstituent.MajorSeventh)
                .build());
        codes.add(new Code.Builder()
                .setRootNote(BaseNote.E)
                .setCodeConstituent(CodeConstituent.MajorSeventh)
                .build());
        codes.add(new Code.Builder()
                .setRootNote(BaseNote.Fs)
                .setCodeConstituent(CodeConstituent.MajorSeventh)
                .build());
        codes.add(new Code.Builder()
                .setRootNote(BaseNote.A)
                .setCodeConstituent(CodeConstituent.MajorSeventh)
                .build());
        mDetectedCodeListAdapter.setCodes(codes);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_detected_code, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mLvDetectedCodeList = (ListView) view.findViewById(R.id.lv_detected_code_list);
        mLvDetectedCodeList.setAdapter(mDetectedCodeListAdapter);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    /* private methods -------------------------------------------------------------------------- */
    private class DetectedCodeListAdapter extends BaseAdapter {

        private final LayoutInflater mmInflator;
        private final List<Code> mmCodes = new ArrayList<Code>();

        DetectedCodeListAdapter(Context context) {
            mmInflator = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }

        @Override
        public int getCount() {
            return mmCodes.size();
        }

        @Override
        public Object getItem(int position) {
            return mmCodes.get(position);
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View view = convertView;
            if (view == null) {
                view = mmInflator.inflate(R.layout.item_detected_code_list, parent, false);
            }

            TextView tvNo = (TextView) view.findViewById(R.id.tv_no);
            TextView tvCodeCategory = (TextView) view.findViewById(R.id.tv_code_category);
            TextView tvCodeName = (TextView) view.findViewById(R.id.tv_code_name);

            Code code = mmCodes.get(position);

            tvNo.setText(String.valueOf(position));
            tvCodeCategory.setText(code.getCodeCategory());
            tvCodeName.setText(code.getCodeName());

            return view;
        }

        public void setCodes(List<Code> codes) {
            mmCodes.clear();
            mmCodes.addAll(codes);
        }
    }

    /* inner classes ---------------------------------------------------------------------------- */
    private class OnModeChangedListenerImpl implements MainActivity.OnModeChangedListener {

        @Override
        public void onModeChanged(MainActivity.Mode mode) {
        }
    }
}
