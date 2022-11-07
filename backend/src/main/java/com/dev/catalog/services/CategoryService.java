package com.dev.catalog.services;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dev.catalog.dto.CategoryDTO;
import com.dev.catalog.entities.Category;
import com.dev.catalog.repositories.CategoryRepository;
import com.dev.catalog.services.exceptions.ResourceNotFoundException;

@Service
public class CategoryService {
	
	@Autowired
	private CategoryRepository categoryRepository;
	
	@Transactional(readOnly=true)
	public List<CategoryDTO> findall(){
		List<Category> list = categoryRepository.findAll();
		return list.stream().map(listElement -> new CategoryDTO(listElement)).collect(Collectors.toList());
	}
	
	@Transactional(readOnly=true)
	public CategoryDTO findById(Long id) {
		Optional<Category> object = categoryRepository.findById(id);
		Category entity = object.orElseThrow(() -> new ResourceNotFoundException("Product not found!"));
		return new CategoryDTO(entity);
	}
	
	@Transactional(readOnly=true)
	public CategoryDTO save(CategoryDTO DTO) {
		Category entity = new Category();
		entity.setName(DTO.getName());
		entity = categoryRepository.save(entity);
		
		return new CategoryDTO(entity);
	}

	@Transactional
	public CategoryDTO update(Long id, CategoryDTO DTO) {
		try {
			Category entity = categoryRepository.getById(id);
			entity.setName(DTO.getName());
			entity = categoryRepository.save(entity);
			return new CategoryDTO(entity);
		}
		catch(EntityNotFoundException exception) {
			throw new ResourceNotFoundException("the id " + id + " was not found!");
		}
	}

	public void delete(Long id) {
		try {
			categoryRepository.deleteById(id);			
		}
		catch(EmptyResultDataAccessException exception) {
			throw new ResourceNotFoundException("the id " + id + " was not found!");
		}
	}
}
