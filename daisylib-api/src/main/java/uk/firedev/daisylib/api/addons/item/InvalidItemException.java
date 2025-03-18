package uk.firedev.daisylib.api.addons.item;

public class InvalidItemException extends RuntimeException {

    public InvalidItemException() {
        super();
    }

    public InvalidItemException(String message) {
        super(message);
    }

    public InvalidItemException(Throwable throwable) {
        super(throwable);
    }

}
