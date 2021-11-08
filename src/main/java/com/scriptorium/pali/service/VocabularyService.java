package com.scriptorium.pali.service;

import com.scriptorium.pali.engine.PaliCharsConverter;
import com.scriptorium.pali.entity.WordDescription;
import com.scriptorium.pali.repository.WordDescriptionRepo;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VocabularyService {
    private final WordDescriptionRepo wordDescriptionRepo;

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

    public List<WordDescription> findByPaliStrict(String pali) {
        return wordDescriptionRepo.findByPaliOrderById(pali);
    }

    public List<WordDescription> findByPaliWide(String pali) {
        String search = PaliCharsConverter.convertToDiacritic(pali);
        return wordDescriptionRepo.findPaliWide(search);
    }
}
