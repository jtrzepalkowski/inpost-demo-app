package pl.jt.demo.inpost.infra.exception;

public class InvalidValueProvidedException extends Throwable {

    private static final String ERROR_MESSAGE_FORMAT = "Provided value: %s is not a valid number.";

    public InvalidValueProvidedException(String valueAsString) {
        super(String.format(ERROR_MESSAGE_FORMAT, valueAsString));
    }
}
