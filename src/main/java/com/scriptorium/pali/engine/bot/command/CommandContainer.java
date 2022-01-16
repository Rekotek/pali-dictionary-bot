package com.scriptorium.pali.engine.bot.command;

import com.scriptorium.pali.engine.bot.SendMessageService;
import com.scriptorium.pali.service.VocabularyService;

import java.util.AbstractMap;
import java.util.Map;

import static com.scriptorium.pali.engine.bot.command.CommandName.*;

public class CommandContainer {
    private final Map<String, Command> commandMap;
    private final Command unknownCommand;

    public CommandContainer(SendMessageService sendMessageService, VocabularyService vocabularyService) {
        commandMap = Map.ofEntries(
                new AbstractMap.SimpleImmutableEntry<>(START.getValue(), new StartCommand(sendMessageService)),
                new AbstractMap.SimpleImmutableEntry<>(STOP.getValue(), new StopCommand(sendMessageService)),
                new AbstractMap.SimpleImmutableEntry<>(CLEAR_CACHE.getValue(), new ClearCacheCommand(sendMessageService, vocabularyService)),
                new AbstractMap.SimpleImmutableEntry<>(HELP.getValue(), new HelpCommand(sendMessageService)),
                new AbstractMap.SimpleImmutableEntry<>(NOUN_CASES.getValue(), new NounCasesCommand(sendMessageService))
        );
        unknownCommand = new UnknownCommand(sendMessageService);
    }

    public Command retrieveCommand(String commandIdentifier) {
        return commandMap.getOrDefault(commandIdentifier, unknownCommand);
    }
}
