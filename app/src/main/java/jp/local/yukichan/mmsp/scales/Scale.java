package jp.local.yukichan.mmsp.scales;

import java.util.ArrayList;
import java.util.List;

import jp.local.yukichan.mmsp.notes.BaseNote;

/**
 * Created by takamk2 on 17/07/30.
 * <p>
 * The Edit Fragment of Base Class.
 */

public class Scale {

    private final String mScaleName;

    private final List<BaseNote> mNoteList;

    public Scale(String scaleName, List<BaseNote> noteList) {
        mScaleName = scaleName;
        mNoteList = noteList;
    }

    public String getScaleName() {
        return mScaleName;
    }

    public static class Builder {

        private BaseNote mmRootNote;
        private ScaleConstituent mmConstituent;
        private String mmScaleName;

        public Builder setRootNote(BaseNote rootNote) {
            mmRootNote = rootNote;
            return this;
        }

        public Builder setScaleConstituent(ScaleConstituent constituent) {
            mmConstituent = constituent;
            return this;
        }

        public Builder setCodeName(String codeName) {
            mmScaleName = codeName;
            return this;
        }

        public Scale build() {
            List<BaseNote> notes = new ArrayList<>();
            for (Integer interval : mmConstituent.intervals) {
                notes.add(BaseNote.getNote(mmRootNote, interval));
            }
            String scaleName = mmScaleName;
            if (scaleName == null) {
                scaleName = notes.get(0).displayName + " " + mmConstituent.displayName;
            }
            return new Scale(scaleName, notes);
        }
    }
}
