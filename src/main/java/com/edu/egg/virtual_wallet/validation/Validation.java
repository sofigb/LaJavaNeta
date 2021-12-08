
package com.edu.egg.virtual_wallet.validation;

import com.edu.egg.virtual_wallet.entity.Account;
import com.edu.egg.virtual_wallet.entity.Payee;
import com.edu.egg.virtual_wallet.exception.MyException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.edu.egg.virtual_wallet.exception.VirtualWalletException;

import java.time.LocalDate;
import java.time.Period;


public class Validation {

    public static void validationName(String name) throws MyException {
        if (name == null || name.isEmpty()) {
            throw MyException.NotNull();
        }

        Pattern patterString = Pattern.compile("^[a-zA-Z]+( [a-zA-Z]+)*$");
       Matcher matcherName = patterString.matcher(name);

        if (!matcherName.matches()) {
            throw MyException.NameFormat();
        }
    }
     public static void exitsPayee(Boolean payee, Payee payeeObj) throws MyException {
         if (!payee || payeeObj==null) {
               throw MyException.NotExist();
          }
     }
      public static void exitsAccount(Boolean accountNumber, Account accountObj) throws MyException {
         if (!accountNumber || accountObj==null) {
               throw MyException.NotExist();
          }
     }

    public static void checkReference(String reference) throws MyException {
        if (reference == null || reference.isEmpty()) {
            throw MyException.NotNull();
        }

        Pattern patterString = Pattern.compile("[a-zA-Z ]{2,30}");
        Matcher matcherReference = patterString.matcher(reference);
        if (!matcherReference.matches()) {
            throw MyException.ReferenceFormat();
        }
    }

    public static void notNullNegativeAmout(Double amount) throws MyException {

        if (amount == null || amount <= 0) {
              throw MyException.amountNotNullNegative();
        }
    }
    public static void insufficientBalance(Double balance , Double amount) throws MyException {

        if (balance == null || balance == 0 || balance < amount ) {
              throw MyException.insufficientBalance();
        }
    }
    // Under Revision --DNA--

    public static void nullCheck(String userStringInput, String inputName) throws VirtualWalletException {
        if(userStringInput.trim().isEmpty() || userStringInput == null) {
            throw new VirtualWalletException(inputName + " is a mandatory field");
        }
    }

    public static void isLegallyOfAge (LocalDate customerDateOfBirth) throws VirtualWalletException {
        nullCheck(customerDateOfBirth.toString(), "Date of Birth");
        if (Period.between(customerDateOfBirth, LocalDate.now()).getYears() < 18) {
            throw new VirtualWalletException("You must be 18 or older to register!");
        }
    }

    public static void validNameCheck(String userName, String inputName) throws VirtualWalletException {
        if (userName.trim().matches("^-?[0-9]+$")) { // TODO: Allow all Unicode Characters "\p{L}\p{M}*$"
            throw new VirtualWalletException("Invalid " + inputName + "! Hint: Make sure " + inputName + "contains only letters"); // ?
        }
    }

    public static void validNumberCheck(Integer userNum, String inputName) throws VirtualWalletException {
        nullCheck(userNum.toString(), inputName);
        if (!userNum.toString().trim().matches("^[0-9]+$")) { // .*[0-9].*
            throw new VirtualWalletException("Invalid " + inputName + "! Hint: Make sure " + inputName + "contains only numbers");
        }
    }

    public static void validEmailCheck(String userEmail) throws VirtualWalletException {
        nullCheck(userEmail, "Email Address");
        if (!userEmail.trim().matches("^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$")) {
            throw new VirtualWalletException("Invalid email! Hint: Make sure you have typed your email address correctly");
        }
    }

    public static void validPhoneNumberCheck(Long phoneNumber) throws VirtualWalletException {
        if (!phoneNumber.toString().trim().matches("^(\\+\\d{1,3}( )?)?((\\(\\d{1,3}\\))|\\d{1,3})[- .]?\\d{3,4}[- .]?\\d{4}$")) {
            throw new VirtualWalletException("Invalid phone number format!");
        }

    }

    /*
    Regex for phone number validation:
    https://stackoverflow.com/questions/42104546/java-regular-expressions-to-validate-phone-numbers/42105140
    https://www.baeldung.com/java-regex-validate-phone-numbers
     */

    public static void validPasswordCheck(String password) throws VirtualWalletException {
        nullCheck(password, "Password");
        if (!password.trim().matches("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}$")) {
            throw new VirtualWalletException("Invalid password! Hint: Make sure you are complying with the password policy");
        }
    }
    //---DANA
}
