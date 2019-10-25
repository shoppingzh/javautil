package com.xpzheng.lang;

import java.util.ArrayList;
import java.util.List;

public class StringUtils {

    public static final int SERIAL_TYPE_FORWORD = 1 << 0;
    public static final int SERIAL_TYPE_BACKWORD = 1 << 1;
    public static final int SERIAL_TYPE_SAME = 1 << 2;
    
    /**
     * 提取字符串中连续的子串<br>
     * 正向连续、反向连续、相同都视为连续，如"abcde", "54321", "yyyyy"都匹配
     * @param str               原字符串
     * @param minSerialCount    最小连续次数
     * @return
     */
    public static List<String> getSerialSubStr(String str, int minSerialCount) {
        return getSerialSubStr(str, minSerialCount, SERIAL_TYPE_BACKWORD | SERIAL_TYPE_FORWORD | SERIAL_TYPE_SAME);
    }

    /**
     * 提取字符串中连续的子串
     * @param str               原字符串
     * @param minSerialCount    最小连续次数
     * @param serialType        连续类型，见{@link #SERIAL_TYPE_FORWORD}{@link #SERIAL_TYPE_BACKWORD}{@link #SERIAL_TYPE_SAME}
     * @return
     */
    public static List<String> getSerialSubStr(String str, int minSerialCount, int serialType) {
        if (str == null) {
            throw new IllegalArgumentException("str is can not be null!");
        }
        if (serialType <= 1) {
            throw new IllegalArgumentException("serialType must be greater than 2!");
        }
        int len = str.length();
        List<String> subStrList = null;
        Integer lastDiff = null;
        int count = 0;
        int serialStartIndex = 0;
        for (int i = 0; i < len - 1; i++) {
            int diff = str.charAt(i) - str.charAt(i + 1);
            boolean match = checkSerialMatch(diff, serialType);
            int lastCount = count;
            if (match && (lastDiff == null || lastDiff == diff)) {
                // 记录第一次连续时的起点
                if (lastCount <= 1) {
                    serialStartIndex = lastDiff == null ? 0 : i - 1;
                }
                count++;
            } else {
                count = 1;
            }
            // 分两种情况讨论：
            // 1. 当前连续次数符合要求且走到了最后一个字符
            // 2. 没有走到最后一个字符且上一轮的连续次数符合要求
            boolean matchEnd = count >= minSerialCount - 1 && (i == len - 2);
            boolean matchPart = lastCount >= minSerialCount - 1 && count <= 1;
            if (matchEnd || matchPart) {
                int serialEndIndex = matchEnd ? (i + 2) : (i + 1);
                String subStr = str.substring(serialStartIndex, serialEndIndex);
                if (subStrList == null) {
                    subStrList = new ArrayList<String>();
                }
                subStrList.add(subStr);
            }
            lastDiff = diff;
        }
        return subStrList;
    }

    /**
     * 根据指定的连续类型判断是否匹配连续性
     * @param diff         字符差值
     * @param serialType   连续类型
     * @return
     */
    private static boolean checkSerialMatch(int diff, int serialType) {
        boolean match = false;
        if ((serialType & SERIAL_TYPE_FORWORD) > 0) {
            match = match || diff == -1;
        }
        if ((serialType & SERIAL_TYPE_BACKWORD) > 0) {
            match = match || diff == 1;
        }
        if ((serialType & SERIAL_TYPE_SAME) > 0) {
            match = match || diff == 0;
        }
        return match;
    }

}
