package com.scriptorium.pali.engine.bot.command;

import com.scriptorium.pali.EndingTypeHelper;
import com.scriptorium.pali.engine.bot.SendMessageService;
import com.scriptorium.pali.enums.EndingType;
import org.telegram.telegrambots.meta.api.objects.Update;

public class NounCasesCommand extends AbstractMessagingCommand {
    private String word;

    public NounCasesCommand(SendMessageService sendMessageService) {
        super(sendMessageService);
    }

    @Override
    protected String generateAnswer() {
        if (word == null) {
            return "Уточните команду";
        }
        EndingType endingType;
        try {
            endingType = EndingTypeHelper.indentify(word);
        } catch (IllegalArgumentException _e) {
            return "Не смог определить окончание слова";
        }
        return String.format("Noun command found: %s with type of %s", word, endingType);
    }

    @Override
    public void execute(Update update) {
        String[] commandString = update.getMessage().getText().trim().split(" ");
        if (commandString.length == 1) {
            word = null;
        } else {
            word = commandString[1];
        }
        sendMessageService.sendMessage(update.getMessage().getChatId().toString(), generateAnswer());
    }
}
