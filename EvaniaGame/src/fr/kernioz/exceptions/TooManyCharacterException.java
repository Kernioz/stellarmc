package fr.kernioz.exceptions;

public class TooManyCharacterException extends Exception {

    private static final long serialVersionUID = 1L;

    public TooManyCharacterException(String exceptionError){
        super("There is too much character (" + exceptionError + ")");
    }

}
