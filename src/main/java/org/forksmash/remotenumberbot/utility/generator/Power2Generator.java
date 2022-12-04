package org.forksmash.remotenumberbot.utility.generator;
import org.forksmash.remotenumberbot.utility.exception.ResultOverflowException;
import org.springframework.stereotype.Component;

@Component
public class Power2Generator implements Generator {

    @Override
    public long generate(int input) throws ResultOverflowException {
        long result = 128 * (int) Math.pow(2, input);
        if (result < 0) {
            throw new ResultOverflowException();
        }
        return result;
    }
}
