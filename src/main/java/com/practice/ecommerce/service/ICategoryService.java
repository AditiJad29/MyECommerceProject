package com.practice.ecommerce.service;

import com.practice.ecommerce.dto.CategoryRequestDTO;
import com.practice.ecommerce.dto.CategoryResponseDTO;
import com.practice.ecommerce.models.Category;

import java.util.List;

public interface ICategoryService {
//all methods in interface are public & abstract by default
    CategoryRequestDTO addCategory(CategoryRequestDTO category);
    CategoryRequestDTO updateCategory(int categoryId, CategoryRequestDTO category);
    CategoryRequestDTO deleteCategory(int categoryID);
    CategoryResponseDTO getAllCategories(int pageSize, int pageNum, String sortBy, String sortOrder);
}
