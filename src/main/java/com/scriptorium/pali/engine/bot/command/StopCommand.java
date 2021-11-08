package com.scriptorium.pali.engine.bot.command;

import com.scriptorium.pali.engine.bot.SendMessageService;
import org.telegram.telegrambots.meta.api.objects.Update;

public class StopCommand implements Command {
    private static final String STOP_MSG = "Да меня не остановишь 😂!";

    private final SendMessageService sendMessageService;

    public StopCommand(SendMessageService sendMessageService) {
        this.sendMessageService = sendMessageService;
    }

    @Override
    public void execute(Update update) {
        sendMessageService.sendMessage(update.getMessage().getChatId().toString(), STOP_MSG);
    }
}
