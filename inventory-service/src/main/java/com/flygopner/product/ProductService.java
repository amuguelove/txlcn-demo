package com.flygopner.product;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.flygopher.common.base.exception.NotFoundException.notFoundException;

@Service
public class ProductService {

    private final ProductRepository productRepository;

    @Autowired
    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Transactional
    public boolean updateProductStock(String productName, Integer productUnit) {
        Product product = this.getProductByName(productName);
        return productRepository.updateProductStock(product.getId(), productUnit) > 0;
    }

    public Product getProductByName(String productName) {
        return productRepository
                .findByName(productName)
                .orElseThrow(
                        notFoundException(String.format("product[%s] not found", productName)));
    }
}
