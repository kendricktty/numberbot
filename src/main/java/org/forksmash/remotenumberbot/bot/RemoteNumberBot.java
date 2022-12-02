package org.forksmash.remotenumberbot.bot;

import org.forksmash.remotenumberbot.utility.message_processor.DefaultMessageProcessor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

@Component
public class RemoteNumberBot extends TelegramLongPollingBot {
    private final String USERNAME;
    private final String TOKEN;
    // private final SendMessage message;
    private final DefaultMessageProcessor processor;
    private static final Logger logger = LoggerFactory.getLogger(RemoteNumberBot.class);

    @Autowired
    public RemoteNumberBot(@Value("${bot.USERNAME}") String USERNAME, @Value("${bot.TOKEN}") String TOKEN,
            DefaultMessageProcessor processor) {
        this.USERNAME = USERNAME;
        this.TOKEN = TOKEN;
        this.processor = processor;
    }

    @Override
    public String getBotToken() {
        return TOKEN;
    }

    @Override
    public String getBotUsername() {
        return USERNAME;
    }

    @Override
    public void onUpdateReceived(Update update) {
        SendMessage message = new SendMessage();
        if (update.hasMessage() && update.getMessage().hasText()) {
            String receivedMessage = update.getMessage().getText();
            message.setText(processor.processMessage(receivedMessage));
        }
        message.setChatId(update.getMessage().getChatId());

        try {
            execute(message);
        } catch (TelegramApiException e) {
            // System.out.println(e.getMessage());
            logger.error(e.getMessage());
        }
    }
}
