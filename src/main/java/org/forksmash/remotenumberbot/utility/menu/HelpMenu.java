package org.forksmash.remotenumberbot.utility.menu;

import org.springframework.stereotype.Component;

@Component
public class HelpMenu implements Menu {
    @Override
    public String getMenuText() {
        return "/start - Starts a new NumberBot session\n/help - Prints a list of available commands";
    }
}
