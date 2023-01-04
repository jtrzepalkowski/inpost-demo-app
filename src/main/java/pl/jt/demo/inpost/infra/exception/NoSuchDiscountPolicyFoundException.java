package pl.jt.demo.inpost.infra.exception;

public class NoSuchDiscountPolicyFoundException extends Exception {

    private static final String ERROR_MESSAGE_FORMAT =
            "No such type of discount policy type: %s found.";

    public NoSuchDiscountPolicyFoundException(String policyType) {
        super(String.format(ERROR_MESSAGE_FORMAT, policyType));
    }
}
