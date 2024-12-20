package com.practice.ecommerce.repository;

import com.practice.ecommerce.models.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ICategoryRepository extends JpaRepository<Category, Integer> {

    Category findByCategoryName(String categoryName);
}
