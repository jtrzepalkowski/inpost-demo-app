package pl.jt.demo.inpost.infra.repository;

import org.springframework.stereotype.Repository;
import pl.jt.demo.inpost.domain.AmountBasedDiscountPolicy;
import pl.jt.demo.inpost.domain.DiscountPolicy;
import pl.jt.demo.inpost.domain.PercentageBasedDiscountPolicy;
import pl.jt.demo.inpost.domain.PolicyType;

import javax.annotation.PostConstruct;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

@Repository
public class DiscountPolicyRepository {

    private static Map<PolicyType, DiscountPolicy> discountPolicyMap;

    @PostConstruct
    private void initialize() {
        discountPolicyMap = new HashMap<>();

        discountPolicyMap.put(PolicyType.AMOUNT_BASED, AmountBasedDiscountPolicy.builder()
                .maxDiscount(BigDecimal.valueOf(25))
                .discountPercentageIncreasePerItem(BigDecimal.valueOf(0.5))
                .build());

        discountPolicyMap.put(PolicyType.PERCENTAGE_BASED, PercentageBasedDiscountPolicy.builder()
                .percentageDiscount(BigDecimal.valueOf(20))
                .build());
    }

    public static DiscountPolicy getDiscountPolicyByType(PolicyType policyType) {

        return discountPolicyMap.get(policyType);
    }

    public static void updatePolicy(PolicyType policyType, BigDecimal... updatedDiscountPolicyValues) {
        if (updatedDiscountPolicyValues.length > 2)
            throw new IllegalArgumentException("Too many numeric values provided for update of Policy");

        DiscountPolicy discountPolicy = discountPolicyMap.get(policyType);

        switch (discountPolicy) {
            case AmountBasedDiscountPolicy policy ->
                    updateAmountBasedPolicy(policy, updatedDiscountPolicyValues);
            case PercentageBasedDiscountPolicy policy ->
                    updatePercentageBasedPolicy(policy, updatedDiscountPolicyValues);
            default ->
                    throw new IllegalStateException("Unexpected value: " + discountPolicy.getType());
        }
    }

    private static void updatePercentageBasedPolicy(PercentageBasedDiscountPolicy policy,
                                                    BigDecimal[] updatedDiscountPolicyValues) {

        policy.setPercentageDiscount(updatedDiscountPolicyValues[0]);

        discountPolicyMap.put(PolicyType.PERCENTAGE_BASED, policy);
    }

    private static void updateAmountBasedPolicy(AmountBasedDiscountPolicy policy,
                                                BigDecimal[] updatedDiscountPolicyValues) {

        policy.setDiscountPercentageIncreasePerItem(updatedDiscountPolicyValues[1]);
        policy.setMaxDiscount(updatedDiscountPolicyValues[0]);

        discountPolicyMap.put(PolicyType.AMOUNT_BASED, policy);
    }

}
