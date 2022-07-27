package org.forksmash.remotenumberbot.utility.menu;

public class StartMenu extends Menu {
    @Override
    public String getMenuText() {
        return "/r - Generates a random number.\n/p - Generates a power of 2 greater than 128.\n\nEnter /r or /p, followed by a space, and then a number.";
    }
}
