package com.flygopner.product;

import com.flygopher.common.base.feign.domain.ProductModel;
import com.flygopher.common.base.utils.Mapper;
import com.flygopner.lcn.LcnProductService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/products")
@Slf4j
public class ProductController {

    private final ProductService productService;

    private final LcnProductService lcnProductService;

    @Autowired
    public ProductController(
            ProductService productService,
            LcnProductService lcnProductService) {
        this.productService = productService;
        this.lcnProductService = lcnProductService;
    }

    @GetMapping("/{name}")
    public ProductModel getProductByName(@PathVariable("name") String productName) {
        return Mapper.map(productService.getProductByName(productName), ProductModel.class);
    }

    @PutMapping("/stock/{productName}/{productUnit}")
    public boolean updateProductStock(
            @PathVariable("productName") String productName,
            @PathVariable("productUnit") Integer productUnit) {
        return productService.updateProductStock(productName, productUnit);
    }

    @PutMapping("/stock/{productName}/{productUnit}/lcn")
    public boolean updateProductStockByLcn(
            @PathVariable("productName") String productName,
            @PathVariable("productUnit") Integer productUnit) {
        return lcnProductService.updateProductStock(productName, productUnit);
    }

    @PutMapping("/stock/{productName}/{productUnit}/lcn/cancel")
    public void cancelUpdateProductStockByLcn(
            @PathVariable("productName") String productName,
            @PathVariable("productUnit") Integer productUnit) {
        log.warn("cancel product stock");
        lcnProductService.cancelProductStock(productName, productUnit);
    }

}
