package com.scriptorium.pali.engine.bot.command;

import com.scriptorium.pali.engine.PaliCharsConverter;
import com.scriptorium.pali.engine.bot.SendMessageService;

import java.util.Map;

import static com.scriptorium.pali.engine.bot.command.CommandName.*;

public class HelpCommand extends AbstractMessagingCommand {
    private final String HELP_MSG;

    public HelpCommand(SendMessageService sendMessageService) {
        super(sendMessageService);
        var topCommands = START.getHelpDescription() +
                STOP.getHelpDescription() +
                NOUN_CASES.getHelpDescription() +
                HELP.getHelpDescription();
        StringBuilder sb = new StringBuilder("<code>");
        for (Map.Entry<String, String> entry : PaliCharsConverter.fromLatinMap.entrySet()) {
            String newLine = "\t" + entry.getKey() + " → " + entry.getValue() + "\n";
            sb.append(newLine);
        }
        sb.append("</code>");
        HELP_MSG = String.format("<b>Команды бота</b>\n%s\n<b>Список вводимых символов</b>\n%s",
                topCommands, sb);
    }

    @Override
    protected String generateAnswer() {
        return HELP_MSG;
    }
//
//    @Override
//    public void execute(Update update) {
//        sendMessageService.sendMessage(update.getMessage().getChatId().toString(), HELP_MSG);
//    }
}
