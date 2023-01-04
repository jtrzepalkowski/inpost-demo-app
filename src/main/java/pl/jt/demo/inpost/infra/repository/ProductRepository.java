package pl.jt.demo.inpost.infra.repository;

import org.springframework.stereotype.Repository;
import pl.jt.demo.inpost.domain.Product;
import pl.jt.demo.inpost.infra.exception.NoSuchProductFoundException;

import javax.annotation.PostConstruct;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@Repository
public class ProductRepository {

    private static Map<String, Product> productMap;

    @PostConstruct
    private void initializePolicyMap() {
        productMap = new HashMap<>();
        productMap.put("47054bb7-fc86-4ed5-a264-1ad615df3f59",
                Product.builder()
                        .id(UUID.fromString("47054bb7-fc86-4ed5-a264-1ad615df3f59"))
                        .name("Eraser")
                        .pricePerItem(BigDecimal.valueOf(5.20))
                        .build());

        productMap.put("1436ed19-30a7-4e82-926d-cf99413386c9",
                Product.builder()
                        .id(UUID.fromString("1436ed19-30a7-4e82-926d-cf99413386c9"))
                        .name("Notepad")
                        .pricePerItem(BigDecimal.valueOf(6.35))
                        .build());

        productMap.put("ed56b6bd-08d4-44d7-a99b-aadc60e4e2d9",
                Product.builder()
                        .id(UUID.fromString("ed56b6bd-08d4-44d7-a99b-aadc60e4e2d9"))
                        .name("Stapler")
                        .pricePerItem(BigDecimal.valueOf(12.10))
                        .build());

        productMap.put("1494a05c-3ed3-46ad-a19a-531cea04c983",
                Product.builder()
                        .id(UUID.fromString("1494a05c-3ed3-46ad-a19a-531cea04c983"))
                        .name("Paper Guillotine")
                        .pricePerItem(BigDecimal.valueOf(32.50))
                        .build());

        productMap.put("7b3720e7-432d-4213-8a4b-98fed0ccae22",
                Product.builder()
                        .id(UUID.fromString("7b3720e7-432d-4213-8a4b-98fed0ccae22"))
                        .name("Calendar")
                        .pricePerItem(BigDecimal.valueOf(21.37))
                        .build());

        productMap.put("7ebf3d13-148e-4293-9741-a9180e21e9da",
                Product.builder()
                        .id(UUID.fromString("7ebf3d13-148e-4293-9741-a9180e21e9da"))
                        .name("Pen")
                        .pricePerItem(BigDecimal.valueOf(1.20))
                        .build());

        productMap.put("2f7b32e1-f41c-4ba8-bea5-2144660810c0",
                Product.builder()
                        .id(UUID.fromString("2f7b32e1-f41c-4ba8-bea5-2144660810c0"))
                        .name("Sticky Notes")
                        .pricePerItem(BigDecimal.valueOf(0.80))
                        .build());

        productMap.put("af40fe45-5d1b-4ce4-bbb4-4d5e485b3134",
                Product.builder()
                        .id(UUID.fromString("af40fe45-5d1b-4ce4-bbb4-4d5e485b3134"))
                        .name("Pencil")
                        .pricePerItem(BigDecimal.valueOf(2.10))
                        .build());

        productMap.put("cb7c2460-98c7-4739-a4df-5e24af504bb5",
                Product.builder()
                        .id(UUID.fromString("cb7c2460-98c7-4739-a4df-5e24af504bb5"))
                        .name("Printing paper")
                        .pricePerItem(BigDecimal.valueOf(4.20))
                        .build());

        productMap.put("7558df5c-7a45-4c07-87cd-225663371aad",
                Product.builder()
                        .id(UUID.fromString("7558df5c-7a45-4c07-87cd-225663371aad"))
                        .name("Marker")
                        .pricePerItem(BigDecimal.valueOf(10.50))
                        .build());
    }

    public Product getProductById(String uuid) throws NoSuchProductFoundException {
        return Optional.ofNullable(productMap.get(uuid)).orElseThrow(() -> new NoSuchProductFoundException(uuid));
    }
}
