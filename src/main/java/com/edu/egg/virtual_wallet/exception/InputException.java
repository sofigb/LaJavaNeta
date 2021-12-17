package com.edu.egg.virtual_wallet.exception;

public class InputException extends Exception {

    public InputException(InputException e){}

    public InputException(String ms) {super(ms);}

    public static InputException NotCreated(String entity){
        return new InputException("No se puede crear " + entity);
    }

    public static InputException NotDeleted(String entity){return new InputException("No se puede eliminar "+entity);}

    public static InputException NotFound(String entity){return new InputException("No se ha podido encontrar "+entity);}

    public static InputException NotRetrievedData(String entity){return new InputException("No se pueden recuperar los datos "+entity);}

    public static InputException NotReturned(String entity){return new InputException("No se ha podido retornar "+entity);}

    public static InputException NotEdited(String entity){return new InputException( "No se ha podido  editar "+entity);}

    public static InputException RepeatedData(String entity){return new InputException(entity + " ya fue utilizado");}

    public static InputException incorrectFormatting(String entity){return new InputException("Formato incorrecto "+entity);}
}
