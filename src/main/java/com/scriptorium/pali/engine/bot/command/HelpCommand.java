package com.scriptorium.pali.engine.bot.command;

import com.scriptorium.pali.engine.PaliCharsConverter;
import com.scriptorium.pali.engine.bot.SendMessageService;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.Map;

import static com.scriptorium.pali.engine.bot.command.CommandName.*;

public class HelpCommand implements Command {
    private final String HELP_MSG;

    private final SendMessageService sendMessageService;

    public HelpCommand(SendMessageService sendMessageService) {
        this.sendMessageService = sendMessageService;
        var topCommands = START.getHelpDescription() +
                STOP.getHelpDescription() + HELP.getHelpDescription();
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
    public void execute(Update update) {
        sendMessageService.sendMessage(update.getMessage().getChatId().toString(), HELP_MSG);
    }
}
