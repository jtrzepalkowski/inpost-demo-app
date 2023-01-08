package pl.jt.demo.inpost.infra.service;

import org.springframework.stereotype.Service;
import pl.jt.demo.inpost.domain.DiscountPolicy;
import pl.jt.demo.inpost.domain.PolicyType;
import pl.jt.demo.inpost.infra.exception.NoSuchDiscountPolicyFoundException;
import pl.jt.demo.inpost.infra.exception.InvalidValueProvidedException;
import pl.jt.demo.inpost.infra.repository.DiscountPolicyRepository;

import java.math.BigDecimal;

@Service
public class DiscountPolicyUpdateService {

    private final DiscountPolicyRepository discountPolicyRepository;

    DiscountPolicyUpdateService(final DiscountPolicyRepository discountPolicyRepository) {
        this.discountPolicyRepository = discountPolicyRepository;
    }

    public DiscountPolicy updateGivenDiscountPolicyWithNewValues(String policyTypeFromRequest,
                                                                 String basicPercentagePolicyDiscount,
                                                                 String maxDiscountForAmountPolicy,
                                                                 String discountIncreasePerStepForAmountPolicy)
            throws NoSuchDiscountPolicyFoundException, InvalidValueProvidedException {

        PolicyType policyType = PolicyType.getByRequestString(policyTypeFromRequest)
                .orElseThrow(() -> new NoSuchDiscountPolicyFoundException(policyTypeFromRequest));

        return switch (policyType) {
            case AMOUNT_BASED -> discountPolicyRepository.updatePolicy(
                    policyType, validateAndConvertValuesFromRequest(maxDiscountForAmountPolicy,
                                                                    discountIncreasePerStepForAmountPolicy)
            );
            case PERCENTAGE_BASED -> discountPolicyRepository.updatePolicy(
                    policyType, validateAndConvertValuesFromRequest(basicPercentagePolicyDiscount)
            );
        };

    }

    private BigDecimal[] validateAndConvertValuesFromRequest(String... valuesAsStrings)
            throws InvalidValueProvidedException {

        BigDecimal[] convertedValues = new BigDecimal[valuesAsStrings.length];


        for (int i = 0; i < valuesAsStrings.length; i++) {
            try {
                convertedValues[i] = BigDecimal.valueOf(
                        Double.parseDouble(valuesAsStrings[i].replace(",", ".")));
            } catch (NumberFormatException e) {
                throw new InvalidValueProvidedException(valuesAsStrings[i]);
            }
        }

        return convertedValues;

    }
}
