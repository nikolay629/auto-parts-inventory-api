package com.nikolay.AutoPartInventory.repository;

import com.nikolay.AutoPartInventory.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Integer> {
    Category findById(int id);

}
