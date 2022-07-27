package org.forksmash.remotenumberbot.utility.tier_checker;

public class TierChecker {
    private final char[] tiers = {'D', 'C', 'B', 'A', 'R'};
    private int generatedNumber;

    public TierChecker(int generatedNumber) {
        this.generatedNumber = generatedNumber;
    }

    private char checkTier() {
        if (generatedNumber == 1024 || generatedNumber == 2048) {
            return tiers[tiers.length - 1];
        }

        int tierIndex = (int) Math.log10(generatedNumber);
        if (tierIndex < 3) {
            tierIndex = 0;
        } else {
            tierIndex -= 3;
        }
        return tierIndex > tiers.length - 2 ? tiers[tiers.length - 2] : tiers[tierIndex];
    }

    public String successMessage() {
        String returnValue = "The number returned is " + generatedNumber + "\nIt is suitable for use with a Category " + checkTier() + " account.";
        return returnValue;
    }
}
