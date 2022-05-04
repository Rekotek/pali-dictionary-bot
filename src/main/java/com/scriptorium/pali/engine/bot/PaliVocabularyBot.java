package com.scriptorium.pali.engine.bot;

import com.scriptorium.pali.engine.bot.command.CommandContainer;
import com.scriptorium.pali.service.AnswerService;
import com.scriptorium.pali.service.VocabularyService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;

@Slf4j
@Component
public class PaliVocabularyBot extends TelegramLongPollingBot {
    private static final String COMMAND_PREFIX = "/";

    @Value("${bot.username}")
    private String username;

    @Value("${bot.token}")
    private String token;

    private final VocabularyService vocabularyService;
    private final AnswerService answerService;
    private final CommandContainer commandContainer;
    private final SendMessageService sendMessageService;

    public PaliVocabularyBot(VocabularyService vs, AnswerService as) {
        vocabularyService = vs;
        answerService = as;
        sendMessageService = new SendMessageServiceImpl(this);
        commandContainer = new CommandContainer(
                sendMessageService,
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
            var commandIdentifier = telRec.message.split(" ")[0].toLowerCase();
            commandContainer.retrieveCommand(commandIdentifier).execute(update);
        } else {
            var answer = answerService.retrieveTranslations(telRec.message);
            sendMessageService.sendMessage(telRec.chatId, answer);
        }
    }

    private record TelRec(String message, String chatId) {}

    private TelRec parseUpdate(final Update update) {
        final Message message;
        if (update.hasMessage() && update.getMessage().hasText()) {
            message = update.getMessage();
        } else if (update.hasEditedMessage() && update.getEditedMessage().hasText()) {
            message = update.getEditedMessage();
        } else
            return null;
        return new TelRec(message.getText().trim(), message.getChatId().toString());
    }
}