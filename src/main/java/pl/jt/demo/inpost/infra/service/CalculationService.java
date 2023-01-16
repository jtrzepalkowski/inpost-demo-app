package pl.jt.demo.inpost.infra.service;

import org.springframework.stereotype.Service;
import pl.jt.demo.inpost.domain.DiscountPolicy;
import pl.jt.demo.inpost.domain.PolicyType;
import pl.jt.demo.inpost.domain.Product;
import pl.jt.demo.inpost.infra.exception.InvalidValueProvidedException;
import pl.jt.demo.inpost.infra.exception.NoSuchDiscountPolicyFoundException;
import pl.jt.demo.inpost.infra.exception.NoSuchProductFoundException;
import pl.jt.demo.inpost.infra.repository.DiscountPolicyRepository;
import pl.jt.demo.inpost.infra.repository.ProductRepository;
import pl.jt.demo.inpost.infra.response.CalculationResponse;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;

@Service
public class CalculationService {

    private final DiscountPolicyRepository discountPolicyRepository;
    private final ProductRepository productRepository;

    private final static DecimalFormat PRICE_FORMAT = new DecimalFormat("0.00",
            new DecimalFormatSymbols(Locale.ROOT));

    CalculationService(final DiscountPolicyRepository discountPolicyRepository,
                       final ProductRepository productRepository) {
        this.discountPolicyRepository = discountPolicyRepository;
        this.productRepository = productRepository;
    }

    public CalculationResponse calculateWithGivenDiscountPolicy(String policyTypeFromRequest, String productId, String amount)
            throws NoSuchProductFoundException, NoSuchDiscountPolicyFoundException, InvalidValueProvidedException {

        int numericAmount = getNumericAmount(amount);

        PolicyType policyType = PolicyType.getByRequestString(policyTypeFromRequest)
                .orElseThrow(() -> new NoSuchDiscountPolicyFoundException(policyTypeFromRequest));

        Product product = productRepository.getProductById(productId);

        DiscountPolicy discountPolicy = discountPolicyRepository.getDiscountPolicyByType(policyType);

        return CalculationResponse.builder()
                .amountOfProduct(numericAmount)
                .discountPolicy(policyTypeFromRequest)
                .productName(product.getName())
                .finalPrice(PRICE_FORMAT.format(discountPolicy.calculateDiscount(product.getPricePerItem(), numericAmount)))
                .build();
    }

    private static int getNumericAmount(String amount) throws InvalidValueProvidedException {
        int numericAmount;

        try {
            numericAmount = Integer.parseInt(amount);
            if (numericAmount < 1) throw new InvalidValueProvidedException(amount);
        } catch (NumberFormatException e) {
            throw new InvalidValueProvidedException(amount);
        }
        return numericAmount;
    }
}
