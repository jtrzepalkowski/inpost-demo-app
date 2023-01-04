package pl.jt.demo.inpost.domain;

import java.math.BigDecimal;
import java.util.List;

public interface DiscountPolicy {

    BigDecimal ONE_PERCENT = BigDecimal.valueOf(0.01);

    BigDecimal calculateDiscount(BigDecimal itemPrice, int amount);

    String getType();
}
