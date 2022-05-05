package com.scriptorium.pali.comparator;

import com.scriptorium.pali.entity.dto.WordDescriptionDto;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

class WordDescriptionComparatorTest {
    static private WordDescriptionDto w1, w2, w3, w4, w5, w6, w7, w8, w9, w10;
    static private WordDescriptionDto[] expectedList;

    @BeforeAll
    static void setUp() {
        w1 = WordDescriptionDto.builder()
                .pali("ākusāla")
                .id(1L)
                .translation("La-la-la")
                .build();
        w2 = WordDescriptionDto.builder()
                .pali("akusala")
                .id(2L)
                .translation("La-la-la")
                .build();
        w3 = WordDescriptionDto.builder()
                .pali("akusāla")
                .id(3L)
                .translation("La-la-la")
                .build();
        w4 = WordDescriptionDto.builder()
                .pali("āk")
                .id(4L)
                .translation("La-la-la")
                .build();
        w5 = WordDescriptionDto.builder()
                .pali("ākusālayā")
                .id(5L)
                .translation("La-la-la")
                .build();
        w6 = WordDescriptionDto.builder()
                .pali("āk")
                .id(6L)
                .translation("La-la-la2")
                .build();
        w7 = WordDescriptionDto.builder()
                .pali("sabbaṭṭhaka")
                .id(7L)
                .translation("La-la-la2")
                .build();
        w8 = WordDescriptionDto.builder()
                .pali("yattha kāmanipātin")
                .id(8L)
                .translation("La-la-la2")
                .build();
        w9 = WordDescriptionDto.builder()
                .pali("yattha kāmaṃ")
                .id(9L)
                .translation("La-la-la2")
                .build();
        w10 = WordDescriptionDto.builder()
                .pali("yattha")
                .id(10L)
                .translation("La-la-la2")
                .build();

        expectedList = new WordDescriptionDto[]{w2, w3, w4, w6, w1, w5, w10, w9, w8, w7};
    }

    @Test
    void testList() {
        WordDescriptionDto[] wordList = {w1, w2, w3, w4, w5, w6, w7, w8, w9, w10};
        var orderedList = Arrays.stream(wordList).sorted(new WordDescriptionDtoComparator()).toList();
        Assertions.assertArrayEquals(expectedList, orderedList.toArray());
    }

    @Test
    void testReversedList() {
        WordDescriptionDto[] wordList = {w10, w9, w8, w7, w6, w5, w4, w3, w2, w1};
        var orderedList = Arrays.stream(wordList).sorted(new WordDescriptionDtoComparator()).toList();
        Assertions.assertArrayEquals(expectedList, orderedList.toArray());
    }

    @Test
    void testShuffled5TimesList() {
        List<WordDescriptionDto> wordList = new ArrayList<>(List.of(w1, w2, w3, w4, w5, w6, w7, w8, w9, w10));
        for (int i = 0; i < 5; i++) {
            Collections.shuffle(wordList);
//            wordList.forEach(System.out::println);
            var orderedList = wordList.stream().sorted(new WordDescriptionDtoComparator()).toList();
            Assertions.assertArrayEquals(expectedList, orderedList.toArray());
        }
    }
}