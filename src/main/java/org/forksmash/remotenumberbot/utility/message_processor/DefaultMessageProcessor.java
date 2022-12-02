package org.forksmash.remotenumberbot.utility.message_processor;

import org.forksmash.remotenumberbot.utility.exception.InvalidInputException;
import org.forksmash.remotenumberbot.utility.generator.Generator;
import org.forksmash.remotenumberbot.utility.generator.RandomNumberGenerator;
import org.forksmash.remotenumberbot.utility.generator.Power2Generator;
import org.forksmash.remotenumberbot.utility.menu.HelpMenu;
import org.forksmash.remotenumberbot.utility.menu.StartMenu;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DefaultMessageProcessor {
    private final HelpMenu helpMenu;

    private final StartMenu startMenu;
    private final RandomNumberGenerator randomNumberGenerator;
    private final Power2Generator power2Generator;

    @Autowired
    public DefaultMessageProcessor(HelpMenu helpMenu, StartMenu startMenu, RandomNumberGenerator randomNumberGenerator, Power2Generator power2Generator) {
        this.helpMenu = helpMenu;
        this.startMenu = startMenu;
        this.randomNumberGenerator = randomNumberGenerator;
        this.power2Generator = power2Generator;
    }

    public String processMessage(String inboundMessage) {
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

            int numToProcess = parseInteger(inboundMessage);
            int generatedNum = generator.generate(numToProcess);
            
            return "The number returned is " + generatedNum + ".\n";
        } catch (InvalidInputException e) {
            return defaultMessage += e.getMessage() + "\n\n";
        }
    }

    private int parseInteger(String input) throws InvalidInputException {
        String[] inputList = input.split(" ");
        if (inputList.length != 2) {
            return -1;
        }
        String[] withPlusOrMinus = null;
        
        boolean containsPlus = containsPlus(input);
        boolean containsMinus = containsMinus(input);
        if (containsPlus(input)) {
            withPlusOrMinus = inputList[1].split("+");
        } else {
            withPlusOrMinus = inputList[1].split("-");
        }
        int integerToReturn = Integer.parseInt(withPlusOrMinus[0]);

        boolean isZero = !containsMinus && !containsPlus;
        return integerToReturn + addOrSubtract(integerToReturn, isZero, containsPlus);
    }

    private boolean containsPlus(String inputString) {
        return inputString.contains("+");
    }

    private boolean containsMinus(String inputString) {
        return inputString.contains("-");
    }

    private int addOrSubtract(int input, boolean isZero, boolean isPlus) {
        if (isZero) {
            return 0;
        }
        int processedInt = input;
        if (!isPlus) {
            processedInt *= -1;
        }
        return processedInt;
    }
}
