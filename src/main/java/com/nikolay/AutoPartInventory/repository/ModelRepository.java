package com.nikolay.AutoPartInventory.repository;

import com.nikolay.AutoPartInventory.entity.Brand;
import com.nikolay.AutoPartInventory.entity.Model;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ModelRepository extends JpaRepository<Model, Integer> {
    Model findById(int id);

    List<Model> findByBrand(Brand brand);

    List<Model> findByRestIdNotIn(int[] restIds);
}
