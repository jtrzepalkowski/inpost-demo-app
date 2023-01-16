package pl.jt.demo.inpost.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Builder
@Setter
@Getter
public class NoDiscountPolicy implements DiscountPolicy {

    @Override
    public BigDecimal calculateDiscount(BigDecimal itemPrice, int amount) {

        return BigDecimal.valueOf(amount)
                .multiply(itemPrice);
    }

    @Override
    public String getType() {
        return PolicyType.NO_DISCOUNT.name();
    }
}
