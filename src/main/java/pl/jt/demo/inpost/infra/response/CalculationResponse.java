package pl.jt.demo.inpost.infra.response;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
public class CalculationResponse {

    private String productName;

    private int amountOfProduct;

    private String discountPolicy;

    private String finalPrice;

}
