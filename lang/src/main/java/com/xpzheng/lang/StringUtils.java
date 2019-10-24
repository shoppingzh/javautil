package com.xpzheng.lang;

import java.util.ArrayList;
import java.util.List;

public class StringUtils {

    public static final int SERIAL_TYPE_FORWORD = 1 << 0;
    public static final int SERIAL_TYPE_BACKWORD = 1 << 1;
    public static final int SERIAL_TYPE_SAME = 1 << 2;

    public static void main(String[] args) {
        System.out.println(getSerialSubStr("fabcde", 5));
    }
    
    /**
     * 判断字符串的字符是否具有连续性
     * @param str               字符串
     * @param minSerialCount    最小连续次数
     * @return
     */
    public static boolean isStrSerial(String str, int minSerialCount) {
        int count = 0;
        Integer lastDiff = null;
        for (int i = 0; i < str.length() - 1; i++) {
            int diff = str.charAt(i) - str.charAt(i + 1);
            boolean match = Math.abs(diff) <= 1; // 正向连续、反向连续、相同字符都视为连续
            if (match && (lastDiff == null || lastDiff == diff)) {
                count++;
            } else {
                count = 1;
            }
            if (count >= minSerialCount - 1) {
                return true;
            }
            lastDiff = diff;
        }
        return false;
    }
    
    public static List<String> getSerialSubStr(String str, int minSerialCount) {
        if (str == null || minSerialCount <= 0) {
            return null;
        }
        int len = str.length();
        List<String> subStrList = null;
        Integer lastDiff = null;
        int count = 1;
        int serialStartIndex = 0;
        int serialEndIndex = 0;
        for (int i = 0; i < len; i++) {
            int left = i, right = i == len - 1 ? i : i + 1;
            int diff = str.charAt(left) - str.charAt(right);
            boolean match = Math.abs(diff) <= 1;
            int lastCount = count;
            if (match && (lastDiff == null || lastDiff == diff)) {
                // 第一次连续时记录连续的起点
                if (lastCount <= 1) {
                    serialStartIndex = lastDiff == null ? 0 : i - 1;
                }
                count++;
            } else {
                count = 1;
            }
            System.out.println(String.format("i: %d, char: %c, start: %d, end: %d, lastCount: %d, count: %d", i,
                    str.charAt(i), serialStartIndex, serialEndIndex, lastCount, count));
            if (lastCount >= minSerialCount - 1 && ((lastCount > 1 && count <= 1) || i == len - 2)) {
                String subStr = str.substring(serialStartIndex, i + 2);
                if (subStrList == null) {
                    subStrList = new ArrayList<String>();
                }
                subStrList.add(subStr);
            }
            lastDiff = diff;
        }
        return subStrList;
    }

}
