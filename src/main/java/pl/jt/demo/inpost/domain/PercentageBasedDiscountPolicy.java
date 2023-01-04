package pl.jt.demo.inpost.domain;

import lombok.Builder;
import lombok.Setter;

import java.math.BigDecimal;

@Builder
@Setter
public class PercentageBasedDiscountPolicy implements DiscountPolicy {

    private BigDecimal percentageDiscount;

    @Override
    public BigDecimal calculateDiscount(BigDecimal itemPrice, int amount) {
        return BigDecimal.valueOf(amount).multiply(itemPrice).multiply(percentageDiscount).multiply(ONE_PERCENT);
    }

    @Override
    public String getType() {
        return PolicyType.PERCENTAGE_BASED.name();
    }
}
