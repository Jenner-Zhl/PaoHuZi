package com.paohuzi;

/**
 *
 * Created by zhl on 2017/9/8.
 */

public class Zi {
    public static final String[] ZI = {
      "0", "一", "二", "三", "四", "五", "六", "七", "八", "九", "十",
            "壹", "贰", "叁", "肆", "伍", "陆", "柒", "捌", "玖", "拾"
    };
    private int mId;

    public static final int RED = 0xffff0000;
    public static final int BLACK = 0xff000000;
    private int mColor;

    private Zi() {}
    public Zi(int id) {
        mId = id;
        int i = id % 10;
        if (i == 2 || i == 7 || i == 0) {
            mColor = RED;
        } else {
            mColor = BLACK;
        }
    }

    public boolean equals(Zi z) {
        if (z != null && z.mId == mId) {
            return true;
        } else {
            return false;
        }
    }

    public int getId() {
        return mId;
    }

    /**
     *
     * @return preffered couple to build a Men. list couple by couple in the array.
     * e.g. res[0] & res[1], res[2] & res[3]
     */
    public int[] prefferdCPid() {
        int c = 2;
        int t = mId % 10;
        if (t == 0) t = 10; // 十、拾
        if (t == 1) {
            c += 1;
        } else if (t == 10 || t == 9) {
            c += 2;
        } else if ( t == 7) {
            c += 4;
        } else {
            c += 3;
        }
        int res[] = new int[c * 2];
        res[0] = (mId + 10) % 20;
        if (res[0] == 0) res[0] = 20;
        res[1] = res[0];
        res[2] = res[0];
        res[3] = mId;
        int i = 4;
        if (t > 1 && t < 10) {
            res[i++] = mId - 1;
            res[i++] = mId + 1;
        }
        if ( t > 2) {
            res[i++] = mId - 2;
            res[i++] = mId - 1;
        }
        if (t < 9) {
            res[i++] = mId + 1;
            res[i++] = mId + 2;
        }
        if (t == 2) {
            res[i++] = mId + 5;
            res[i++] = mId + 8;
        } else if (t == 7) {
            res[i++] = mId - 5;
            res[i++] = mId + 3;
        } else if (t == 10) {
            res[i++] = mId - 3;
            res[i++] = mId - 8;
        }

        return res;
    }
}
