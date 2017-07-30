package jp.local.yukichan.mmsp.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
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
 * Created by takamk2 on 17/07/30.
 * <p>
 * The Edit Fragment of Base Class.
 */

public class ScaleSelectorDialogFragment extends DialogFragment {

    private ListView mLvScaleList;
    private ScaleListAdapter mScaleListAdapter;
    private List<Scale> mScales = new ArrayList<>();

    static ScaleSelectorDialogFragment newInstance() {
        ScaleSelectorDialogFragment fragment = new ScaleSelectorDialogFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mScaleListAdapter = new ScaleListAdapter(getActivity());
        mScales.add(new Scale.Builder().setRootNote(BaseNote.C).setScaleConstituent(ScaleConstituent.Major).build());
        mScales.add(new Scale.Builder().setRootNote(BaseNote.Cs).setScaleConstituent(ScaleConstituent.Major).build());
        mScales.add(new Scale.Builder().setRootNote(BaseNote.D).setScaleConstituent(ScaleConstituent.Major).build());
        mScales.add(new Scale.Builder().setRootNote(BaseNote.Ds).setScaleConstituent(ScaleConstituent.Major).build());
        mScales.add(new Scale.Builder().setRootNote(BaseNote.E).setScaleConstituent(ScaleConstituent.Major).build());
        mScaleListAdapter.setScales(mScales);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_scale_selector_dialog, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mLvScaleList = (ListView) view.findViewById(R.id.lv_scale_list);
        mLvScaleList.setAdapter(mScaleListAdapter);
        mScaleListAdapter.notifyDataSetChanged();
    }

    /* ------------------------------------------------------------------------------------------ */
    private class ScaleListAdapter extends BaseAdapter {

        private final LayoutInflater mmInflator;
        private ArrayList<Scale> mmScales = new ArrayList<>();

        ScaleListAdapter(Context context) {
            mmInflator = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }

        @Override
        public int getCount() {
            return mmScales.size();
        }

        @Override
        public Scale getItem(int position) {
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
                view = mmInflator.inflate(R.layout.item_scale_list, parent, false);
            }
            Scale scale = getItem(position);
            TextView tvScaleName = (TextView) view.findViewById(R.id.tv_scale_name);
            tvScaleName.setText(scale.getScaleName());
            return view;
        }

        public void setScales(List<Scale> scales) {
            mmScales.clear();
            mmScales.addAll(scales);
        }
    }
}
