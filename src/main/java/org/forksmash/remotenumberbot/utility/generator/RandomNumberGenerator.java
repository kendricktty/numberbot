package org.forksmash.remotenumberbot.utility.generator;

import org.forksmash.remotenumberbot.utility.exception.ResultOverflowException;
import org.springframework.stereotype.Component;

import java.util.Random;

@Component
public class RandomNumberGenerator implements Generator {
    @Override
    public long generate(int numOfDigits) throws ResultOverflowException {
        Random random = new Random();
        int result = 0;
        for (int i = 0; i <= numOfDigits; i++) {
            random = new Random();
            int randomNumber = i == numOfDigits ? random.nextInt(10 - 1) + 1 : random.nextInt(10);
            result += ((int) Math.pow(10, i) * randomNumber);
        }
        if (result < 0) {
            throw new ResultOverflowException();
        }
        return result;
    }
}
