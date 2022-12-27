package com.dev.catalog.repositories;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
public class ProductRepositoryTests {
	@Autowired 
	private ProductRepositoy productRepositoy;
	@Test
	public void deleteShouldDeleteObjectWhenIdExistis() {
		
	}
}
