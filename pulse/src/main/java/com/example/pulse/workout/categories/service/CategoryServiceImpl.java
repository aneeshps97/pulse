package com.example.pulse.workout.categories.service;

import com.example.pulse.constants.StatusCodes;
import com.example.pulse.exception.PulseException;
import com.example.pulse.workout.categories.entity.Category;
import com.example.pulse.workout.categories.repository.CategoryRepository;
import com.example.pulse.workout.exercise.entity.Exercise;
import com.example.pulse.workout.exercise.repository.ExerciseRepository;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Service
@AllArgsConstructor
public class CategoryServiceImpl implements CategoryService{
CategoryRepository categoryRepository;
ExerciseRepository exerciseRepository;
static final Logger logger = LoggerFactory.getLogger(CategoryServiceImpl.class);
    @Override
    public Category findById(int categoryId) throws PulseException {
        Category category =null;
        try {
              category = categoryRepository.findById(categoryId).orElseThrow(()->new PulseException(StatusCodes.CATEGORY_FETCHING_FAILED));
              logger.debug("Category received from repository::{}",category);
        }catch (DataAccessException e){
            logger.error("Category finding failed cause::",e.getCause());
            throw new PulseException(StatusCodes.CATEGORY_FETCHING_FAILED);
        }
        return category;
    }

    @Override
    public List<Category> findAllCategories() throws PulseException {
        List<Category> categories = new ArrayList<>();
        try {
            categories = categoryRepository.findAll();
            logger.debug("Categories received from repository::{}",categories.toString());
        }catch (DataAccessException e){
            logger.error("Category finding failed cause::",e.getCause());
            throw new PulseException(StatusCodes.CATEGORY_FETCHING_FAILED);
        }
        return categories;
    }

    @Override
    public Category add(Category category) throws PulseException {
        try {
            logger.info("Category data ::{}",category.toString());
            List<Exercise> exercises = category.getExercises();
            List<Exercise> existingExercises = new ArrayList<>();
            if (exercises != null && !exercises.isEmpty()) {
                for (Exercise exercise : exercises) {
                    if (exercise.getId() > 0) {
                        Exercise existingExercise = exerciseRepository.findById(exercise.getId()).orElseThrow(() -> new PulseException(StatusCodes.EXERCISE_FETCHING_FAILED));
                        Category existingCategory = existingExercise.getCategory();
                        if (existingCategory != null && existingCategory.getId() > 0) {
                            logger.error("Existing exerciseId::{} is already assigned for category id::{}",exercise.getId(),existingCategory.getId());
                            throw new PulseException(StatusCodes.EXERCISE_ALREADY_ASSIGNED_FOR_ANOTHER_CATEGORY);
                        } else {
                            existingExercise.setCategory(category);
                            existingExercises.add(existingExercise);
                        }
                    } else {
                        exercise.setCategory(category);
                        existingExercises.add(exercise);
                    }
                }
            }

            category.getExercises().clear();
            category.setExercises(existingExercises);
            category = categoryRepository.save(category);

        } catch (DataAccessException e ) {
            throw new PulseException(StatusCodes.CATEGORY_ADDING_FAILED);
        }
        return category;
    }

    @Override
    public Category update(int id, Category category) throws PulseException {
        logger.info("Category data received ::{}",category);
        Category existingCategory = null;
        List<Exercise> existingExercises = new ArrayList<>();
        List<Exercise> exercises = null;
        try {
            exercises = category.getExercises();
            existingCategory = categoryRepository.findById(id).orElseThrow(()-> new PulseException(StatusCodes.CATEGORY_FETCHING_FAILED));
            logger.debug("Existing category received ::{}",existingCategory.toString());
            if (exercises!=null && !exercises.isEmpty()){
                for (Exercise exercise : exercises){
                    if (exercise.getId()>0){
                        Exercise exisingExercise = exerciseRepository.findById(exercise.getId()).orElseThrow(()->new PulseException(StatusCodes.EXERCISE_FETCHING_FAILED));
                        logger.debug("Existing exercise received ::{}",exisingExercise.toString());
                        exisingExercise.setCategory(existingCategory);
                        existingExercises.add(exisingExercise);
                    }else {
                        exercise.setCategory(existingCategory);
                        existingExercises.add(exercise);
                    }
                }
            }
            existingCategory.getExercises().clear();
            existingCategory.setExercises(existingExercises);

        }catch (DataAccessException e){
            logger.error("Category update failed ::",e.getCause());
            throw new PulseException(StatusCodes.CATEGORY_UPDATING_FAILED);
        }
        return existingCategory;
    }

    @Override
    public boolean deleteById(int id) throws PulseException {
        boolean isCategoryDeleted = false;
        try {
            Category category = categoryRepository.findById(id).orElseThrow(()->new PulseException(StatusCodes.CATEGORY_FETCHING_FAILED));
            List<Exercise> exercises = category.getExercises();
            logger.debug("Exercises received ::{}",exercises.toString());
            for (Exercise exercise: exercises){
                exercise.setCategory(null);
            }
            category.setExercises(null);
            categoryRepository.delete(category);
            isCategoryDeleted = true;
        }catch (DataAccessException e){
            logger.error("category deletion failed ::",e.getCause());
            throw new PulseException(StatusCodes.CATEGORY_DELETION_FAILED);
        }
        return isCategoryDeleted;
    }
}
