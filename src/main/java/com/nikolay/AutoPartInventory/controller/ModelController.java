package com.nikolay.AutoPartInventory.controller;

import com.nikolay.AutoPartInventory.entity.Model;
import com.nikolay.AutoPartInventory.repository.ModelRepository;
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
@RequestMapping(ModelController.BASE_PATH)
public class ModelController {
    public static final String BASE_PATH = "/model";

    private Model model;
    private List<Model> modelList;
    private ModelRepository modelRepository;

    @Autowired
    public ModelController(ModelRepository modelRepository) {
        this.modelRepository = modelRepository;
    }

    @PostMapping(path = "/get-all-new")
    public ResponseEntity<List<Model>> getAllNew(
            @RequestBody List<Model> mobileModelList
    ) {
        if (mobileModelList.size() != 0)  {
            int[] restIds = mobileModelList.stream().map(Model::getRestId).mapToInt(Integer::intValue).toArray();
            modelList = modelRepository.findByRestIdNotIn(restIds);
        } else {
            modelList = modelRepository.findAll();
        }
        return new ResponseEntity<>(modelList, HttpStatus.OK);
    }

    @PostMapping(path = "/get-with-difference")
    public ResponseEntity<List<Model>> getWithDifference(
            @RequestBody List<Model> mobileModelList
    ) {
        List<Model> differenceModelList = new ArrayList<>();
        for (Model mobileModel: mobileModelList) {
            model = modelRepository.findById(mobileModel.getRestId());
            if (
                !model.getName().equals(mobileModel.getName())
                || model.getBrand().getRestId() != mobileModel.getBrand().getRestId()
            ) {
                model.setId(mobileModel.getId());
                differenceModelList.add(model);
            }
        }

        return new ResponseEntity<>(differenceModelList, HttpStatus.OK);
    }

    @PostMapping(path = "/check-for-difference")
    public ResponseEntity<Boolean> checkForDifference(
            @RequestBody List<Model> mobileModelList
    ) {
        if(mobileModelList.size() != modelRepository.findAll().size()) {
            return new ResponseEntity<>(true, HttpStatus.OK);
        }

        boolean hasDifference = false;
        for (Model mobileModel: mobileModelList) {
            model = modelRepository.findById(mobileModel.getRestId());
            if (
                !model.getName().equals(mobileModel.getName())
                || model.getBrand().getRestId() != mobileModel.getBrand().getRestId()
            ) {
                hasDifference = true;
                break;
            }
        }
        return new ResponseEntity<>(hasDifference, HttpStatus.OK);
    }

    @PostMapping(path = "/add-list")
    public ResponseEntity<List<Model>> addList(
            @RequestBody List<Model> mobileModelList
    ) {
        if (mobileModelList.size() != 0) {
            modelRepository.saveAllAndFlush(mobileModelList);
        }
        return new ResponseEntity<>(mobileModelList, HttpStatus.OK);
    }

    @PostMapping(path = "/update")
    public ResponseEntity<Boolean> update(
            @RequestBody List<Model> mobileModelList
    ) {
        if(mobileModelList.size()  == 0) {
            return new ResponseEntity<>(true, HttpStatus.OK);
        }

        for (Model mobileModel: mobileModelList) {
            model = modelRepository.findById(mobileModel.getRestId());
            if (
                model != null && (
                    !model.getName().equals(mobileModel.getName())
                    || model.getBrand().getRestId() != mobileModel.getBrand().getRestId()
                )
            ) {
                modelRepository.saveAndFlush(mobileModel);
            }
        }
        return new ResponseEntity<>(true, HttpStatus.OK);
    }

}
