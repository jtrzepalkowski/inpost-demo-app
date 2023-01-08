package pl.jt.demo.inpost.domain;

import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;
import java.util.UUID;

@Builder
@Getter
public class Product {

    private UUID id;
    private String name;
    private BigDecimal pricePerItem;

}
