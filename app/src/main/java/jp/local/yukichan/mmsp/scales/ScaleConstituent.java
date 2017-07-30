package jp.local.yukichan.mmsp.scales;

/**
 * Created by takamk2 on 17/08/06.
 * <p>
 * The Edit Fragment of Base Class.
 */

public enum ScaleConstituent {
    Major(new Integer[]{1, 3, 5, 6, 8, 10, 12}, "Major scale"),
    ;

    public final Integer[] intervals;
    public final String displayName;

    ScaleConstituent(Integer[] intervals, String displayName) {
        this.intervals = intervals;
        this.displayName = displayName;
    }
}
