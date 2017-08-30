package jp.local.yukichan.mmsp.fragments;


import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import jp.local.yukichan.mmsp.R;
import jp.local.yukichan.mmsp.activities.MainActivity;
import jp.local.yukichan.mmsp.application.MMSPApplication;
import jp.local.yukichan.mmsp.history.SelectedCode;
import jp.local.yukichan.mmsp.history.SelectedCodeManager;
import jp.local.yukichan.mmsp.history.SelectedCodeManager.OnParameterChangedListener;
import timber.log.Timber;

/**
 * A simple {@link Fragment} subclass.
 */
public class HistriesFragment extends Fragment {

    private RecyclerView mRvHistries;
    private RecyclerAdapter mAdapter;
    private MainActivity mActivity;
    private MMSPApplication mApplication;
    private SelectedCodeManager mSelectedCodeManager;
    private OnParameterChangedListener mOnParameterChangedListener = new OnParameterChangedListenerImpl();

    public HistriesFragment() {
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
        mSelectedCodeManager.setOnParameterChangedListener(mOnParameterChangedListener);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_histries, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mRvHistries = (RecyclerView) view.findViewById(R.id.rv_histries);
        mRvHistries.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        mAdapter = new RecyclerAdapter(getActivity(), mSelectedCodeManager, new RecyclerAdapter.OnRecyclerListener() {
                    @Override
                    public void onRecyclerClicked(View v, int position) {
                        v.setBackgroundResource(R.color.colorAccent);
                    }
                });
        mRvHistries.setAdapter(mAdapter);
    }

    /* ------------------------------------------------------------------------------------------ */
    private static class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder> {

        private final SelectedCodeManager mmSelectedCodeManager;

        interface OnRecyclerListener {
            void onRecyclerClicked(View v, int position);
        }

        private LayoutInflater mmInflater;
        private OnRecyclerListener mmListener;

        public RecyclerAdapter(Context context, SelectedCodeManager selectedCodeManager, OnRecyclerListener listener) {
            mmInflater = LayoutInflater.from(context);
            mmSelectedCodeManager = selectedCodeManager;
            mmListener = listener;
        }

        @Override
        public RecyclerAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
            return new ViewHolder(mmInflater.inflate(R.layout.item_history_list, viewGroup, false));
        }

        @Override
        public void onBindViewHolder(ViewHolder viewHolder, final int i) {
            Timber.i("onBindViewHolder: i=%d", i);
            List<SelectedCode> selectedCodes = mmSelectedCodeManager.getSelectedCodeList();
            if (selectedCodes != null && selectedCodes.size() > i && selectedCodes.get(i) != null) {
                viewHolder.tvCodeName.setText(selectedCodes.get(i).getCode().getCodeName());
            }

            int currentIndex = mmSelectedCodeManager.getCurrentIndex();
            if (currentIndex == i) {
                viewHolder.parent.setBackgroundColor(Color.YELLOW);
            }

            viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mmListener.onRecyclerClicked(v, i);
                }
            });

        }

        @Override
        public int getItemCount() {
            List<SelectedCode> selectedCodes = mmSelectedCodeManager.getSelectedCodeList();
            if (selectedCodes != null) {
                return selectedCodes.size();
            } else {
                return 0;
            }
        }

        class ViewHolder extends RecyclerView.ViewHolder {
            View parent;
            TextView tvCodeName;

            public ViewHolder(View itemView) {
                super(itemView);
                parent = itemView;
                tvCodeName = (TextView) itemView.findViewById(R.id.tv_code_name);
            }
        }
    }

    /* inner classed ---------------------------------------------------------------------------- */
    private class OnParameterChangedListenerImpl implements OnParameterChangedListener {

        @Override
        public void onParameterChanged() {
            Timber.i("onParameterChanged: count=%d", mSelectedCodeManager.getSelectedCodeList().size());
            // FIXME: listの更新ができないから一旦保留
//            mAdapter.setSelectedCodes(mSelectedCodeManager.getSelectedCodeList());
            mAdapter.notifyItemInserted(mSelectedCodeManager.getSelectedCodeList().size() - 1);
//            mRvHistries.invalidate();
        }
    }
}
