package com.nikolay.AutoPartInventory.controller;


import com.nikolay.AutoPartInventory.entity.Brand;
import com.nikolay.AutoPartInventory.repository.BrandRepository;
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
@RequestMapping(BrandController.BASE_PATH)
public class BrandController {
    public static final String BASE_PATH = "/brand";

    private Brand brand;
    private List<Brand> brandList;
    private BrandRepository brandRepository;

    @Autowired
    public BrandController(BrandRepository brandRepository) {
        this.brandRepository = brandRepository;
    }

    @PostMapping(path = "/get-with-difference")
    public ResponseEntity<List<Brand>> getWithDifference(
        @RequestBody List<Brand> mobileBrandList
    ) {
        List<Brand> differenceBrandList = new ArrayList<>();
        for (Brand mobileBrand: mobileBrandList) {
           brand = brandRepository.findById(mobileBrand.getRestId());
           if (!brand.getName().equals(mobileBrand.getName())) {
               differenceBrandList.add(brand);
           }
        }

        return new ResponseEntity<>(differenceBrandList, HttpStatus.OK);
    }

    @PostMapping(path = "/check-for-difference")
    public ResponseEntity<Boolean> checkForDifference(
        @RequestBody List<Brand> mobileBrandList
    ) {
        if(mobileBrandList.size() != brandRepository.findAll().size()) {
            return new ResponseEntity<>(true, HttpStatus.OK);
        }

        boolean hasDifference = false;
        for (Brand mobileBrand: mobileBrandList) {
            brand = brandRepository.findById(mobileBrand.getRestId());
            if (!brand.getName().equals(mobileBrand.getName())) {
                hasDifference = true;
                break;
            }
        }
        return new ResponseEntity<>(hasDifference, HttpStatus.OK);
    }

    @PostMapping(path = "/add-list")
    public ResponseEntity<List<Brand>> addList(
            @RequestBody List<Brand> mobileBrandList
    ) {
        if (mobileBrandList.size() != 0) {
            brandRepository.saveAllAndFlush(mobileBrandList);
        }
        return new ResponseEntity<>(mobileBrandList, HttpStatus.OK);
    }


    @PostMapping(path = "/update")
    public ResponseEntity<Boolean> update(
            @RequestBody List<Brand> mobileBrandList
    ) {
        if(mobileBrandList.size() != brandRepository.findAll().size()) {
            return new ResponseEntity<>(true, HttpStatus.OK);
        }

        for (Brand mobileBrand: mobileBrandList) {
            brand = brandRepository.findById(mobileBrand.getRestId());
            if (!brand.getName().equals(mobileBrand.getName())) {
                brandRepository.saveAndFlush(mobileBrand);
            }
        }
        return new ResponseEntity<>(true, HttpStatus.OK);
    }
}
