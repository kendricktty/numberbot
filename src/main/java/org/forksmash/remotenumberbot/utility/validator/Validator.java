package org.forksmash.remotenumberbot.utility.validator;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public abstract class Validator {
    public abstract boolean validate(String input, String checkAgainst);
}
