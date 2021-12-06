package com.edu.egg.virtual_wallet.exception;

public class MyException extends Exception {

    public MyException() {
    }

    public MyException(String ms) {
        super(ms);
    }

    public static MyException nameFormat() {

        return new MyException("El campo solo puede contener letras");
    }

    public static MyException nameNotNull() {

        return new MyException("El nombre  es obligatorio");
    }
}
