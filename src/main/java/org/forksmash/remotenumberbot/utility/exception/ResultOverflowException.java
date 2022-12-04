package org.forksmash.remotenumberbot.utility.exception;

public class ResultOverflowException extends Exception {

    public ResultOverflowException() {
        super("The number you provided is too large. Please enter a smaller number.");
    }
}
