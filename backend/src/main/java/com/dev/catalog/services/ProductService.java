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
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dev.catalog.dto.CategoryDTO;
import com.dev.catalog.dto.ProductDTO;
import com.dev.catalog.entities.Category;
import com.dev.catalog.entities.Product;
import com.dev.catalog.repositories.CategoryRepository;
import com.dev.catalog.repositories.ProductRepository;
import com.dev.catalog.services.exceptions.DatabaseException;
import com.dev.catalog.services.exceptions.ResourceNotFoundException;

@Service
public class ProductService {

	@Autowired
	private ProductRepository productRepository;
	
	@Autowired
	private CategoryRepository categoryRepository; 

	@Transactional(readOnly = true)
	public Page<ProductDTO> findAllPaged(Pageable pageable) {
		Page<Product> list = productRepository.findAll(pageable);
		return list.map(listElement -> new ProductDTO(listElement));
	}

	@Transactional(readOnly = true)
	public ProductDTO findById(Long id) {
		Optional<Product> object = productRepository.findById(id);
		Product entity = object.orElseThrow(() -> new ResourceNotFoundException("Product not found!"));
		return new ProductDTO(entity, entity.getCategories());
	}

	@Transactional(readOnly = true)
	public ProductDTO save(ProductDTO DTO) {
		Product entity = new Product();
		copyDtoToEntity(DTO, entity);
		entity = productRepository.save(entity);

		return new ProductDTO(entity);
	}

	@Transactional
	public ProductDTO update(Long id, ProductDTO DTO) {
		try {
			Product entity = productRepository.getById(id);
			copyDtoToEntity(DTO, entity);
			entity = productRepository.save(entity);
			return new ProductDTO(entity);
		} catch (EntityNotFoundException exception) {
			throw new ResourceNotFoundException("the id " + id + " was not found!");
		}
	}

	public void delete(Long id) {
		try {
			productRepository.deleteById(id);
		} catch (EmptyResultDataAccessException exception) {
			throw new ResourceNotFoundException("the id " + id + " was not found!");
		} catch (DataIntegrityViolationException exception) {
			throw new DatabaseException("Interity violation!");
		}
	}
	
	private void copyDtoToEntity(ProductDTO dto, Product entity) {
		entity.setName(dto.getName());
		entity.setDescription(dto.getDescription());
		entity.setDate(dto.getDate());
		entity.setImgUrl(dto.getImgUrl());
		entity.setPrice(dto.getPrice());
		
		entity.getCategories().clear();
		for(CategoryDTO categoryDTO : dto.getCategories()) {
			Category category = categoryRepository.getById(categoryDTO.getId());
			entity.getCategories().add(category);
		}
		
	}
}
