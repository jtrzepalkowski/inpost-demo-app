package pl.jt.demo.inpost.domain;

import java.util.Arrays;
import java.util.Optional;

public enum PolicyType {

    AMOUNT_BASED("amount-based"),
    PERCENTAGE_BASED("percentage-based"),

    NO_DISCOUNT("no-discount");

    private final String stringFromRequest;

    PolicyType(String stringFromRequest) {
        this.stringFromRequest = stringFromRequest;
    }

    public static Optional<PolicyType> getByRequestString(String fromRequest) {
        return Arrays.stream(PolicyType.values())
                .filter(policyType -> policyType.stringFromRequest.equals(fromRequest))
                .findFirst();
    }
}
