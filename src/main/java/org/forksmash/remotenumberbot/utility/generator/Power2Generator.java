package org.forksmash.remotenumberbot.utility.generator;

public class Power2Generator extends Generator {
    @Override
    public int generate(int input) {
        int result = 128 * (int) Math.pow(2, input);
        if (result == 4096) {
            result *= 2;
        } else if (result == 2048) {
            result *= 4;
        } else if (result == 1024) {
            result *= 8;
        }
        return result;
    }
}
