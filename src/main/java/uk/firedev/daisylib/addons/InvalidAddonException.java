package uk.firedev.daisylib.addons;

public class InvalidAddonException extends RuntimeException {

    public InvalidAddonException() {
        super();
    }

    public InvalidAddonException(String message) {
        super(message);
    }

    public InvalidAddonException(Throwable throwable) {
        super(throwable);
    }

}
