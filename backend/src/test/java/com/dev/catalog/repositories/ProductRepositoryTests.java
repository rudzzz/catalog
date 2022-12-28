package com.dev.catalog.repositories;

import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.dev.catalog.entities.Product;
import com.dev.catalog.repositories.ProductRepository;

@DataJpaTest
public class ProductRepositoryTests {
	@Autowired 
	private ProductRepository productRepositoy;
	
	@Test
	public void deleteShouldDeleteObjectWhenIdExistis() {
		long id = 1L;
		productRepositoy.deleteById(id);
		
		Optional<Product> result = productRepositoy.findById(id);
		
		Assertions.assertFalse(result.isPresent());
	}
}
