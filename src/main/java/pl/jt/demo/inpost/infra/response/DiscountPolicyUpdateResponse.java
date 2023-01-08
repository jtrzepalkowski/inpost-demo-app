package pl.jt.demo.inpost.infra.response;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import lombok.Builder;

@Builder
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
public class DiscountPolicyUpdateResponse {

    private String policyType;

    private String newMaxDiscountValue;

    private String newBasicPercentageDiscountValue;

    private String newDiscountIncreasePerItemValue;
}
