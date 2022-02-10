package com.scriptorium.pali.engine.bot.command;

import com.scriptorium.pali.engine.bot.SendMessageService;
import com.scriptorium.pali.service.VocabularyService;

import java.util.Map;

import static com.scriptorium.pali.engine.bot.command.CommandName.*;

public class CommandContainer {
    private final Map<String, Command> commandMap;
    private final Command unknownCommand;

    public CommandContainer(final SendMessageService sendMessageService, final VocabularyService vocabularyService) {
        commandMap = Map.of(
                START.getValue(), new StartCommand(sendMessageService),
                STOP.getValue(), new StopCommand(sendMessageService),
                CLEAR_CACHE.getValue(), new ClearCacheCommand(sendMessageService, vocabularyService),
                HELP.getValue(), new HelpCommand(sendMessageService),
                NOUN_CASES.getValue(), new NounCasesCommand(sendMessageService, vocabularyService)
        );
        unknownCommand = new UnknownCommand(sendMessageService);
    }

    public Command retrieveCommand(final String commandIdentifier) {
        return commandMap.getOrDefault(commandIdentifier, unknownCommand);
    }
}
