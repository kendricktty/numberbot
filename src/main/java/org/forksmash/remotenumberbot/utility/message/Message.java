package org.forksmash.remotenumberbot.utility.message;

import org.springframework.stereotype.Component;

@Component
public interface Message {
    public String getMessageText();
}
