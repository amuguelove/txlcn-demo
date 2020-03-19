package com.flygopner.lcn;

import com.codingapi.txlcn.tc.annotation.DTXPropagation;
import com.codingapi.txlcn.tc.annotation.TxcTransaction;
import com.codingapi.txlcn.tc.config.EnableDistributedTransaction;
import com.flygopner.product.Product;
import com.flygopner.product.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.flygopher.common.base.exception.NotFoundException.notFoundException;

@Service
@EnableDistributedTransaction
public class LcnProductService {

    private final ProductRepository productRepository;

    @Autowired
    public LcnProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Transactional
    @TxcTransaction(propagation = DTXPropagation.SUPPORTS)
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

    @Transactional
    public void cancelProductStock(String productName, Integer productUnit) {
        Product product = this.getProductByName(productName);
        productRepository.cancelUpdateProductStock(product.getId(), productUnit);
    }
}
