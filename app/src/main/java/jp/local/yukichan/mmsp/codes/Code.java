package jp.local.yukichan.mmsp.codes;

import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

import jp.local.yukichan.mmsp.codes.CodeConstituent.Tension;
import jp.local.yukichan.mmsp.notes.BaseNote;

/**
 * Created by takamk2 on 17/07/26.
 * <p>
 * The Edit Fragment of Base Class.
 */

public class Code {

    public boolean canSetTension(Tension tension) {
        return mConstituent.canSetTension(tension);
    }

    enum Degree {
        Root,
        Third,
        Fifth,
        Seventh,
        Ninth,
        Eleventh,
        Thirteenth,
        ;
    }

    private final String mCodeName;
    private final CodeConstituent mConstituent;

    private final ConcurrentHashMap<Degree, BaseNote> mNotes = new ConcurrentHashMap();

    private Code(String codeName, CodeConstituent constituent, BaseNote rootNote, BaseNote thirdNote, BaseNote fifthNote,
                 BaseNote seventhNote) {
        mCodeName = codeName;
        mConstituent = constituent;
        mNotes.put(Degree.Root, rootNote);
        mNotes.put(Degree.Third, thirdNote);
        mNotes.put(Degree.Fifth, fifthNote);
        mNotes.put(Degree.Seventh, seventhNote);
    }

    public String getCodeName() {
        return mCodeName;
    }

    public String getCodeCategory() {
        return "T"; // TODO: スケールを元にコードのカテゴリを返す
    }

    public String getTensions() {
        return "(9)"; // TODO: Temporary
//        return null;
    }

    public String getOnCode() {
        return "G"; // TODO: Temporary
//        return null;
    }

    public void play() {
        // Todo:
    }

    public static class Builder {

        private BaseNote mmRootNote;
        private CodeConstituent mmConstituent;
        private String mmCodeName;

        public Builder setRootNote(BaseNote rootNote) {
            mmRootNote = rootNote;
            return this;
        }

        public Builder setCodeConstituent(CodeConstituent constituent) {
            mmConstituent = constituent;
            return this;
        }

        public Builder setCodeName(String codeName) {
            mmCodeName = codeName;
            return this;
        }

        public Code build() {
            List<BaseNote> notes = new ArrayList<>();
            for (Integer interval : mmConstituent.intervals) {
                notes.add(BaseNote.getNote(mmRootNote, interval));
            }
            String codeName = mmCodeName;
            if (codeName == null) {
                codeName = notes.get(0).displayName + mmConstituent.displayName;
            }
            return new Code(codeName, mmConstituent, notes.get(0), notes.get(1), notes.get(2), notes.get(3));
        }
    }
}
