package com.dev.catalog.services;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dev.catalog.dto.ProductDTO;
import com.dev.catalog.entities.Product;
import com.dev.catalog.repositories.ProductRepository;
import com.dev.catalog.services.exceptions.DatabaseException;
import com.dev.catalog.services.exceptions.ResourceNotFoundException;

@Service
public class ProductService {
	
	@Autowired
	private ProductRepository productRepository;
	
	@Transactional(readOnly=true)
	public Page<ProductDTO> findAllPaged(PageRequest pageRequest){
		Page<Product> list = productRepository.findAll(pageRequest);
		return list.map(listElement -> new ProductDTO(listElement));
	}
	
	@Transactional(readOnly=true)
	public ProductDTO findById(Long id) {
		Optional<Product> object = productRepository.findById(id);
		Product entity = object.orElseThrow(() -> new ResourceNotFoundException("Product not found!"));
		return new ProductDTO(entity, entity.getCategories());
	}
	
	@Transactional(readOnly=true)
	public ProductDTO save(ProductDTO DTO) {
		Product entity = new Product();
		//entity.setName(DTO.getName());
		entity = productRepository.save(entity);
		
		return new ProductDTO(entity);
	}

	@Transactional
	public ProductDTO update(Long id, ProductDTO DTO) {
		try {
			Product entity = productRepository.getById(id);
			//entity.setName(DTO.getName());
			entity = productRepository.save(entity);
			return new ProductDTO(entity);
		}
		catch(EntityNotFoundException exception) {
			throw new ResourceNotFoundException("the id " + id + " was not found!");
		}
	}

	public void delete(Long id) {
		try {
			productRepository.deleteById(id);			
		}
		catch(EmptyResultDataAccessException exception) {
			throw new ResourceNotFoundException("the id " + id + " was not found!");
		}
		catch(DataIntegrityViolationException exception) {
			throw new DatabaseException("Interity violation!");
		}
	}
}
