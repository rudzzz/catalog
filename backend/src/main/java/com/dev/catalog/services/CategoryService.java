package com.dev.catalog.services;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dev.catalog.dto.CategoryDTO;
import com.dev.catalog.entities.Category;
import com.dev.catalog.repositories.CategoryRepository;
import com.dev.catalog.services.exceptions.EntityNotFoundException;

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
		Category entity = object.orElseThrow(() -> new EntityNotFoundException("Product not found!"));
		return new CategoryDTO(entity);
	}
}
