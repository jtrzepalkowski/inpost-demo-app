package pl.jt.demo.inpost.infra.repository;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import pl.jt.demo.inpost.domain.AmountBasedDiscountPolicy;
import pl.jt.demo.inpost.domain.DiscountPolicy;
import pl.jt.demo.inpost.domain.NoDiscountPolicy;
import pl.jt.demo.inpost.domain.PercentageBasedDiscountPolicy;
import pl.jt.demo.inpost.domain.PolicyType;

import javax.annotation.PostConstruct;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

@Component
@Slf4j
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

        discountPolicyMap.put(PolicyType.NO_DISCOUNT, NoDiscountPolicy.builder().build());

        log.info("""
                Created two discount policies with default values.
                Amount based: max discount = 25%, step per item 0.5%.
                Percentage based: discount = 20%.
                """);
    }

    public DiscountPolicy getDiscountPolicyByType(PolicyType policyType) {

        return discountPolicyMap.get(policyType);
    }

    public DiscountPolicy updatePolicy(PolicyType policyType, BigDecimal... updatedDiscountPolicyValues) {
        if (updatedDiscountPolicyValues.length > 2)
            throw new IllegalArgumentException("Too many numeric values provided for update of Policy");

        DiscountPolicy discountPolicy = discountPolicyMap.get(policyType);

        if (discountPolicy instanceof AmountBasedDiscountPolicy) {
            return updateAmountBasedPolicy((AmountBasedDiscountPolicy) discountPolicy, updatedDiscountPolicyValues);
        } else if (discountPolicy instanceof PercentageBasedDiscountPolicy) {
            return updatePercentageBasedPolicy((PercentageBasedDiscountPolicy) discountPolicy, updatedDiscountPolicyValues);
        } else {
            log.error("unexpected value: " + discountPolicy.getType());
            throw new IllegalStateException("Unexpected value: " + discountPolicy.getType());
        }
    }

    private DiscountPolicy updatePercentageBasedPolicy(PercentageBasedDiscountPolicy policy,
                                                    BigDecimal[] updatedDiscountPolicyValues) {

        policy.setPercentageDiscount(updatedDiscountPolicyValues[0]);

        discountPolicyMap.put(PolicyType.PERCENTAGE_BASED, policy);

        log.info("""
                New values for percentage based discount have been set.
                Percacentage based: discount = 
                """ + updatedDiscountPolicyValues[0]);

        return policy;
    }

    private DiscountPolicy updateAmountBasedPolicy(AmountBasedDiscountPolicy policy,
                                                BigDecimal[] updatedDiscountPolicyValues) {

        policy.setDiscountPercentageIncreasePerItem(updatedDiscountPolicyValues[1]);
        policy.setMaxDiscount(updatedDiscountPolicyValues[0]);

        discountPolicyMap.put(PolicyType.AMOUNT_BASED, policy);

        log.info("""
                New values for percentage based discount have been set.
                Amount based: max discount = 
                """ + updatedDiscountPolicyValues[0] +
                ", step per item = " + updatedDiscountPolicyValues[1]);

        return policy;
    }

}
