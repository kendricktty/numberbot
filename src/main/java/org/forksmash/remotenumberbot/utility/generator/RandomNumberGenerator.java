package org.forksmash.remotenumberbot.utility.generator;

import java.util.Random;

import org.forksmash.remotenumberbot.utility.exception.ResultOverflowException;
import org.forksmash.remotenumberbot.utility.exception.ZeroSmallerInputException;
import org.springframework.stereotype.Component;

@Component
public class RandomNumberGenerator implements Generator {
    private final int MAX_DIGITS = 10;
    @Override
    public long generate(int numOfDigits) throws ResultOverflowException, ZeroSmallerInputException {
        int ceiling = 0;
        if (numOfDigits <= ceiling) {
            throw new ZeroSmallerInputException(ceiling);
        }
        
        if (numOfDigits > MAX_DIGITS) {
            throw new ResultOverflowException();
        }

        Random random = new Random();
        int result = 0;

        // Generate the most significant digit (first digit) separately to ensure it's not zero
        int firstDigit = random.nextInt(9) + 1; // Generate a random number between 1 and 9
        result += firstDigit * (int) Math.pow(10, numOfDigits - 1);
        if (result >= INT_MAX) {
            throw new ResultOverflowException();
        }

        // Generate the remaining digits
        for (int i = numOfDigits - 2; i >= 0; i--) {
            random = new Random();
            int randomNumber = random.nextInt(10);
            result += ((int) Math.pow(10, i) * randomNumber);
        }

        if (result < 0) {
            throw new ResultOverflowException();
        }
        return result;
    }
}
