package pl.jt.demo.inpost.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Builder
@Setter
@Getter
public class AmountBasedDiscountPolicy implements DiscountPolicy {

    private BigDecimal maxDiscount;
    private BigDecimal discountPercentageIncreasePerItem;

    @Override
    public BigDecimal calculateDiscount(BigDecimal itemPrice, int amount) {

        BigDecimal actualDiscount = discountPercentageIncreasePerItem.multiply(new BigDecimal(amount));

        return BigDecimal.valueOf(amount)
                .multiply(itemPrice)
                .multiply(ONE_HUNDRED.subtract(actualDiscount.min(maxDiscount)))
                .multiply(ONE_PERCENT);
    }

    @Override
    public String getType() {
        return PolicyType.AMOUNT_BASED.name();
    }
}
