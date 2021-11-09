package com.scriptorium.pali.engine.bot.command;

public enum CommandName {
    START("/start", "Начало работы"),
    STOP("/stop", "Приостановить работу и очистить кэш"),
    HELP("/help", "Помощь");

    private final String value;
    private final String shortDescr;

    CommandName(String commandName, String shortDescr) {
        this.value = commandName;
        this.shortDescr = shortDescr;
    }

    public String getValue() {
        return value;
    }

    public String getHelpDescription() {
        return value + " - " + shortDescr + '\n';
    }
}