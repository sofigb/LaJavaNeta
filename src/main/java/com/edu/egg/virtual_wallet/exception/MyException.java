package com.edu.egg.virtual_wallet.exception;

public class MyException extends Exception {

    public MyException() {
    }

    public MyException(String ms) {
        super(ms);
    }

    public static MyException NameFormat() {

        return new MyException("El nombre solo puede contener letras.");
    }
    
     public static MyException ReferenceFormat() {

        return new MyException("El campo solo acepta hasta 30 caracteres alfabéticos.");
    }

    public static MyException NotNull() {

        return new MyException("El campo es obligatorio");
    }
    
    
    public static MyException NotExist() {

        return new MyException("No existe contacto frecuente");
    }
    
     public static MyException amountNotNullNegative() {

        return new MyException("El monto ingresado debe ser mayor a 0");
    }
     public static MyException insufficientBalance() {

        return new MyException("El saldo de su cuenta es insuficiente para realizar la operación");
    }
}
