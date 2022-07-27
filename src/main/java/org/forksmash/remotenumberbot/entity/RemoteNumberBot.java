package org.forksmash.remotenumberbot.entity;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
    private final String AUTHORISED_USER;
//    private static final Logger logger = LoggerFactory.getLogger(RemoteNumberBot.class);

    public RemoteNumberBot(@Value("${bot.USERNAME}") String USERNAME, @Value("${bot.TOKEN}") String TOKEN, @Value("${bot.AUTHORISED_USER") String AUTHORISED_USER) {
        this.USERNAME = USERNAME;
        this.TOKEN = TOKEN;
        this.AUTHORISED_USER = AUTHORISED_USER;

        System.out.println("Username: " + this.USERNAME);
        System.out.println("Token: " + this.TOKEN);
        System.out.println("Authorised user: " + this.AUTHORISED_USER);
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
        SendMessage message = null;

//        logger.info("Chat ID:" + update.getMessage().getChatId().toString());
        if (!(update.getMessage().getChatId().toString().equals(AUTHORISED_USER))) {
            message = new SendMessage();
//            logger.info("DENIED DENIED DENIED");
            message.setText("You are not authorised to use this bot.");
        } else if (update.hasMessage() && update.getMessage().hasText()){
            String receivedMessage = update.getMessage().getText();
            message = new SendMessage();
            message.setText(receivedMessage);
        }
        message.setChatId(update.getMessage().getChatId());
        try {
            execute(message);
        } catch (TelegramApiException e) {
//            logger.error(e.getMessage());
            e.printStackTrace();
        }
    }
}
