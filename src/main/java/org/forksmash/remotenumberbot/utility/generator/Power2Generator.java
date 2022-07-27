package org.forksmash.remotenumberbot.utility.generator;

public class Power2Generator extends Generator {
    @Override
    public int generate(int input) {
        int result = 128 * (int) Math.pow(2, input);
        return result;
    }
}
