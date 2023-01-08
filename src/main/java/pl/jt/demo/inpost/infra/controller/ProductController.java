package pl.jt.demo.inpost.infra.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import pl.jt.demo.inpost.domain.Product;
import pl.jt.demo.inpost.infra.exception.InvalidValueProvidedException;
import pl.jt.demo.inpost.infra.exception.NoSuchDiscountPolicyFoundException;
import pl.jt.demo.inpost.infra.exception.NoSuchProductFoundException;
import pl.jt.demo.inpost.infra.repository.ProductRepository;
import pl.jt.demo.inpost.infra.response.CalculationResponse;
import pl.jt.demo.inpost.infra.service.CalculationService;
import pl.jt.demo.inpost.infra.service.ProductService;

import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductController {

    private final ProductService productService;

    ProductController(final ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/show-all")
    public List<Product> calculateFinalPrice() {
        return productService.retrieveAll();
    }

}
