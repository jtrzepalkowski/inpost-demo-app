package pl.jt.demo.inpost.infra.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.jt.demo.inpost.domain.Product;
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
    public List<Product> fetchAllProducts() {
        return productService.retrieveAll();
    }

}
