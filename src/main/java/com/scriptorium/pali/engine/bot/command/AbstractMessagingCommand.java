package com.scriptorium.pali.engine.bot.command;

import com.scriptorium.pali.engine.bot.SendMessageService;
import org.telegram.telegrambots.meta.api.objects.Update;

public abstract class AbstractMessagingCommand implements Command {
    protected final SendMessageService sendMessageService;

    protected AbstractMessagingCommand(final SendMessageService service) {
        this.sendMessageService = service;
    }

    protected abstract String generateAnswer();

    @Override
    public void execute(final Update update) {
        sendMessageService.sendMessage(update.getMessage().getChatId().toString(), generateAnswer());
    }
}
