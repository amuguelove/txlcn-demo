package com.flygopner.product;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface ProductRepository
        extends JpaRepository<Product, Long>, QuerydslPredicateExecutor<Product> {

    Optional<Product> findByName(String name);

    @Modifying
    @Query(
            value =
                    "update tb_product set stock = stock - :productUnit where id = :productId and stock >= :productUnit",
            nativeQuery = true)
    int updateProductStock(
            @Param("productId") Long productId, @Param("productUnit") Integer productUnit);

    @Modifying
    @Query(
            value =
                    "update tb_product set stock = stock + :productUnit where id = :productId",
            nativeQuery = true)
    int cancelUpdateProductStock(
            @Param("productId") Long productId, @Param("productUnit") Integer productUnit);
}
