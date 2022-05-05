package com.scriptorium.pali.engine.bot.command;

import com.scriptorium.pali.engine.bot.SendMessageService;

public class StopCommand extends AbstractMessagingCommand {
    private static final String STOP_MSG = "Да меня не остановишь 😂!";

    public StopCommand(SendMessageService sendMessageService) {
        super(sendMessageService);
    }

    @Override
    protected String generateAnswer() {
        return STOP_MSG;
    }
}
