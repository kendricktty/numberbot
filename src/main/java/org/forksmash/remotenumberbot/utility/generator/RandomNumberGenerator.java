package org.forksmash.remotenumberbot.utility.generator;

import java.util.Random;

public class RandomNumberGenerator extends Generator {
    @Override
    public int generate(int pow) {
        Random random = new Random();
        int result = random.nextInt(10 - 1) + 1;
        for (int i = 1; i <= pow; i++) {
            random = new Random();
            int randomNumber = random.nextInt(10);
            result += ((int) Math.pow(10, i) + randomNumber);
        }
        return result;
    }
}
