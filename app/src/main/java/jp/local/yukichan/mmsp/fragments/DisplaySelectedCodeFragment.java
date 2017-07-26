package jp.local.yukichan.mmsp.fragments;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import jp.local.yukichan.mmsp.R;
import jp.local.yukichan.mmsp.codes.Code;
import jp.local.yukichan.mmsp.codes.CodeConstituent;
import jp.local.yukichan.mmsp.notes.BaseNote;

public class DisplaySelectedCodeFragment extends Fragment {

    private TextView mTvCodeName;
    private TextView mTvOnText;
    private TextView mTvOnCode;
    private TextView mTvTensions;

    public static DisplaySelectedCodeFragment newInstance(String param1, String param2) {
        DisplaySelectedCodeFragment fragment = new DisplaySelectedCodeFragment();
        return fragment;
    }

    public DisplaySelectedCodeFragment() {
        // NOP
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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

        debugDisplayCode(); // TODO: Remove this temporary method
    }

    private void initializeViews(View view) {
        mTvCodeName = (TextView) view.findViewById(R.id.tv_code_name);
        mTvOnText = (TextView) view.findViewById(R.id.tv_on_text);
        mTvOnCode = (TextView) view.findViewById(R.id.tv_on_code);
        mTvTensions = (TextView) view.findViewById(R.id.tv_tensions);
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

        if (code.getTensions() == null && code.getOnCode() == null) {
            mTvOnCode.setText(null);
            mTvTensions.setText(null);

            mTvOnText.setVisibility(View.GONE);
            mTvOnCode.setVisibility(View.GONE);
            mTvTensions.setVisibility(View.GONE);
            return;
        }

        mTvTensions.setText(code.getTensions());
        mTvOnCode.setText(code.getOnCode());

        mTvOnText.setVisibility(View.VISIBLE);
        mTvOnText.setVisibility(View.VISIBLE);
        mTvOnCode.setVisibility(View.VISIBLE);
    }

    private void debugDisplayCode() {
        Code code = new Code.Builder()
                .setRootNote(BaseNote.C)
                .setCodeConstituent(CodeConstituent.MajorSeventh)
                .build();
        Toast.makeText(getActivity(), "codeName=" + code.getCodeName(), Toast.LENGTH_LONG).show();
        updateDisplayCode(code);
        // Todo: 選択済みコード変更通知を受けて表示のupdateを行う
    }
}
