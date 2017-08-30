package jp.local.yukichan.mmsp.codes;

import java.util.ArrayList;
import java.util.Arrays;

import static jp.local.yukichan.mmsp.codes.CodeConstituent.Tension.FLAT_NINE;
import static jp.local.yukichan.mmsp.codes.CodeConstituent.Tension.NATURAL_ELEVEN;
import static jp.local.yukichan.mmsp.codes.CodeConstituent.Tension.NATURAL_NINE;

/**
 * Created by takamk2 on 17/07/26.
 * <p>
 * The Edit Fragment of Base Class.
 */

public enum CodeConstituent {
    MajorSeventh(new Integer[]{1, 3, 5, 7}, "M7", new Tension[]{NATURAL_NINE, FLAT_NINE, NATURAL_ELEVEN});
    ;

    private ArrayList<Tension> mEnabledTensions;

    public enum Tension {
        FLAT_NINE,
        NATURAL_NINE,
        SHAPE_NINE,
        FLAT_ELEVEN,
        NATURAL_ELEVEN,
        SHAPE_ELEVEN,
        FLAT_THIRTEEN,
        NATURAL_THIRTEEN,
        SHAPE_THIRTEEN,
    }

    public final Integer[] intervals;
    public final String displayName;

    CodeConstituent(Integer[] intervals, String displayName, Tension[] enabledTensions) {
        this.intervals = intervals;
        this.displayName = displayName;
        mEnabledTensions = new ArrayList<>(Arrays.asList(enabledTensions));
    }

    public boolean canSetTension(Tension tension) {
        return mEnabledTensions.contains(tension);
    }
}
