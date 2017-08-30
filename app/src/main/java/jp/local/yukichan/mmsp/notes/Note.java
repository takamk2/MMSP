package jp.local.yukichan.mmsp.notes;

import timber.log.Timber;

import static jp.local.yukichan.mmsp.notes.BaseNote.A;
import static jp.local.yukichan.mmsp.notes.BaseNote.As;
import static jp.local.yukichan.mmsp.notes.BaseNote.B;
import static jp.local.yukichan.mmsp.notes.BaseNote.C;
import static jp.local.yukichan.mmsp.notes.BaseNote.Cs;
import static jp.local.yukichan.mmsp.notes.BaseNote.D;
import static jp.local.yukichan.mmsp.notes.BaseNote.Ds;
import static jp.local.yukichan.mmsp.notes.BaseNote.E;
import static jp.local.yukichan.mmsp.notes.BaseNote.F;
import static jp.local.yukichan.mmsp.notes.BaseNote.Fs;
import static jp.local.yukichan.mmsp.notes.BaseNote.G;
import static jp.local.yukichan.mmsp.notes.BaseNote.Gs;

/**
 * Note
 *
 * 鍵盤の数だけ音が存在する
 */
public enum Note {
    C0(0, C), Cs0(1, Cs), D0(2, D), Ds0(3, Ds), E0(4, E), F0(5, F), Fs0(6, Fs),
    G0(7, G), Gs0(8, Gs), A0(9, A), As0(10, As), B0(11, B),
    C1(12, C), Cs1(13, Cs), D1(14, D), Ds1(15, Ds), E1(16, E), F1(17, F), Fs1(18, Fs),
    G1(19, G), Gs1(20, Gs), A1(21, A), As1(22, As), B1(23, B),
    C2(24, C), Cs2(25, Cs), D2(26, D), Ds2(27, Ds), E2(28, E), F2(29, F), Fs2(30, Fs),
    G2(31, G), Gs2(32, Gs), A2(33, A), As2(34, As), B2(35, B),
    C3(36, C), Cs3(37, Cs), D3(38, D), Ds3(39, Ds), E3(40, E), F3(41, F), Fs3(42, Fs),
    G3(43, G), Gs3(44, Gs), A3(45, A), As3(46, As), B3(47, B),
    C4(48, C), Cs4(49, Cs), D4(50, D), Ds4(51, Ds), E4(52, E), F4(53, F), Fs4(54, Fs),
    G4(55, G), Gs4(56, Gs), A4(57, A), As4(58, As), B4(59, B);

    private static final int OCTAVE_INTERVAL = 12;

    private final Integer mNo;
    private final BaseNote mBaseNote;
    private final String mFileName;

    Note(Integer no, BaseNote baseNote) {
        mNo = no;
        mBaseNote = baseNote;
        mFileName = name().toLowerCase() + ".wav";
    }

    public static Note getNote(Integer noteNo) {
        Timber.i("getNote(noteNo=%s)", noteNo);

        for (Note note : Note.values()) {
            if (note.mNo.equals(noteNo)) {
                return note;
            }
        }

        return null;
    }

    public static Note getNote(int octave, int keyNo) {
        int noteNo = octave * OCTAVE_INTERVAL + keyNo;
        Timber.i("getNote(octave=%d, keyNote=%d) noteNo=%d", octave, keyNo, noteNo);
        return getNote(noteNo);
    }

    public BaseNote getRootNote() {
        return mBaseNote;
    }

    public String getFileName() {
        return mFileName;
    }
}

