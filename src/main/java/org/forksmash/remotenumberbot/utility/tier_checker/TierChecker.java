package org.forksmash.remotenumberbot.utility.tier_checker;

import org.checkerframework.checker.units.qual.C;
import org.springframework.stereotype.Component;

@Component
public class TierChecker {
    private final char[] tiers = {'D', 'C', 'B', 'A', 'R'};

    private char checkTier(int generatedNumber) {
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

    public String successMessage(int generatedNumber) {
        String returnValue = "The number returned is " + generatedNumber + "\nIt is suitable for use with a Category " + checkTier(generatedNumber) + " account.";
        return returnValue;
    }
}
