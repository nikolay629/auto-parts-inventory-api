package com.nikolay.AutoPartInventory.controller;

import com.nikolay.AutoPartInventory.entity.Model;
import com.nikolay.AutoPartInventory.entity.Part;
import com.nikolay.AutoPartInventory.repository.PartRepository;
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
@RequestMapping(PartController.BASE_PATH)
public class PartController {
    public static final String BASE_PATH = "/part";

    private Part part;
    private List<Part> partList;
    private PartRepository partRepository;

    @Autowired
    public PartController(PartRepository partRepository) {
        this.partRepository = partRepository;
    }

    @PostMapping(path = "/get-with-difference")
    public ResponseEntity<List<Part>> getWithDifference(
            @RequestBody List<Part> mobilePartList
    ) {
        List<Part> differencePartList = new ArrayList<>();
        for (Part mobilePart: mobilePartList) {
            part = partRepository.findById(mobilePart.getRestId());
            if (
                !part.getName().equals(mobilePart.getName())
                || part.getQty() != mobilePart.getQty()
                || part.getPrice() != mobilePart.getPrice()
                || part.getModel().getRestId() != mobilePart.getModel().getRestId()
                || part.getCategory().getRestId() != mobilePart.getCategory().getRestId()
            ) {
                differencePartList.add(part);
            }
        }

        return new ResponseEntity<>(differencePartList, HttpStatus.OK);
    }

    @PostMapping(path = "/check-for-difference")
    public ResponseEntity<Boolean> checkForDifference(
            @RequestBody List<Part> mobilePartList
    ) {
        if(mobilePartList.size() != partRepository.findAll().size()) {
            return new ResponseEntity<>(true, HttpStatus.OK);
        }

        boolean hasDifference = false;
        for (Part mobilePart: mobilePartList) {
            part = partRepository.findById(mobilePart.getRestId());
            if (
                !part.getName().equals(mobilePart.getName())
                || part.getQty() != mobilePart.getQty()
                || part.getPrice() != mobilePart.getPrice()
                || part.getModel().getRestId() != mobilePart.getModel().getRestId()
                || part.getCategory().getRestId() != mobilePart.getCategory().getRestId()
            ) {
                hasDifference = true;
                break;
            }
        }
        return new ResponseEntity<>(hasDifference, HttpStatus.OK);
    }

    @PostMapping(path = "/add-list")
    public ResponseEntity<List<Part>> addList(
            @RequestBody List<Part> mobilePartList
    ) {
        if (mobilePartList.size() != 0) {
            partRepository.saveAllAndFlush(mobilePartList);
        }
        return new ResponseEntity<>(mobilePartList, HttpStatus.OK);
    }

    @PostMapping(path = "/update")
    public ResponseEntity<Boolean> update(
            @RequestBody List<Part> mobilePartList
    ) {
        if(mobilePartList.size() != partRepository.findAll().size()) {
            return new ResponseEntity<>(true, HttpStatus.OK);
        }

        for (Part mobilePart: mobilePartList) {
            part = partRepository.findById(mobilePart.getRestId());
            if (
                !part.getName().equals(mobilePart.getName())
                || part.getQty() != mobilePart.getQty()
                || part.getPrice() != mobilePart.getPrice()
                || part.getModel().getRestId() != mobilePart.getModel().getRestId()
                || part.getCategory().getRestId() != mobilePart.getCategory().getRestId()
            ) {
                partRepository.saveAndFlush(mobilePart);
            }
        }
        return new ResponseEntity<>(true, HttpStatus.OK);
    }
}
