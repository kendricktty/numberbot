package org.forksmash.remotenumberbot.utility.generator;

import org.forksmash.remotenumberbot.utility.exception.ResultOverflowException;
import org.forksmash.remotenumberbot.utility.exception.ZeroSmallerInputException;

public interface Generator {
    final int INT_MAX = Integer.MAX_VALUE;
    public long generate(int initialiser) throws ResultOverflowException, ZeroSmallerInputException;
}
