package jp.local.yukichan.mmsp.history;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import jp.local.yukichan.mmsp.codes.Code;
import jp.local.yukichan.mmsp.codes.CodeOptions;
import jp.local.yukichan.mmsp.notes.BaseNote;
import jp.local.yukichan.mmsp.scales.Scale;

/**
 * Created by takamk2 on 17/08/25.
 * <p>
 * The Edit Fragment of Base Class.
 */

public class SelectedCode {

    private int mId = -1;
    private int mIndex = -1;
    private Scale mScale;
    private Code mCode;
    private CodeOptions mCodeOptions;

    private List<SelectedCodeListener> mListeners = new CopyOnWriteArrayList<>();

    interface SelectedCodeListener {
        void onSelectedCodeChanged(int id, Code code);
    }

    public void clear() {
        mId = -1;
        mScale = null;
        mCode = null;
        mCodeOptions = null;
    }

    public void setIndex(int index) {
        mIndex = index;
    }

    public int getIndex() {
        return mIndex;
    }

    public void setScale(Scale scale) {
        mScale = scale;
    }

    public Scale getScale() {
        return mScale;
    }

    public void setCode(Code code) {
        mCode = code;
    }

    private void notifyCodeChanged(int id, Code code) {
        for (SelectedCodeListener listener : mListeners) {
            listener.onSelectedCodeChanged(id, code);
        }
    }

    public Code getCode() {
        return mCode;
    }

    public void setCodeOptions(CodeOptions codeOptions) {
        mCodeOptions = codeOptions;
    }

    public CodeOptions getCodeOptions() {
        return mCodeOptions;
    }

    public List<BaseNote> getNoteList() {
        List<BaseNote> noteList = new ArrayList<>();
        noteList.add(BaseNote.C);
        noteList.add(BaseNote.E);
        noteList.add(BaseNote.G);
        noteList.add(BaseNote.B);
        noteList.add(BaseNote.D);
        noteList.add(BaseNote.F);
        noteList.add(BaseNote.A);
        return noteList;
    }

    public BaseNote getBaseNote() {
        return mCodeOptions.getBaseNote();
    }
}
