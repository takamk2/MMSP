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
import jp.local.yukichan.mmsp.notes.BaseNote;
import jp.local.yukichan.mmsp.scales.Scale;
import jp.local.yukichan.mmsp.scales.ScaleConstituent;

/**
 * A simple {@link Fragment} subclass.
 */
public class DetectecScaleFragment extends Fragment {

    private ListView mLiDetectedScale;
    private DetectedScaleAdapter mDetectedScaleAdapter;

    /* constructor ------------------------------------------------------------------------------ */
    public DetectecScaleFragment() {
        // Required empty public constructor
    }

    /* public methods --------------------------------------------------------------------------- */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_detectec_scale, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initializeViews(view);
    }

    private void initializeViews(View view) {
        mLiDetectedScale = (ListView) view.findViewById(R.id.li_detected_scale);

        mDetectedScaleAdapter = new DetectedScaleAdapter(getActivity());
        mLiDetectedScale.setAdapter(mDetectedScaleAdapter);

        // DEBUG:
        List<Scale> scales = new ArrayList<>();
        scales.add(new Scale.Builder().setRootNote(BaseNote.C).setScaleConstituent(ScaleConstituent.Major).build());
        scales.add(new Scale.Builder().setRootNote(BaseNote.D).setScaleConstituent(ScaleConstituent.Major).build());
        scales.add(new Scale.Builder().setRootNote(BaseNote.E).setScaleConstituent(ScaleConstituent.Major).build());
        scales.add(new Scale.Builder().setRootNote(BaseNote.F).setScaleConstituent(ScaleConstituent.Major).build());
        scales.add(new Scale.Builder().setRootNote(BaseNote.G).setScaleConstituent(ScaleConstituent.Major).build());
        scales.add(new Scale.Builder().setRootNote(BaseNote.A).setScaleConstituent(ScaleConstituent.Major).build());
        scales.add(new Scale.Builder().setRootNote(BaseNote.B).setScaleConstituent(ScaleConstituent.Major).build());
        mDetectedScaleAdapter.setScales(scales);
        mDetectedScaleAdapter.notifyDataSetChanged();
    }

    /* inner classes ---------------------------------------------------------------------------- */
    private class DetectedScaleAdapter extends BaseAdapter {

        private final LayoutInflater mmInflator;
        private final List<Scale> mmScales = new ArrayList<>();

        DetectedScaleAdapter(Context context) {
            mmInflator = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
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
                view = mmInflator.inflate(R.layout.item_detected_scale_list, parent, false);
            }

            Scale scale = (Scale) getItem(position);

            TextView txNo = (TextView) view.findViewById(R.id.tx_no);
            TextView txScaleName = (TextView) view.findViewById(R.id.tx_scale_name);

            txNo.setText(String.valueOf(position + 1));
            txScaleName.setText(scale.getScaleName());

            return view;
        }

        public void setScales(List<Scale> scales) {
            mmScales.clear();
            mmScales.addAll(scales);
        }
    }
}
