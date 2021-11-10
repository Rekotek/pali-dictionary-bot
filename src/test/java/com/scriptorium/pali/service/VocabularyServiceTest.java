package com.scriptorium.pali.service;

import com.scriptorium.pali.entity.WordDescription;
import com.scriptorium.pali.repository.WordDescriptionRepo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.Objects;

import static com.scriptorium.pali.config.CacheTuning.CACHE_NAME_PALI_STRICT;
import static com.scriptorium.pali.config.CacheTuning.CACHE_NAME_PALI_WIDE;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = VocabularyServiceCacheConfiguration.class)
class VocabularyServiceTest {
    private static final String WORD_PALI = "pali";
    private static final String WORD_PAALI = "pAli";
    private static final String DIACRITIC_WORD_PAALI = "pāli";

    @MockBean
    WordDescriptionRepo wordDescriptionRepo;

    @Autowired
    VocabularyService vocabularyService;

    @Autowired
    CacheManager cacheManager;

    @BeforeEach
    void setUp() {
        var word1 = new WordDescription(1L, WORD_PALI, WORD_PALI, "First description");
        var word2 = new WordDescription(2L, WORD_PALI, WORD_PALI, "Second description");
        var answer1 = new ArrayList<WordDescription>(1);
        answer1.add(word1);
        var answer2 = new ArrayList<WordDescription>(1);
        answer2.add(word2);
        when(wordDescriptionRepo.findPaliWide(WORD_PALI)).thenReturn(answer1, answer2);
        var newWord = new WordDescription(3L, DIACRITIC_WORD_PAALI, WORD_PAALI, "Another description");
        var newAnswer = new ArrayList<WordDescription>(1);
        newAnswer.add(newWord);
        when(wordDescriptionRepo.findPaliWide(WORD_PAALI)).thenReturn(newAnswer);
    }

    @Test
    void cachesExist() {
        var managerCache = cacheManager.getCache(CACHE_NAME_PALI_WIDE);
        assertNotNull(managerCache);
        managerCache = cacheManager.getCache(CACHE_NAME_PALI_STRICT);
        assertNotNull(managerCache);
    }

    @Test
    void findSameWordsPaliWide() {
        evictAllCaches();
        var answerOne = vocabularyService.findByPaliWide(WORD_PALI);
        var answerTwo = vocabularyService.findByPaliWide(WORD_PALI);
        assertSame(answerOne, answerTwo);
        verify(wordDescriptionRepo, times(1)).findPaliWide(WORD_PALI);
        Cache cache = cacheManager.getCache(CACHE_NAME_PALI_WIDE);
        assertNotNull(cache);
        var valueInCache = cache.get(WORD_PALI);
        assertNotNull(valueInCache);
    }

    @Test
    void findTwoDifferentPaliWide() {
        evictAllCaches();
        var answerOne = vocabularyService.findByPaliWide(WORD_PALI);
        verify(wordDescriptionRepo, times(1)).findPaliWide(WORD_PALI);
        var answerAnotherOne = vocabularyService.findByPaliWide(WORD_PAALI);
        assertNotSame(answerOne, answerAnotherOne);
        verify(wordDescriptionRepo, times(1)).findPaliWide(DIACRITIC_WORD_PAALI);
        var answerTwo = vocabularyService.findByPaliWide(WORD_PALI);
        var answerAnotherTwo = vocabularyService.findByPaliWide(WORD_PAALI);
        assertSame(answerOne, answerTwo);
        assertSame(answerAnotherOne, answerAnotherTwo);
    }

    @Test
    void invalidateCache() {
        var answerOne = vocabularyService.findByPaliWide(WORD_PALI);
        var answerTwo = vocabularyService.findByPaliWide(WORD_PALI);
        vocabularyService.evictAllCaches();
        var answerThree = vocabularyService.findByPaliWide(WORD_PALI);
        assertSame(answerOne, answerTwo);
        assertNotSame(answerOne, answerThree);
        verify(wordDescriptionRepo, times(2)).findPaliWide(WORD_PALI);
    }

    private void evictAllCaches() {
        for (String name : cacheManager.getCacheNames()) {
            Objects.requireNonNull(cacheManager.getCache(name)).clear();
        }
    }
}