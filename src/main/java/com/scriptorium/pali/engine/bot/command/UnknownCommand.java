package com.scriptorium.pali.engine.bot.command;

import com.scriptorium.pali.engine.bot.SendMessageService;

public class UnknownCommand extends AbstractMessagingCommand {
    public static final String UNKNOWN_MSG = "Я не понимаю. Чтобы получить список команд, наберите /help";

    public UnknownCommand(SendMessageService sendMessageService) {
        super(sendMessageService);
    }

    @Override
    public String generateAnswer() {
        return UNKNOWN_MSG;
    }
}
