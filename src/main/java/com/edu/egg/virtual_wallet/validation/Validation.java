
package com.edu.egg.virtual_wallet.validation;

import com.edu.egg.virtual_wallet.entity.Account;
import com.edu.egg.virtual_wallet.entity.Payee;
import com.edu.egg.virtual_wallet.exception.InputException;
import com.edu.egg.virtual_wallet.exception.MyException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.edu.egg.virtual_wallet.exception.VirtualWalletException;

import java.time.LocalDate;
import java.time.Period;


public class Validation {

    public static void validationName(String name) throws MyException {
        if (name == null || name.isEmpty()) throw MyException.NotNull();

        if(!name.matches("^[a-zA-Z]+( [a-zA-Z]+)*$")) throw MyException.NameFormat();
    }
    public static void exitsPayee(Boolean payee, Payee payeeObj) throws MyException {
         if (!payee || payeeObj==null) throw MyException.NotExist();
     }
    public static void exitsAccount(Boolean accountNumber, Account accountObj) throws MyException {
         if (!accountNumber || accountObj==null) throw MyException.NotExist();
     }
    public static void checkReference(String reference) throws MyException {
        if (reference == null || reference.isEmpty()) throw MyException.NotNull();

        if(!reference.matches("[a-zA-Z ]{2,30}")) throw MyException.ReferenceFormat();
    }
    public static void notNullNegativeAmout(Double amount) throws MyException {
        if (amount == null || amount <= 0) throw MyException.amountNotNullNegative();
    }
    public static void insufficientBalance(Double balance , Double amount) throws MyException {
        if (balance == null || balance == 0 || balance < amount ) throw MyException.insufficientBalance();
    }

    public static void nullCheck(String userStringInput, String inputName) throws VirtualWalletException {
        if(userStringInput.trim().isEmpty() || userStringInput == null) throw new VirtualWalletException(inputName + " is a mandatory field");
    }
    public static void isLegallyOfAge (LocalDate customerDateOfBirth) throws VirtualWalletException {
        nullCheck(customerDateOfBirth.toString(), "Date of Birth");
        if (Period.between(customerDateOfBirth, LocalDate.now()).getYears() < 18) {
            throw new VirtualWalletException("You must be 18 or older to register!");
        }
    }
    public static void validNameCheck(String userName, String inputName) throws InputException {
        if (userName.trim().matches("^-?[0-9]+$")) { // TODO: Allow all Unicode Characters "\p{L}\p{M}*$"
            String name=" del nombre . Recuerde que solo contiene letras ";
            throw  InputException.incorrectFormatting(name);
        }
    }

    public static void validNumberCheck(Integer userNum, String inputName) throws InputException, VirtualWalletException {
        nullCheck(userNum.toString(), inputName);
        if (!userNum.toString().trim().matches("^[0-9]+$")) { // .*[0-9].*
            String numberCheck=" del numero de usuario . Recuerde que solo contiene numeros ";
            throw   InputException.incorrectFormatting(numberCheck);
        }
    }

    public static void validEmailCheck(String userEmail) throws VirtualWalletException, InputException {
        nullCheck(userEmail, "Email Address");
        if (!userEmail.trim().matches("^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$")) {
            String email =" del email . Verifique que escribio su dirección de mail correctamente";
            throw   InputException.incorrectFormatting(email);
        }
    }


    public static void validPhoneNumberCheck(Long phoneNumber) throws VirtualWalletException, InputException {
        if (!phoneNumber.toString().trim().matches("^(\\+\\d{1,3}( )?)?((\\(\\d{1,3}\\))|\\d{1,3})[- .]?\\d{3,4}[- .]?\\d{4}$")) {
            String PhoneNumber =" del numero de telefono ";
            throw  InputException.incorrectFormatting(PhoneNumber);
            // if (phoneNumber==null)

//        if (!phoneNumber.toString().trim().matches("^(\\+\\d{1,3}( )?)?((\\(\\d{1,3}\\))|\\d{1,3})[- .]?\\d{3,4}[- .]?\\d{4}$")) {
//            throw new VirtualWalletException("Invalid phone number format!");
//        }
    }
    /*
    Regex for phone number validation:
    https://stackoverflow.com/questions/42104546/java-regular-expressions-to-validate-phone-numbers/42105140
    https://www.baeldung.com/java-regex-validate-phone-numbers
     */
    public static void validPasswordCheck(String password) throws VirtualWalletException, InputException {
        nullCheck(password, "Password");
        if (!password.trim().matches("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}$")) {
            String pass =" de la contraseña. Asegurese de cumplir con la politica de contraseñas ";
            throw   InputException.incorrectFormatting(pass);
        }
    }
    //---DANA
}
