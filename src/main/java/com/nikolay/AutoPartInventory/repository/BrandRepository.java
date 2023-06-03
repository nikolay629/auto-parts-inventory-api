package com.nikolay.AutoPartInventory.repository;

import com.nikolay.AutoPartInventory.entity.Brand;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BrandRepository extends JpaRepository<Brand, Integer> {
    Brand findById(int id);
}
