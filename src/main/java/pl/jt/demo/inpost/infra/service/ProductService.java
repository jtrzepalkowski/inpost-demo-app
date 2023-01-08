package pl.jt.demo.inpost.infra.service;

import org.springframework.stereotype.Service;
import pl.jt.demo.inpost.domain.Product;
import pl.jt.demo.inpost.infra.repository.ProductRepository;

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
