package org.forksmash.remotenumberbot.utility.generator;

// import java.util.ArrayList;
// import java.util.Collections;
// import java.util.List;
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
            throw new ZeroSmallerInputException();
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
        int lastDigit = firstDigit;
        for (int i = numOfDigits - 2; i >= 0; i--) {
            random = new Random();
            int nextDigit;
            do {
                nextDigit = random.nextInt(10);
            } while (nextDigit == lastDigit);
            lastDigit = nextDigit;
            result += ((int) Math.pow(10, i) * nextDigit);
        }

        if (result < 0) {
            throw new ResultOverflowException();
        }
        return result;
    }

    // private int uniqueNumber(int numOfDigits) {
    //     List<Integer> digits = new ArrayList<>();
    //     for (int i = 0; i < 10; i++) {
    //         digits.add(i);
    //     }
    //     Collections.shuffle(digits);
    //     int number = 0;
    //     for (int i = 0; i < numOfDigits; i++) {
    //         number = number * 10 + digits.get(i);
    //     }
    //     return number;
    // }
}
