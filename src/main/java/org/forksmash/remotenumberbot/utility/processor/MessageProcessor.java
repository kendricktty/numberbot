package org.forksmash.remotenumberbot.utility.processor;

import org.forksmash.remotenumberbot.utility.exception.InvalidInputException;
import org.forksmash.remotenumberbot.utility.exception.ResultOverflowException;
import org.forksmash.remotenumberbot.utility.exception.ZeroSmallerInputException;
import org.forksmash.remotenumberbot.utility.generator.Generator;
import org.forksmash.remotenumberbot.utility.generator.RandomNumberGenerator;

import org.forksmash.remotenumberbot.utility.generator.Power2Generator;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class MessageProcessor {
    private final RandomNumberGenerator randomNumberGenerator;
    private final Power2Generator power2Generator;

    // @Autowired
    public MessageProcessor(RandomNumberGenerator randomNumberGenerator,
            Power2Generator power2Generator) {
        this.randomNumberGenerator = randomNumberGenerator;
        this.power2Generator = power2Generator;
    }

    public String processRequest(String request) throws InvalidInputException, ResultOverflowException, ZeroSmallerInputException {
        Generator generator = null;
        if (request.startsWith("/r ")) {
            log.info("Generate random number");
            generator = randomNumberGenerator;
        } else if (request.startsWith("/p ")) {
            log.info("Generate power of 2");
            generator = power2Generator;
        } else {
            log.error("Invalid input.");
            throw new InvalidInputException("Invalid input.");
        }

        int[] numbersToProcess = getNumbersFromInputString(request);
        int generatorInput = numbersToProcess[0];
        int offset = numbersToProcess[1];
        long generatedNum = generator.generate(generatorInput);
        generatedNum += offset;

        return "The number returned is " + generatedNum + ".\n";
    }

    private int[] getNumbersFromInputString(String inputString) throws InvalidInputException {
        int[] numbers = new int[2];

        String[] inputList = inputString.split(" ");
        if (inputList.length != 2) {
            throw new InvalidInputException("Invalid input. Enter /r or /p, followed by a space, and then a number.");
        }
        String[] withPlusOrMinus = null;

        boolean containsPlus = containsPlus(inputString);
        if (containsPlus(inputString)) {
            withPlusOrMinus = inputList[1].split("\\+");
        } else {
            withPlusOrMinus = inputList[1].split("-");
        }
        // Index 0 is the input for the generator, index 1 is the offset
        int generatorInput = parseInteger(withPlusOrMinus[0]);
        int offset = 0;
        if (withPlusOrMinus.length == 2) {
             offset = parseInteger(withPlusOrMinus[1]);
        }
        offset = addOrSubtract(offset, containsPlus);

        numbers[0] = generatorInput;
        numbers[1] = offset;
        return numbers;
    }

    private int parseInteger(String input) throws InvalidInputException {
        return Integer.parseInt(input);
    }

    private boolean containsPlus(String inputString) {
        return inputString.contains("+");
    }

    private int addOrSubtract(int input, boolean containsPlus) {
        int processedInt = input;
        if (!containsPlus) {
            processedInt *= -1;
        }
        return processedInt;
    }
}
