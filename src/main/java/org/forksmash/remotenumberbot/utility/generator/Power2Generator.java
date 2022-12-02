package org.forksmash.remotenumberbot.utility.generator;
import org.springframework.stereotype.Component;

@Component
public class Power2Generator implements Generator {

    @Override
    public int generate(int input) {
        return 128 * (int) Math.pow(2, input);
    }
}
