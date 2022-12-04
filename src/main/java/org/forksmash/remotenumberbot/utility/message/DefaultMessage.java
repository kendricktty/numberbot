package org.forksmash.remotenumberbot.utility.message;

public class DefaultMessage implements Message {
    @Override
    public String getMessageText() {
        return "Type /start for a list of valid commands.";
    }
}
