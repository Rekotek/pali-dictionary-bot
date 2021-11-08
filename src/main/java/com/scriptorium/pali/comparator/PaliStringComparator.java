package com.scriptorium.pali.comparator;

import org.apache.commons.collections4.map.HashedMap;

import java.util.Comparator;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class PaliStringComparator implements Comparator<String> {
    private static final List<String> PALI_ABC = List.of(
            "a", "ā", "i", "ī", "u", "ū", "e", "o", "ṃ",
            "k", "kh", "g", "gh", "ṅ",
            "c", "ch", "j", "jh", "ñ",
            "ṭ", "ṭh", "ḍ", "ḍh", "ṇ",
            "t", "th", "d", "dh", "n",
            "p", "ph", "b", "bh", "m",
            "y", "r", "l", "ḷ", "v", "s", "h"
    );

    private static final Character PAIR_KH = 'k';
    private static final Character PAIR_GH = 'g';
    private static final Character PAIR_CH = 'c';
    private static final Character PAIR_JH = 'j';
    private static final Character PAIR_T_H = 'ṭ';
    private static final Character PAIR_D_H = 'ḍ';
    private static final Character PAIR_TH = 't';
    private static final Character PAIR_DH = 'd';
    private static final Character PAIR_PH = 'p';
    private static final Character PAIR_BH = 'b';

    private static final Character[] PAIRED_LIST = {
            PAIR_KH, PAIR_GH, PAIR_CH, PAIR_JH,
            PAIR_T_H, PAIR_D_H, PAIR_TH, PAIR_DH,
            PAIR_PH, PAIR_BH
    };

    private static final Map<Character, Integer> PAIRED_CHARS_MAP = new HashedMap<>(PAIRED_LIST.length);

    static {
        for (Character firstSymbol : PAIRED_LIST) {
            PAIRED_CHARS_MAP.put(firstSymbol, PALI_ABC.indexOf("" + firstSymbol) + 1);
        }
    }

    private record CharIndex(boolean isPaired, int index) {
    }

    private CharIndex getDigitalValue(Character current) {
        int index = PALI_ABC.indexOf("" + current);
        if (index < 0) {
            throw new IllegalArgumentException("Unknown char: " + current);
        }
        return new CharIndex(false, index);
    }

    private CharIndex getDigitalValue(Character current, Character next) {
        if (next != 'h') {
            return getDigitalValue(current);
        }
        Integer pair_index = PAIRED_CHARS_MAP.get(current);
        if (pair_index == null) {
            return getDigitalValue(current);
        } else {
            return new CharIndex(true, pair_index);
        }
    }

    @Override
    public int compare(String s1, String s2) {
        String strLower1 = s1.toLowerCase(Locale.ROOT);
        String strLower2 = s2.toLowerCase(Locale.ROOT);
        strLower1 = strLower1.replace("x", "")
                .replaceFirst("^-", "").trim();
        strLower2 = strLower2.replace("x", "")
                .replaceFirst("^-", "").trim();
        int length1 = strLower1.length();
        int length2 = strLower2.length();
        int minLength = Math.min(length1, length2);

        int idx1 = 0;
        int idx2 = 0;
        while ( (idx1 < minLength) || (idx2 < minLength)) {
            char currentChar1 = strLower1.charAt(idx1);
            char currentChar2 = strLower2.charAt(idx2);
            if (isInBothSpecialChar(currentChar1, currentChar2)
            ) {
                idx1++;
                idx2++;
                continue;
            }
            if (currentChar1 == '(' && currentChar2 != '(') return -1;
            if (currentChar1 != '(' && currentChar2 == '(') return 1;
            if (currentChar1 == '-' && currentChar2 != '-') return -1;
            if (currentChar1 != '-' && currentChar2 == '-') return 1;

            CharIndex digitalValue1;
            CharIndex digitalValue2;

            if (length1 - idx1 > 1)  {
                digitalValue1 = getDigitalValue(currentChar1, strLower1.charAt(idx1 + 1));
            } else {
                digitalValue1 = getDigitalValue(currentChar1);
            }
            if (length2 - idx2 > 1) {
                digitalValue2 = getDigitalValue(currentChar2, strLower2.charAt(idx2 + 1));
            } else {
                digitalValue2 = getDigitalValue(currentChar2);
            }

            if (digitalValue1.index != digitalValue2.index) {
                return digitalValue1.index - digitalValue2.index;
            }

            idx1++;
            if (digitalValue1.isPaired) {
                idx1++;
            }
            idx2++;
            if (digitalValue2.isPaired) {
                idx2++;
            }
        }
        return length1 - length2;
    }

    private boolean isInBothSpecialChar(char currentChar1, char currentChar2) {
        return (currentChar1 == ' ' && currentChar2 == ' ')
                || (currentChar1 == '(' && currentChar2 == '(')
                || (currentChar1 == ')' && currentChar2 == ')')
                || (currentChar1 == '-' && currentChar2 == '-');
    }
}
