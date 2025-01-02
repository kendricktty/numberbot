package org.forksmash.remotenumberbot.utility.message;

import org.springframework.stereotype.Component;

@Component
public class DefaultMessage implements Message {
    @Override
    public String getMessageText() {
        return "Type /help or refer to the command list for a list of valid commands.";
    }
}
