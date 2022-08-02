package org.forksmash.remotenumberbot.utility.validator;

import org.springframework.stereotype.Component;

@Component
public class AuthorisedUserValidator extends Validator {
    @Override
    public boolean validate(String chatId, String AUTHORISED_USER) {
        return chatId.equals(AUTHORISED_USER);
    }
}
