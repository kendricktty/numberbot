package org.forksmash.remotenumberbot.utility.tier_checker;

public class TierChecker {
    private final char[] tiers = {'D', 'C', 'B', 'A', 'R'};
    private final int generatedNumber;
    private final boolean isRandomNumber;

    public TierChecker(int generatedNumber, boolean isRandomNumber) {
        this.generatedNumber = generatedNumber;
        this.isRandomNumber = isRandomNumber;
    }

    private char checkTier() {
        if (isRandomNumber) {
            if (generatedNumber > tiers.length - 1) {
                generatedNumber = tiers.length - 2;
            }
            return tiers[(int) Math.log10(generatedNumber)];
        } else if (generatedNumber == 1024 || generatedNumber == 2048) {
            return tiers[tiers.length - 1];
        } else if (generatedNumber < 256) {
            return tiers[0];
        } else if (generatedNumber < 1024) {
            return tiers[1];
        } else if (generatedNumber < 10000) {
            return tiers[2];
        } else {
            return tiers[3];
        }
    }

    public String successMessage() {
        String returnValue = "The number returned is " + generatedNumber + "\nIt is suitable for use with a Category " + checkTier() + " account.";
        return returnValue;
    }
}
