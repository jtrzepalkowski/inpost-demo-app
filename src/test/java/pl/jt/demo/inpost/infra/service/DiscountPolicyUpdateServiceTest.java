package pl.jt.demo.inpost.infra.service;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import pl.jt.demo.inpost.domain.AmountBasedDiscountPolicy;
import pl.jt.demo.inpost.domain.PercentageBasedDiscountPolicy;
import pl.jt.demo.inpost.domain.PolicyType;
import pl.jt.demo.inpost.infra.exception.InvalidValueProvidedException;
import pl.jt.demo.inpost.infra.exception.NoSuchDiscountPolicyFoundException;
import pl.jt.demo.inpost.infra.repository.DiscountPolicyRepository;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@RunWith(SpringRunner.class)
class DiscountPolicyUpdateServiceTest {

    @Autowired
    private DiscountPolicyUpdateService discountPolicyUpdateService;

    @Autowired
    private DiscountPolicyRepository discountPolicyRepository;

    private static final String AMOUNT_BASED_NAME_STRING = "amount-based";

    private static final String PERCENTAGE_BASED_NAME_STRING = "percentage-based";

    @Test
    void updateAmountBasedPolicyWithNewValues() throws NoSuchDiscountPolicyFoundException, InvalidValueProvidedException {

        //given
        String newMaxDiscount = "50";
        String newDiscountIncrementPerStep = "2,5";

        //when
        discountPolicyUpdateService.updateGivenDiscountPolicyWithNewValues(
                AMOUNT_BASED_NAME_STRING,
                null,
                newMaxDiscount,
                newDiscountIncrementPerStep);

        AmountBasedDiscountPolicy amountBasedDiscountPolicy =
                (AmountBasedDiscountPolicy) discountPolicyRepository.
                        getDiscountPolicyByType(PolicyType.AMOUNT_BASED);

        //then
        assertEquals(BigDecimal.valueOf(50.0), amountBasedDiscountPolicy.getMaxDiscount());
        assertEquals(BigDecimal.valueOf(2.5), amountBasedDiscountPolicy.getDiscountPercentageIncreasePerItem());
    }

    @Test
    void updatePercentageBasedPolicyWithNewValues() throws NoSuchDiscountPolicyFoundException, InvalidValueProvidedException {

        //given
        String newBasicPercentageDiscount = "42";

        //when
        discountPolicyUpdateService.updateGivenDiscountPolicyWithNewValues(
                PERCENTAGE_BASED_NAME_STRING,
                newBasicPercentageDiscount,
                null,
                null);

        PercentageBasedDiscountPolicy percentageBasedDiscountPolicy =
                (PercentageBasedDiscountPolicy) discountPolicyRepository.
                        getDiscountPolicyByType(PolicyType.PERCENTAGE_BASED);

        //then
        assertEquals(BigDecimal.valueOf(42.0), percentageBasedDiscountPolicy.getPercentageDiscount());

    }

    @Test
    void shouldThrowNoSuchDiscountPolicyException() {

        //given
        String newMaxDiscount = "50";
        String newDiscountIncrementPerStep = "2,5";
        String newBasicPercentageDiscount = "42";

        //when & then
        assertThrows(NoSuchDiscountPolicyFoundException.class,
                () -> discountPolicyUpdateService.updateGivenDiscountPolicyWithNewValues(
                        "some-non-existent-policy",
                        newBasicPercentageDiscount,
                        newMaxDiscount,
                        newDiscountIncrementPerStep));
    }

    @Test
    void shouldThrowInvalidValueProvidedException() {
        //given
        String newMaxDiscount = "Fifty";
        String newDiscountIncrementPerStep = "2,5";
        String newBasicPercentageDiscount = "42";

        //when & then
        assertThrows(InvalidValueProvidedException.class,
                () -> discountPolicyUpdateService.updateGivenDiscountPolicyWithNewValues(
                        AMOUNT_BASED_NAME_STRING,
                        newBasicPercentageDiscount,
                        newMaxDiscount,
                        newDiscountIncrementPerStep));
    }
}