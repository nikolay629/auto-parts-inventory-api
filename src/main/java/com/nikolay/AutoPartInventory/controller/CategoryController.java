package com.nikolay.AutoPartInventory.controller;

import com.nikolay.AutoPartInventory.entity.Category;
import com.nikolay.AutoPartInventory.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(CategoryController.BASE_PATH)
public class CategoryController {

    public static final String BASE_PATH = "/category";

    private Category category;
    private List<Category> categoryList;
    private CategoryRepository categoryRepository;

    @Autowired
    public CategoryController(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }


    @PostMapping(path = "/get-all-new")
    public ResponseEntity<List<Category>> getAllNew(
            @RequestBody List<Category> mobileCategoryList
    ) {
        if (mobileCategoryList.size() != 0)  {
            int[] restIds = mobileCategoryList.stream().map(Category::getRestId).mapToInt(Integer::intValue).toArray();
            categoryList = categoryRepository.findByRestIdNotIn(restIds);
        } else {
            categoryList = categoryRepository.findAll();
        }
        return new ResponseEntity<>(categoryList, HttpStatus.OK);
    }

    @PostMapping(path = "/get-with-difference")
    public ResponseEntity<List<Category>> getWithDifference(
            @RequestBody List<Category> mobileCategoryList
    ) {
        List<Category> differenceCategoryList = new ArrayList<>();
        for (Category mobileCategory: mobileCategoryList) {
            category = categoryRepository.findById(mobileCategory.getRestId());
            if (!category.getName().equals(mobileCategory.getName())) {
                category.setId(mobileCategory.getId());
                differenceCategoryList.add(category);
            }
        }

        return new ResponseEntity<>(differenceCategoryList, HttpStatus.OK);
    }

    @PostMapping(path = "/check-for-difference")
    public ResponseEntity<Boolean> checkForDifference(
            @RequestBody List<Category> mobileCategoryList
    ) {
        if(mobileCategoryList.size() != categoryRepository.findAll().size()) {
            return new ResponseEntity<>(true, HttpStatus.OK);
        }

        boolean hasDifference = false;
        for (Category mobileCategory: mobileCategoryList) {
            category = categoryRepository.findById(mobileCategory.getRestId());
            if (!category.getName().equals(mobileCategory.getName())) {
                hasDifference = true;
                break;
            }
        }
        return new ResponseEntity<>(hasDifference, HttpStatus.OK);
    }

    @PostMapping(path = "/add-list")
    public ResponseEntity<List<Category>> addList(
            @RequestBody List<Category> mobileCategoryList
    ) {
        if (mobileCategoryList.size() != 0) {
            categoryRepository.saveAllAndFlush(mobileCategoryList);
        }
        return new ResponseEntity<>(mobileCategoryList, HttpStatus.OK);
    }

    @PostMapping(path = "/update")
    public ResponseEntity<Boolean> update(
            @RequestBody List<Category> mobileCategoryList
    ) {
        if(mobileCategoryList.size() == 0) {
            return new ResponseEntity<>(true, HttpStatus.OK);
        }

        for (Category mobileCategory: mobileCategoryList) {
            category = categoryRepository.findById(mobileCategory.getRestId());
            if (!category.getName().equals(mobileCategory.getName())) {
                categoryRepository.saveAndFlush(mobileCategory);
            }
        }
        return new ResponseEntity<>(true, HttpStatus.OK);
    }
}
