package org.forksmash.remotenumberbot.utility.exception;

public class ZeroSmallerInputException extends BotException {
    public ZeroSmallerInputException() {
        super("The number you provided is too small. Please enter a positive number (for /r) or number larger than 0 (for /p)");
    }
}
