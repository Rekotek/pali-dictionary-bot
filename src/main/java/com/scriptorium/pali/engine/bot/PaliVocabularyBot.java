package com.scriptorium.pali.engine.bot;

import com.google.common.html.HtmlEscapers;
import com.scriptorium.pali.engine.bot.command.CommandContainer;
import com.scriptorium.pali.entity.WordDescription;
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

    public static String COMMAND_PREFIX = "/";

    @Value("${bot.username}")
    private String username;

    @Value("${bot.token}")
    private String token;

    private final VocabularyService vocabularyService;
    private final CommandContainer commandContainer;

    public PaliVocabularyBot(VocabularyService vocabularyService) {
        this.vocabularyService = vocabularyService;
        commandContainer = new CommandContainer(new SendMessageServiceImpl(this),
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
        if (update.hasMessage() && update.getMessage().hasText()) {
            String message = update.getMessage().getText().trim();
            String chatId = update.getMessage().getChatId().toString();

            if (message.startsWith(COMMAND_PREFIX)) {
                String commandIdentifier = message.split(" ")[0].toLowerCase();

                commandContainer.retrieveCommand(commandIdentifier).execute(update);
            } else {
                SendMessage sm = new SendMessage();
                sm.setChatId(chatId);

                List<WordDescription> translations;
                char firstChar = message.charAt(0);
                if (Character.UnicodeBlock.CYRILLIC.equals(Character.UnicodeBlock.of(firstChar))) {
                    translations = vocabularyService.findInsideTranslation(message);
                } else {
                    translations = vocabularyService.findByPaliWide(message);
                }
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
                translations.forEach(row -> {
                    String paliHead = "<b>" + row.getPali() + "</b>\n";
                    String translationBody = HtmlEscapers.htmlEscaper().escape(row.getTranslation());
                    answer.append(paliHead);
                    answer.append(translationBody);
                    answer.append("\n\n");
                });
                if (answer.isEmpty()) {
                    answer.append(String.format("Не найдено слово: <code>%s</code>", message));
                }
                sm.enableHtml(true);
                sm.setText(answer.toString().trim());

                try {
                    execute(sm);
                } catch (TelegramApiException e) {
                    log.error(e.getMessage());
                }
            }
        }
    }
}
