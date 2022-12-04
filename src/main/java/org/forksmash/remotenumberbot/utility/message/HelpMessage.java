package org.forksmash.remotenumberbot.utility.message;

import org.springframework.stereotype.Component;

@Component
public class HelpMessage implements Message {
    @Override
    public String getMessageText() {
        return "/start - Starts a new NumberBot session\n/help - Prints a list of available commands";
    }
}
