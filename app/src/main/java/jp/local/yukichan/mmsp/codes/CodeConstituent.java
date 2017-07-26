package jp.local.yukichan.mmsp.codes;

/**
 * Created by takamk2 on 17/07/26.
 * <p>
 * The Edit Fragment of Base Class.
 */

public enum CodeConstituent {
    MajorSeventh(new Integer[]{1, 3, 5, 7}, "M7"),
    ;

    public final Integer[] intervals;
    public final String displayName;

    CodeConstituent(Integer[] intervals, String displayName) {
        this.intervals = intervals;
        this.displayName = displayName;
    }
}
