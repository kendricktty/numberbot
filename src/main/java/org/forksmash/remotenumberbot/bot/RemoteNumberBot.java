package org.forksmash.remotenumberbot.bot;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.forksmash.remotenumberbot.utility.exception.BotException;
import org.forksmash.remotenumberbot.utility.exception.InvalidInputException;
import org.forksmash.remotenumberbot.utility.exception.ResultOverflowException;
import org.forksmash.remotenumberbot.utility.exception.ZeroSmallerInputException;
import org.forksmash.remotenumberbot.utility.message.DefaultMessage;
import org.forksmash.remotenumberbot.utility.message.HelpMessage;
import org.forksmash.remotenumberbot.utility.message.StartMessage;
import org.forksmash.remotenumberbot.utility.processor.MessageProcessor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import org.telegram.telegrambots.meta.api.methods.commands.SetMyCommands;
import org.telegram.telegrambots.meta.api.objects.commands.BotCommand;

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
    private DefaultMessage defaultMessage;

    // @Autowired
    public RemoteNumberBot(@Value("${bot.USERNAME}") String USERNAME, @Value("${bot.TOKEN}") String TOKEN,
            MessageProcessor processor, StartMessage startMessage, HelpMessage helpMessage, DefaultMessage defaultMessage) {
        this.USERNAME = USERNAME;
        this.TOKEN = TOKEN;
        this.processor = processor;
        this.helpMessage = helpMessage;
        this.startMessage = startMessage;
        this.defaultMessage = defaultMessage;
        startMessage.setHelpMessage(helpMessage);
        setBotCommands();
        log.info("Welcome to the NumberBot");
        log.info("This bot generates random numbers or powers of 2.");
    }

    private void setBotCommands() {
        log.info("Setting bot commands");
        List<BotCommand> commandsList = new LinkedList<>();
        Map<String, String> commandsMap = helpMessage.getCommands();
        for (String command : commandsMap.keySet()) {
            String commandDescription = commandsMap.get(command);
            log.debug(command + ": " + commandDescription);
            commandsList.add(new BotCommand(command, commandDescription));
        }

        SetMyCommands setMyCommands = new SetMyCommands();
        setMyCommands.setCommands(commandsList);
        try {
            execute(setMyCommands);
        } catch (TelegramApiException e) {
            // System.out.println(e.getMessage());
            handleAPIException(e);
        }
        log.info("Commands set");
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
        return errorMessage + "\n\n" + defaultMessage.getMessageText();
    }

    @Override
    public void onUpdateReceived(Update update) {
        SendMessage message = new SendMessage();
        message.enableHtml(true);
        String messageText = null;
        try {
            if (update.hasMessage() && update.getMessage().hasText()) {
                String receivedMessage = update.getMessage().getText();
                log.debug("Received message: " + receivedMessage);
                messageText = initialProcess(receivedMessage);
            } else {
                throw new InvalidInputException("Invalid input.");
            }
        } catch (BotException e) {
            log.error(e.getMessage());
            messageText = generateDefaultErrorMessages(e.getMessage());
        } catch (Exception e) {
            log.error(e.getMessage());
            log.debug(e.getStackTrace().toString());
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
            handleAPIException(e);
        }
    }

    private void handleAPIException(TelegramApiException e) {
        log.error("Caught a Telegram API excpetion");
        log.error(e.getMessage());
    }
}
