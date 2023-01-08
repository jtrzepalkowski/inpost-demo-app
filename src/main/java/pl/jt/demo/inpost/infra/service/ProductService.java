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
import java.util.List;

@Service
public class ProductService {
    private final ProductRepository productRepository;

    ProductService(final ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public List<Product> retrieveAll() {
        return productRepository.getAll();
    }
}
