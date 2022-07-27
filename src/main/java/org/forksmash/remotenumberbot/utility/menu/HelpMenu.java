package org.forksmash.remotenumberbot.utility.menu;

public class HelpMenu extends Menu {
    @Override
    public String getMenuText() {
        String helpMessage = "/start - Starts a new NumberBot session\n/help - Prints a list of available commands";
        return helpMessage;
    }
}
