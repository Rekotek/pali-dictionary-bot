package com.scriptorium.pali.engine.bot.command;

public enum CommandName {
    START("/start", "Начало работы"),
    STOP("/stop", "Приостановить работу и очистить кэш"),
    HELP("/help", "Помощь"),
    CLEAR_CACHE("/clearcache", "Очистить кэш"),
    NOUN_CASES("/noun", "Склонение существительных: введите основу и род в виде m|n|f");

    private final String value;
    private final String shortDescr;

    CommandName(final String cmd, final String descr) {
        this.value = cmd;
        this.shortDescr = descr;
    }

    public String getValue() {
        return value;
    }

    public String getHelpDescription() {
        return value + " - " + shortDescr + '\n';
    }
}
