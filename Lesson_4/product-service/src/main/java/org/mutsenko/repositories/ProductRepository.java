package org.mutsenko.repositories;

import org.mutsenko.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {
    @Query("SELECT p FROM Product p WHERE p.user.id = :userId")
    List<Product> findAllByUserId(@Param("userId") Long userId);

}


