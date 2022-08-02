package org.forksmash.remotenumberbot.utility.message_processor;

import org.forksmash.remotenumberbot.utility.exception.InvalidInputException;
import org.forksmash.remotenumberbot.utility.generator.Generator;
import org.forksmash.remotenumberbot.utility.generator.RandomNumberGenerator;
import org.forksmash.remotenumberbot.utility.generator.Power2Generator;
import org.forksmash.remotenumberbot.utility.menu.HelpMenu;
import org.forksmash.remotenumberbot.utility.menu.StartMenu;
import org.forksmash.remotenumberbot.utility.tier_checker.TierChecker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DefaultMessageProcessor {

    private String inboundMessage;
    private final HelpMenu helpMenu;

    private final StartMenu startMenu;
    private final RandomNumberGenerator randomNumberGenerator;
    private final Power2Generator power2Generator;
    private final TierChecker tierChecker;

    @Autowired
    public DefaultMessageProcessor(HelpMenu helpMenu, StartMenu startMenu, RandomNumberGenerator randomNumberGenerator, Power2Generator power2Generator, TierChecker tierChecker) {
        this.helpMenu = helpMenu;
        this.startMenu = startMenu;
        this.randomNumberGenerator = randomNumberGenerator;
        this.power2Generator = power2Generator;
        this.tierChecker = tierChecker;
    }

    public String getInboundMessage() {
        return inboundMessage;
    }

    public void setInboundMessage(String inboundMessage) {
        this.inboundMessage = inboundMessage;
    }
    @Autowired
    public String processMessage() {
        Generator generator = null;
        String defaultMessage = "";
        try {
            if (inboundMessage.equals("/help")) {
                return helpMenu.getMenuText();
            } else if (inboundMessage.equals("/start")) {
                return startMenu.getMenuText();
            }

            if (inboundMessage.startsWith("/r ")) {
                generator = randomNumberGenerator;
            } else if (inboundMessage.startsWith("/p ")) {
                generator = power2Generator;
            }

            int numToProcess = sanitiseInput(inboundMessage);
            int generatedNum = generator.generate(numToProcess);
            return tierChecker.successMessage();
        } catch (InvalidInputException e) {
            defaultMessage += e.getMessage() + "\n\n";
        }
        return defaultMessage + "Please enter a valid command.";
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
        String numberToAddOrSubtract = isPlus ? inputString.substring(inputString.indexOf('+')) : inputString.substring(inputString.indexOf('-'));
        return Integer.parseInt(numberToAddOrSubtract);
    }
}
