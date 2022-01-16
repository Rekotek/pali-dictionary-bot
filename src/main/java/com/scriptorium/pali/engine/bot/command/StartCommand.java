package com.scriptorium.pali.engine.bot.command;

import com.scriptorium.pali.engine.bot.SendMessageService;

public class StartCommand extends AbstractMessagingCommand {
    private static final String START_MSG = """
            Привет! Я перевожу с пали на русский по первым буквам в словах.
            Диакритические символы вводить не обязательно: они автоматически подставятся при поиске.
            Например, при вводе <b>at</b> будут выведены все возможные комбинации слов,
            начинающихся на <b>at</b>, <b>āt</b>, <b>aṭ</b>, <b>āṭ</b>.
            Для более точного поиска можно использовать прописные символы.
            Введите /help для вывода списка символов для замены.
            """;

    public StartCommand(SendMessageService sendMessageService) {
        super(sendMessageService);
    }

    @Override
    protected String generateAnswer() {
        return START_MSG;
    }
}
