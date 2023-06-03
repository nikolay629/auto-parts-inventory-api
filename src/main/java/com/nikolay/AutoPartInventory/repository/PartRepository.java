package com.nikolay.AutoPartInventory.repository;

import com.nikolay.AutoPartInventory.entity.Part;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PartRepository extends JpaRepository<Part, Integer> {

    Part findById(int id);

    List<Part> findByRestIdNotIn(int[] restIds);
}
