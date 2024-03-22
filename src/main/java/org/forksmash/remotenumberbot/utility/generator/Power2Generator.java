package org.forksmash.remotenumberbot.utility.generator;
import org.forksmash.remotenumberbot.utility.exception.ResultOverflowException;
import org.forksmash.remotenumberbot.utility.exception.ZeroSmallerInputException;
import org.springframework.stereotype.Component;

@Component
public class Power2Generator implements Generator {
    private final int MAX_VALUE = 99999999;
    @Override
    public long generate(int input) throws ResultOverflowException, ZeroSmallerInputException {
        int ceiling = -1;
        if (input <= ceiling) {
            throw new ZeroSmallerInputException(ceiling);
        }
        int result = (int) Math.pow(2, input + 5);
        if (result < 0 || result > MAX_VALUE || result >= INT_MAX) {
            throw new ResultOverflowException();
        }
        return result;
    }
}
