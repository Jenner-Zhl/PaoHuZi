package com.paohuzi;

/**
 * Created by  on 2017/9/8.
 */

public class TianKouResult {
    public Men[] mMens = new Men[7];

    public boolean equalsByLastMen(TianKouResult res) {
        int[] a1 = new int[2];
        int[] a2 = new int[2];
        int i = 0;
        for (Zi z : mMens[6].getZi()) {
            if (z == null) break;
            a1[i++] = z.getId();
        }
        i = 0;
        for (Zi z1 : res.mMens[6].getZi()) {
            if (z1 == null) break;
            a2[i++] = z1.getId();
        }

        if ((a1[0] == a2[0] && a1[1] == a2[1]) ||
                (a1[0] == a2[1] && a1[1] == a2[0])) {
            return true;
        }

        return false;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for(Men m : mMens) {
            for (Zi z : m.getZi()) {
                if (z == null) break;
                sb.append(z.getId());
            }
            sb.append(' ');
        }
        return sb.toString();
    }
}
