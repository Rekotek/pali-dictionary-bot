package com.scriptorium.pali.service;

import com.scriptorium.pali.comparator.WordDescriptionDtoComparator;
import com.scriptorium.pali.entity.dto.WordDescriptionDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

import static java.lang.Math.min;

@Slf4j
@Service
public class AnswerService {
    private final VocabularyService vocabularyService;

    public AnswerService(VocabularyService vocabularyService) {
        this.vocabularyService = vocabularyService;
    }

    public String retrieveTranslations(String inputWord) {
        List<WordDescriptionDto> translations;
        var firstChar = inputWord.charAt(0);
        if (Character.UnicodeBlock.CYRILLIC.equals(Character.UnicodeBlock.of(firstChar))) {
            translations = vocabularyService.findInsideTranslation(inputWord);
        } else {
            if (inputWord.endsWith("!")) {
                inputWord = inputWord.substring(0, inputWord.length() - 1);
                translations = vocabularyService.findByPaliStrict(inputWord);
            } else {
                translations = vocabularyService.findByPaliWide(inputWord);
            }
        }
        translations.sort(new WordDescriptionDtoComparator());
        return makeAnswer(inputWord, translations);
    }

    private String makeAnswer(String inputWord, List<WordDescriptionDto> translations) {
        if (translations.size() == 0) {
            return String.format("Не найдено слово: <code>%s</code>", inputWord);
        }
        StringBuilder answer = new StringBuilder();
        int stringLength = translations.stream()
                .map(WordDescriptionDto::getTranslation)
                .reduce("", String::concat)
                .length();
        if (stringLength > 2048) {
            answer.append("<b>Слишком много значений!</b>\n");
            int len = min(10, translations.size());
            answer.append(String.format("Будет выведены результат для первых <b>%d</b> слов.\n\n", len));
            translations.removeAll(translations.subList(len, translations.size()));
        }
        translations.forEach(row -> answer.append(row.toHtml()));
        return answer.toString().trim();
    }
}
