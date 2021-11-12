package com.scriptorium.pali.service;

import com.scriptorium.pali.engine.PaliCharsConverter;
import com.scriptorium.pali.entity.WordDescription;
import com.scriptorium.pali.repository.WordDescriptionRepo;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.scriptorium.pali.config.CacheHelper.CACHE_NAME_PALI_STRICT;
import static com.scriptorium.pali.config.CacheHelper.CACHE_NAME_PALI_WIDE;

@Slf4j
@Service
public class VocabularyService {
    private final WordDescriptionRepo wordDescriptionRepo;

    @SuppressWarnings("SpringJavaAutowiringInspection")
    @Autowired(required = false)
    private SessionFactory sessionFactory;

    public VocabularyService(WordDescriptionRepo wordDescriptionRepo) {
        this.wordDescriptionRepo = wordDescriptionRepo;
    }

    public boolean isEmptyDatabase() {
        return wordDescriptionRepo.count() == 0;
    }

    public void saveNewEntry(String pali, String translation) {
        WordDescription word = WordDescription.builder()
                .pali(pali.trim())
                .simplified(PaliCharsConverter.simplify(pali))
                .translation(translation.trim())
                .build();
        wordDescriptionRepo.save(word);
    }

    @Cacheable(CACHE_NAME_PALI_STRICT)
    public List<WordDescription> findByPaliStrict(String pali) {
        log.debug("Running strict search for {}", pali);
        return wordDescriptionRepo.findByPaliOrderById(pali);
    }

    @Cacheable(CACHE_NAME_PALI_WIDE)
    public List<WordDescription> findByPaliWide(String pali) {
        log.debug("Running wide search for {}", pali);
        String search = PaliCharsConverter.convertToDiacritic(pali);
        return wordDescriptionRepo.findPaliWide(search);
    }

    @CacheEvict(cacheNames = {CACHE_NAME_PALI_WIDE, CACHE_NAME_PALI_STRICT}, allEntries = true)
    public void evictAllCaches() {
        if (sessionFactory != null) {
            sessionFactory.getCache().evictAllRegions();
        }
    }
}
