package org.forksmash.remotenumberbot.utility.validator;

public class AuthorisedUserValidator extends Validator {
    @Override
    public boolean validate(String chatId, String AUTHORISED_USER) {
        return chatId.equals(AUTHORISED_USER);
    }
}
