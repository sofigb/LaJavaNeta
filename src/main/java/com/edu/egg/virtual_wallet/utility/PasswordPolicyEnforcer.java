package com.edu.egg.virtual_wallet.utility;

import com.edu.egg.virtual_wallet.exception.VirtualWalletException;
import org.passay.*;

import java.util.ArrayList;
import java.util.List;

public class PasswordPolicyEnforcer {

    public static List<Rule> passwordPolicy() {
        List<Rule> rules = new ArrayList<>();

        rules.add(new LengthRule(8, 12));
        rules.add(new WhitespaceRule());
        rules.add(new CharacterRule(EnglishCharacterData.UpperCase, 1));
        rules.add(new CharacterRule(EnglishCharacterData.LowerCase, 1));
        rules.add(new CharacterRule(EnglishCharacterData.Digit, 1));
        rules.add(new CharacterRule(EnglishCharacterData.Special, 1));
        return rules;
    }

    public static void validatePassword(String generatedPassword) throws VirtualWalletException {
        PasswordValidator validator = new PasswordValidator(passwordPolicy());
        RuleResult result = validator.validate(new PasswordData(generatedPassword));

        if (!result.isValid()) {
            throw new VirtualWalletException("Invalid password! Hint: Make sure you are complying with the password policy");
        }
    }

    public static String generatePassword() throws VirtualWalletException{
        PasswordGenerator passwordGenerator = new PasswordGenerator();

        List<CharacterRule> characterRules = new ArrayList<>();
        characterRules.add(new CharacterRule(EnglishCharacterData.UpperCase, 2));
        characterRules.add(new CharacterRule(EnglishCharacterData.LowerCase, 2));
        characterRules.add(new CharacterRule(EnglishCharacterData.Digit, 2));
        characterRules.add(new CharacterRule(EnglishCharacterData.Special, 2));

        String password = passwordGenerator.generatePassword(8, characterRules);
        validatePassword(password);

        return password;
    }
}