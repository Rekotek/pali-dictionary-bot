package com.scriptorium.pali.engine.bot.command;

import com.scriptorium.pali.engine.bot.SendMessageService;
import com.scriptorium.pali.service.VocabularyService;
import org.telegram.telegrambots.meta.api.objects.Update;

public class ClearCacheCommand implements Command {
    private static final String CLEAR_CACHE_MSG = "••• Кэш очищен •••";

    private final VocabularyService vocabularyService;
    private final SendMessageService sendMessageService;

    public ClearCacheCommand(SendMessageService sendMessageService, VocabularyService vocabularyService) {
        this.vocabularyService = vocabularyService;
        this.sendMessageService = sendMessageService;
    }

    @Override
    public void execute(Update update) {
        vocabularyService.evictAllCaches();
        sendMessageService.sendMessage(update.getMessage().getChatId().toString(), CLEAR_CACHE_MSG);
    }
}
