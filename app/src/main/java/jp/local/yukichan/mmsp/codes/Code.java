package jp.local.yukichan.mmsp.codes;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

import jp.local.yukichan.mmsp.notes.BaseNote;

/**
 * Created by takamk2 on 17/07/26.
 * <p>
 * The Edit Fragment of Base Class.
 */

public class Code {

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

    private final ConcurrentHashMap<Degree, BaseNote> mNotes = new ConcurrentHashMap();

    private Code(String codeName, BaseNote first, BaseNote third, BaseNote fifth, BaseNote seventh) {
        mCodeName = codeName;
        mNotes.put(Degree.Root, first);
        mNotes.put(Degree.Third, third);
        mNotes.put(Degree.Fifth, fifth);
        mNotes.put(Degree.Seventh, seventh);
    }

    public String getCodeName() {
        return mCodeName;
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
            return new Code(codeName, notes.get(0), notes.get(1), notes.get(2), notes.get(3));
        }
    }
}
