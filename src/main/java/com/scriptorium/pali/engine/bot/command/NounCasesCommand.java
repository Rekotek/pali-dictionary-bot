package com.scriptorium.pali.engine.bot.command;

import com.scriptorium.pali.engine.bot.SendMessageService;
import com.scriptorium.pali.engine.gramma.enums.NounTypes;
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
        int lastChar = word.length() - 1;
        String ending;
        if (word.charAt(lastChar) == 'ṃ') {
            ending = word.substring(lastChar - 1, lastChar + 1);
        } else {
            ending = "" + word.charAt(lastChar);
        }
        NounTypes nounType = NounTypes.getNounTypeByForm(ending);
        return String.format("Noun command found: %s with type of %s", word, nounType);
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
