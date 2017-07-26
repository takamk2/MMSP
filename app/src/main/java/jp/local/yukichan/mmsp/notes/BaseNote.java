package jp.local.yukichan.mmsp.notes;

import android.util.Log;

import java.util.Objects;

/**
 * Created by takamk2 on 17/07/26.
 * <p>
 * The Edit Fragment of Base Class.
 */

public enum BaseNote {
    Empty(Integer.MAX_VALUE, "{ERROR}"),
    C(0, "C"),
    Cs(1, "C#"),
    D(2, "D"),
    Ds(3, "D#"),
    E(4, "E"),
    F(5, "F"),
    Fs(6, "F#"),
    G(7, "G"),
    Gs(8, "G#"),
    A(9, "A"),
    As(10, "A#"),
    B(11, "B"),
    ;

    private static final String LOGTAG = BaseNote.class.getSimpleName();

    private static int OCTAVE_INTERVAL = 12;

    public final Integer noteNo;
    public final String displayName;

    BaseNote(Integer noteNo, String displayName) {
        this.noteNo = noteNo;
        this.displayName = displayName;
    }

    public static BaseNote getNote(BaseNote rootNote, Integer degrees) {
        Log.d(LOGTAG, "getNote(rootNote=" + rootNote + ", degrees=" + degrees.toString() + ")");

        Integer noteNo = rootNote.noteNo + (degrees - 1);
        if (noteNo >= OCTAVE_INTERVAL) {
            noteNo %= OCTAVE_INTERVAL;
        }

        for (BaseNote note : BaseNote.values()) {
            if (note.equals(BaseNote.Empty)) continue;

            Log.d(LOGTAG, "getNote: noteNoA=" + note.noteNo + " noteNoB=" + noteNo);
            if (Objects.equals(note.noteNo, noteNo)) {
                return note;
            }
        }

        return null;
    }
}
