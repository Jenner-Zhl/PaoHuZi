package com.paohuzi;

import java.util.Arrays;

/**
 *
 * Created by zhl on 2017/9/8.
 */

public class Men {
    public static final int FLAG_TI = 1;
    public static final int FLAG_PAO = 2;
    public static final int FLAG_KAN = 3;
    public static final int FLAG_WEI = 4;

    private Zi mZis[] = new Zi[4];
    private int mXi;

    public void setZi(int id, int count) {
        if (count < 5 && count > 0) {
            for(int i=0; i<count; i++) {
                mZis[i] = new Zi(id);
            }
        }
    }

    public void setZi(int z1, int z2, int z3) {
        mZis[0] = new Zi(z1);
        if (z2 > 0) {
            mZis[1] = new Zi(z2);
        }
        if( z3 > 0) {
            mZis[2] = new Zi(z3);
        }
    }

    public Zi[] getZi() {
        return mZis;
    }

    public void clear() {
        for (int i=0; i<4; i++) {
            mZis[i] = null;
        }
    }

    public Men deepClone() {
        Men m = new Men();
        for (int i=0; i < 4; i++) {
            if (mZis[i] == null) {
                break;
            }
            m.mZis[i] = new Zi(mZis[i].getId());
        }

        m.mXi = mXi;

        return m;
    }
}
