
package com.edu.egg.virtual_wallet.validation;

import com.edu.egg.virtual_wallet.exception.MyException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class Validation {
    
    
    public static void validationName(String name) throws MyException {
        if (name == null || name.isEmpty()) {
            throw MyException.nameNotNull();
        }

        Pattern patterString = Pattern.compile("^[a-zA-Z]+( [a-zA-Z]+)*$");
        Matcher matcherName = patterString.matcher(name);
        if (!matcherName.matches()) {
            throw MyException.nameFormat();
        }
    }
}
