package uk.firedev.daisylib.api.addons.reward;

public class InvalidRewardException extends RuntimeException {

    public InvalidRewardException() {
        super();
    }

    public InvalidRewardException(String message) {
        super(message);
    }

    public InvalidRewardException(Throwable throwable) {
        super(throwable);
    }

}
