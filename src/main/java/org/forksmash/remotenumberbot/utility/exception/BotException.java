package org.forksmash.remotenumberbot.utility.exception;

public class BotException extends Exception {
    public BotException(String message) {
        super(message);
    }

    public BotException(String message, Throwable cause) {
        super(message, cause);
    }
}
