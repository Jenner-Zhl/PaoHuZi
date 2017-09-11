package com.paohuzi;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

public class PaoHuZi {

    private Zi[] mAllZi = new Zi[20];
    private Men[] mAllMen = new Men[7];

    private int[] mData = {12, 12, 12, 12, 17, 17, 17, 20, 20, 10, 10, 9, 8, 7, 7, 6, 5, 4, 3, 2};

    private HashMap<Integer, Integer> mZiAndCount = new HashMap<Integer, Integer>();

    private int mPointerMen;

    private boolean mNeedMaQue;

    private ArrayList<TianKouResult> mResults = new ArrayList<>();

    private void initZi(int[] data) {
        if (data.length != 20) {
            System.err.println("number error");
            return;
        }

        for(int i = 0; i < 20; i++) {
            mAllZi[i] = new Zi(data[i]);
            Integer v = mZiAndCount.get(data[i]);
            if (v == null) {
                mZiAndCount.put(data[i], 1);
            } else {
                mZiAndCount.put(data[i], v + 1);
            }
        }
        for (int i = 0; i<mAllMen.length; i++) {
            mAllMen[i] = new Men();
        }
    }

    /**
     *
     * @return left Zis after mPointerMen
     */
    @SuppressWarnings("unchecked")
    private HashMap<Integer, Integer> findKan() {
        HashMap<Integer, Integer> res = null;
        for (int key : mZiAndCount.keySet()) {
            Integer v = mZiAndCount.get(key);
            if (v > 2) {
                if (v == 4) mNeedMaQue = true;
                System.out.println("findKan: " + v + "¸ö" + key);
                mAllMen[mPointerMen].setZi(key, v);
                mPointerMen++;
                if (res == null) {
                    res = (HashMap<Integer, Integer>) mZiAndCount.clone();
                }
                res.remove(key);
            }
        }
        if (res != null) {
            return res;
        } else {
            return mZiAndCount;
        }
    }

    private boolean TianKou(HashMap<Integer, Integer> ziCount) {
        //System.out.println("TK:" + ziCount);
        boolean res = false;

        if (mPointerMen == 5 && mNeedMaQue) {
            checkLast2Men(ziCount);
        }

        if (mPointerMen == 6) {
            return checkLastMen(ziCount);
        }

        LinkedList<Integer> keys = new LinkedList<>();
        for (int key : ziCount.keySet()) {
            keys.add(key);
        }
        while (keys.size() > 0){
            int key = keys.getFirst();
            keys.remove(Integer.valueOf(key));
            int[] preffercp = new Zi(key).prefferdCPid();
            HashMap<Integer, Integer> zcTemp = (HashMap<Integer, Integer>) ziCount.clone();
            removeZi(zcTemp, key);
            int[] availablecp = findAvailableCp(preffercp, zcTemp);
            if (availablecp[0] > 0) {
                for (int i=0, j=1; i<availablecp.length; i+=2, j+=2) {
                    if(availablecp[i] == 0) {
                        break;
                    }
                    HashMap<Integer, Integer> zc = (HashMap<Integer, Integer>) ziCount.clone();
                    mAllMen[mPointerMen++].setZi(key, availablecp[i], availablecp[j]);
                    removeZi(zc, key, availablecp[i], availablecp[j]);
                    keys.remove(Integer.valueOf(availablecp[i]));
                    keys.remove(Integer.valueOf(availablecp[j]));
                    boolean tk = TianKou(zc);
                    if (tk) res = true;
                    mPointerMen--;
                }
            }
        }

        return res;
    }

    private void removeZi(HashMap<Integer, Integer> zc, int... key) {
        //System.out.println("Remove :" + key.toString() + "zzcc:" + zc);
        for (int k : key) {
            //System.out.println("remove : " + k);
            if (zc.get(k) == 1) {
                zc.remove(k);
            } else if (zc.get(k) > 1) {
                zc.put(k, zc.get(k) - 1);
            }
        }
    }

    private void saveResult(Men[] mAllMen) {
        TianKouResult res = new TianKouResult();
        for (int i=0; i<7; i++) {
            res.mMens[i] = mAllMen[i].deepClone();
        }
        if(!mResults.isEmpty()) {
            for (TianKouResult r : mResults) {
                if (r.equalsByLastMen(res)) {
                    return;
                }
            }
        }

        mResults.add(res);

    }

    private boolean isMenZi(Set<Integer> integers) {
        //System.out.println("isMenZi: " + integers.size());
        int zi = 0;
        for (int i: integers) {
            if ( zi == 0 ) {
                zi = i;
            } else {
                int[] zs = new Zi(zi).prefferdCPid();
                for (int j : zs) {
                    if (i == j) return true;
                }
            }
        }
        return false;
    }

    private int[] findAvailableCp(int[] preffercp, HashMap<Integer, Integer> ziCount) {
        int[] res = new int[12];
        int p = 0;
        //System.out.println("findAV:" + ziCount);
        for(int i=0, j=1; i<preffercp.length; i+=2, j+=2) {
            //System.out.println("findAV:  " + preffercp[i]);
            if (preffercp[i] == preffercp[j]) {
                if (ziCount.get(preffercp[i]) == null || ziCount.get(preffercp[i]) != 2) continue;
            }
            if (ziCount.containsKey(preffercp[i]) && ziCount.containsKey(preffercp[j])) {
                res[p++] = preffercp[i];
                res[p++] = preffercp[j];
            }
        }
        return res;
    }

    private int getZi(HashMap<Integer, Integer> ziCount) {
        for(int key = 1; key < 21; key++) {
            if (ziCount.containsKey(key)) {
                //System.out.println(" -------key: " + key);
                return key;
            }
        }

        return -1;
    }

    private boolean checkLastMen(HashMap<Integer, Integer> ziCount) {
        mAllMen[6].clear();
        if(ziCount.size() == 1) {
            for (int key : ziCount.keySet()) {
                mAllMen[mPointerMen].setZi(key, ziCount.get(key));
            }
            saveResult(mAllMen);
            return true;
        } else {
            if(isMenZi(ziCount.keySet())) {
                int k1 = 0;
                for(int key : ziCount.keySet()) {
                    if (k1 == 0) {
                        k1 = key;
                    } else {
                        mAllMen[mPointerMen].setZi(k1, key, 0);
                    }
                }
                saveResult(mAllMen);
                return true;
            } else {
                return false;
            }
        }
    }


    private boolean checkLast2Men(HashMap<Integer, Integer> ziCount) {
        mAllMen[5].clear();
        mAllMen[6].clear();
        if (ziCount.keySet().size() == 2) {
            int i = 5;
            for(int key : ziCount.keySet()) {
                mAllMen[i++].setZi(key, ziCount.get(key));
            }
            saveResult(mAllMen);
            return true;
        } else if (ziCount.keySet().size() == 3) {
            int k2 = 0, k1a = 0, k1b = 0;
            for (int key : ziCount.keySet()) {
                if (ziCount.get(key) == 2) {
                    k2 = key;
                } else if (k1a == 0) {
                    k1a = key;
                } else {
                    k1b = key;
                }
            }
            if( isMenZi(k1a, k1b)) {
                mAllMen[5].setZi(k2, 2);
                mAllMen[6].setZi(k1a, k1b, 0);

                saveResult(mAllMen);
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    private boolean isMenZi(int k1a, int k1b) {
        Zi z = new Zi(k1a);
        int[] preffer = z.prefferdCPid();
        for(int i : preffer) {
            if(i == k1b) {
                return true;
            }
        }
        return false;
    }

    /*public static void main(String[] args) {
        PaoHuZi phz = new PaoHuZi();
        phz.initZi(phz.mData);
        phz.TianKou(phz.findKan());

        for (TianKouResult res : phz.mResults) {
            System.out.println(res.toString());
        }
    }*/

    /**
     *
     * @param data must be 20 items of Pai ID
     * @return
     */
    public static List<TianKouResult> calculate(int[] data) {
        PaoHuZi ph = new PaoHuZi();
        ph.initZi(data);
        ph.TianKou(ph.findKan());

        for (TianKouResult res : ph.mResults) {
            System.out.println(res.toString());
        }
        return ph.mResults;
    }
}
