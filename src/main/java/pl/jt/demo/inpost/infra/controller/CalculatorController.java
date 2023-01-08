package pl.jt.demo.inpost.infra.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import pl.jt.demo.inpost.infra.exception.InvalidValueProvidedException;
import pl.jt.demo.inpost.infra.exception.NoSuchDiscountPolicyFoundException;
import pl.jt.demo.inpost.infra.exception.NoSuchProductFoundException;
import pl.jt.demo.inpost.infra.response.CalculationResponse;
import pl.jt.demo.inpost.infra.service.CalculationService;

@RestController
@RequestMapping("/calculate")
public class CalculatorController {

    private final CalculationService calculationService;

    CalculatorController(CalculationService calculationService) {
        this.calculationService = calculationService;
    }

    @GetMapping("/{productId}/{amount}")
    public CalculationResponse calculateFinalPrice(@PathVariable String productId,
                                                   @PathVariable String amount,
                                                   @RequestParam(value = "discount-policy", required = false) String discountPolicy)
            throws NoSuchProductFoundException, NoSuchDiscountPolicyFoundException, InvalidValueProvidedException {

            if (discountPolicy == null || discountPolicy.isBlank()) {
                return calculationService.calculateWithoutDiscount(productId, amount);
            } else {
                return calculationService.getDiscountPolicyAndCalculate(discountPolicy, productId, amount);
            }

    }

}
