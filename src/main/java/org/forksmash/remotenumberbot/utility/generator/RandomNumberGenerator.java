package org.forksmash.remotenumberbot.utility.generator;

import org.springframework.stereotype.Component;

import java.util.Random;

@Component
public class RandomNumberGenerator implements Generator {
    @Override
    public int generate(int pow) {
        Random random = new Random();
        int result = 0;
        for (int i = 0; i <= pow; i++) {
            random = new Random();
            int randomNumber = i == pow ? random.nextInt(10 - 1) + 1 : random.nextInt(10);
            result += ((int) Math.pow(10, i) * randomNumber);
        }
        return result;
    }
}
