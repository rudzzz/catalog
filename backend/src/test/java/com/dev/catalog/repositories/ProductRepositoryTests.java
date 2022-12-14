package com.dev.catalog.repositories;

import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.EmptyResultDataAccessException;

import com.dev.catalog.entities.Product;
import com.dev.catalog.repositories.ProductRepository;

@DataJpaTest
public class ProductRepositoryTests {
	@Autowired 
	private ProductRepository productRepositoy;
	
	private long id;
	private long nonExistingId;
	
	@BeforeEach
	void setUp() throws Exception {
		id = 1L;
		nonExistingId = 1000L;
	}
	
	@Test
	public void deleteShouldDeleteObjectWhenIdExistis() {
		productRepositoy.deleteById(id);
		
		Optional<Product> result = productRepositoy.findById(id);
		
		Assertions.assertFalse(result.isPresent());
	}
	
	@Test
	public void deleteShouldThrowEmptyResultDataAccessExceptionWhenIdDoesNotExist() {
		Assertions.assertThrows(EmptyResultDataAccessException.class, () -> {
			productRepositoy.deleteById(nonExistingId);
		});
	}
}
