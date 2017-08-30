package jp.local.yukichan.mmsp.codes;

import jp.local.yukichan.mmsp.notes.BaseNote;

/**
 * Created by takamk2 on 17/08/25.
 * <p>
 * The Edit Fragment of Base Class.
 */

public class CodeOptions {

    private final BaseNote mBaseNote;

    enum Degree {
        Ninth,
        Eleventh,
        Thirteenth,
        ;
    }

    public CodeOptions() {
        mBaseNote = BaseNote.C;
    }

    public String getDisplayTension() {
        return "(9, 11)";
    }

    public String getDisplayBaseNote() {
        return "G";
    }

    public BaseNote getBaseNote() {
        return mBaseNote;
    }
}
