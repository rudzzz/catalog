package com.dev.catalog.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.dev.catalog.entities.Category;
import com.dev.catalog.entities.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long>{

}
