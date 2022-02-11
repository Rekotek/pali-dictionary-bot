package com.scriptorium.pali.engine.bot.command;

import com.scriptorium.pali.NounCases;
import com.scriptorium.pali.engine.PaliCharsConverter;
import com.scriptorium.pali.engine.bot.SendMessageService;
import com.scriptorium.pali.enums.Gender;
import com.scriptorium.pali.enums.NumberType;
import com.scriptorium.pali.enums.WordCase;
import com.scriptorium.pali.service.VocabularyService;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.List;

public class NounCasesCommand extends AbstractMessagingCommand {
    private final VocabularyService vocabularyService;

    private String dhatuWord;
    private Gender gender;

    public NounCasesCommand(final SendMessageService sendMessageService, final VocabularyService vocService) {
        super(sendMessageService);
        this.vocabularyService = vocService;
    }

    @Override
    protected String generateAnswer() {
        if ( (dhatuWord == null) || (gender == null)) {
            return "Использование: <code>/noun &lt;основа слова (dhatu)&gt; &lt;m|n|f&gt;</code>";
        }
        try {
            var output = new StringBuilder();
            var nounCases = new NounCases(dhatuWord, gender);
            var wordsByDhatu = vocabularyService.findByPaliStrict(dhatuWord);
            if (wordsByDhatu.size() == 1) {
                output.append(wordsByDhatu.get(0).toHtml());
            } else {
                List<String> nomForms = nounCases.getFormsFor(WordCase.NOM, NumberType.SG);
                var wordsByNom = vocabularyService.findByPaliStrict(nomForms.get(0));
                if (wordsByNom.size() > 0) {
                    output.append(wordsByNom.get(0).toHtml());
                }
            }
            output.append(nounCases.toHtml());
            return output.toString();
        } catch (IllegalArgumentException exception) {
            return exception.getMessage();
        }
    }

    @Override
    public void execute(Update update) {
        String[] commandString = update.getMessage().getText().trim().split(" ");
        if (commandString.length != 3) {
            dhatuWord = null;
        } else {
            dhatuWord = PaliCharsConverter.convertToDiacritic(commandString[1]);
            var genderSymbol = commandString[2];
            gender = Gender.from(genderSymbol);
        }
        sendMessageService.sendMessage(update.getMessage().getChatId().toString(), generateAnswer());
    }
}