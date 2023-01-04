package pl.jt.demo.inpost.infra.exception;

public class NoSuchProductFoundException extends Exception {

    private static final String ERROR_MESSAGE_FORMAT = "No product with id: %s found.";

    public NoSuchProductFoundException(String uuid) {
        super(String.format(ERROR_MESSAGE_FORMAT, uuid));
    }
}
