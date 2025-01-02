package org.forksmash.remotenumberbot.utility.message;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Component;

@Component
public class HelpMessage implements Message {
    private Map<String, String> commands;
    public HelpMessage() {
        commands = new HashMap<>();
        // commands.put("/start", "Starts a new NumberBot session");
        commands.put("/help", "Prints available commands and usage");
        commands.put("/r", "Generate a random number. Append a space followed by a number m from 1 through 9 to generate a random m-digit number.");
        commands.put("/p", "Compute a power of 2. Append a space followed by a number n to return 2^n.");
    }
    
    public Map<String, String> getCommands() {
        return commands;
    }

    @Override
    public String getMessageText() {
        StringBuilder sb = new StringBuilder("");
        for (String command : commands.keySet()) {
            sb.append(command);
            sb.append(": ");
            sb.append(commands.get(command));
            sb.append("\n");
        }
        return sb.toString();
    }
}
