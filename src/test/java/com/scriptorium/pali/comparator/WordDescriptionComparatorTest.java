package com.scriptorium.pali.comparator;

import com.scriptorium.pali.entity.WordDescription;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

class WordDescriptionComparatorTest {
    static private WordDescription w1, w2, w3, w4, w5, w6, w7, w8, w9, w10;
    static private WordDescription[] expectedList;

    @BeforeAll
    static void setUp() {
        w1 = WordDescription.builder()
                .pali("ākusāla").simplified("akusala")
                .id(1L)
                .translation("La-la-la")
                .build();
        w2 = WordDescription.builder()
                .pali("akusala").simplified("akusala")
                .id(2L)
                .translation("La-la-la")
                .build();
        w3 = WordDescription.builder()
                .pali("akusāla").simplified("akusala")
                .id(3L)
                .translation("La-la-la")
                .build();
        w4 = WordDescription.builder()
                .pali("āk").simplified("ak")
                .id(4L)
                .translation("La-la-la")
                .build();
        w5 = WordDescription.builder()
                .pali("ākusālayā").simplified("akusalaya")
                .id(5L)
                .translation("La-la-la")
                .build();
        w6 = WordDescription.builder()
                .pali("āk").simplified("ak")
                .id(6L)
                .translation("La-la-la2")
                .build();
        w7 = WordDescription.builder()
                .pali("sabbaṭṭhaka").simplified("sabbatthaka")
                .id(7L)
                .translation("La-la-la2")
                .build();
        w8 = WordDescription.builder()
                .pali("yattha kāmanipātin").simplified("yattha kamanipatin")
                .id(8L)
                .translation("La-la-la2")
                .build();
        w9 = WordDescription.builder()
                .pali("yattha kāmaṃ").simplified("yattha kamam")
                .id(9L)
                .translation("La-la-la2")
                .build();
        w10 = WordDescription.builder()
                .pali("yattha").simplified("yattha")
                .id(10L)
                .translation("La-la-la2")
                .build();

        expectedList = new WordDescription[]{w2, w3, w4, w6, w1, w5, w10, w9, w8, w7};
    }

    @Test
    void testList() {
        WordDescription[] wordList = {w1, w2, w3, w4, w5, w6, w7, w8, w9, w10};
        var orderedList = Arrays.stream(wordList).sorted(new WordDescriptionComparator()).toList();
        Assertions.assertArrayEquals(expectedList, orderedList.toArray());
    }

    @Test
    void testReversedList() {
        WordDescription[] wordList = {w10, w9, w8, w7, w6, w5, w4, w3, w2, w1};
        var orderedList = Arrays.stream(wordList).sorted(new WordDescriptionComparator()).toList();
        Assertions.assertArrayEquals(expectedList, orderedList.toArray());
    }

    @Test
    void testShuffled5TimesList() {
        List<WordDescription> wordList = new ArrayList<>(List.of(w1, w2, w3, w4, w5, w6, w7, w8, w9, w10));
        for (int i = 0; i < 5; i++) {
            Collections.shuffle(wordList);
//            wordList.forEach(System.out::println);
            var orderedList = wordList.stream().sorted(new WordDescriptionComparator()).toList();
            Assertions.assertArrayEquals(expectedList, orderedList.toArray());
        }
    }
}