package com.flygopher.common.base.feign.inventory;

import com.flygopher.common.base.feign.domain.ProductModel;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;

public interface InventoryClient {

    @GetMapping(path = "/products/{name}")
    ProductModel getProductByName(@PathVariable("name") String productName);

    @PutMapping(
            path = "/products/stock/{productName}/{productUnit}",
            consumes = MediaType.APPLICATION_JSON_VALUE)
    boolean updateProductStock(
            @PathVariable("productName") String productName,
            @PathVariable("productUnit") Integer productUnit);

    @PutMapping(
            path = "/products/stock/{productName}/{productUnit}/lcn",
            consumes = MediaType.APPLICATION_JSON_VALUE)
    boolean updateProductStockByLcn(
            @PathVariable("productName") String productName,
            @PathVariable("productUnit") Integer productUnit);

    @PutMapping("/products/stock/{productName}/{productUnit}/lcn/cancel")
    void cancelUpdateProductStockByLcn(
            @PathVariable("productName") String productName,
            @PathVariable("productUnit") Integer productUnit);
}
