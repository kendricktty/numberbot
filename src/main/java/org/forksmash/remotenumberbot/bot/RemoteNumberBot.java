package org.forksmash.remotenumberbot.bot;

import org.forksmash.remotenumberbot.utility.exception.InvalidInputException;
import org.forksmash.remotenumberbot.utility.exception.ResultOverflowException;
import org.forksmash.remotenumberbot.utility.exception.ZeroSmallerInputException;
import org.forksmash.remotenumberbot.utility.message.HelpMessage;
import org.forksmash.remotenumberbot.utility.message.StartMessage;
import org.forksmash.remotenumberbot.utility.processor.MessageProcessor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class RemoteNumberBot extends TelegramLongPollingBot {
    private final String USERNAME;
    private final String TOKEN;
    // private final SendMessage message;
    private final MessageProcessor processor;
    private StartMessage startMessage;
    private HelpMessage helpMessage;

    // @Autowired
    public RemoteNumberBot(@Value("${bot.USERNAME}") String USERNAME, @Value("${bot.TOKEN}") String TOKEN,
            MessageProcessor processor, StartMessage startMessage, HelpMessage helpMessage) {
        this.USERNAME = USERNAME;
        this.TOKEN = TOKEN;
        this.processor = processor;
        this.startMessage = startMessage;
        this.helpMessage = helpMessage;
        log.info("Welcome to the NumberBot");
        log.info("Use this bot to generate random numbers or powers of 2.");
    }

    @Override
    public String getBotToken() {
        return TOKEN;
    }

    @Override
    public String getBotUsername() {
        return USERNAME;
    }

    private String initialProcess(String inboundMessage) throws InvalidInputException, ResultOverflowException, ZeroSmallerInputException {
        if (inboundMessage.equals("/help")) {
            return helpMessage.getMessageText();
        } else if (inboundMessage.equals("/start")) {
            return startMessage.getMessageText();
        }
        return processor.processRequest(inboundMessage);
    }

    private String generateDefaultErrorMessages(String errorMessage) {
        log.info("Generating error message");
        return errorMessage + "\n\n" + "Type /start for a list of valid commands.";
    }

    @Override
    public void onUpdateReceived(Update update) {
        SendMessage message = new SendMessage();
        String messageText = null;
        try {
            if (update.hasMessage() && update.getMessage().hasText()) {
                String receivedMessage = update.getMessage().getText();
                log.debug("Received message: " + receivedMessage);
                messageText = initialProcess(receivedMessage);
            } else {
                throw new InvalidInputException("Invalid input.");
            }
        } catch (InvalidInputException e) {
            messageText = generateDefaultErrorMessages(e.getMessage());
        } catch (ResultOverflowException e) {
            log.error(e.getMessage());
            messageText = generateDefaultErrorMessages(e.getMessage());
        } catch (ZeroSmallerInputException e) {
            log.error(e.getMessage());
            messageText = generateDefaultErrorMessages(e.getMessage());
        } catch (Exception e) {
            log.error(e.getMessage());
            messageText = generateDefaultErrorMessages("An error occured.");
        } finally {
            message.setText(messageText);
        }
        message.setChatId(update.getMessage().getChatId());

        try {
            log.info("Send message");
            log.debug("Send message: " + message.getText());
            execute(message);
            log.info("Message sent.");
        } catch (TelegramApiException e) {
            // System.out.println(e.getMessage());
            log.error("Caught a Telegram API excpetion");
            log.error(e.getMessage());
        }
    }
}
