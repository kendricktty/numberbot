package org.forksmash.remotenumberbot.utility.message;

import org.springframework.stereotype.Component;

@Component
public class StartMessage implements Message {
    @Override
    public String getMessageText() {
        return "Available commands:\n/r - Generates a random number.\n/p - Generates a power of 2 greater than 32.\n\nEnter /r or /p, followed by a space, and then a number.";
    }
}
