package com.practice.ecommerce.controller;

import com.practice.ecommerce.kafkaPubSub.MyStringKafkaProducer;
import com.practice.ecommerce.dto.CategoryRequestDTO;
import com.practice.ecommerce.dto.CategoryResponseDTO;
import com.practice.ecommerce.service.ICategoryService;
import com.practice.ecommerce.util.AppConstants;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/api/v1/public")
public class CategoryController {

    @Autowired
    private ICategoryService categoryService;

    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private MyStringKafkaProducer kafkaProducer;

    /*Constructor injection
    public CategoryController(ICategoryService categoryService) {
        this.categoryService = categoryService;
    }*/

    @GetMapping("/categories")
    public ResponseEntity<CategoryResponseDTO> getAllCategories(@RequestParam(name="pageNum", defaultValue = AppConstants.PAGE_NUM, required = false) Integer pageNum,
                                                                @RequestParam(value = "pageSize", defaultValue = AppConstants.PAGE_SIZE, required = false) Integer pageSize,
                                                                @RequestParam(value = "sortBy", defaultValue = AppConstants.SORT_BY, required = false) String sortBy,
                                                                @RequestParam(value = "sortOrder", defaultValue = AppConstants.SORT_ORDER, required = false) String sortOrder){
        return new ResponseEntity<>(categoryService.getAllCategories(pageSize,pageNum,sortBy,sortOrder), HttpStatus.OK);
    }

    @PostMapping("/category")
    public ResponseEntity<CategoryRequestDTO> addCategory(@Valid @RequestBody CategoryRequestDTO category){
        CategoryRequestDTO requestDTO = categoryService.addCategory(category);
        kafkaProducer.sendCategoryInfo(requestDTO.getCategoryId()+"_"+requestDTO.getCategoryName());
        return new ResponseEntity<>(requestDTO,HttpStatus.CREATED);
    }

    @DeleteMapping("/category/{id}")
    public ResponseEntity<CategoryRequestDTO> deleteCategory(@PathVariable int id){
        CategoryRequestDTO deletedCategory =  categoryService.deleteCategory(id);
        return new ResponseEntity<>(deletedCategory, HttpStatus.OK);
    }

    @PutMapping("/category/{id}")
    public ResponseEntity<CategoryRequestDTO> updateCategory(@Valid @RequestBody CategoryRequestDTO category, @PathVariable int id){
        CategoryRequestDTO updatedCategory = categoryService.updateCategory(id, category);
        return new ResponseEntity<>(updatedCategory, HttpStatus.OK);
    }

}
