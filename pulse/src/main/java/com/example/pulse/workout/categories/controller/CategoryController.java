package com.example.pulse.workout.categories.controller;

import com.example.pulse.Response.GenerateResponse;
import com.example.pulse.Response.Response;
import com.example.pulse.constants.StatusCodes;
import com.example.pulse.workout.categories.entity.Category;
import com.example.pulse.workout.categories.service.CategoryService;
import com.example.pulse.workout.day.controller.DayController;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/category")
@AllArgsConstructor
public class CategoryController {
    CategoryService categoryService;
    GenerateResponse generateResponse;
    static final Logger logger = LoggerFactory.getLogger(CategoryController.class);

    @GetMapping("/{id}")
    public ResponseEntity<Response<Category>> findCategory(@PathVariable int id){
      Category category = categoryService.findById(id);
      return generateResponse.formatResponse(StatusCodes.CATEGORY_FETCHED_SUCCESSFULLY,StatusCodes.SUCCESS,category, HttpStatus.ACCEPTED);
    }

    @GetMapping
    public ResponseEntity<Response<List<Category>>>findAllCategories(){
        List<Category> categories = categoryService.findAllCategories();
        return generateResponse.formatResponse(StatusCodes.CATEGORY_FETCHED_SUCCESSFULLY,StatusCodes.SUCCESS,categories, HttpStatus.ACCEPTED);
    }

    @PostMapping
    public ResponseEntity<Response<Category>>addCategory(@RequestBody Category category){
        category = categoryService.add(category);
        return generateResponse.formatResponse(StatusCodes.CATEGORY_ADDED_SUCCESSFULLY,StatusCodes.SUCCESS,category, HttpStatus.ACCEPTED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Response<Category>>updateCategory(@PathVariable int id,@RequestBody Category category){
        return generateResponse.formatResponse(StatusCodes.CATEGORY_UPDATED_SUCCESSFULLY,StatusCodes.SUCCESS,category, HttpStatus.ACCEPTED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Response<Boolean>>deleteCategory(@PathVariable int id){
        boolean isCategoryDeleted = false;
        isCategoryDeleted = categoryService.deleteById(id);
        return generateResponse.formatResponse(StatusCodes.CATEGORY_DELETED_SUCCESSFULLY,StatusCodes.SUCCESS,isCategoryDeleted, HttpStatus.ACCEPTED);
    }
}
