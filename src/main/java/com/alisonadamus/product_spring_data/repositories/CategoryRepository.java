package com.alisonadamus.product_spring_data.repositories;

import com.alisonadamus.product_spring_data.entities.Category;
import com.alisonadamus.product_spring_data.entities.CategoryProjection;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {

    Optional<Category> findByName(@Param("name") String name);

    @Query("SELECT c.name AS name FROM Category c")
    List<CategoryProjection> findAllForSelection();

    @Query("SELECT c FROM Category c "
        + "WHERE (:searchValue IS NULL OR LOWER(c.name) LIKE LOWER(CONCAT('%', :searchValue, '%')))")
    Page<Category> findAllForList(@Param("searchValue") String searchValue, Pageable pageable);

}
