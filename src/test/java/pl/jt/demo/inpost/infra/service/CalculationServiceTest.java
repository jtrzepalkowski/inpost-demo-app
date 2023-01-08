package pl.jt.demo.inpost.infra.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import pl.jt.demo.inpost.infra.exception.InvalidValueProvidedException;
import pl.jt.demo.inpost.infra.exception.NoSuchDiscountPolicyFoundException;
import pl.jt.demo.inpost.infra.exception.NoSuchProductFoundException;
import pl.jt.demo.inpost.infra.response.CalculationResponse;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@RunWith(SpringRunner.class)
class CalculationServiceTest {

    @Autowired
    private CalculationService calculationService;

    private static final String AMOUNT_BASED_NAME_STRING = "amount-based";

    private static final String PERCENTAGE_BASED_NAME_STRING = "percentage-based";


    @Test
    void getDiscountPolicyAndCalculateWithAmountBasedPolicy()
            throws NoSuchProductFoundException, NoSuchDiscountPolicyFoundException, InvalidValueProvidedException {

        //given
        String productId = "af40fe45-5d1b-4ce4-bbb4-4d5e485b3134";
        String amount = "8";

        //when
        CalculationResponse result = calculationService.getDiscountPolicyAndCalculate(
                AMOUNT_BASED_NAME_STRING,
                productId,
                amount
        );

        //then
        assertEquals("Pencil", result.getProductName());
        assertEquals("15.36", result.getFinalPrice());
        assertEquals(8, result.getAmountOfProduct());
        assertEquals("amount-based", result.getDiscountPolicy());

    }

    @Test
    void getDiscountPolicyAndCalculateWithBasicPercentagePolicy()
            throws NoSuchProductFoundException, NoSuchDiscountPolicyFoundException, InvalidValueProvidedException {

        //given
        String productId = "af40fe45-5d1b-4ce4-bbb4-4d5e485b3134";
        String amount = "10";

        //when
        CalculationResponse result = calculationService.getDiscountPolicyAndCalculate(
                PERCENTAGE_BASED_NAME_STRING,
                productId,
                amount
        );

        //then
        assertEquals("Pencil", result.getProductName());
        assertEquals("16.00", result.getFinalPrice());
        assertEquals(10, result.getAmountOfProduct());
        assertEquals("percentage-based", result.getDiscountPolicy());
    }

    @Test
    void getDiscountPolicyAndCalculateWithAmountBasedPolicyExceedingMaxDiscountWithSteps()
            throws NoSuchProductFoundException, NoSuchDiscountPolicyFoundException, InvalidValueProvidedException {

        //given
        String productId = "af40fe45-5d1b-4ce4-bbb4-4d5e485b3134";
        String amount = "100";

        //when
        CalculationResponse result = calculationService.getDiscountPolicyAndCalculate(
                AMOUNT_BASED_NAME_STRING,
                productId,
                amount
        );

        //then
        assertEquals("Pencil", result.getProductName());
        assertEquals("150.00", result.getFinalPrice());
        assertEquals(100, result.getAmountOfProduct());
        assertEquals("amount-based", result.getDiscountPolicy());

    }

    @Test
    void calculateWithoutDiscount() throws NoSuchProductFoundException, InvalidValueProvidedException {

        //given
        String productId = "7b3720e7-432d-4213-8a4b-98fed0ccae22";
        String amount = "2";

        //when
        CalculationResponse result = calculationService.calculateWithoutDiscount(productId, amount);

        //then
        assertEquals("Calendar", result.getProductName());
        assertEquals("42.74", result.getFinalPrice());
        assertEquals(2, result.getAmountOfProduct());
        assertNull(result.getDiscountPolicy());
    }

    @Test
    void shouldThrowNoSuchProductException() {

        //given
        String productId = "some-non-existent-product-id";
        String amount = "10";

        //when & then
        assertThrows(NoSuchProductFoundException.class,
                () -> calculationService.calculateWithoutDiscount(productId, amount));
    }

    @Test
    void shouldThrowNoSuchDiscountPolicyException() {

        //given
        String productId = "7b3720e7-432d-4213-8a4b-98fed0ccae22";
        String amount = "2";

        //when & then
        assertThrows(NoSuchDiscountPolicyFoundException.class,
                () -> calculationService.getDiscountPolicyAndCalculate("some-policy", productId, amount));

    }

    @Test
    void shouldThrowInvalidValueProvidedException() {

        //given
        String productId = "7b3720e7-432d-4213-8a4b-98fed0ccae22";
        String amount = "-8";

        //when & then
        assertThrows(InvalidValueProvidedException.class,
                () -> calculationService.calculateWithoutDiscount(productId, amount));
    }

    @Test
    void shouldThrowInvalidValueProvidedExceptionWithAmountNotNumeric() {

        //given
        String productId = "7b3720e7-432d-4213-8a4b-98fed0ccae22";
        String amount = "two";

        //when & then
        assertThrows(InvalidValueProvidedException.class,
                () -> calculationService.calculateWithoutDiscount(productId, amount));
    }
}