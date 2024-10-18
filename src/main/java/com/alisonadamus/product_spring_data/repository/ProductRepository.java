package com.alisonadamus.product_spring_data.repository;

import com.alisonadamus.product_spring_data.entity.Product;
import com.alisonadamus.product_spring_data.entity.ProductProjectionForList;
import java.math.BigDecimal;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    @Query("SELECT p.id AS id, p.name AS name, p.cost AS cost, c.name AS categoryName"
        + " FROM Product p JOIN p.category c")
    Page<ProductProjectionForList> findAllForList(Pageable pageable);

    @Query("SELECT p FROM Product p "
        + "WHERE (:searchValue IS NULL OR LOWER(p.name) LIKE LOWER(CONCAT('%', :searchValue, '%'))) "
        + "AND (:categoryId IS NULL OR p.category.id = :categoryId) "
        + "AND (p.cost BETWEEN :minPrice AND :maxPrice) ")
    Page<Product> findAll(
        @Param("searchValue") String searchValue,
        @Param("categoryId") Long categoryId,
        @Param("minPrice") BigDecimal minPrice,
        @Param("maxPrice") BigDecimal maxPrice,
        Pageable pageable);


    @Query("SELECT p.id AS id, p.name AS name, p.cost AS cost, c.name AS categoryName "
        + "FROM Product p JOIN p.category c "
        + "WHERE (:searchValue IS NULL OR LOWER(p.name) LIKE LOWER(CONCAT('%', :searchValue, '%'))) "
        + "AND (:categoryId IS NULL OR p.category.id = :categoryId) "
        + "AND (p.cost BETWEEN :minPrice AND :maxPrice) ")
    Page<ProductProjectionForList> findAllForList(
        @Param("searchValue") String searchValue,
        @Param("categoryId") Long categoryId,
        @Param("minPrice") BigDecimal minPrice,
        @Param("maxPrice") BigDecimal maxPrice,
        Pageable pageable);

    @Query("SELECT p FROM Product p WHERE p.name LIKE %:name%")
    Page<Product> searchByName(@Param("name") String name, Pageable pageable);
}
