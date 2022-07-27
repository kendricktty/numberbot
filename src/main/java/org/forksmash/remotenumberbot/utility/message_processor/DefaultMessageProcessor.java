package org.forksmash.remotenumberbot.utility.message_processor;

import org.forksmash.remotenumberbot.utility.exception.InvalidInputException;
import org.forksmash.remotenumberbot.utility.generator.RandomNumberGenerator;
import org.forksmash.remotenumberbot.utility.generator.Power2Generator;
import org.forksmash.remotenumberbot.utility.menu.HelpMenu;
import org.forksmash.remotenumberbot.utility.menu.StartMenu;
import org.forksmash.remotenumberbot.utility.tier_checker.TierChecker;

public class DefaultMessageProcessor {
    private final String inboundMessage;
    public DefaultMessageProcessor(String inboundMessage) {
        this.inboundMessage = inboundMessage;
    }

    public String processMessage() {
        try {
            if (inboundMessage.equals("/help")) {
                HelpMenu helpMenu = new HelpMenu();
                return helpMenu.getMenuText();
            } else if (inboundMessage.equals("/start")) {
                StartMenu startMenu = new StartMenu();
                return startMenu.getMenuText();
            } else if (inboundMessage.startsWith("/r ")) {
                int numToProcess = sanitiseInput(inboundMessage);
                RandomNumberGenerator generator = new RandomNumberGenerator();
                int generatedNum = generator.generate(numToProcess);

                TierChecker tierChecker = new TierChecker(generatedNum);
                return tierChecker.successMessage();
            } else if (inboundMessage.startsWith("/p ")) {
                int numToProcess = sanitiseInput(inboundMessage);

                Power2Generator generator = new Power2Generator();
                int generatedNum = generator.generate(numToProcess);

//                generatedNum += addOrSubtract(generatedNum, inboundMessage);
                TierChecker tierChecker = new TierChecker(generatedNum);
                return tierChecker.successMessage();
            }
        } catch (InvalidInputException e) {
            return e.getMessage();
        }
        return "Please enter a valid command.";
    }

    public int sanitiseInput(String input) throws InvalidInputException {
        String[] inputList = inboundMessage.split(" ");
        if (inputList.length != 2) {
            throw new InvalidInputException("Invalid input. Enter /r or /p, followed by a space, and then a number.");
        }
        System.out.println(inputList[1]);
        try {
            return Integer.parseInt(inputList[1]);
        } catch (Exception e) {
            throw new InvalidInputException("Invalid input. Enter /r or /p, followed by a space, and then a number.");
        }
    }

    public int addOrSubtract(int generatedNumber, String inputString) {
        boolean isZero = inputString.contains("+") || inputString.contains("-");
        boolean isPlus = inputString.contains("+");

        if (isZero) {
            return 0;
        }
    }
}
