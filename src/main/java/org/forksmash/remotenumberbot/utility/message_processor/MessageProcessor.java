package org.forksmash.remotenumberbot.utility.message_processor;

public abstract class MessageProcessor {
    private String inboundMessage;
    public abstract String processMessage();
}
