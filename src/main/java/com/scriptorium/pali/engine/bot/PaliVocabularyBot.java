package com.scriptorium.pali.engine.bot;

import com.scriptorium.pali.comparator.WordDescriptionDtoComparator;
import com.scriptorium.pali.engine.bot.command.CommandContainer;
import com.scriptorium.pali.entity.dto.WordDescriptionDto;
import com.scriptorium.pali.service.VocabularyService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.List;

import static java.lang.Math.min;

@Slf4j
@Component
public class PaliVocabularyBot extends TelegramLongPollingBot {
    private static final String COMMAND_PREFIX = "/";

    @Value("${bot.username}")
    private String username;

    @Value("${bot.token}")
    private String token;

    private final VocabularyService vocabularyService;
    private final CommandContainer commandContainer;

    public PaliVocabularyBot(VocabularyService vocabularyService) {
        this.vocabularyService = vocabularyService;
        commandContainer = new CommandContainer(
                new SendMessageServiceImpl(this),
                vocabularyService);
    }

    @Override
    public String getBotUsername() {
        return username;
    }

    @Override
    public String getBotToken() {
        return token;
    }

    @Override
    public void onUpdateReceived(Update update) {
        var telRec = parseUpdate(update);
        if (telRec == null) return;

        if (telRec.message.startsWith(COMMAND_PREFIX)) {
            String commandIdentifier = telRec.message.split(" ")[0].toLowerCase();

            commandContainer.retrieveCommand(commandIdentifier).execute(update);
        } else {
            var answer = retrieveTranslations(telRec.message);
            SendMessage sm = new SendMessage();
            sm.setChatId(telRec.chatId);
            sm.enableHtml(true);
            sm.setText(answer);
            try {
                execute(sm);
            } catch (TelegramApiException e) {
                log.error(e.getMessage());
            }
        }
    }

    private record TelRec(String message, String chatId) {}

    private TelRec parseUpdate(final Update update) {
        String message;
        String chatId;
        if (update.hasMessage() && update.getMessage().hasText()) {
            message = update.getMessage().getText().trim();
            chatId = update.getMessage().getChatId().toString();
        } else if (update.hasEditedMessage() && update.getEditedMessage().hasText()) {
            message = update.getEditedMessage().getText().trim();
            chatId = update.getEditedMessage().getChatId().toString();
        } else
            return null;
        return new TelRec(message, chatId);
    }

    private String retrieveTranslations(String inputWord) {
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
        StringBuilder answer = new StringBuilder();
        int stringLength = translations.stream()
                .map(w -> w.getPali() + " " + w.getTranslation())
                .reduce("", String::concat)
                .length();
        if (stringLength > 2048) {
            answer.append("<b>Слишком много значений!</b>\n");
            int len = min(10, translations.size());
            answer.append(String.format("Будет выведены результат для первых <b>%d</b> слов.\n\n", len));
            translations.removeAll(translations.subList(len, translations.size()));
        }
        translations.forEach(row -> answer.append(row.toHtml()));
        if (answer.isEmpty()) {
            answer.append(String.format("Не найдено слово: <code>%s</code>", inputWord));
        }
        return answer.toString().trim();
    }
}
