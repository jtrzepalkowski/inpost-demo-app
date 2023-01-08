package pl.jt.demo.inpost.infra.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import pl.jt.demo.inpost.domain.Product;

import java.util.List;

@SpringBootTest
@RunWith(SpringRunner.class)
class ProductServiceTest {

    @Autowired
    private ProductService productService;

    @Test
    void retrieveAllTest() {
        //when
        List<Product> allProducts = productService.retrieveAll();

        //then
        Assertions.assertEquals(10, allProducts.size());
    }
}