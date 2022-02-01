package com.scriptorium.pali.comparator;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

class PaliStringComparatorTest {
    @Test
    void simpleStringOfCharsUA() {
        String[] strings = {"u", "a"};
        List<String> expected = List.of("a", "u");
        List<String> sortedList = Arrays.stream(strings)
                .sorted(new PaliStringComparator())
                .toList();
        Assertions.assertArrayEquals(expected.toArray(), sortedList.toArray());
    }

    @Test
    void simpleStringOfCharsCCH() {
        String[] strings = {"ch", "c"};
        List<String> expected = List.of("c", "ch");
        List<String> sortedList = Arrays.stream(strings)
                .sorted(new PaliStringComparator())
                .toList();
        Assertions.assertArrayEquals(expected.toArray(), sortedList.toArray());
    }

    @Test
    void longStringOfChars() {
        String[] strings = {"u", "a", "ū", "ā", "s", "e", "g", "o", "j"};
        List<String> expected = List.of("a", "ā", "u", "ū", "e", "o", "g", "j", "s");
        List<String> sortedList = Arrays.stream(strings)
                .sorted(new PaliStringComparator())
                .toList();
        Assertions.assertArrayEquals(expected.toArray(), sortedList.toArray());
    }

    @Test
    void longStringWithPairedChars() {
        String[] strings = {"u", "a", "kh", "ū", "ā", "s", "jh", "e", "g", "o", "j"};
        List<String> expected = List.of("a", "ā", "u", "ū", "e", "o", "kh", "g", "j", "jh", "s");
        List<String> sortedList = Arrays.stream(strings)
                .sorted(new PaliStringComparator())
                .toList();
        Assertions.assertArrayEquals(expected.toArray(), sortedList.toArray());
    }

    @Test
    void longStringWithOnlyPairedAndConsonantChars() {
        String[] strings =
                {"ch", "c", "kh", "th", "d", "ṭh", "ñ", "ḍh", "ḍ", "ṭ", "t", "k"};
        List<String> expected =
                List.of("k", "kh", "c", "ch", "ñ", "ṭ", "ṭh", "ḍ", "ḍh", "t", "th", "d");
        List<String> sortedList = Arrays.stream(strings)
                .sorted(new PaliStringComparator())
                .toList();
        Assertions.assertArrayEquals(expected.toArray(), sortedList.toArray());
    }

    @Test
    void longStringWithOnlyCAndOtherChars() {
        String[] strings =
                {"ch", "chā", "ca", "cā", "cuuuu", "ck", "cka", "ckh", "ckp", "ckha", "ckhu",
                        "cjhū", "c", "chu", "chcch", "cchā", "cja", "cjh", "cjūa", "chc",
                        "cbha", "chccu"};
        List<String> expected =
                List.of("c", "ca", "cā", "cuuuu", "ck", "cka", "ckp", "ckh", "ckha", "ckhu",
                        "cchā", "cja", "cjūa", "cjh", "cjhū", "cbha", "ch", "chā", "chu",
                        "chc", "chccu", "chcch");
        List<String> sortedList = Arrays.stream(strings)
                .sorted(new PaliStringComparator())
                .toList();
        Assertions.assertArrayEquals(expected.toArray(), sortedList.toArray());
    }

    @Test
    void compareSimpleStrings() {
        String[] strings = {"seṭṭhatā", "susirarukkho", "seṭṭha"};
        List<String> expected =
                List.of("susirarukkho", "seṭṭha", "seṭṭhatā");
        List<String> sortedList = Arrays.stream(strings)
                .sorted(new PaliStringComparator())
                .toList();
        Assertions.assertArrayEquals(expected.toArray(), sortedList.toArray());
    }

    @Test
    void compareStringsWithBigLetter() {
        String[] strings = {"suSIrarukKhoa", "seṭṭhAtā", "seṭṭHAtāa", "susirarukKho", "SEṭṭha"};
        List<String> expected =
                List.of("susirarukKho", "suSIrarukKhoa", "SEṭṭha", "seṭṭhAtā", "seṭṭHAtāa");
        List<String> sortedList = Arrays.stream(strings)
                .sorted(new PaliStringComparator())
                .toList();
        Assertions.assertArrayEquals(expected.toArray(), sortedList.toArray());
    }

    @Test
    void compareStringWithOneSpace() {
        String[] strings = {"yattha kāmaṃ", "yattha"};
        List<String> expected = List.of("yattha", "yattha kāmaṃ");
        List<String> sortedList = Arrays.stream(strings)
                .sorted(new PaliStringComparator())
                .toList();
        Assertions.assertArrayEquals(expected.toArray(), sortedList.toArray());
    }

    @Test
    void compareStringWithTwoSpaces() {
        String[] strings = {"yattha kāmanipātin", "yattha kāmaṃ", "yattha"};
        List<String> expected = List.of("yattha", "yattha kāmaṃ", "yattha kāmanipātin");
        List<String> sortedList = Arrays.stream(strings)
                .sorted(new PaliStringComparator())
                .toList();
        Assertions.assertArrayEquals(expected.toArray(), sortedList.toArray());
    }

    @Test
    void compareStringWithDash() {
        String[] strings = {"yattha kāmanipātin", "yattha kāmaṃ", "sassamaṇa-brāhmaṇa", "dvija", "dvi-"};
        List<String> expected = List.of("dvi-", "dvija", "yattha kāmaṃ", "yattha kāmanipātin", "sassamaṇa-brāhmaṇa");
        List<String> sortedList = Arrays.stream(strings)
                .sorted(new PaliStringComparator())
                .toList();
        Assertions.assertArrayEquals(expected.toArray(), sortedList.toArray());
    }

    @Test
    void compareStringWithXAndSpace() {
        String[] strings = {"jāta", "jāgaranto", "X jāta", "jātarūpaṃ"};
        List<String> expected = List.of("jāgaranto", "jāta", "X jāta", "jātarūpaṃ");
        List<String> sortedList = Arrays.stream(strings)
                .sorted(new PaliStringComparator())
                .toList();
        Assertions.assertArrayEquals(expected.toArray(), sortedList.toArray());
    }

    @Test
    void compareStringWithXAndDash() {
        String[] strings = {"jāta", "jāgaranto", "X-jāta", "jātarūpaṃ"};
        List<String> expected = List.of("jāgaranto", "jāta", "X-jāta", "jātarūpaṃ");
        List<String> sortedList = Arrays.stream(strings)
                .sorted(new PaliStringComparator())
                .toList();
        Assertions.assertArrayEquals(expected.toArray(), sortedList.toArray());
    }

    @Test
    void compareStringWithSomeHhh() {
        String[] strings = {"jjhātha", "jjuātha", "jjsātha", "jjhātua", "jhātha", "jjsāt", "jhātua", "jjsātv"};
        List<String> expected = List.of("jjuātha", "jjsāt", "jjsātv", "jjsātha", "jjhātua", "jjhātha", "jhātua", "jhātha");
        List<String> sortedList = Arrays.stream(strings)
                .sorted(new PaliStringComparator())
                .toList();
        Assertions.assertArrayEquals(expected.toArray(), sortedList.toArray());
    }

    @Test
    void expectedUncorrectedChar() {
        String[] strings = {"seṭṭhatā", "susirarukkho", "seṭṭHAz"};
        var exception = Assertions.assertThrows(IllegalArgumentException.class, Arrays.stream(strings)
                .sorted(new PaliStringComparator())::toList);

        Assertions.assertEquals("Unknown char: z", exception.getMessage());
    }

    @Test
    @DisplayName("Found bug in production: 'yathā kathaṃ pana' - 'yathābhūtaṃ'")
    void foundBugWithOneSpace() {
        final String FIRST_WORD = "yathā kathaṃ pana";
        final String SECOND_WORD = "yathābhūtaṃ";
        String[] oneStrings = {SECOND_WORD, FIRST_WORD};
        List<String> expected = List.of(FIRST_WORD, SECOND_WORD);
        List<String> oneSortedList = Arrays.stream(oneStrings)
                .sorted(new PaliStringComparator())
                .toList();
        Assertions.assertArrayEquals(expected.toArray(), oneSortedList.toArray());
        String[] reversedStrings = {FIRST_WORD, SECOND_WORD};
        List<String> anotherSortedList = Arrays.stream(reversedStrings)
                .sorted(new PaliStringComparator())
                .toList();
        Assertions.assertArrayEquals(expected.toArray(), anotherSortedList.toArray());
    }
}