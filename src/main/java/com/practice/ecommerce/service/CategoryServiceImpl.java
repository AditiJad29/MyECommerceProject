package com.practice.ecommerce.service;

import com.practice.ecommerce.dto.CategoryRequestDTO;
import com.practice.ecommerce.dto.CategoryResponseDTO;
import com.practice.ecommerce.models.Category;
import com.practice.ecommerce.exceptions.APIException;
import com.practice.ecommerce.exceptions.ResourceNotFoundException;
import com.practice.ecommerce.repository.ICategoryRepository;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryServiceImpl implements ICategoryService{

    @Autowired
    private ICategoryRepository categoryRepository;

    @Autowired
    private ModelMapper modelMapper;

 /*   @Autowired
    CategoryResponseDTO responseDTO;*/

    /*private List<Category> orderCategories = new ArrayList<>();
    private int nextID = 1;*/

    @Transactional
    @Override
    public CategoryRequestDTO addCategory(CategoryRequestDTO categoryRequestDTO) {
/*        category.setCategoryID(nextID++);
        orderCategories.add(category);*/
        Category existingCategory = categoryRepository.findByCategoryName(categoryRequestDTO.getCategoryName());
        if(existingCategory!=null){
            throw new APIException("Category with name : " + categoryRequestDTO.getCategoryName() + " already exists! Use a different name.");
        }
        Category newCategory = categoryRepository.save(modelMapper.map(categoryRequestDTO,Category.class));
        CategoryRequestDTO requestDTO = modelMapper.map(newCategory,CategoryRequestDTO.class);
        return requestDTO;
    }

    @Override
    public CategoryRequestDTO updateCategory(int categoryId, CategoryRequestDTO dtoCategory) {
        Category toUpdate =  categoryRepository.findById(categoryId).orElseThrow(()->new ResourceNotFoundException("Category", "categoryId", categoryId));
        toUpdate.setCategoryName(dtoCategory.getCategoryName());
        Category updatedEntity = categoryRepository.save(toUpdate);
        return modelMapper.map(updatedEntity,CategoryRequestDTO.class);

       /* Category old = orderCategories.stream().filter(c->c.getCategoryID()==categoryId).findFirst().orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND,"Cateogry not found!"));
        if(old != null) {
            old.setCategoryName(category.getCategoryName());
           }
        System.out.println(orderCategories);
        return old;*/
    }

    @Override
    public CategoryRequestDTO deleteCategory(int categoryID)  {
        Category toDelete =  categoryRepository.findById(categoryID).orElseThrow(()->new ResourceNotFoundException("Category", "categoryId", categoryID));
        categoryRepository.delete(toDelete);
        return modelMapper.map(toDelete,CategoryRequestDTO.class);
/*        Category foundCategory = orderCategories.stream().filter(c -> c.getCategoryID() == categoryID).findFirst().orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,"Category with id: " + categoryID + " does not exist!"));
        orderCategories.remove(foundCategory);*/
    }

    @Override
    public CategoryResponseDTO getAllCategories(int pageSize, int pageNum, String sortBy, String sortOrder) {
        //return orderCategories;

        Sort sortByAndOrder = sortOrder.equalsIgnoreCase("asc")? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
        Pageable pageable = PageRequest.of(pageNum,pageSize,sortByAndOrder);
        Page<Category> pagedDbCategories = categoryRepository.findAll(pageable);
        if(pagedDbCategories.isEmpty()){
            throw new APIException("No categories present yet!");
        }

        List<CategoryRequestDTO> dtoCategories = pagedDbCategories.getContent().stream()
                .map(dbCategory -> modelMapper.map(dbCategory, CategoryRequestDTO.class)).toList();

        CategoryResponseDTO responseDTO = new CategoryResponseDTO();
        responseDTO.setCategories(dtoCategories);
        responseDTO.setLastPage(pagedDbCategories.isLast());
        responseDTO.setPageNumber(pagedDbCategories.getNumber());
        responseDTO.setPageSize(pagedDbCategories.getSize());
        responseDTO.setTotalElements(pagedDbCategories.getTotalElements());
        responseDTO.setTotalPages(pagedDbCategories.getTotalPages());

        return responseDTO;
    }
}
