package org.forksmash.remotenumberbot.bot;

import org.forksmash.remotenumberbot.utility.exception.InvalidInputException;
import org.forksmash.remotenumberbot.utility.exception.ResultOverflowException;
import org.forksmash.remotenumberbot.utility.message.HelpMessage;
import org.forksmash.remotenumberbot.utility.message.StartMessage;
import org.forksmash.remotenumberbot.utility.processor.MessageProcessor;
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
    private final MessageProcessor processor;
    private static final Logger logger = LoggerFactory.getLogger(RemoteNumberBot.class);
    private StartMessage startMessage;
    private HelpMessage helpMessage;

    @Autowired
    public RemoteNumberBot(
            MessageProcessor processor, StartMessage startMessage, HelpMessage helpMessage) {
        this.USERNAME = System.getenv("BOT_USERNAME");
        this.TOKEN = System.getenv("BOT_TOKEN");
        this.processor = processor;
        this.startMessage = startMessage;
        this.helpMessage = helpMessage;
    }

    @Override
    public String getBotToken() {
        return TOKEN;
    }

    @Override
    public String getBotUsername() {
        return USERNAME;
    }

    private String initialProcess(String inboundMessage) throws InvalidInputException, ResultOverflowException {
        if (inboundMessage.equals("/help")) {
            return helpMessage.getMessageText();
        } else if (inboundMessage.equals("/start")) {
            return startMessage.getMessageText();
        }
        return processor.processRequest(inboundMessage);
    }

    private String generateDefaultErrorMessages(String errorMessage) {
        logger.error(errorMessage);
        return errorMessage + "\n\n" + "Type /start for a list of valid commands.";
    }

    @Override
    public void onUpdateReceived(Update update) {
        SendMessage message = new SendMessage();
        String messageText = null;
        try {
            if (update.hasMessage() && update.getMessage().hasText()) {
                String receivedMessage = update.getMessage().getText();
                messageText = initialProcess(receivedMessage);
            } else {
                throw new InvalidInputException("Invalid input.");
            }
        } catch (InvalidInputException e) {
            messageText = generateDefaultErrorMessages(e.getMessage());
        } catch (ResultOverflowException e) {
            logger.error(e.getMessage());
            messageText = generateDefaultErrorMessages(e.getMessage());
        } catch (Exception e) {
            logger.error(e.getMessage());
            messageText = generateDefaultErrorMessages("An error occured.");
        } finally {
            message.setText(messageText);
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
