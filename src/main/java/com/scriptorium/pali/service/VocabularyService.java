package com.scriptorium.pali.service;

import com.scriptorium.pali.engine.PaliCharsConverter;
import com.scriptorium.pali.entity.WordDescription;
import com.scriptorium.pali.entity.dto.WordDescriptionDto;
import com.scriptorium.pali.entity.mapper.WordDescriptionMapper;
import com.scriptorium.pali.repository.WordDescriptionRepo;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.SessionFactory;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Locale;

import static com.scriptorium.pali.config.CacheHelper.CACHE_NAME_PALI_STRICT;
import static com.scriptorium.pali.config.CacheHelper.CACHE_NAME_PALI_WIDE;

@Slf4j
@Service
public class VocabularyService {
    private final WordDescriptionRepo wordDescriptionRepo;
    private final WordDescriptionMapper mapper;

    @SuppressWarnings("SpringJavaAutowiringInspection")
    @Autowired(required = false)
    private SessionFactory sessionFactory;

    public VocabularyService(WordDescriptionRepo wordDescriptionRepo) {
        this.wordDescriptionRepo = wordDescriptionRepo;
        mapper = Mappers.getMapper(WordDescriptionMapper.class);
    }

    public void saveNewEntry(String pali, String translation) {
        WordDescription word = WordDescription.builder()
                .pali(pali.trim())
                .translation(translation.trim())
                .build();
        wordDescriptionRepo.save(word);
    }

    @Cacheable(CACHE_NAME_PALI_STRICT)
    public List<WordDescriptionDto> findByPaliStrict(String pali) {
        log.debug("Running strict search for {}", pali);
        var diacriticWord = PaliCharsConverter.convertToDiacritic(pali);
        List<WordDescription> wordList = wordDescriptionRepo.findByPaliOrderById(diacriticWord);
        return mapper.mapFromDb(wordList);
    }

    @Cacheable(CACHE_NAME_PALI_WIDE)
    public List<WordDescriptionDto> findByPaliWide(String pali) {
        log.debug("Running wide search for {}", pali);
        var diacriticWord = PaliCharsConverter.convertToDiacritic(pali);
        List<WordDescription> wordList = wordDescriptionRepo.findPaliWide(diacriticWord);
        return mapper.mapFromDb(wordList);
    }

    @Cacheable(CACHE_NAME_PALI_STRICT)
    public List<WordDescriptionDto> findInsideTranslation(String word) {
        log.debug("Running cyrillic search for {}", word);
        var searchWord = "\\m" + word.toLowerCase(Locale.ROOT) + "\\M";
        List<WordDescription> wordList = wordDescriptionRepo.findInsideTranslation(searchWord);
        return mapper.mapFromDb(wordList);
    }

    @CacheEvict(cacheNames = {CACHE_NAME_PALI_WIDE, CACHE_NAME_PALI_STRICT}, allEntries = true)
    public void evictAllCaches() {
        if (sessionFactory != null) {
            sessionFactory.getCache().evictAllRegions();
        }
    }
//
//    public void replaceSpecialCharacters() {
//        Iterable<WordDescription> words = wordDescriptionRepo.findAll();
//        for (WordDescription word : words) {
//            String translation = word.getTranslation();
//            if (translation.contains(">") || translation.contains("<")) {
//                var newTranslation = translation.replaceAll("<", "&lt;").replaceAll(">", "&gt;");
//                word.setTranslation(newTranslation);
//                wordDescriptionRepo.save(word);
//                log.info("Found for word: {}", word.getPali());
//            }
//        }
//    }
//    @PostConstruct
//    public void run() {
//        log.info("======= REPLACE STARTED =======");
//        replaceSpecialCharacters();
//    }
}
