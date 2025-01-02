package org.forksmash.remotenumberbot.utility.message;

import org.springframework.stereotype.Component;

@Component
public class StartMessage implements Message {
    private HelpMessage helpMessage;
    public StartMessage() {}
    public StartMessage(HelpMessage helpMessage) {
        this.helpMessage = helpMessage;
    }
    // @Override
    // public String getMessageText() {
    //     return "Welcome to the NumberBot!\nGenerate a random number or power of 2 with this bot.\nType /help for a list of valid commands.";
    // }

    public void setHelpMessage(HelpMessage helpMessage) {
        this.helpMessage = helpMessage;
    }
    @Override
    public String getMessageText() {
        return helpMessage.getMessageText();
    }
}
