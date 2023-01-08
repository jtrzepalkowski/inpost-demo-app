package pl.jt.demo.inpost.infra.controller;

import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import pl.jt.demo.inpost.domain.DiscountPolicy;
import pl.jt.demo.inpost.infra.exception.InvalidValueProvidedException;
import pl.jt.demo.inpost.infra.exception.NoSuchDiscountPolicyFoundException;
import pl.jt.demo.inpost.infra.response.DiscountPolicyUpdateResponse;
import pl.jt.demo.inpost.infra.service.DiscountPolicyUpdateService;

@RestController
@RequestMapping("/admin")
public class DiscountPolicyConfigController {

    private final DiscountPolicyUpdateService discountPolicyUpdateService;

    DiscountPolicyConfigController(final DiscountPolicyUpdateService discountPolicyUpdateService) {
        this.discountPolicyUpdateService = discountPolicyUpdateService;
    }

    @PatchMapping("/update/{policyType}")
    public DiscountPolicyUpdateResponse setNewValuesOfDiscountPolicy(
            @PathVariable("policyType") String policyType,
            @RequestParam(value = "basicPercentage", required = false) String basicPercentage,
            @RequestParam(value = "maxDiscount", required = false) String maxDiscount,
            @RequestParam(value = "discountIncreasePerItem", required = false) String discountIncreasePerItem)
            throws NoSuchDiscountPolicyFoundException, InvalidValueProvidedException {

        DiscountPolicy policyWithNewValues =
                discountPolicyUpdateService.updateGivenDiscountPolicyWithNewValues(
                        policyType,
                        basicPercentage,
                        maxDiscount,
                        discountIncreasePerItem);

        return DiscountPolicyUpdateResponse.builder()
                .policyType(policyType)
                .newMaxDiscountValue(maxDiscount)
                .newBasicPercentageDiscountValue(basicPercentage)
                .newDiscountIncreasePerItemValue(discountIncreasePerItem)
                .build();
    }

}
