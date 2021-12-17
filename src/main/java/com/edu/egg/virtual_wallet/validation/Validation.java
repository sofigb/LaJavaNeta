
package com.edu.egg.virtual_wallet.validation;


import com.edu.egg.virtual_wallet.exception.InputException;



public class Validation {
    private final String name=" del nombre .Recuerde que solo puede contener letras ";

    public static void validationName(String name) throws InputException {
        if (name == null || name.isEmpty()) throw InputException.NotNull("nombre");

        if(!name.matches("^[a-zA-Z]+( [a-zA-Z]+)*$")) throw InputException.incorrectFormatting(name);
    }

    public static void checkReference(String reference) throws InputException {
        if (reference == null || reference.isEmpty()) throw InputException.NotNull("de referencia de las transacciones");

        if(!reference.matches("[a-zA-Z ]{2,30}")){
             String refer=" .El campo solo acepta hasta 30 caracteres alfabéticos";
            throw InputException.incorrectFormatting(refer);
        }

    }

    public static void notNullNegativeAmount(Double amount) throws InputException {
        if (amount == null || amount <= 0) {
            String insufAmount= "El monto ingresado ";
            String help = ".Recuerde que debe ser mayor a cero";
            throw InputException.insufficient(insufAmount,help);
        }
    }

    public static void insufficientBalance(Double balance , Double amount) throws InputException {
        if (balance == null || balance == 0 || balance < amount ){
            String insufBalance="El saldo de su cuenta ";
            String help = "";
            throw InputException.insufficient(insufBalance,help);
        }
    }

    public static void nullCheck(String userStringInput, String inputName) throws InputException {
        if(userStringInput.trim().isEmpty() || userStringInput == null) throw new InputException(inputName + " is a mandatory field");
    }
  /*  public static void isLegallyOfAge (LocalDate customerDateOfBirth) throws InputException {
        nullCheck(customerDateOfBirth.toString(), "Date of Birth");
        if (Period.between(customerDateOfBirth, LocalDate.now()).getYears() < 18) {
            String age = "La edad";
            String help = ".Recuerde que debe ser mayor a 18 años";
            throw  InputException.insufficient(age,help);
        }
    }
*/
    public static void validNameCheck(String userName, String inputName) throws InputException {
        if (userName.trim().matches("^-?[0-9]+$")) { // TODO: Allow all Unicode Characters "\p{L}\p{M}*$"
            String name=" del nombre . Recuerde que solo contiene letras ";
            throw  InputException.incorrectFormatting(name);
        }
    }
  /*  public static void validNumberCheck(Integer userNum, String inputName) throws InputException {
        nullCheck(userNum.toString(), inputName);
        if (!userNum.toString().trim().matches("^[0-9]+$")) {
            String numberCheck=" del numero de usuario . Recuerde que solo contiene numeros ";
            throw   InputException.incorrectFormatting(numberCheck);
        }
    }
*/
    public static void validEmailCheck(String userEmail) throws InputException {
        nullCheck(userEmail, "Email Address");
        if (!userEmail.trim().matches("^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$")) {
            String email =" del email . Verifique que escribio su dirección de mail correctamente";
            throw   InputException.incorrectFormatting(email);
        }
    }

    public static void validPhoneNumberCheck(Long phoneNumber) throws InputException {
        if (!phoneNumber.toString().trim().matches("^(\\+\\d{1,3}( )?)?((\\(\\d{1,3}\\))|\\d{1,3})[- .]?\\d{3,4}[- .]?\\d{4}$")) {
            String PhoneNumber =" del numero de telefono ";
            throw  InputException.incorrectFormatting(PhoneNumber);
        }
    }

    public static void validPasswordCheck(String password) throws InputException {
        nullCheck(password, "Password");
        if (!password.trim().matches("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}$")) {
            String pass =" de la contraseña. Asegurese de cumplir con la politica de contraseñas ";
            throw   InputException.incorrectFormatting(pass);
        }
    /*
    Regex for phone number validation:
    https://stackoverflow.com/questions/42104546/java-regular-expressions-to-validate-phone-numbers/42105140
    https://www.baeldung.com/java-regex-validate-phone-numbers
     */
//    public static void validPasswordCheck(String password) throws  InputException {
//        nullCheck(password, "Password");
//        if (!password.trim().matches("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}$")) {
//            String pass =" de la contraseña. Asegurese de cumplir con la politica de contraseñas ";
//            throw   InputException.incorrectFormatting(pass);
//        }
//    }
    //---DANA

    }
}
