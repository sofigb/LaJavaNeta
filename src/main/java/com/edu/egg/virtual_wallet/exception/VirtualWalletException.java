package com.edu.egg.virtual_wallet.exception;

public class VirtualWalletException extends Exception{

    private String message;

    public VirtualWalletException(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
