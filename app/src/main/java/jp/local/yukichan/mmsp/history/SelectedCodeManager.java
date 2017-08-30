package jp.local.yukichan.mmsp.history;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import jp.local.yukichan.mmsp.codes.Code;
import jp.local.yukichan.mmsp.codes.CodeConstituent;
import jp.local.yukichan.mmsp.codes.CodeOptions;
import jp.local.yukichan.mmsp.notes.BaseNote;
import jp.local.yukichan.mmsp.scales.Scale;
import jp.local.yukichan.mmsp.scales.ScaleConstituent;
import timber.log.Timber;

/**
 * Created by takamk2 on 17/08/25.
 * <p>
 * The Edit Fragment of Base Class.
 */

public class SelectedCodeManager {

    private List<SelectedCode> mSelectedCodeList = new ArrayList<>();

    private int mCurrentIndex;
    private List<OnParameterChangedListener> mOnParameterChangedListeners = new CopyOnWriteArrayList<>();

    public interface OnParameterChangedListener {
        void onParameterChanged();
    }

    // DEBUG:
    public void restoreSelectedCodes() {
        SelectedCode selectedCode;
        selectedCode = new SelectedCode();
        selectedCode.setScale(new Scale.Builder().setRootNote(BaseNote.C).setScaleConstituent(ScaleConstituent.Major).build());
        selectedCode.setCode(new Code.Builder().setRootNote(BaseNote.C).setCodeConstituent(CodeConstituent.MajorSeventh).build());
        selectedCode.setCodeOptions(new CodeOptions());
        addNewCode(selectedCode);

        selectedCode = new SelectedCode();
        selectedCode.setScale(new Scale.Builder().setRootNote(BaseNote.C).setScaleConstituent(ScaleConstituent.Major).build());
        selectedCode.setCode(new Code.Builder().setRootNote(BaseNote.F).setCodeConstituent(CodeConstituent.MajorSeventh).build());
        selectedCode.setCodeOptions(new CodeOptions());
        addNewCode(selectedCode);
    }

    public void addNewCode(SelectedCode selectedCode) {
        Timber.i("addNewCode: 1 code=%s count=%d", selectedCode.getCode().getCodeName(), mSelectedCodeList.size());
        mSelectedCodeList.add(selectedCode);
        Timber.i("addNewCode: 2 count=%d", mSelectedCodeList.size());
        int index = mSelectedCodeList.indexOf(selectedCode);
        Timber.i("addNewCode: 3 count=%d", mSelectedCodeList.size());
        selectedCode.setIndex(index);
        Timber.i("addNewCode: 4 count=%d", mSelectedCodeList.size());
        mCurrentIndex = index;
        Timber.i("addNewCode: 5 count=%d", mSelectedCodeList.size());
//        notifyParameterChanged();
        Timber.i("addNewCode: 6 count=%d", mSelectedCodeList.size());
    }

    private void notifyParameterChanged() {
        for (OnParameterChangedListener listener : mOnParameterChangedListeners) {
            listener.onParameterChanged();
        }
    }

    public SelectedCode getSelectedCode(int index) {
        return mSelectedCodeList.get(index);
    }

    public List<SelectedCode> getSelectedCodeList() {
        return mSelectedCodeList;
    }

    public int getCurrentIndex() {
        return mCurrentIndex;
    }

    public void setOnParameterChangedListener(OnParameterChangedListener listener) {
        mOnParameterChangedListeners.add(listener);
    }

    public void removeOnParameterChangedListener(OnParameterChangedListener listener) {
        mOnParameterChangedListeners.remove(listener);
    }
}
