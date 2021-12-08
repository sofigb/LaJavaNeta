public class InputException extends Exception {

    public static InputException NotCreated(String entity){
        return new InputException("No se puede crear " + entity);
    }
    public static InputException NotDeleted(String entity){
        return new InputException("No se puede eliminar "+entity);
    }
    public static InputException NotFound(String entity){
        return new InputException("No se ha podido encontrar"+entity);
    }
    public static InputException NotRetrievedData(String entity){
        return new InputException("No se pueden recuperar los datos "+entity);
    }
    public static InputException NotReturned(String entity){
        return new InputException("No se ha podido retornar "+entity);//al o el ?
    }
    public static InputException NotEdited(String entity){
        return new InputException( "No se ha podido  editar"+entity);
    }

    public static InputException NotFoundName(String name){
        return new InputException("No se ha podido encontrar el nombre "+entity);
    }

    public static InputException UsedEmail(String email){
        return new InputException("El Email " + email +  " ya fue utilizado");
    }

}
