package org.forksmash.remotenumberbot.utility.generator;

import org.forksmash.remotenumberbot.utility.exception.ResultOverflowException;

public interface Generator {
    public long generate(int initialiser) throws ResultOverflowException;
}
