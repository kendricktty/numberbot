package org.forksmash.remotenumberbot.utility.exception;

public class ZeroSmallerInputException extends BotException {
    public ZeroSmallerInputException(int floor) {
        super("The number you provided is too small. Please enter a number greater than " + floor);
    }
}
