package com.scriptorium.pali.engine.bot;

import org.slf4j.Logger;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import static org.slf4j.LoggerFactory.getLogger;

@Service
public class SendMessageServiceImpl implements SendMessageService {
    private static final Logger LOG = getLogger(SendMessageServiceImpl.class);

    private final PaliVocabularyBot bot;

    public SendMessageServiceImpl(PaliVocabularyBot bot) {
        this.bot = bot;
    }

    @Override
    public void sendMessage(String chatId, String message) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(chatId);
        sendMessage.enableHtml(true);
        sendMessage.setText(message);

        try {
            bot.execute(sendMessage);
        } catch (TelegramApiException e) {
            LOG.error(e.getMessage());
        }
    }
}



