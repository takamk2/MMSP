package jp.local.yukichan.mmsp.scales;

/**
 * Created by takamk2 on 17/07/30.
 * <p>
 * The Edit Fragment of Base Class.
 */

public class Scale {

    private final String mScaleName;

    public Scale(String scaleName) {
        mScaleName = scaleName;
    }

    public String getScale() {
        return mScaleName;
    }
}
