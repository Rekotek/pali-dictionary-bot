package com.scriptorium.pali.engine.bot.command;

import com.scriptorium.pali.engine.bot.SendMessageService;
import com.scriptorium.pali.service.VocabularyService;
import org.telegram.telegrambots.meta.api.objects.Update;

public class ClearCacheCommand extends AbstractMessagingCommand {
    private static final String CLEAR_CACHE_MSG = "••• Кэш очищен •••";

    private final VocabularyService vocabularyService;

    public ClearCacheCommand(SendMessageService sendMessageService, VocabularyService vocabularyService) {
        super(sendMessageService);
        this.vocabularyService = vocabularyService;
    }

    @Override
    protected String generateAnswer() {
        return CLEAR_CACHE_MSG;
    }

    @Override
    public void execute(Update update) {
        vocabularyService.evictAllCaches();
        sendMessageService.sendMessage(update.getMessage().getChatId().toString(), generateAnswer());
    }
}
