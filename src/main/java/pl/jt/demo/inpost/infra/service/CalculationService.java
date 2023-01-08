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

@Service
public class CalculationService {

    private final DiscountPolicyRepository discountPolicyRepository;
    private final ProductRepository productRepository;

    CalculationService(final DiscountPolicyRepository discountPolicyRepository,
                       final ProductRepository productRepository) {
        this.discountPolicyRepository = discountPolicyRepository;
        this.productRepository = productRepository;
    }

    public CalculationResponse getDiscountPolicyAndCalculate(String policyTypeFromRequest, String productId, String amount)
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
                .finalPrice(discountPolicy.calculateDiscount(product.getPricePerItem(), numericAmount).toString())
                .build();
    }

    public CalculationResponse calculateWithoutDiscount(String productId, String amount)
            throws InvalidValueProvidedException, NoSuchProductFoundException {

        int numericAmount = getNumericAmount(amount);

        Product product = productRepository.getProductById(productId);

        return CalculationResponse.builder()
                .amountOfProduct(numericAmount)
                .productName(product.getName())
                .finalPrice(product.getPricePerItem().multiply(BigDecimal.valueOf(numericAmount)).toString())
                .build();
    }

    private static int getNumericAmount(String amount) throws InvalidValueProvidedException {
        int numericAmount;

        try {
            numericAmount = Integer.parseInt(amount);
        } catch (NumberFormatException e) {
            throw new InvalidValueProvidedException(amount);
        }
        return numericAmount;
    }
}
