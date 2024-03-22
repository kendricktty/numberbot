package org.forksmash.remotenumberbot.utility.exception;

public class ZeroSmallerInputException extends Exception {
    public ZeroSmallerInputException(int ceiling) {
        super("The number you provided is too small. Please enter a number greater than " + ceiling);
    }
}
